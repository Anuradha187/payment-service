package com.payment.dto.request;

import java.math.BigDecimal;
import com.payment.dto.request.base.PaymentDetails;
import com.payment.entity.enums.PaymentMethod;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PaymentRequest {

@NotNull
@Positive
private BigDecimal amount;

@NotNull
@Pattern(
	    regexp = "^(USD|EUR|INR)$",
	    message = "Only USD, EUR, and INR currencies are allowed."
	)
private String currency;

@NotNull
private String customerId;

@NotBlank
private String idempotencyKey;

@NotNull
private PaymentMethod paymentMethod;

@NotNull
@JsonTypeInfo(
		      use=JsonTypeInfo.Id.NAME,
		      include=JsonTypeInfo.As.EXTERNAL_PROPERTY,
		      property="paymentMethod")
@JsonSubTypes({
	   @JsonSubTypes.Type(value=CardPaymentDetails.class,name="CARD"),
	   @JsonSubTypes.Type(value=UpiPaymentDetails.class,name="UPI"),
	   @JsonSubTypes.Type(value=BankTransferPaymentDetails.class,name="BANK_TRANSFER"),
	   @JsonSubTypes.Type(value=PayoutPaymentDetails.class,name="PAYOUT"),
	   @JsonSubTypes.Type(value=WalletPaymentDetails.class,name="WALLET"),
	   @JsonSubTypes.Type(value=SubscriptionPaymentDetails.class,name="SUBSCRIPTION")
	   })
public  PaymentDetails paymentDetails;


}