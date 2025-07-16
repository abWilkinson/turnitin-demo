package com.abwilkinson.demo.repository;

import com.abwilkinson.demo.domain.Athlete;
import com.abwilkinson.demo.domain.Championship;
import com.abwilkinson.demo.domain.Skill;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.Objects;

/**
 * AthleteSpecifications
 * A collection of Specification builders to build up the query based on the search criteria.
 */
public class AthleteSpecifications {

    /**
     * Does fuzzy matching on the name if it's included, otherwise ignored.
     */
    public static Specification<Athlete> nameContains(String name) {
        return (root, query, cb) -> {
            if (name == null || name.isBlank()) return null;
            return cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
        };
    }

    /**
     * If there is a ageRange provided add a clause for the earliest birthday
     */
    public static Specification<Athlete> birthdateAfter(LocalDate minDate) {
        return (root, query, cb) -> {
            if (minDate == null) return null;
            return cb.greaterThanOrEqualTo(root.get("birthdate"), minDate);
        };
    }

    /**
     * If there is a ageRange provided add a clause for the latest birthday
     */
    public static Specification<Athlete> birthdateBefore(LocalDate maxDate) {
        return (root, query, cb) -> {
            if (maxDate == null) return null;
            return cb.lessThanOrEqualTo(root.get("birthdate"), maxDate);
        };
    }

    /**
     * If skills are included then we include a clause to check if a skill is in their list.
     */
    public static Specification<Athlete> hasSkills(List<Long> skillIds) {
        return (root, query, builder) -> {
            Join<Athlete, Skill> skillJoin = root.join("skills");

            return skillJoin.get("id").in(skillIds);
        };
    }
    /**
     * If professional experience is included we lookup the championships finding the earliest to compare.
     */
    public static Specification<Athlete> hasMinProfessionalExperience(int minExperience) {
        return (root, query, cb) -> {
            if (minExperience <= 0) return cb.conjunction();

            int cutoffYear = Year.now().getValue() - minExperience;

            Subquery<Integer> minYearSubquery = Objects.requireNonNull(query).subquery(Integer.class);
            Root<Athlete> subRoot = minYearSubquery.from(Athlete.class);
            Join<Athlete, Championship> subChamps = subRoot.join("championships", JoinType.LEFT);

            minYearSubquery.select(cb.min(subChamps.get("year")))
                    .where(cb.equal(subRoot, root));

            // Athlete must have at least one championship & min year <= cutoff
            Predicate hasChampionship = cb.isNotNull(minYearSubquery);
            Predicate experiencePredicate = cb.lessThanOrEqualTo(minYearSubquery, cutoffYear);

            return cb.and(hasChampionship, experiencePredicate);
        };
    }
}
