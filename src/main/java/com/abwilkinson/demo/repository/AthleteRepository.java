package com.abwilkinson.demo.repository;

import com.abwilkinson.demo.domain.Athlete;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * AthleteRepository
 * JPA Repository for Athlete operations, extended to execute our Specifications. Uses an entity graph to
 * include skills and championships to avoid 1+n hibernate problems.
 */
@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long>, JpaSpecificationExecutor<Athlete> {
    @EntityGraph(value = "Athlete.withSkillsAndChampionships")
    @NonNull
    List<Athlete> findAll(Specification<Athlete> spec);

}