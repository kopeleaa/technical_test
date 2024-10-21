package com.example.technical_test.exception.handler;

import com.example.technical_test.exception.*;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String VALIDATION_ERROR_MESSAGE = "Error during validation on field '{}' with message '{}'";

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        List<ValidationError> validationErrors = exception.getBindingResult().getFieldErrors()
                .stream().map(fieldError -> new ValidationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        validationErrors.forEach(validationError -> logValidationError(validationError.field(), validationError.errorMessage()));

        return validationErrors;
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ValidationError> handleConstraintViolationException(ConstraintViolationException exception) {
        List<ValidationError> validationErrors = exception.getConstraintViolations()
                .stream().map(violation -> new ValidationError(violation.getPropertyPath().toString(), violation.getMessage()))
                .toList();

        validationErrors.forEach(validationError -> logValidationError(validationError.field(), validationError.errorMessage()));

        return validationErrors;
    }

    @ExceptionHandler(NoEntriesFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNoEntriesFoundException(NoEntriesFoundException exception) {
        log.error(exception.getMessage());
        return exception.getMessage();
    }

    @ExceptionHandler(PersonAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError handlerPersonAlreadyExistsException(PersonAlreadyExistsException exception) {
        ValidationError validationError = new ValidationError("name", exception.getMessage());
        logValidationError(validationError.field(), validationError.errorMessage());
        return validationError;
    }

    @ExceptionHandler(PersonNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError handlePersonNotFoundByIdException(PersonNotFoundByIdException exception) {
        ValidationError validationError = new ValidationError("id", exception.getMessage());
        logValidationError(validationError.field(), validationError.errorMessage());
        return validationError;
    }

    @ExceptionHandler(AddressNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError handleAddressNotFoundByIdException(AddressNotFoundByIdException exception) {
        ValidationError validationError = new ValidationError("id", exception.getMessage());
        logValidationError(validationError.field(), validationError.errorMessage());
        return validationError;
    }

    @ExceptionHandler(ContactInformationNotFoundByValueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationError handleContactInformationNotFoundByValueException(ContactInformationNotFoundByValueException exception) {
        ValidationError validationError = new ValidationError("value", exception.getMessage());
        logValidationError(validationError.field(), validationError.errorMessage());
        return validationError;
    }


    private void logValidationError(String field, String message) {
        log.error(VALIDATION_ERROR_MESSAGE, field, message);
    }


}
