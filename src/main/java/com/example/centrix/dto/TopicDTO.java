package com.example.centrix.dto;

import lombok.Data;
import java.util.List;

@Data
public class TopicDTO {
    private Integer topicId;
    private String topicTitle;
    private Integer noa;
    private List<ArtifactDTO> artifacts;
}