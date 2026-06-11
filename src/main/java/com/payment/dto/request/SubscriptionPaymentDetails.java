package com.payment.dto.request;

import com.payment.dto.request.base.PaymentDetails;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SubscriptionPaymentDetails extends PaymentDetails{

	@NotBlank
	private String planId;
	
	@NotBlank
	private String subscriptionName;
	
	@NotBlank
	private String billingCycle;
	
	@NotNull
	private Boolean autoDebit;
	
	@Email(message="It should be in a proper email format")
	@Pattern(regexp="^[A-Za-z0-9._%+-]+@gmail\\.com",
	         message="email must be a valid format")
	private String customerEmail;
	
}
