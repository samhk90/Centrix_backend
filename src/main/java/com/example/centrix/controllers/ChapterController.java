package com.example.centrix.controllers;

import com.example.centrix.dto.ChapterDTO;
import com.example.centrix.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/chapters")
public class ChapterController {
    
    @Autowired
    private ChapterService chapterService;

    @GetMapping
    public ResponseEntity<List<ChapterDTO>> getAllChapters() {
        return ResponseEntity.ok(chapterService.getAllChapters());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChapterDTO> getChapterById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok(chapterService.getChapterById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<ChapterDTO> getChapterByName(@PathVariable String name) {
        ChapterDTO chapter = chapterService.getChapterByName(name);
        return chapter != null ? ResponseEntity.ok(chapter) : ResponseEntity.notFound().build();
    }
}