package com.abwilkinson.demo.controller;

import com.abwilkinson.demo.dto.ApiErrorResponse;
import com.abwilkinson.demo.exception.AgeRangeException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

/**
 * GlobalExceptionHandler
 * Handle exceptions in a standard way to return to the client with reasonable error codes.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidAgeRange(AgeRangeException ex, HttpServletRequest request) {

        if (ex.getCause() instanceof AgeRangeException ageEx) {
            return buildErrorResponse(ageEx, HttpStatus.BAD_REQUEST, request.getRequestURI());
        }

        return buildErrorResponse(ex, HttpStatus.BAD_REQUEST, request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericError(Exception ex, HttpServletRequest request) {

        return buildErrorResponse(ex, HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
    }

    private ResponseEntity<ApiErrorResponse> buildErrorResponse(Exception ex, HttpStatus status, String path) {
        ApiErrorResponse error = ApiErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(ex.getMessage())
                .path(path)
                .build();

        return new ResponseEntity<>(error, status);
    }
}
