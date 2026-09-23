package com.cognitrack.model;

import lombok.Data;
import java.time.Instant;

@Data
public class GameSessionMetrics {
    private String childId;
    private double reactionTimeMs;
    private int omissionErrors;
    private int commissionErrors;
    private Instant timestamp = Instant.now();
}