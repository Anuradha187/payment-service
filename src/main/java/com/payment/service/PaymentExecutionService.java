
  package com.payment.service;
  
  import org.springframework.stereotype.Service;
  import com.payment.entity.Payment; 
  import com.payment.entity.enums.PaymentStatus; 
  import com.payment.gateway.PaymentGateway;
  import com.payment.repository.PaymentRepository;
  import lombok.RequiredArgsConstructor; 
  import lombok.extern.slf4j.Slf4j;
  
  @Service
  @RequiredArgsConstructor
  @Slf4j 
  public class PaymentExecutionService {
  
  private final PaymentRepository paymentRepository; 
  private final PaymentGateway paymentGateway;
  
  public void execute(Long paymentId) { 
	  Payment payment=paymentRepository.findById(paymentId) 
			    .orElse(null);
  if(payment==null) {
  log.warn("Payment {} not found in database . Skipping queue processing",
  paymentId); 
  return;
  }
  
  log.info("Processing paymnet {} ", payment.getId());
  payment.setStatus(PaymentStatus.PROCESSING); 
  paymentRepository.save(payment);
  
  try {
  Thread.sleep(10000); // 10 seconds for testing
  }catch(InterruptedException e) { 
	  Thread.currentThread().interrupt(); 
  
  log.error("Thread Interrupted while processing payment {}", payment.getId(),e); 
       return; 
       } 
  log.info("Payment {} status changes to PROCESSING ",
  payment.getId());
  
  processWithRetry(payment);
  
  } 
  
  public void processWithRetry(Payment payment) {
   int maxRetries = 3;
  for (int attempt = 1; attempt <= maxRetries; attempt++) {
  log.info("Attempt {} for payment {}", attempt, payment.getId()); 
  payment.setRetryCount(attempt);
   boolean success = paymentGateway.process(payment);
   if (success) {
  payment.setStatus(PaymentStatus.SUCCESS);
  paymentRepository.save(payment);
  log.info("Payment {} SUCCESS on attempt {}", payment.getId(), attempt);
  
  return;
  
  }
  
  if (attempt < maxRetries) {
  log.info("Retrying payment {} after 2 seconds...", payment.getId());
  
  try {
  
  Thread.sleep(2000);
  
  } catch (InterruptedException e) {
  
  Thread.currentThread().interrupt();
  
  throw new RuntimeException(e);
  
  }
  
  }
  
  }
  
  // FINAL FAILURE AFTER 3 ATTEMPTS
  
  payment.setStatus(PaymentStatus.FAILED);
  
  payment.setRemarks("Failed after 3 attempts");
  
  paymentRepository.save(payment);
  
  log.error("Payment {} FAILED after {} attempts", payment.getId(),
  maxRetries);
  
  } }
 