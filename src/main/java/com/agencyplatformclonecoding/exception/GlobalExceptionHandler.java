package com.agencyplatformclonecoding.exception;

import com.agencyplatformclonecoding.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(SQLException.class)
    protected ResponseEntity<ErrorResponse> handleSQLException(SQLException e) {
        log.error("handleSQLException throw AdPlatformException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("handlIllegalArgumentException throw AdPlatformException : {}", e.getLocalizedMessage());
        return ErrorResponse.toResponseEntity(ErrorCode.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNotEntityNotFoundException(EntityNotFoundException e) {
        log.error("handleEntityNotFoundException throw AdPlatformException : {}", e.getLocalizedMessage());
        return ErrorResponse.toResponseEntity(ErrorCode.NOT_FOUND);
    }

    @ExceptionHandler(AdPlatformException.class)
    protected ResponseEntity<ErrorResponse> handleAdPlatformException(AdPlatformException e) {
        log.error("handleAdPlatformException throw AdPlatformException : {}", e.getErrorCode());
        return ErrorResponse.toResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleExException(Exception e) {
        log.error("handleExException throw AdPlatformException : {}", e.getLocalizedMessage());
        return ErrorResponse.toResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
    }
}
