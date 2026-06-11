package com.payment.gateway;

import com.payment.entity.Payment;

public interface PaymentGateway {
	
	boolean process(Payment payment);

}
