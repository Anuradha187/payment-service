package com.payment.service;

import com.payment.dto.request.PaymentRequest;
import com.payment.dto.response.PaymentResponse;
import com.payment.dto.response.PaymentStatusResponse;
import com.payment.entity.Payment;

public interface PaymentService {
	
	Payment createBasePayment(PaymentRequest request);
	PaymentStatusResponse getPaymentStatus(Long paymentId);
	PaymentResponse buildPaymentResponse(Payment payment);

}