package com.abwilkinson.demo.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

/**
 * Championship
 * Domain model representation of a championship
 */
@Entity
@Table(
        name = "championship",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "year"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer year;

    @ManyToMany(mappedBy = "championships")
    private Set<Athlete> athletes = new HashSet<>();
}