package com.abwilkinson.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Athlete
 * Domain model representation of an Athlete.
 */
@Entity
@Table(name = "athlete")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@NamedEntityGraph(
        name = "Athlete.withSkillsAndChampionships",
        attributeNodes = {
                @NamedAttributeNode("skills"),
                @NamedAttributeNode("championships")
        }
)
public class Athlete {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate birthdate;

    // Many-to-many with Skill
    @ManyToMany
    @JoinTable(
            name = "athlete_skill",
            joinColumns = @JoinColumn(name = "athlete_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private Set<Skill> skills = new HashSet<>();

    // Many-to-many with Championship
    @ManyToMany
    @JoinTable(
            name = "athlete_championship",
            joinColumns = @JoinColumn(name = "athlete_id"),
            inverseJoinColumns = @JoinColumn(name = "championship_id")
    )
    private Set<Championship> championships = new HashSet<>();
}