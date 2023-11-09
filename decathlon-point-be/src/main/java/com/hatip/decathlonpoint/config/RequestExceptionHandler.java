package com.hatip.decathlonpoint.config;

import com.hatip.decathlonpoint.exception.ImpossibleScoreException;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RequestExceptionHandler {

    private static final String INTERNAL_SERVER_ERROR_MESSAGE = "Internal server error";
    private static final String ERROR_MESSAGE = "errorMessage";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    public Map<String, String> handleInternalServerError(Exception e) {
        log.error(INTERNAL_SERVER_ERROR_MESSAGE, e);
        return buildErrorResponseBody(INTERNAL_SERVER_ERROR_MESSAGE);
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public Map<String, String> handleHttpMethodNotAllowed(Exception e) {
        log.error("Method not allowed", e);
        return buildErrorResponseBody(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        final List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(error -> createMissedFieldsErrorMessage(((FieldError) error).getField(), error.getDefaultMessage())).toList();
        return buildErrorResponseBody("Mandatory fields did not set:\n" + errors);
    }

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(value = {HttpMediaTypeNotSupportedException.class})
    public Map<String, String> handleHttpUnsupportedMediaType(Exception e) {
        log.error("Unsupported Media Type", e);
        return buildErrorResponseBody(e);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Map<String, String> handleMismatchedInputException(Exception e) {
        log.error("HttpMessageNotReadableException: {}", e.getMessage());
        return buildErrorResponseBody("Required request body is missing or has a wrong format");
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ExceptionHandler(value = {NoSuchFieldException.class})
    public Map<String, String> handleNoSuchFieldException(Exception e) {
        log.error("Record not found", e);
        return buildErrorResponseBody(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ImpossibleScoreException.class})
    public Map<String, String> handleImpossibleScoreException(Exception e) {
        log.error("Record not found", e);
        return buildErrorResponseBody(e);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ConstraintViolationException.class})
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error("Unique variable tried to re-insert ", e);
        return buildErrorResponseBody(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {ValidationException.class})
    public Map<String, String> handleValidationException(ValidationException e) {
        log.error("Validation exception", e);
        return buildErrorResponseBody(e);
    }
    private Map<String, String> buildErrorResponseBody(Exception e) {
        final Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_MESSAGE, e.getMessage());
        return errors;
    }

    private Map<String, String> buildErrorResponseBody(String messageDefault) {
        final Map<String, String> errors = new HashMap<>();
        errors.put(ERROR_MESSAGE, messageDefault);
        return errors;
    }

    private String createMissedFieldsErrorMessage(String fieldName, String error) {
        return String.format("%s - %s", fieldName, error);
    }
}
