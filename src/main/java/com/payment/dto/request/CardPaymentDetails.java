package com.payment.dto.request;

import com.payment.dto.request.base.PaymentDetails;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardPaymentDetails extends PaymentDetails{
	
	@NotBlank
	private String cardHolderName;
	
	@NotBlank
	@Pattern(regexp="\\d{16}")
	private String cardNumber;
	
	@NotBlank
	@Future(message="Expiry must be some future date")
	private String expiryMonth;
	
	@NotBlank
	private String expiryYear;
	
	@NotBlank
	@Pattern(regexp="\\d{3}")
	private String cvv;
	
	}
