package com.payment.consumer;
 
import java.util.concurrent.BlockingQueue;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.payment.service.PaymentExecutionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
 
@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {
 
    private final BlockingQueue<Long> paymentQueue;

    private final PaymentExecutionService executionService;
 
    @Async
    @EventListener(ApplicationReadyEvent.class)
   public void startConsumer() {
 
        log.info("Payment Consumer Started");
           while (true) {
        try {
      log.info("Waiting for payment...");
      Long paymentId = paymentQueue.take();  // Consumer  
      log.info("QUEUE_CONSUMED paymentId={}", paymentId);
        executionService.execute(paymentId);
 
           } catch (Exception e) {
 
                log.error("Queue Error", e);
           }

        }
      }}
 