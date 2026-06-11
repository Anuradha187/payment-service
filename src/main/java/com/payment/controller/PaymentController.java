package com.payment.controller;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.payment.dto.request.PaymentRequest;
import com.payment.dto.response.PaymentResponse;
import com.payment.dto.response.PaymentStatusResponse;
import com.payment.entity.Payment;
import com.payment.entity.enums.PaymentStatus;
import com.payment.facade.PaymentFacade;
import com.payment.service.ReportService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Slf4j
public class PaymentController {
	
	
	private final PaymentFacade paymentFacade;
	
	
	private final ReportService reportService;
	
	@PostMapping
    public ResponseEntity<PaymentResponse> createPayment(@Valid @RequestBody PaymentRequest request) {
 		log.info("Received create payment request: {}", request);
        PaymentResponse response = paymentFacade.createPayment(request);
        log.info("Payment request completed  with paymentId: {}",
        response.getPaymentId());
 
        return ResponseEntity
         .status(HttpStatus.CREATED)
          .body(response);

    }

	@GetMapping("/{id}")
    public ResponseEntity<PaymentStatusResponse> getPaymentStatus(@PathVariable Long id) {
        log.info("Fetching payment status for paymentId: {}", id);
        PaymentStatusResponse response =
                paymentFacade.getPaymentStatus(id);
        log.info("Payment status fetched successfully for paymentId: {}", id);
        return ResponseEntity.ok(response);
    }
	
	
	@GetMapping("/report")
	public ResponseEntity<List<Payment>> report(@RequestParam PaymentStatus status){
		
		return ResponseEntity.ok(reportService.getByStatus(status));
	}
	}

