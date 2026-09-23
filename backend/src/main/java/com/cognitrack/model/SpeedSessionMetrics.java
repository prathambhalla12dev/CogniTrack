package com.cognitrack.model;

import lombok.Data;
import java.time.Instant;

@Data
public class SpeedSessionMetrics {
    private String childId;
    private int completionTimeMs;
    private int errorsMade;
    private Instant timestamp = Instant.now();
}