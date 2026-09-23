package com.cognitrack.model;

import lombok.Data;
import java.time.Instant;

@Data
public class FlexibilitySessionMetrics {
    private String childId;
    private double accuracyRate;
    private double avgReactionTimeMs;
    private Instant timestamp = Instant.now();
}