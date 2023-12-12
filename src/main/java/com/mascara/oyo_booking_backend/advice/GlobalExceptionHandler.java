package com.mascara.oyo_booking_backend.advice;

import com.mascara.oyo_booking_backend.exceptions.NotCredentialException;
import com.mascara.oyo_booking_backend.exceptions.ResourceExistException;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import com.mascara.oyo_booking_backend.exceptions.TokenRefreshException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 17/10/2023
 * Time      : 4:07 SA
 * Filename  : CustomErroController
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotCredentialException.class)
    public ProblemDetail handleNotPermission(NotCredentialException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setProperty("timestamp",Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ProblemDetail handleSQLExcuteException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
        problemDetail.setProperty("timestamp",Instant.now());
        return problemDetail;
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, MissingPathVariableException.class})
    public ProblemDetail handleConstraintArgumentTypeMismatchException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setProperty("timestamp",Instant.now());
        return problemDetail;
    }

    @ExceptionHandler({ConstraintViolationException.class, HttpMessageNotReadableException.class})
    public ProblemDetail handleConstraintViolationException(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setProperty("timestamp",Instant.now());
        return problemDetail;
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
    public ProblemDetail handleValidationExceptions(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setProperty("timestamp",Instant.now());
        return problemDetail;
    }

    @ExceptionHandler({ExpiredJwtException.class, MalformedJwtException.class, UnsupportedJwtException.class, AuthenticationException.class})
    public ProblemDetail handleSecurityExceptions(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setProperty("timestamp",Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(TokenRefreshException.class)
    public ProblemDetail handleTokenRefreshException(TokenRefreshException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED, ex.getMessage());
        problemDetail.setProperty("timestamp",Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setProperty("timestamp",Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(ResourceExistException.class)
    public ProblemDetail handleResourceExistWhenCreateException(ResourceExistException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }
}