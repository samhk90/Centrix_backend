package com.example.centrix.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer uid;
    private String firstName;
    private String lastName;
    private String employeeid;
    private String email;
    private int flag;
    private int completed;
    private  int assigned;
    private  int inprogress;
    private RoleDTO role;
    private ChapterDTO chapter;
}