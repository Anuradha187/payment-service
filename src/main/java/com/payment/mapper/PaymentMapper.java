package com.payment.mapper;

import java.time.LocalDate;
import org.springframework.stereotype.Component;
import com.payment.dto.request.BankTransferPaymentDetails;
import com.payment.dto.request.CardPaymentDetails;
import com.payment.dto.request.PaymentRequest;
import com.payment.dto.request.PayoutPaymentDetails;
import com.payment.dto.request.SubscriptionPaymentDetails;
import com.payment.dto.request.UpiPaymentDetails;
import com.payment.dto.request.WalletPaymentDetails;
import com.payment.dto.response.PaymentResponse;
import com.payment.dto.response.PaymentStatusResponse;
import com.payment.entity.BankTransferPayment;
import com.payment.entity.CardPayment;
import com.payment.entity.Payment;
import com.payment.entity.PayoutPayment;
import com.payment.entity.SubscriptionPayment;
import com.payment.entity.UpiPayment;
import com.payment.entity.WalletPayment;
import com.payment.entity.enums.PaymentStatus;
import com.payment.util.MaskingUtils;


@Component
public class PaymentMapper {
	
	//common payment mapping
	
	public Payment toPaymentEntity(PaymentRequest request) {
		
		Payment payment=new Payment();
		payment.setAmount(request.getAmount());	
	    payment.setCurrency(request.getCurrency());
	    payment.setCustomerId(request.getCustomerId());
	    payment.setPaymentMethod(request.getPaymentMethod());
	    payment.setStatus(PaymentStatus.INITIATED);
	    payment.setIdempotencyKey(request.getIdempotencyKey());	  
	    payment.setCreatedAt(LocalDate.now());
	    payment.setUpdatedAt(LocalDate.now());
	    
	    return payment;
	}
	
	// CARD PAYMENT
	public CardPayment toCardPaymentEntity(Payment payment, CardPaymentDetails details) {
		CardPayment cardPayment= new CardPayment();
		cardPayment.setPayment(payment);
		cardPayment.setCardHolderName(details.getCardHolderName());
		cardPayment.setMaskedCardNumber(MaskingUtils.maskCardNumber(details.getCardNumber()));
		cardPayment.setExpiryMonth(details.getExpiryMonth());
		cardPayment.setExpiryYear(details.getExpiryYear());
		
		return cardPayment;
	}
	
	//UPI PAYMENT
	
	public UpiPayment toUpiPaymentEntity(Payment payment, UpiPaymentDetails details) {
		
		UpiPayment upiPayment=new UpiPayment();
		upiPayment.setPayment(payment);
        upiPayment.setVpa(details.getVpa());
        
		return upiPayment;
		}
	
	// BANK TRANSFER PAYMENT
	
	 public BankTransferPayment toBankTransferEntity(Payment payment, BankTransferPaymentDetails details) {
		 
		 BankTransferPayment bankTransfer=new BankTransferPayment();
		 bankTransfer.setPayment(payment);
		 bankTransfer.setAccountHoldername(details.getAccountHolderName());
		 bankTransfer.setAccountNumber(MaskingUtils.maskAccountNumber(details.getAccountNumber()));
		 bankTransfer.setIfsc(MaskingUtils.maskIfscNumber(details.getIfsc()));
		  return bankTransfer;
		 }
	
	// WALLET PAYMENT
	
	 public WalletPayment toWalletPaymentEntity(Payment payment, WalletPaymentDetails details) {
		 
		 WalletPayment walletPayment=new WalletPayment();
		 walletPayment.setPayment(payment);
		 walletPayment.setWalletProvider(details.getWalletProvider());
		 walletPayment.setMobileNumber(details.getMobileNumber());
		 
		 return walletPayment;
	 }
	
	// PAYOUT PAYMENT
	 
	 public PayoutPayment toPayoutPayment(Payment payment, PayoutPaymentDetails details) {
		 
		 PayoutPayment payoutPayment=new PayoutPayment();
		 payoutPayment.setPayment(payment);
		 payoutPayment.setMerchantId(details.getMerchantId());
		 payoutPayment.setIfsc(MaskingUtils.maskIfscNumber(details.getIfsc()));
		 payoutPayment.setBeneficiaryName(details.getBeneficiaryName());
		 payoutPayment.setBankAccountNumber(MaskingUtils.maskAccountNumber(details.getBankAccountNumber()));
		 payoutPayment.setPayoutAmount(details.getPayoutAmount());
		 
		 return payoutPayment;
		 
	 }

	 // SUBSCRIPTION PAYMENT
	 
	 public SubscriptionPayment toSubscriptionPaymentEntity(Payment payment, SubscriptionPaymentDetails details) {
		 
		 SubscriptionPayment subscriptionPayment=new SubscriptionPayment();
		 subscriptionPayment.setPayment(payment);
		 subscriptionPayment.setPlanId(details.getPlanId());
		 subscriptionPayment.setSubscriptionName(details.getSubscriptionName());
		 subscriptionPayment.setBillingCycle(details.getBillingCycle());
		 subscriptionPayment.setAutoDebit(details.getAutoDebit());
		 subscriptionPayment.setCustomerEmail(details.getCustomerEmail());
		 
		  return subscriptionPayment;
		 }
	 
	 // CREATE PAYMENT RESPONSE
	 
      public PaymentResponse toPaymentResponse(Payment payment) {
    	  return PaymentResponse.builder()
    			  .paymentId(payment.getId())
    			  .paymentMethod(payment.getPaymentMethod())
    			  .status(payment.getStatus())
    			  .amount(payment.getAmount())
    			  .currency(payment.getCurrency())
    			  .message("Payment Initiated Successfully")
    			  .createdAt(payment.getCreatedAt())
    			  .build();
      }
	 
	 // PAYMENT STATUS RESPONSE
      
      public PaymentStatusResponse toPaymentStatusResponse(Payment payment) {
    	  
    	  return PaymentStatusResponse.builder()
    			  .paymentId(payment.getId())
    			  .paymentMethod(payment.getPaymentMethod())
    			  .status(payment.getStatus())
    			  .amount(payment.getAmount())
    			  .currency(payment.getCurrency())
    			  .transactionReference(payment.getTransactionId())
    			  .gatewayReference(payment.getGatewayReference())
    			  .remarks(payment.getRemarks())
    			  .createdAt(payment.getCreatedAt())
    			  .updatedAt(payment.getUpdatedAt())
    			  .build();
      }

      }
