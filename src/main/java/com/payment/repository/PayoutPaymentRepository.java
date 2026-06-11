package com.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.payment.entity.PayoutPayment;

public interface PayoutPaymentRepository extends JpaRepository<PayoutPayment, Long>{

}
