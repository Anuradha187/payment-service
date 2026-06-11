package com.payment.dto.response;
 
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;
 
@Data
@Builder
public class ErrorResponse {
 
    private int status;

    private String error;

    private String message;

    private LocalDateTime timestamp;

}
 