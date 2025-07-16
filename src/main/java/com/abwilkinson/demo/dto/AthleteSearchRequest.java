package com.abwilkinson.demo.dto;


import com.abwilkinson.demo.domain.AgeRange;

import java.util.List;

/**
 * AthleteSearchRequest
 * Search fields which can be sent via query parameters
 */
public record AthleteSearchRequest(
        String name,
        AgeRange ageRange,
        List<String> skills,
        Integer minExperience
) {}