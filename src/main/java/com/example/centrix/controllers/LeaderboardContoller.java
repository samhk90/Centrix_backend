package com.example.centrix.controllers;

import com.example.centrix.dto.LeaderboardDTO;
import com.example.centrix.dto.LeaderboardUserDTO;
import com.example.centrix.entity.User;
import com.example.centrix.service.AuthService;
import com.example.centrix.service.LeaderboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin( origins= "*")
@RestController
@RequestMapping("/api")
public class LeaderboardContoller {
    @Autowired
    private LeaderboardService leaderboardService;

    @Autowired
    private AuthService authService;
    @GetMapping("/leaderboard")
    public ResponseEntity<List<LeaderboardDTO>> getAllLeaderboard() {
        return ResponseEntity.ok(leaderboardService.getLeaderboard());
    }
    @GetMapping("/leaderboard/chapter/{chapterId}")
    public ResponseEntity<List<LeaderboardDTO>> getLeaderboardByCourseId(@PathVariable Integer chapterId) {
        if (chapterId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(leaderboardService.getLeaderboardByChapterId(chapterId));
    }
    @GetMapping("/leaderboard/user/{userId}")
    public ResponseEntity<LeaderboardUserDTO> getLeaderboardUser(@PathVariable Integer userId) {
        if (userId == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(leaderboardService.getLeaderboardUser(userId));
    }
}
