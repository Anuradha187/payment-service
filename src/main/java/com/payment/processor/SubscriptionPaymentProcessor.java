package com.payment.processor;

import org.springframework.stereotype.Component;
import com.payment.dto.request.PaymentRequest;
import com.payment.dto.request.SubscriptionPaymentDetails;
import com.payment.entity.Payment;
import com.payment.entity.SubscriptionPayment;
import com.payment.entity.enums.PaymentMethod;
import com.payment.entity.enums.PaymentStatus;
import com.payment.mapper.PaymentMapper;
import com.payment.repository.SubscriptionPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SubscriptionPaymentProcessor implements PaymentProcessor{
	 private final SubscriptionPaymentRepository repository;
	 private final PaymentMapper mapper;
	 
	   @Override
	     public PaymentMethod getPaymentMethod() {
	       return PaymentMethod.SUBSCRIPTION;
	     }
	   
	   @Override
	    public void process(Payment payment, PaymentRequest request) {
	     	// SAFETY CHECK
	     	if(payment.getStatus()==PaymentStatus.SUCCESS) {
	     		log.info("Payment already processed , skipping paymentId {} ", payment.getId());
	     		return;
	     	}
	     	log.info("Saving Subscription payment details for payment {}", payment.getId());
	     	
	       SubscriptionPaymentDetails details=(SubscriptionPaymentDetails)request.getPaymentDetails();
	       SubscriptionPayment entity = mapper.toSubscriptionPaymentEntity(payment,details);
	         repository.save(entity);
	            }
	 
	 }


