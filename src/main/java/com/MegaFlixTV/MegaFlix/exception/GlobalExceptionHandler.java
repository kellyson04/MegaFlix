package com.MegaFlixTV.MegaFlix.exception;

import com.MegaFlixTV.MegaFlix.controller.response.ErrorResponse;
import com.MegaFlixTV.MegaFlix.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MovieNotFoundException.class)
    public ResponseEntity<ErrorResponse> movieNotFoundExceptionHandler (MovieNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404, "NOT_FOUND", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNotFoundExceptionHandler (UserNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404,"NOT_FOUND", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(RelationNotFoundException.class)
    public ResponseEntity<ErrorResponse> relationNotFoundExceptionHandler (RelationNotFoundException e) {
        ErrorResponse errorResponse = new ErrorResponse(404,"NOT_FOUND", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(StreamingNotFoundException.class)
    public ResponseEntity<ErrorResponse> streamingNotFoundExceptionHandler (StreamingNotFoundException s) {
        ErrorResponse errorResponse = new ErrorResponse(404,"NOT_FOUND",s.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse> invalidCredentialsExceptionHandler (InvalidCredentialsException i) {
        ErrorResponse errorResponse = new ErrorResponse(401,"UNAUTHORIZED",i.getMessage());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
    }

}
