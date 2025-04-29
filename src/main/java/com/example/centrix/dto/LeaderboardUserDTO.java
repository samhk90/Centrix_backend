package com.example.centrix.dto;

import com.example.centrix.entity.Chapter;
import lombok.Data;

@Data
public class LeaderboardUserDTO {
    private Integer userId;
    private String firstName;
    private String lastName;
    private int flag;
    private int completed;
    private int assigned;
    private int Assessmentnumber;
    private Chapter chapter;
}
