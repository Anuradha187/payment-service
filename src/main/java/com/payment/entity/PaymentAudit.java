package com.payment.entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="payment_audit")
public class PaymentAudit {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	
	private String transactionId;
	
	private String action;
	
	private String performedBy;
	
	private LocalDateTime timeStamp;
	

	
}
