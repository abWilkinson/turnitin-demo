
package com.abwilkinson.demo.controller;

import com.abwilkinson.demo.domain.AgeRange;
import com.abwilkinson.demo.exception.AgeRangeException;
import org.springframework.lang.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * AgeRangeConverter
 * Convert a json String value to an AgeRange enum. Throw an appropriate error if no matches.
 */
@Component
public class AgeRangeConverter implements Converter<String, AgeRange> {
    @Override
    public AgeRange convert(@NonNull String source) {
        return AgeRange.fromLabel(source)
                .orElseThrow(() -> new AgeRangeException(source));
    }
}