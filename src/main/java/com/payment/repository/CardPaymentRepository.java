package com.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.payment.entity.CardPayment;

public interface CardPaymentRepository extends JpaRepository<CardPayment, Long> {

}
