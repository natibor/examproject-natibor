package hu.progmasters.vizsgaremek.exception;

import hu.progmasters.vizsgaremek.dto.ValidationError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ValidationError>> handleValidationExceptions(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ResponseEntity<List<ValidationError>>(validationErrors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ResponseEntity<HttpStatus> handleEmployeeNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProjectNotFoundException.class)
    public ResponseEntity<HttpStatus> handleProjectNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WorkSessionNotFoundException.class)
    public ResponseEntity<HttpStatus> handleWorkSessionNotFoundException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
