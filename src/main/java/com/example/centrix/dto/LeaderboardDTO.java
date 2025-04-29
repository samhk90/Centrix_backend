package com.example.centrix.dto;

import lombok.Data;

@Data
public class LeaderboardDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private String email;
    private Integer flag;
    private String location;
    private String chapterName;
}
