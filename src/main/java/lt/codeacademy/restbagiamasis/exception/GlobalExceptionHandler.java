package lt.codeacademy.restbagiamasis.exception;

import lombok.extern.slf4j.Slf4j;
import lt.codeacademy.restbagiamasis.enums.Error;
import lt.codeacademy.restbagiamasis.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(ResourceNotFoundException e) {
        log.warn("Resource not found: id = {}", e.getId());

        String message = String.format("Resource with id = %d was not found", e.getId());
        ErrorResponse errorResponse = new ErrorResponse(message, Error.RESOURCE_NOT_FOUND,404);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handle(UserNotFoundException e) {
        log.warn("User not found: username {}", e.getUsername());

        String message = String.format("User with username = %s was not found", e.getUsername());
        ErrorResponse errorResponse = new ErrorResponse(message, Error.USER_NOT_FOUND, 404);

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handle(UserAlreadyExistsException e) {
        log.warn("User already exists: username {}", e.getUsername());

        String message = String.format("User with username = %s already exists", e.getUsername());
        ErrorResponse errorResponse = new ErrorResponse(message, Error.USERNAME_ALREADY_EXISTS, 400);

        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {
        log.warn("Validation failed: message = {}", e.getMessage());

        String message = "Invalid request body provided";
        ErrorResponse errorResponse = new ErrorResponse(message, Error.VALIDATION_FAILED, 400);

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handle(AccessDeniedException e) {
        log.warn("Access is denied: message = {}", e.getMessage());

        ErrorResponse errorResponse = new ErrorResponse("Access is denied", Error.ACCESS_DENIED, 403);

        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handle(Exception e) {
        log.error("Unexpected error occurred: message = {}", e.getMessage());
        e.printStackTrace();

        String message = "Server error";
        ErrorResponse errorResponse = new ErrorResponse(message, Error.SERVER_ERROR, 500);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
