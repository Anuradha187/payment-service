package com.payment.facade;
 
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import org.springframework.stereotype.Component;
import com.payment.dto.request.PaymentRequest;
import com.payment.dto.response.PaymentResponse;
import com.payment.dto.response.PaymentStatusResponse;
import com.payment.entity.Payment;
import com.payment.factory.PaymentProcessorFactory;
import com.payment.mapper.PaymentMapper;
import com.payment.repository.PaymentRepository;
import com.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 
@Component
@RequiredArgsConstructor
@Slf4j

public class PaymentFacade {
 
    private final PaymentService paymentService;
    private final PaymentProcessorFactory processorFactory;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final BlockingQueue<Long> paymentQueue;
 
    public PaymentResponse createPayment(PaymentRequest request) {
 
        log.info("Initiating Payment");
 
        Optional<Payment> existing = paymentRepository.findByIdempotencyKey
        		                                      (request.getIdempotencyKey());
 
         if (existing.isPresent()) {
             log.info("Returning existing payment for idempotency key {}",
                                      request.getIdempotencyKey());
               return paymentMapper.toPaymentResponse(existing.get());
               }
 
         Payment payment = paymentService.createBasePayment(request);
         log.info("QUEUE_OFFER_START paymentId={}",payment.getId());
        
        boolean offered = paymentQueue.offer(payment.getId());   // producer 
         log.info("QUEUE_OFFER_SUCCESS paymentId={} offered={} queueSize={}", 
        		 payment.getId(), offered,paymentQueue.size());
 
        processorFactory.getProcessor(request.getPaymentMethod())
                        .process(payment, request);
 
        return paymentService.buildPaymentResponse(payment);
      }
 
    public PaymentStatusResponse getPaymentStatus(Long paymentId) {

        return paymentService.getPaymentStatus(paymentId);

    }

}
 