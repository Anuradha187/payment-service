package com.payment.dto.request;

import com.payment.dto.request.base.PaymentDetails;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BankTransferPaymentDetails extends PaymentDetails{
	
	@NotBlank
	private String accountHolderName;
	
	@NotBlank
	@Size(min=1,max=12)
	private String accountNumber;
	
	@NotBlank
	private String ifsc;

}
