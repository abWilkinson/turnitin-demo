package com.abwilkinson.demo.controller;

import com.abwilkinson.demo.domain.AgeRange;
import com.abwilkinson.demo.domain.Athlete;
import com.abwilkinson.demo.dto.AthleteResponse;
import com.abwilkinson.demo.dto.AthleteSearchRequest;
import com.abwilkinson.demo.service.AthleteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AthleteController
 * REST Controller to expose the athlete search endpoint.
 */
@RestController
@RequestMapping("/api/athletes")
@RequiredArgsConstructor
@Slf4j
public class AthleteController {

    private final AthleteService athleteService;

    @GetMapping("/search")
    public List<AthleteResponse> searchAthletes(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) AgeRange ageRange,
            @RequestParam(required = false) List<String> skills,
            @RequestParam(required = false) Integer minExperience) {

        AthleteSearchRequest request = new AthleteSearchRequest(name, ageRange, skills, minExperience);
        log.info("Processing request for name:{}, ageRange:{}, skills:{}, minExperience:{}", name, ageRange, skills, minExperience);
        return athleteService.searchAthletes(request);
    }
}