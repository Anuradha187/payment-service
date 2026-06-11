package com.payment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.payment.entity.Payment;
import com.payment.entity.enums.PaymentStatus;
import com.payment.repository.PaymentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReportService {
	
	private final PaymentRepository repository;
	
	public List<Payment> getByStatus(PaymentStatus status){
		return repository.findByStatus(status);
		
	}

}
