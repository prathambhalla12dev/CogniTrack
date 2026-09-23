package com.cognitrack.model;

import lombok.Data;
import java.time.Instant;

@Data
public class MemorySessionMetrics {
    private String childId;
    private int maxSpan;
    private Instant timestamp = Instant.now();
}