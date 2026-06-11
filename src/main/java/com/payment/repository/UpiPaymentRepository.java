package com.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.payment.entity.UpiPayment;
public interface UpiPaymentRepository extends JpaRepository<UpiPayment, Long>{

}
