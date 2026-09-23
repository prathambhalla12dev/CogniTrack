package com.cognitrack.service;

import com.cognitrack.model.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MlService {

    public CognitiveProfile analyzeCognitiveMetrics(GameSessionMetrics m, CognitiveProfile existing) {
        String attentionScore;
        if (m.getReactionTimeMs() > 500 && m.getOmissionErrors() > 3) {
            attentionScore = "Low";
        } else if (m.getReactionTimeMs() < 350 && m.getOmissionErrors() <= 1) {
            attentionScore = "High";
        } else {
            attentionScore = "Normal";
        }

        String impulsivityScore;
        if (m.getCommissionErrors() > 3) {
            impulsivityScore = "High";
        } else if (m.getCommissionErrors() == 0) {
            impulsivityScore = "Low";
        } else {
            impulsivityScore = "Normal";
        }

        List<String> recs = existing != null ? new ArrayList<>(existing.getRecommendations()) : new ArrayList<>();
        List<String> newRecs = recs.stream()
                .filter(r -> containsAny(r, "memory", "span", "flexibility", "rule", "speed", "search"))
                .collect(Collectors.toList());

        if ("Low".equals(attentionScore)) {
            newRecs.add("Recommend shorter, more frequent play sessions to build attention stamina.");
        } else if ("High".equals(attentionScore)) {
            newRecs.add("Excellent sustained attention. Introduce more complex visual distillers.");
        }
        if ("High".equals(impulsivityScore)) {
            newRecs.add("High commission error rate. Practice response inhibition through 'Stop-Signal' games.");
        }
        if (newRecs.isEmpty() && (existing == null || (existing.getWorkingMemoryScore() == null
                && existing.getCognitiveFlexibilityScore() == null && existing.getProcessingSpeedScore() == null))) {
            newRecs.add("Healthy cognitive performance detected. Maintain current engagement levels.");
        }

        CognitiveProfile profile = new CognitiveProfile();
        profile.setChildId(m.getChildId());
        profile.setAttentionScore(attentionScore);
        profile.setImpulsivityScore(impulsivityScore);
        profile.setWorkingMemoryScore(existing != null ? existing.getWorkingMemoryScore() : null);
        profile.setCognitiveFlexibilityScore(existing != null ? existing.getCognitiveFlexibilityScore() : null);
        profile.setProcessingSpeedScore(existing != null ? existing.getProcessingSpeedScore() : null);
        profile.setRecommendations(newRecs);
        return profile;
    }

    public CognitiveProfile analyzeMemoryMetrics(MemorySessionMetrics m, CognitiveProfile existing) {
        String workingMemoryScore;
        if (m.getMaxSpan() >= 6) {
            workingMemoryScore = "High";
        } else if (m.getMaxSpan() <= 3) {
            workingMemoryScore = "Low";
        } else {
            workingMemoryScore = "Normal";
        }

        List<String> recs = existing != null ? new ArrayList<>(existing.getRecommendations()) : new ArrayList<>();
        List<String> newRecs = recs.stream()
                .filter(r -> !containsAny(r, "memory", "span"))
                .collect(Collectors.toList());

        if ("Low".equals(workingMemoryScore)) {
            newRecs.add("Visuospatial memory span is below average. Recommend regular block-tapping exercises.");
        } else if ("High".equals(workingMemoryScore)) {
            newRecs.add("Excellent working memory span detected. Introduce dual n-back tasks for further challenge.");
        }

        CognitiveProfile profile = new CognitiveProfile();
        profile.setChildId(m.getChildId());
        profile.setAttentionScore(existing != null ? existing.getAttentionScore() : null);
        profile.setImpulsivityScore(existing != null ? existing.getImpulsivityScore() : null);
        profile.setWorkingMemoryScore(workingMemoryScore);
        profile.setCognitiveFlexibilityScore(existing != null ? existing.getCognitiveFlexibilityScore() : null);
        profile.setProcessingSpeedScore(existing != null ? existing.getProcessingSpeedScore() : null);
        profile.setRecommendations(newRecs);
        return profile;
    }

    public CognitiveProfile analyzeFlexibilityMetrics(FlexibilitySessionMetrics m, CognitiveProfile existing) {
        String flexibilityScore;
        if (m.getAccuracyRate() >= 0.8 && m.getAvgReactionTimeMs() <= 800) {
            flexibilityScore = "High";
        } else if (m.getAccuracyRate() <= 0.6) {
            flexibilityScore = "Low";
        } else {
            flexibilityScore = "Normal";
        }

        List<String> recs = existing != null ? new ArrayList<>(existing.getRecommendations()) : new ArrayList<>();
        List<String> newRecs = recs.stream()
                .filter(r -> !containsAny(r, "flexibility", "rule", "switch"))
                .collect(Collectors.toList());

        if ("Low".equals(flexibilityScore)) {
            newRecs.add("Cognitive flexibility indicates difficulty with rule-switching. Recommend games involving sorting by multiple criteria.");
        } else if ("High".equals(flexibilityScore)) {
            newRecs.add("High cognitive flexibility. The patient adapts rapidly to new rule sets without significant accuracy drops.");
        }

        CognitiveProfile profile = new CognitiveProfile();
        profile.setChildId(m.getChildId());
        profile.setAttentionScore(existing != null ? existing.getAttentionScore() : null);
        profile.setImpulsivityScore(existing != null ? existing.getImpulsivityScore() : null);
        profile.setWorkingMemoryScore(existing != null ? existing.getWorkingMemoryScore() : null);
        profile.setCognitiveFlexibilityScore(flexibilityScore);
        profile.setProcessingSpeedScore(existing != null ? existing.getProcessingSpeedScore() : null);
        profile.setRecommendations(newRecs);
        return profile;
    }

    public CognitiveProfile analyzeSpeedMetrics(SpeedSessionMetrics m, CognitiveProfile existing) {
        String speedScore;
        if (m.getCompletionTimeMs() <= 8000 && m.getErrorsMade() == 0) {
            speedScore = "High";
        } else if (m.getCompletionTimeMs() >= 15000 || m.getErrorsMade() >= 3) {
            speedScore = "Low";
        } else {
            speedScore = "Normal";
        }

        List<String> recs = existing != null ? new ArrayList<>(existing.getRecommendations()) : new ArrayList<>();
        List<String> newRecs = recs.stream()
                .filter(r -> !containsAny(r, "speed", "search", "trail"))
                .collect(Collectors.toList());

        if ("Low".equals(speedScore)) {
            newRecs.add("Processing speed and visual search are below expected thresholds. Recommend visual scanning exercises.");
        } else if ("High".equals(speedScore)) {
            newRecs.add("Exceptional processing speed and visual scanning abilities detected.");
        }

        CognitiveProfile profile = new CognitiveProfile();
        profile.setChildId(m.getChildId());
        profile.setAttentionScore(existing != null ? existing.getAttentionScore() : null);
        profile.setImpulsivityScore(existing != null ? existing.getImpulsivityScore() : null);
        profile.setWorkingMemoryScore(existing != null ? existing.getWorkingMemoryScore() : null);
        profile.setCognitiveFlexibilityScore(existing != null ? existing.getCognitiveFlexibilityScore() : null);
        profile.setProcessingSpeedScore(speedScore);
        profile.setRecommendations(newRecs);
        return profile;
    }

    private boolean containsAny(String text, String... keywords) {
        String lower = text.toLowerCase();
        for (String keyword : keywords) {
            if (lower.contains(keyword)) return true;
        }
        return false;
    }
}