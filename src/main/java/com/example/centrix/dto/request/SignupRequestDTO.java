package com.example.centrix.dto.request;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private String firstName;
    private String lastName;
    private String employeeId;
    private String email;
    private String password;
    private String location;
    private Integer roleId;
    private Integer chapterId;
}