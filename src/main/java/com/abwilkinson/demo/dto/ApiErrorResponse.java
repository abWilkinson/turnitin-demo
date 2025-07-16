package com.abwilkinson.demo.dto;

import java.time.LocalDateTime;
import lombok.Builder;

/**
 * ApiErrorResponse
 * Standard error response to return to a client
 */
@Builder
public record ApiErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}