package com.abwilkinson.demo.dto;

import java.time.LocalDate;
import java.util.List;

/**
 * AthleteResponse
 * Object representation of a search response for an athlete.
 */
public record AthleteResponse(
        String name,
        LocalDate birthdate,
        List<String> skills,
        List<ChampionshipResponse> championships
) {}
