package com.payment.processor;

import org.springframework.stereotype.Component;
import com.payment.dto.request.PaymentRequest;
import com.payment.dto.request.UpiPaymentDetails;
import com.payment.entity.Payment;
import com.payment.entity.UpiPayment;
import com.payment.entity.enums.PaymentMethod;
import com.payment.entity.enums.PaymentStatus;
import com.payment.mapper.PaymentMapper;
import com.payment.repository.UpiPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UpiPaymentProcessor implements PaymentProcessor{

	private final UpiPaymentRepository repository;
	private final PaymentMapper mapper;
	 
	   @Override
	     public PaymentMethod getPaymentMethod() {
	       return PaymentMethod.UPI;
	     }
	   
	    @Override
	    public void process(Payment payment, PaymentRequest request) {
	     	// SAFETY CHECK
	     	if(payment.getStatus()==PaymentStatus.SUCCESS) {
	     		log.info("Payment already processed , skipping paymentId {} ", payment.getId());
	     		return;
	     	}
	     	log.info("Saving Upi payment details for payment {}", payment.getId());
	     	
	       UpiPaymentDetails details=(UpiPaymentDetails)request.getPaymentDetails();
	       UpiPayment entity = mapper.toUpiPaymentEntity(payment,details);
	         repository.save(entity);
	            }
	 
	 }
