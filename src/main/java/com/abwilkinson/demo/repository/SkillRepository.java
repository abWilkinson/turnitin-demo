package com.abwilkinson.demo.repository;

import com.abwilkinson.demo.domain.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * SkillRepository
 * JPA Repository for Skill queries. Currently used to find skill Ids based on names, including supersets.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Query(value = """
            WITH RECURSIVE skill_descendants AS (
              SELECT id FROM skill WHERE LOWER(name) IN (:skillNames)
              UNION ALL
              SELECT s.id FROM skill s
              INNER JOIN skill_descendants sd ON s.parent_id = sd.id
            )
            SELECT id FROM skill_descendants;
            """, nativeQuery = true)
    List<Long> findSkillHierarchyIds(@Param("skillNames") List<String> skillNames);
}