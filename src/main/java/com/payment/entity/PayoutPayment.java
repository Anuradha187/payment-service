package com.payment.entity;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="payout_payment")
public class PayoutPayment {
	
	@Id
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="payment_id")
	private Payment payment;
	
	private String merchantId;
	private String beneficiaryName;
    private String bankAccountNumber;
    private String ifsc;
    private BigDecimal payoutAmount;
    
    }