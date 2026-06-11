package com.payment.util;

public class MaskingUtils {

	private MaskingUtils() {
		
	}
	
	// CARD NUMBER MASKING
	
	public static String maskCardNumber(String cardNumber) {
		if(cardNumber==null || cardNumber.length()<4) {
			return Constants.MASKED_VALUE;
			}
		return "************"+cardNumber.substring(cardNumber.length()-4);
	}
	
	// ACCOUNT NUMBER MASKING
	
	public static String maskAccountNumber(String accountNumber) {
		if(accountNumber == null || accountNumber.length()<4) {
			return Constants.MASKED_VALUE;
		}
		
		return "XXXXXX"+accountNumber.substring(accountNumber.length()-4);
	}
	
	// IFSC MASKIGN
	
		public static String maskIfscNumber(String ifsc) {
			if(ifsc == null || ifsc.length()<4) {
				return Constants.MASKED_VALUE;
			}
			
			return "XXXX"+ifsc.substring(ifsc.length()-2);
		}
	
	// EMAIL MASKING
	
	 public static String maskEmail(String email) {
		 if(email==null || !email.contains("@")) {
			 return Constants.MASKED_VALUE;
			 }
		  String[] parts=email.split("@");
		  
		  return parts[0].charAt(0)+"****@"+parts[1];
	 }
	
	}
