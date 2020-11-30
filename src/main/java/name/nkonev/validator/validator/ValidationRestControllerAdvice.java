package name.nkonev.validator.validator;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestControllerAdvice
public class ValidationRestControllerAdvice {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handle(Throwable t) {
        return new ErrorResponseDto("General error");
    }

    // Handles body validation exceptions
    // https://docs.spring.io/spring-framework/docs/4.1.7.RELEASE/spring-framework-reference/html/validation.html
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleMethodArgument(MethodArgumentNotValidException t) {
        List<ObjectError> allErrors = t.getAllErrors();
        List<ErrorDto> validationErrors = new ArrayList<>();
        for (ObjectError objectError: allErrors) {
            ConstraintViolation<?> unwrapped = objectError.unwrap(ConstraintViolation.class);
            validationErrors.add(new ErrorDto(unwrapped.getPropertyPath().toString(), unwrapped.getMessage()));
        }
        return new ErrorResponseDto("Validation error", validationErrors);
    }

    // Handles request param validation exceptions and service too
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleConstraint(ConstraintViolationException t) {
        Set<ConstraintViolation<?>> constraintViolations = t.getConstraintViolations();
        List<ErrorDto> validationErrors = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation: constraintViolations) {
            validationErrors.add(new ErrorDto(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
        }
        return new ErrorResponseDto("Validation error", validationErrors);
    }

}
