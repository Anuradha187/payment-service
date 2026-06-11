package com.payment.processor;

import com.payment.dto.request.PaymentRequest;
import com.payment.entity.Payment;
import com.payment.entity.enums.PaymentMethod;

public interface PaymentProcessor {
	
	PaymentMethod getPaymentMethod();
	void process(Payment payment, PaymentRequest request);

}

