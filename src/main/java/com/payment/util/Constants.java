package com.payment.util;

public final class Constants {

	private Constants() {
		
	}
	
	// PAYMENT STATUS MESSAGES
	
	public static final String PAYMENT_CREATED="payment initiated successfully";
	public static final String PAYMENT_SUCCESS="payment processed successfully";
	public static final String PAYMENT_FAILED="Payment processing failed";
	public static final String  PAYMENT_NOT_FOUND="payment not found";
	
	// VALIDATIONS MESSAGES
	
	public static final String INVALID_AMOUNT="Amount must be greater than zero";
	public static final String INVALID_CURRENCY="Invalid currency code";
	public static final String INVALID_PAYMENT_METHOD="Unsupported payment method";
	
	// COMMON VALUES
	
	public static final String DEFAULT_CURRENCY = "INR";
	public static final String MASKED_VALUE="********";
			
	// TRANSACTION PREFIXES
	
	public static final String PAYMENT_PREFIX="PAY-";
	public static final String TXN_PREFIX="TXN-";
	public static final String GATEWAY_PREFIX="GWY-";
	
}
