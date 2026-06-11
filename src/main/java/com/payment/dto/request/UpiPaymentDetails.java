package com.payment.dto.request;

import com.payment.dto.request.base.PaymentDetails;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpiPaymentDetails extends PaymentDetails{
	
	@NotBlank
	private String vpa;

}