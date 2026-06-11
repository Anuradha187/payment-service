package com.payment.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.payment.entity.PaymentAudit;
import com.payment.repository.AuditRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuditService {
	
	private final AuditRepository repository;

	public void log(String txId,String action, String user) {
		PaymentAudit audit=new PaymentAudit();
		audit.setTransactionId(txId);
		audit.setAction(action);
		audit.setPerformedBy(user);
		audit.setTimeStamp(LocalDateTime.now());
		repository.save(audit);
	}
}
