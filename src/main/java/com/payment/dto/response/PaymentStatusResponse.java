package com.payment.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.payment.entity.enums.PaymentMethod;
import com.payment.entity.enums.PaymentStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Builder
public class PaymentStatusResponse {

	private Long paymentId;
	private PaymentMethod paymentMethod;
	private PaymentStatus status;
	private BigDecimal amount;
	private String currency;
	private String accountReference;
	private String transactionReference;
	private String gatewayReference;
	private String remarks;
	private LocalDate createdAt;
	private LocalDate updatedAt;
	
	}
