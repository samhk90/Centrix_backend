package com.example.centrix.service;

import com.example.centrix.dto.UserDTO;
import com.example.centrix.dto.request.AuthRequestDTO;
import com.example.centrix.dto.request.SignupRequestDTO;
import com.example.centrix.dto.response.JwtAuthResponse;
import com.example.centrix.entity.Chapter;
import com.example.centrix.entity.Role;
import com.example.centrix.entity.User;
import com.example.centrix.mapper.UserMapper;
import com.example.centrix.repository.UserRepository;
import com.example.centrix.repository.RoleRepository;
import com.example.centrix.repository.ChapterRepository;
import com.example.centrix.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ChapterRepository chapterRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public JwtAuthResponse login(AuthRequestDTO request) {
        // Validate request
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Email and password are required");
        }

        try {
            // Find user by email
            User user = userRepository.findByEmail(request.getEmail());
            if (user == null) {
                logger.error("User not found: {}", request.getEmail());
                throw new IllegalArgumentException("User not found");
            }

            // Direct password comparison
            if (!request.getPassword().equals(user.getPassword())) {
                logger.error("Invalid password for email: {}", request.getEmail());
                throw new IllegalArgumentException("Invalid email or password");
            }

            // Generate JWT token
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password("{noop}" + user.getPassword())
                .authorities("ROLE_" + user.getRole().getName())
                .build();

            String token = jwtTokenUtil.generateToken(userDetails);
            UserDTO userDTO = userMapper.toDto(user);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(token);
            response.setUser(userDTO);
            
            logger.debug("Authentication successful for user: {}", user.getEmail());
            return response;

        } catch (Exception e) {
            logger.error("Authentication failed for email: {}", request.getEmail(), e);
            throw new IllegalArgumentException("Authentication failed: " + e.getMessage());
        }
    }

    public JwtAuthResponse register(SignupRequestDTO request) {
        // Validate request
        if (request == null || request.getEmail() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Email and password are required");
        }

        // Check if email already exists
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Create new user with plain text password
        User user = new User();
        user.setEmployeeid(request.getEmployeeId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Store password as plain text
        user.setLocation(request.getLocation());
        
        // Set default values
        user.setFlag(0);
        user.setInprogress(0);
        user.setCompleted(0);
        user.setAssigned(0);
        user.setAssessmentnumber(0);

        // Set role
        Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid role ID"));
        user.setRole(role);

        // Set chapter
        Chapter chapter = chapterRepository.findById(request.getChapterId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid chapter ID"));
        user.setChapter(chapter);

        try {
            // Save user
            User savedUser = userRepository.save(user);
            UserDTO userDTO = userMapper.toDto(savedUser);

            // Generate JWT token
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(savedUser.getEmail())
                .password("{noop}" + savedUser.getPassword())
                .authorities("ROLE_" + role.getName())
                .build();

            String token = jwtTokenUtil.generateToken(userDetails);

            JwtAuthResponse response = new JwtAuthResponse();
            response.setToken(token);
            response.setUser(userDTO);
            return response;
            
        } catch (Exception e) {
            logger.error("Registration failed for email: {}", request.getEmail(), e);
            throw new IllegalArgumentException("Registration failed: " + e.getMessage());
        }
    }

    public UserDTO userInfo(Integer userId) {
        if (userId == null) {
            return null;
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return userMapper.toDto(user);
    }
}
