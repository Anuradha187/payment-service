package com.payment.processor;

import org.springframework.stereotype.Component;
import com.payment.dto.request.PaymentRequest;
import com.payment.dto.request.PayoutPaymentDetails;
import com.payment.entity.Payment;
import com.payment.entity.PayoutPayment;
import com.payment.entity.enums.PaymentMethod;
import com.payment.entity.enums.PaymentStatus;
import com.payment.mapper.PaymentMapper;
import com.payment.repository.PayoutPaymentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PayoutPaymentProcessor implements PaymentProcessor{
	   private final PayoutPaymentRepository repository;
	   private final PaymentMapper mapper;
	 
	   @Override
	     public PaymentMethod getPaymentMethod() {
	       return PaymentMethod.PAYOUT;
	     }
	 
	   
	   @Override
	    public void process(Payment payment, PaymentRequest request) {
	     	// SAFETY CHECK
	     	if(payment.getStatus()==PaymentStatus.SUCCESS) {
	     		log.info("Payment already processed , skipping paymentId {} ", payment.getId());
	     		return;
	     	}
	     	log.info("Saving Payout payment details for payment {}", payment.getId());
	     	
	       PayoutPaymentDetails details=(PayoutPaymentDetails)request.getPaymentDetails();
	       PayoutPayment entity = mapper.toPayoutPayment(payment,details);
	         repository.save(entity);
	            }
	 
	 }
	   
	 