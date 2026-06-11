package com.payment.exception;
 
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.payment.dto.response.ErrorResponse;
 
@RestControllerAdvice
public class GlobalExceptionHandler {
 
   // 400 - Validation Errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
   public Map<String, String> handleValidationException(MethodArgumentNotValidException e) {
 
        Map<String, String> errorMap = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(error -> {
        String fieldName = ((FieldError) error).getField();
        String message = error.getDefaultMessage();
            errorMap.put(fieldName, message);

        });
 
        return errorMap;

    }

    // 404 - Payment Not Found
    
    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
public ErrorResponse handleNotFoundException(PaymentNotFoundException ex) {
 
        return ErrorResponse.builder()
                 .status(HttpStatus.NOT_FOUND.value())
                 .error("NOT_FOUND")
                 .message(ex.getMessage())
                 .timestamp(LocalDateTime.now())
                .build();
 }
 
    // 409 - Business Rule Violation

    @ExceptionHandler(PaymentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
 public ErrorResponse handlePaymentException(PaymentException ex) {
 
        return ErrorResponse.builder()
                      .status(HttpStatus.CONFLICT.value())
                      .error("CONFLICT")
                      .message(ex.getMessage())
                      .timestamp(LocalDateTime.now())

                .build();

    }


    // 500 - Generic Exception

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGenericException(Exception ex) {
 
        return ErrorResponse.builder()
             .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
             .error("INTERNAL_SERVER_ERROR")
             .message("Something went wrong. Please try again later.")
              .timestamp(LocalDateTime.now())

                .build();

    }
    
  
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
   public ErrorResponse handleHttpMessageNotReadable(

            HttpMessageNotReadableException ex) {
     
        return ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .error("BAD_REQUEST")
                .message("Unsupported payment method")
                .timestamp(LocalDateTime.now())
                .build();

    }
     
    
    

}
 
