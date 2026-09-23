package com.cognitrack.controller;

import com.cognitrack.model.CognitiveProfile;
import com.cognitrack.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileRepository profileRepository;

    @GetMapping("/api/profile/{childId}")
    public CognitiveProfile getProfile(@PathVariable String childId) {
        return profileRepository.findTopByChildIdOrderByGeneratedAtDesc(childId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Cognitive profile not found for this child ID"));
    }
}