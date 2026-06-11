package com.payment.service;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.payment.dto.request.BankTransferPaymentDetails;
import com.payment.dto.request.CardPaymentDetails;
import com.payment.dto.request.PaymentRequest;
import com.payment.dto.request.PayoutPaymentDetails;
import com.payment.dto.request.SubscriptionPaymentDetails;
import com.payment.dto.request.UpiPaymentDetails;
import com.payment.dto.request.WalletPaymentDetails;
import com.payment.dto.response.PaymentResponse;
import com.payment.dto.response.PaymentStatusResponse;
import com.payment.entity.Payment;
import com.payment.entity.enums.PaymentMethod;
import com.payment.entity.enums.PaymentStatus;
import com.payment.exception.PaymentNotFoundException;
import com.payment.repository.PaymentRepository;
import com.payment.mapper.PaymentMapper;
import com.payment.util.PaymentUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class PaymentServiceImpl implements PaymentService{
	
	private final PaymentRepository paymentRepository;
	private final PaymentMapper paymentMapper;
	private final AuditService auditService;
	
	@Override
	@Transactional
	public Payment createBasePayment(PaymentRequest request) {
		
		validateCurrency(request.getCurrency());
		validateAmount(request.getAmount(),
				       request.getPaymentMethod());
		validatePaymentMethod(request);
	
		
	   // STEP 1 : IDEMPOTENCY CHECK FIRST
		
		Optional<Payment> existingPayment=paymentRepository
				                   .findByIdempotencyKey(request.getIdempotencyKey());
		
		if(existingPayment.isPresent()) {
			Payment payment=existingPayment.get();
			
			log.info("Returning existing payment for idempotency key {}",request.getIdempotencyKey());
			
			 if(payment.getAmount().compareTo(request.getAmount())!=0 ||
			    !payment.getCustomerId().equals(request.getCustomerId())||
			    payment.getPaymentMethod() != request.getPaymentMethod()) 
			 {
				 
				 throw new IllegalArgumentException("Idempotency key reused with different payload");
				 
			 }
			
			 return payment;
		}
		
		// STEP 2: CREATE NEW PAYMENT ONLY
		
		String username=SecurityContextHolder.getContext().getAuthentication().getName();
		
		Payment payment
		           =paymentMapper.toPaymentEntity(request);
		
		payment.setIdempotencyKey(request.getIdempotencyKey());
		
		payment.setTransactionId(
				    PaymentUtils.generateTransactionReference());
		
		payment.setGatewayReference(
				    PaymentUtils.generateGatewayReference());
		
		payment.setStatus(PaymentStatus.INITIATED);
		
		Payment savedPayment=paymentRepository.save(payment);
		
		paymentRepository.flush();
		
		auditService.log(savedPayment.getTransactionId(),
				                "PAYMENT_CREATED",
				                username);
		
		log.info("Audit log created for transaction {} ", savedPayment.getTransactionId());
		
		
		return savedPayment;
	}
	
	@Override
	public PaymentStatusResponse getPaymentStatus(Long paymentId) {
	 
	    Payment payment = paymentRepository.findById(paymentId)
	            .orElseThrow(() ->
	                    new PaymentNotFoundException(
	                            "Payment not found with id: " + paymentId));
	 
	    return paymentMapper.toPaymentStatusResponse(payment);
	}
	
	
    @Override
    public PaymentResponse buildPaymentResponse(Payment payment) {
        return paymentMapper.toPaymentResponse(payment);
    }
    
    // PRIVATE VALIDATION METHODS
    
    private void validateCurrency(String currency) {
    	if(!currency.equals("USD") 
    			&& !currency.equals("EUR")
    			&& !currency.equals("INR")) {
    		throw new IllegalArgumentException("Unsupported currency");
    		
    	}
    }
    
    private void validateAmount(BigDecimal amount, PaymentMethod method) {
    	//BigDecimal maxLimit;
    	
    	if(amount==null || method==null) {
    		throw new IllegalArgumentException("Amount or PaymentMethod cannot be null");
    	}
    	if(amount.compareTo(BigDecimal.ZERO)<=0) {
    		throw new IllegalArgumentException("Amount must be greater than zero");
    	}

    }

    private void validatePaymentMethod(PaymentRequest request) {
    	
    	switch(request.getPaymentMethod()) {
    	case CARD -> {
    		if(!(request.getPaymentDetails() instanceof CardPaymentDetails)) {
    			throw new IllegalArgumentException("Invalid CARD payment details");
    		}
    	}
    	case UPI -> {
    		if(!(request.getPaymentDetails() instanceof UpiPaymentDetails)) {
    			throw new IllegalArgumentException("Invalid UPI payment details");
    		}
    	}
    	case BANK_TRANSFER -> {
    		if(!(request.getPaymentDetails() instanceof BankTransferPaymentDetails)) {
    			throw new IllegalArgumentException("Invalid BANKTRANSFER payment details");
    		}
    	}
    	case PAYOUT -> {
    		if(!(request.getPaymentDetails() instanceof PayoutPaymentDetails)) {
    			throw new IllegalArgumentException("Invalid PAYOUT payment details");
    		}
    	}
    	case WALLET -> {
    		if(!(request.getPaymentDetails() instanceof WalletPaymentDetails)) {
    			throw new IllegalArgumentException("Invalid WALLET payment details");
    		}
    	}
    	case SUBSCRIPTION -> {
    		if(!(request.getPaymentDetails() instanceof SubscriptionPaymentDetails)) {
    			throw new IllegalArgumentException("Invalid SUBSCRIPTION payment details");
    		}
    	}
    	default -> throw new IllegalArgumentException("Unsupported payment Method");
    	}
    }
    
   }

	
