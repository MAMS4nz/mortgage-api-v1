package com.mortgage.api.v1.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.ZonedDateTime;
import java.util.UUID;

import static java.time.ZoneOffset.UTC;


@Slf4j
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RestExceptionHandler {

    private String getUniqueExceptionId() {
        var uuid = UUID.randomUUID();
        var systemMillis = System.currentTimeMillis();
        return uuid + "-" + systemMillis;
    }

    /**
     * Custom validation handling.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GeneralErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex
    ) {
        var message = ex.getMessage();
        log.error("handleMethodArgumentNotValid: {}:", message, ex);
        var httpStatus = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(
                new GeneralErrorResponse(
                        getUniqueExceptionId(),
                        httpStatus.value(),
                        message,
                        ZonedDateTime.now(UTC)
                ),
                httpStatus
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<GeneralErrorResponse> handleNoResourceFoundException(
            Exception ex
    ) {
        log.error("handleNoResourceFoundException: {}:", ex.getMessage(), ex);
        var httpStatus = HttpStatus.NOT_FOUND;
        var id = getUniqueExceptionId();
        return new ResponseEntity<>(
                new GeneralErrorResponse(
                        id,
                        httpStatus.value(),
                        "The resource could not be found. ",
                        ZonedDateTime.now(UTC)
                ),
                httpStatus
        );
    }

    /**
     * Unexpected exceptions. Generic handling.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralErrorResponse> handleUnexpectedException(
            Exception ex
    ) {
        log.error("handleUnexpectedException: {}:", ex.getMessage(), ex);
        var httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        var id = getUniqueExceptionId();
        return new ResponseEntity<>(
                new GeneralErrorResponse(
                        id,
                        httpStatus.value(),
                "There was an unexpected problem. Please, " +
                        "contact the support team and provide this exception ID: " +
                        id,
                        ZonedDateTime.now(UTC)
                ),
                httpStatus
        );
    }

}
