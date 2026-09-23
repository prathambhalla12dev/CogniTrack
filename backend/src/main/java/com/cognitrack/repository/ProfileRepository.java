package com.cognitrack.repository;

import com.cognitrack.model.CognitiveProfile;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ProfileRepository extends MongoRepository<CognitiveProfile, String> {
    Optional<CognitiveProfile> findTopByChildIdOrderByGeneratedAtDesc(String childId);
}