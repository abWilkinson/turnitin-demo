package com.abwilkinson.demo.dto;

/**
 * ChampionshipResponse
 * The championship values included in the search response
 */
public record ChampionshipResponse(
        String name,
        int year
) {}
