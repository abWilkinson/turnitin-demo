package com.abwilkinson.demo.service;

import com.abwilkinson.demo.domain.Athlete;
import com.abwilkinson.demo.domain.Skill;
import com.abwilkinson.demo.dto.AthleteResponse;
import com.abwilkinson.demo.dto.AthleteSearchRequest;
import com.abwilkinson.demo.dto.ChampionshipResponse;
import com.abwilkinson.demo.repository.AthleteRepository;
import com.abwilkinson.demo.repository.AthleteSpecifications;
import com.abwilkinson.demo.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

/**
 * AthleteService
 * Service layer for performing an athlete search.
 * Contains most business logic for searching and builds a Specification for running a query
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private final SkillRepository skillRepository;

    public List<AthleteResponse> searchAthletes(AthleteSearchRequest request) {
        LocalDate maxBirthdate = null;
        LocalDate minBirthdate = null;
        int minExperience = request.minExperience() != null ? request.minExperience() : 0;

        if (request.ageRange() != null) {
            LocalDate today = LocalDate.now();
            maxBirthdate = today.minusYears(request.ageRange().getMinAge()); // younger than
            minBirthdate = today.minusYears(request.ageRange().getMaxAge() + 1); // older than
            log.info("Date Range included, processing records older after: {} and before: {}", minBirthdate, maxBirthdate);
        }
        var spec = Specification
                .allOf(AthleteSpecifications.nameContains(request.name())
                    .and(AthleteSpecifications.birthdateAfter(minBirthdate))
                    .and(AthleteSpecifications.birthdateBefore(maxBirthdate))
                    .and(AthleteSpecifications.hasMinProfessionalExperience(minExperience)));

        if (request.skills() != null && !request.skills().isEmpty()) {
            log.info("Including skills in search.");
            List<String> normalizedSkills = request.skills().stream()
                    .map(String::toLowerCase)
                    .toList();
            List<Long> skillIds = skillRepository.findSkillHierarchyIds(normalizedSkills);
            if (skillIds.isEmpty()) {
                log.info("No matching skills found.");
                return Collections.emptyList();
            }
            spec = spec.and(AthleteSpecifications.hasSkills(skillIds));
        }

        List<Athlete> athletes = athleteRepository.findAll(spec);

        return athletes.stream()
                .map(athlete -> new AthleteResponse(
                        athlete.getName(),
                        athlete.getBirthdate(),
                        athlete.getSkills().stream().map(Skill::getName).toList(),
                        athlete.getChampionships()
                                .stream()
                                .map(championship -> new ChampionshipResponse(championship.getName(), championship.getYear()))
                                .toList()
                ))
                .toList();
    }

}