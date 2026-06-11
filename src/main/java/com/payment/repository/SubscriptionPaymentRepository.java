package com.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.entity.SubscriptionPayment;

public interface SubscriptionPaymentRepository extends JpaRepository<SubscriptionPayment, Long>{

}
