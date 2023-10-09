package com.mascara.oyo_booking_backend.advice;

import com.mascara.oyo_booking_backend.exceptions.NotCredentialException;
import com.mascara.oyo_booking_backend.exceptions.ResourceExistException;
import com.mascara.oyo_booking_backend.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: IntelliJ IDEA
 * User      : boyng
 * Date      : 07/10/2023
 * Time      : 6:56 CH
 * Filename  : ApplicationExceptionHandler
 */
public class ApplicationExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException err) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status", HttpStatus.NOT_FOUND.value());
        body.put("errors", err.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(body);
    }

    @ExceptionHandler(ResourceExistException.class)
    public ResponseEntity<Object> handleResourceExistException(ResourceExistException err) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status",HttpStatus.NOT_FOUND.value());
        body.put("errors", err.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                .body(body);
    }

    @ExceptionHandler(NotCredentialException.class)
    public ResponseEntity<Object> handleResourceExistException(NotCredentialException err) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", System.currentTimeMillis());
        body.put("status",HttpStatus.NOT_FOUND.value());
        body.put("errors", err.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(body);
    }

//    @ExceptionHandler(JWTException.class)
//    public ResponseEntity<Object> handleResourceExistException(JWTException err) {
//        Map<String, Object> body = new LinkedHashMap<>();
//        body.put("timestamp", System.currentTimeMillis());
//        body.put("status",HttpStatus.NOT_FOUND.value());
//        body.put("errors", err.getMessage());
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body(body);
//    }
}
