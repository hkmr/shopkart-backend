package io.shopkart.shopkart.common.exception;

import io.shopkart.shopkart.common.dto.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        List<String> details = ex.getBindingResult().getFieldErrors().stream()
                .map(fe -> fe.getField() + ": " + fe.getDefaultMessage())
                .toList();
        return build(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", "Request validation failed", details, req);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<ApiError> handleDuplicateUser(DuplicateUserException ex, HttpServletRequest req) {
        return build(HttpStatus.CONFLICT, "DUPLICATE_USER", "User Already Exists", req);
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String code, String message, HttpServletRequest req) {
        return build(status, code, message, List.of(), req);
    }

    private ResponseEntity<ApiError> build(HttpStatus status, String code, String message,
                                           List<String> details, HttpServletRequest req) {
        return ResponseEntity.status(status).body(ApiError.of(code, message, details, req.getRequestURI()));
    }

}