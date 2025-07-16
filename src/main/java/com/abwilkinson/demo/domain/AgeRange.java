package com.abwilkinson.demo.domain;

import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * AgeRange
 * enum mapping of valid age ranges. Map from String representation to integers for min and max age.
 */
@Getter
public enum AgeRange {
    UNDER_18("<18", 0, 17),
    FROM_18_TO_21("18-21", 18, 21),
    FROM_22_TO_25("22-25", 22, 25),
    FROM_26_TO_30("26-30", 26, 30),
    OVER_30(">30", 31, 150);

    private final String label;
    private final int minAge;
    private final int maxAge;

    AgeRange(String label, int minAge, int maxAge) {
        this.label = label;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public static Optional<AgeRange> fromLabel(String label) {
        return Arrays.stream(values())
                .filter(r -> r.label.equalsIgnoreCase(label))
                .findFirst();
    }

    @Override
    public String toString() {
        return label;
    }
}