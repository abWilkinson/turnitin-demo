package com.abwilkinson.demo.controller;

import com.abwilkinson.demo.domain.AgeRange;
import com.abwilkinson.demo.dto.ApiErrorResponse;
import com.abwilkinson.demo.dto.AthleteResponse;
import com.abwilkinson.demo.dto.AthleteSearchRequest;
import com.abwilkinson.demo.service.AthleteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * AthleteController
 * REST Controller to expose the athlete search endpoint.
 */
@RestController
@RequestMapping("/api/athletes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Athletes", description = "Endpoints for searching athletes")
public class AthleteController {

    private final AthleteService athleteService;

    @Operation(
            summary = "Search for young athletes",
            description = "Search athletes by name, ageRange, skills, and experience filters"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful search"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request (e.g. wrong age range)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ApiErrorResponse.class)
                    )
            )
    })
    @GetMapping("/search")
    public List<AthleteResponse> searchAthletes(
            @Parameter(description = "Name filter (partial match anywhere in the name)") @RequestParam(required = false) String name,
            @Parameter(
                    description = "Age range filter",
                    schema = @Schema(implementation = AgeRange.class)
            )
            @RequestParam(required = false) AgeRange ageRange,
            @Parameter(description = "Skills filter (comma-separated)") @RequestParam(required = false) List<String> skills,
            @Parameter(description = "Minimum years of experience") @RequestParam(required = false) Integer minExperience) {

        AthleteSearchRequest request = new AthleteSearchRequest(name, ageRange, skills, minExperience);
        log.info("Processing request for name:{}, ageRange:{}, skills:{}, minExperience:{}", name, ageRange, skills, minExperience);
        return athleteService.searchAthletes(request);
    }
}