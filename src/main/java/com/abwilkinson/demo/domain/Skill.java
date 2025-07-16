package com.abwilkinson.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Skill
 * Domain model representation of a skill
 */
@Entity
@Table(name = "skill")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Skill parent;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private Set<Skill> subSkills = new HashSet<>();
}