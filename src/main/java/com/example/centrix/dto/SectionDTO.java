package com.example.centrix.dto;

import lombok.Data;
import java.util.List;

@Data
public class SectionDTO {
    private Integer sectionId;
    private String sectionTitle;
    private Integer numberOfTopics;
    private List<TopicDTO> topics;
}