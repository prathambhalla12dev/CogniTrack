package com.cognitrack.controller;

import com.cognitrack.model.*;
import com.cognitrack.repository.ProfileRepository;
import com.cognitrack.service.MlService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MetricsController {

    private final MongoTemplate mongoTemplate;
    private final ProfileRepository profileRepository;
    private final MlService mlService;

    @GetMapping("/")
    public java.util.Map<String, String> root() {
        return java.util.Map.of("message", "Welcome to the CogniTrack Backend API");
    }

    @PostMapping("/api/metrics")
    public CognitiveProfile submitMetrics(@RequestBody GameSessionMetrics metrics) {
        mongoTemplate.insert(metrics, "metrics");
        CognitiveProfile existing = profileRepository.findTopByChildIdOrderByGeneratedAtDesc(metrics.getChildId()).orElse(null);
        CognitiveProfile profile = mlService.analyzeCognitiveMetrics(metrics, existing);
        return profileRepository.save(profile);
    }

    @PostMapping("/api/metrics/memory")
    public CognitiveProfile submitMemoryMetrics(@RequestBody MemorySessionMetrics metrics) {
        mongoTemplate.insert(metrics, "metrics");
        CognitiveProfile existing = profileRepository.findTopByChildIdOrderByGeneratedAtDesc(metrics.getChildId()).orElse(null);
        CognitiveProfile profile = mlService.analyzeMemoryMetrics(metrics, existing);
        return profileRepository.save(profile);
    }

    @PostMapping("/api/metrics/flexibility")
    public CognitiveProfile submitFlexibilityMetrics(@RequestBody FlexibilitySessionMetrics metrics) {
        mongoTemplate.insert(metrics, "metrics");
        CognitiveProfile existing = profileRepository.findTopByChildIdOrderByGeneratedAtDesc(metrics.getChildId()).orElse(null);
        CognitiveProfile profile = mlService.analyzeFlexibilityMetrics(metrics, existing);
        return profileRepository.save(profile);
    }

    @PostMapping("/api/metrics/speed")
    public CognitiveProfile submitSpeedMetrics(@RequestBody SpeedSessionMetrics metrics) {
        mongoTemplate.insert(metrics, "metrics");
        CognitiveProfile existing = profileRepository.findTopByChildIdOrderByGeneratedAtDesc(metrics.getChildId()).orElse(null);
        CognitiveProfile profile = mlService.analyzeSpeedMetrics(metrics, existing);
        return profileRepository.save(profile);
    }
}