package io.shopkart.shopkart.common.dto;

import java.time.Instant;
import java.util.List;

public record ApiError(boolean success, Body error, String path, Instant timestamp) {

    public record Body(String code, String message, List<String> details) {}

    public static ApiError of(String code, String message, List<String> details, String path) {
        return new ApiError(false, new Body(code, message, details), path, Instant.now());
    }
}