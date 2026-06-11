package com.payment.processor;

import org.springframework.stereotype.Component;
import com.payment.dto.request.BankTransferPaymentDetails;
import com.payment.dto.request.PaymentRequest;
import com.payment.entity.BankTransferPayment;
import com.payment.entity.Payment;
import com.payment.entity.enums.PaymentMethod;
import com.payment.entity.enums.PaymentStatus;
import com.payment.repository.BankTransferPaymentRepository;
import com.payment.mapper.PaymentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class BankTransferPaymentProcessor implements PaymentProcessor {
 
    private final BankTransferPaymentRepository repository;
    private final PaymentMapper mapper;
 
    @Override
     public PaymentMethod getPaymentMethod() {
     return PaymentMethod.BANK_TRANSFER;
     }
 
    @Override
    public void process(Payment payment, PaymentRequest request) {
     	// SAFETY CHECK
     	if(payment.getStatus()==PaymentStatus.SUCCESS) {
     		log.info("Payment already processed , skipping paymentId {} ", payment.getId());
     		return;
     	}
     	log.info("Saving Bank Transfer payment details for payment {}", payment.getId());
     	
      BankTransferPaymentDetails details=(BankTransferPaymentDetails)request.getPaymentDetails();
      BankTransferPayment entity = mapper.toBankTransferEntity(payment,details);
         repository.save(entity);
            }
 
 }