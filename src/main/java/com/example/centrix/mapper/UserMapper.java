package com.example.centrix.mapper;

import com.example.centrix.dto.*;
import com.example.centrix.entity.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {

    public UserDTO toDto(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setUid(user.getUid());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmployeeid(user.getEmployeeid());
        dto.setEmail(user.getEmail());
        dto.setFlag(user.getFlag());
        dto.setRole(toRoleDto(user.getRole()));
        dto.setChapter(toChapterDto(user.getChapter()));
        return dto;
    }

    public List<UserDTO> toUserDtoList(List<User> users) {
        return users.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private RoleDTO toRoleDto(Role role) {
        if (role == null) return null;

        RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        return dto;
    }

    private ChapterDTO toChapterDto(Chapter chapter) {
        if (chapter == null) return null;

        ChapterDTO dto = new ChapterDTO();
        dto.setId(chapter.getId());
        dto.setName(chapter.getName());
        return dto;
    }
}