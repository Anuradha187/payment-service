package com.payment.util;

import java.time.LocalDateTime;
import java.util.UUID;
public final class PaymentUtils {
	
	private PaymentUtils(){
		
	}
	
	// PAYMENT ID GENERATOR
	
	public static String generatePaymentID() {
		
		return Constants.PAYMENT_PREFIX+UUID.randomUUID()
		                                    .toString()
		                                    .replace("_","")
		                                    .substring(0,12)
		                                    .toUpperCase();
		}
	// TRANSACTION REFERENCE
	
	public static String generateTransactionReference() {
		
		return Constants.TXN_PREFIX+UUID.randomUUID()
		                                .toString()
		                                .replace("_", "")
		                                .substring(0,10)
		                                .toUpperCase();
				  
	   }
	
	 // GATEWAY REFERENCE
	
   public static String generateGatewayReference() {
	     
	   return Constants.GATEWAY_PREFIX+UUID.randomUUID()
	                                        .toString()
	                                        .replace("_", "")
	                                        .substring(0,8)
	                                        .toUpperCase();
       }
   
     // CURRENT TIMESTAMP
   
   public static LocalDateTime currentTimestamp() {
	   return LocalDateTime.now();
	   
   }
	
	// SIMPLE PAYMENT VALIDATION
   
   public static boolean isValidAmount(double amount) {
	   return amount>0;
   }
   
   // CURRENCY VALIDATION
   
   public static boolean isValidCurrency(String currency) {
	   
	   return currency!=null && currency.matches("^[A-Z]{3}$");
   }
	
	}
