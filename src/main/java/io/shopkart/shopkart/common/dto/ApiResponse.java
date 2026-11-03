package io.shopkart.shopkart.common.dto;

import java.time.Instant;

public record ApiResponse<T>(boolean success, String message, T data, Instant timeStamp) {

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "Ok", data, Instant.now());
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, Instant.now());
    }

}
