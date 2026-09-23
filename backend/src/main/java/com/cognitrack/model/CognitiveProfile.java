package com.cognitrack.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@Document("profiles")
public class CognitiveProfile {
    @Id
    private String id;
    private String childId;
    private String attentionScore;
    private String impulsivityScore;
    private String workingMemoryScore;
    private String cognitiveFlexibilityScore;
    private String processingSpeedScore;
    private Instant generatedAt = Instant.now();
    private List<String> recommendations = new ArrayList<>();
}