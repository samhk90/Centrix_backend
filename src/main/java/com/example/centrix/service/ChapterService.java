package com.example.centrix.service;

import com.example.centrix.dto.ChapterDTO;
import com.example.centrix.entity.Chapter;
import com.example.centrix.repository.ChapterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChapterService {
    
    @Autowired
    private ChapterRepository chapterRepository;

    @Transactional(readOnly = true)
    public List<ChapterDTO> getAllChapters() {
        return chapterRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ChapterDTO getChapterById(Integer id) {
        return chapterRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new IllegalArgumentException("Chapter not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public ChapterDTO getChapterByName(String name) {
        Chapter chapter = chapterRepository.findByName(name);
        return chapter != null ? convertToDTO(chapter) : null;
    }

    private ChapterDTO convertToDTO(Chapter chapter) {
        ChapterDTO dto = new ChapterDTO();
        dto.setId(chapter.getId());
        dto.setName(chapter.getName());
        return dto;
    }
}