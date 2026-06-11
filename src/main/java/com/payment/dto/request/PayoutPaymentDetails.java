package com.payment.dto.request;

import java.math.BigDecimal;
import com.payment.dto.request.base.PaymentDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PayoutPaymentDetails extends PaymentDetails{
	
	@NotBlank
	private String merchantId;
	
	@NotBlank
	private String beneficiaryName;
	
	@NotBlank
	@Size(min=1,max=12)
	private String bankAccountNumber;
	
	@NotBlank
	private String ifsc;
	
	@Positive
	private BigDecimal payoutAmount;
	
	private String remarks;
	

}

