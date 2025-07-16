package com.abwilkinson.demo.exception;

/**
 * AgeRangeException
 * A custom exception which will be thrown in the case where an invalid age range is supplied.
 */
public class AgeRangeException extends RuntimeException {

    public AgeRangeException(String suppliedRange) {
        super(String.format("The supplied Age Range: '%s' is not currently supported", suppliedRange));
    }
}
