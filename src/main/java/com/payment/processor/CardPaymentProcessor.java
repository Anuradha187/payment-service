package com.payment.processor;

import org.springframework.stereotype.Component;
import com.payment.dto.request.CardPaymentDetails;
import com.payment.dto.request.PaymentRequest;
import com.payment.entity.CardPayment;
import com.payment.entity.Payment;
import com.payment.entity.enums.PaymentMethod;
import com.payment.entity.enums.PaymentStatus;
import com.payment.mapper.PaymentMapper;
import com.payment.repository.CardPaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class CardPaymentProcessor implements PaymentProcessor {
 
    private final CardPaymentRepository repository;
    private final PaymentMapper mapper;
 
     @Override
    public PaymentMethod getPaymentMethod() {
       return PaymentMethod.CARD;
     }
 
    @Override
   public void process(Payment payment, PaymentRequest request) {
    	// SAFETY CHECK
    	if(payment.getStatus()==PaymentStatus.SUCCESS) {
    		log.info("Payment already processed , skipping paymentId {} ", payment.getId());
    		return;
    	}
    	log.info("Saving card payment details for payment {}", payment.getId());
    	
      CardPaymentDetails details=(CardPaymentDetails)request.getPaymentDetails();
      CardPayment entity = mapper.toCardPaymentEntity(payment,details);
        repository.save(entity);
        
   }
}
 