package space.qiuxuan.tickets.controllers;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.View;
import space.qiuxuan.tickets.domain.dtos.ErrorDto;
import space.qiuxuan.tickets.domain.entities.Ticket;
import space.qiuxuan.tickets.exceptions.*;

import java.util.List;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(TicketSoldOutException.class)
    public ResponseEntity<ErrorDto> handleTicketSoldOutException(TicketSoldOutException ex) {
        log.error("Caught TicketSoldOutException", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Ticket sold out for this ticket type");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(QrCodeNotFoundException.class)
    public ResponseEntity<ErrorDto> handleQrNotFoundException(QrCodeNotFoundException ex) {
        log.error("Caught QrCodeNotFoundException", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("QR Code Not Found");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QrCodeGenerationException.class)
    public ResponseEntity<ErrorDto> handleQrCodeGenerationException(QrCodeGenerationException ex) {
        log.error("Caught QrCodeGenerationException", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Unable to generate QR Code");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EventUpdateException.class)
    public ResponseEntity<ErrorDto> handleEventUpdateException(EventUpdateException ex) {
        log.error("Caught EventUpdateException", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Unable to update ticket type");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketTypeNotFoundException.class)
    public ResponseEntity<ErrorDto> handleTicketTypeNotFoundException(TicketTypeNotFoundException ex) {
        log.error("Caught TicketTypeNotFoundException", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Ticket type not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<ErrorDto> handleEventNotFoundException(EventNotFoundException ex) {
        log.error("Caught EventNotFoundException", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("Event not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("Caught UserNotFoundException", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("User not found");
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Caught MethodArgumentNotValidException", ex);
        ErrorDto errorDto = new ErrorDto();

        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        String errorMessage = fieldErrors.stream()
                .findFirst()
                .map(fieldError -> fieldError.getField() + ":" + fieldError.getDefaultMessage())
                        .orElse("Validation Error Occurred");
        errorDto.setError(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorDto> handleConstraintViolation(
            ConstraintViolationException ex
    ) {
        log.error("Caught ConstraintViolationException", ex);
        ErrorDto errorDto = new ErrorDto();

        String errorMessage = ex.getConstraintViolations()
                .stream()
                .findFirst()
                .map(constraintViolation ->
                        constraintViolation.getPropertyPath() + ": " + constraintViolation.getMessage()
                ).orElse("Constraint Violation occurred");

        errorDto.setError(errorMessage);
        return new ResponseEntity<>(errorDto, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        log.error("Caught exception", ex);
        ErrorDto errorDto = new ErrorDto();
        errorDto.setError("An unknown error occurred");
        return new ResponseEntity<>(errorDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
