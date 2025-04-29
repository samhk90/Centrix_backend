package com.example.centrix.service;

import com.example.centrix.dto.LeaderboardDTO;
import com.example.centrix.dto.LeaderboardUserDTO;
import com.example.centrix.dto.UserDTO;
import com.example.centrix.entity.User;
import com.example.centrix.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaderboardService {
    @Autowired
    private UserRepository userRepository;
    public List<LeaderboardDTO> getLeaderboardByChapterId(Integer chapterId) {
        List<User> users = userRepository.findByChapter_Id(chapterId);

        return users.stream()
                .map(user -> {
                    LeaderboardDTO dto = new LeaderboardDTO();
                    dto.setUserId(user.getUid());
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                    dto.setEmail(user.getEmail());
                    dto.setFlag(user.getFlag());
                    dto.setLocation(user.getLocation());
                    dto.setChapterName(user.getChapter() != null ?
                            user.getChapter().getName() : null);
                    return dto;
                })
                .sorted(Comparator
                        .comparing(LeaderboardDTO::getFlag).reversed())
                .collect(Collectors.toList());
    }
    public List<LeaderboardDTO> getLeaderboard() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> {
                    LeaderboardDTO dto = new LeaderboardDTO();
                    dto.setUserId(user.getUid());
                    dto.setFirstName(user.getFirstName());
                    dto.setLastName(user.getLastName());
                    dto.setEmail(user.getEmail());
                    dto.setFlag(user.getFlag());
                    dto.setLocation(user.getLocation());
                    dto.setChapterName(user.getChapter() != null ?
                            user.getChapter().getName() : null);
                    return dto;
                })
                .sorted(Comparator
                        .comparing(LeaderboardDTO::getFlag).reversed())
                .collect(Collectors.toList());
    }
    public LeaderboardUserDTO getLeaderboardUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        LeaderboardUserDTO dto = new LeaderboardUserDTO();
        dto.setFlag(user.getFlag());
        dto.setUserId(user.getUid());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setCompleted(user.getCompleted());
        dto.setAssigned(user.getAssigned());
        dto.setChapter(user.getChapter());
        dto.setAssessmentnumber(user.getAssessmentnumber());

        return dto;
    }
}