package com.payment.gateway;
import java.util.Random;

import org.springframework.stereotype.Component;

import com.payment.entity.Payment;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j

public class MockPaymentGateway implements PaymentGateway {
 
    private final Random random = new Random();
 
    @Override
    public boolean process(Payment payment) {
 
        // simulate network instability (70% fail, 30% success)

        boolean result = random.nextInt(10) < 3;
        log.info("Gateway call for payment {} result = {}", payment.getId(), result);
        return result;

    }

}
 