package com.example.centrix.dto;

import lombok.Data;

@Data
public class ArtifactDTO {
    private Integer artifactId;
    private String artifactTitle;
    private String artifactLink;
    private Boolean isVideo;
}