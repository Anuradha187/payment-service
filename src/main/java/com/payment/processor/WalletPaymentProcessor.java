package com.payment.processor;

import org.springframework.stereotype.Component;
import com.payment.dto.request.PaymentRequest;
import com.payment.dto.request.WalletPaymentDetails;
import com.payment.entity.Payment;
import com.payment.entity.WalletPayment;
import com.payment.entity.enums.PaymentMethod;
import com.payment.entity.enums.PaymentStatus;
import com.payment.mapper.PaymentMapper;
import com.payment.repository.WalletPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WalletPaymentProcessor implements PaymentProcessor {

	private final WalletPaymentRepository repository;
	private final PaymentMapper mapper;
	 
	   @Override
	     public PaymentMethod getPaymentMethod() {
	       return PaymentMethod.WALLET;
	     }
	 
	   @Override
	    public void process(Payment payment, PaymentRequest request) {
	     	// SAFETY CHECK
	     	if(payment.getStatus()==PaymentStatus.SUCCESS) {
	     		log.info("Payment already processed , skipping paymentId {} ", payment.getId());
	     		return;
	     	}
	     	log.info("Saving Wallet payment details for payment {}", payment.getId());
	     	
	     WalletPaymentDetails details=(WalletPaymentDetails)request.getPaymentDetails();
	      WalletPayment entity = mapper.toWalletPaymentEntity(payment,details);
	         repository.save(entity);
	            }
	 
	 }
