package pl.bykowski.week3.configuration.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import pl.bykowski.week3.exception.EmptyJsonException;
import pl.bykowski.week3.exception.NotFoundException;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@ControllerAdvice
public class MainControllerAdvice {

    private final String DATA_WRONG_MESSAGE = "The data sent is wrong";
    private final String PATH_VARIABLE_WRONG = "Path Variable is wrong!";
    private final String INTERNAL_ERROR_MESSAGE = "There is unexpected error during processing request";

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

    @ExceptionHandler(EmptyJsonException.class)
    private ResponseEntity<String> handleEmptyJsonException(EmptyJsonException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(prepareFieldErrors(exception));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    private ResponseEntity<String> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PATH_VARIABLE_WRONG);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        log.warn(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DATA_WRONG_MESSAGE);
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<String> handleException(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(INTERNAL_ERROR_MESSAGE);
    }

    private String prepareFieldErrors(MethodArgumentNotValidException exception) {
        StringBuilder sb = new StringBuilder();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        for(FieldError fieldError: fieldErrors) {
            sb.append(fieldError.getField())
                .append(" ")
                .append(fieldError.getDefaultMessage())
                .append("\n");
        }

        return sb.toString();
    }

}
