package com.nitish.flappy;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Keeps the global high score in memory (resets when the server restarts). */
@RestController
@RequestMapping("/api/highscore")
public class ScoreController {

    private final AtomicInteger highScore = new AtomicInteger(0);

    @GetMapping
    public Map<String, Integer> get() {
        return Map.of("highScore", highScore.get());
    }

    @PostMapping
    public Map<String, Integer> submit(@RequestBody Map<String, Integer> body) {
        int score = body.getOrDefault("score", 0);
        highScore.accumulateAndGet(score, Math::max);
        return Map.of("highScore", highScore.get());
    }
}
