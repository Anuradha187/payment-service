package com.payment.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.payment.entity.Payment;
import com.payment.entity.enums.PaymentStatus;

public interface PaymentRepository extends JpaRepository<Payment, Long>{

	Optional<Payment> findByIdempotencyKey(String idempotencyKey);
	
	Optional<Payment> findByTransactionId(String txId);
	List<Payment> findByStatus(PaymentStatus status);
	
}
