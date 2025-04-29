package com.example.centrix.mapper;

import com.example.centrix.dto.*;
import com.example.centrix.entity.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CourseMapper {
    
    public CourseDTO toDto(Course course) {
        if (course == null) return null;

        CourseDTO dto = new CourseDTO();
        dto.setCourseId(course.getCourseId());
        dto.setTitle(course.getTitle());
        dto.setDescription(course.getDescription());
        dto.setDuration(course.getDuration());
        dto.setNos(course.getNos());
        dto.setChapter(toChapterDto(course.getChapter()));
        dto.setTrainer(toTrainerDto(course.getTrainer()));
        dto.setSections(toSectionDtoList(course.getSections()));
        return dto;
    }

    public List<CourseDTO> toCourseDtoList(List<Course> courses) {
        return courses.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private ChapterDTO toChapterDto(Chapter chapter) {
        if (chapter == null) return null;

        ChapterDTO dto = new ChapterDTO();
        dto.setId(chapter.getId());
        dto.setName(chapter.getName());
        return dto;
    }

    private TrainerDTO toTrainerDto(Trainer trainer) {
        if (trainer == null) return null;

        TrainerDTO dto = new TrainerDTO();
        dto.setTid(trainer.getTid());
        dto.setTfirstName(trainer.getTfirstName());
        dto.setTlastName(trainer.getTlastName());
        dto.setTemail(trainer.getTemail());
        dto.setSpecialization(trainer.getSpecialization());
        return dto;
    }

    private SectionDTO toSectionDto(Sections section) {
        if (section == null) return null;

        SectionDTO dto = new SectionDTO();
        dto.setSectionId(section.getSectionId());
        dto.setSectionTitle(section.getSectionTitle());
        dto.setNumberOfTopics(section.getNumberOfTopics());
        dto.setTopics(toTopicDtoList(section.getTopics()));
        return dto;
    }

    private List<SectionDTO> toSectionDtoList(List<Sections> sections) {
        if (sections == null) return null;
        return sections.stream()
                .map(this::toSectionDto)
                .collect(Collectors.toList());
    }

    private TopicDTO toTopicDto(Topics topic) {
        if (topic == null) return null;

        TopicDTO dto = new TopicDTO();
        dto.setTopicId(topic.getTopicId());
        dto.setTopicTitle(topic.getTopicTitle());
        dto.setNoa(topic.getNoa());
        dto.setArtifacts(toArtifactDtoList(topic.getArtifacts()));
        return dto;
    }

    private List<TopicDTO> toTopicDtoList(List<Topics> topics) {
        if (topics == null) return null;
        return topics.stream()
                .map(this::toTopicDto)
                .collect(Collectors.toList());
    }

    private ArtifactDTO toArtifactDto(Artifacts artifact) {
        if (artifact == null) return null;

        ArtifactDTO dto = new ArtifactDTO();
        dto.setArtifactId(artifact.getArtifactId());
        dto.setArtifactTitle(artifact.getArtifactTitle());
        dto.setArtifactLink(artifact.getArtifactLink());
        dto.setIsVideo(artifact.getIsVideo());
        return dto;
    }

    private List<ArtifactDTO> toArtifactDtoList(List<Artifacts> artifacts) {
        if (artifacts == null) return null;
        return artifacts.stream()
                .map(this::toArtifactDto)
                .collect(Collectors.toList());
    }
}