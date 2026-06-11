package com.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.entity.WalletPayment;

public interface WalletPaymentRepository  extends JpaRepository<WalletPayment, Long>{

}

