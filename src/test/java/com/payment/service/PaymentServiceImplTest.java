package com.payment.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.payment.dto.request.CardPaymentDetails;
import com.payment.dto.request.PaymentRequest;
import com.payment.entity.Payment;
import com.payment.entity.enums.PaymentMethod;
import com.payment.exception.PaymentNotFoundException;
import com.payment.mapper.PaymentMapper;
import com.payment.repository.PaymentRepository;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private PaymentMapper paymentMapper;

    @Mock
    private AuditService auditService;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    // ---------- helper ----------
    private PaymentRequest buildRequest() {

        PaymentRequest request = new PaymentRequest();

        CardPaymentDetails details = new CardPaymentDetails();
        details.setCardHolderName("John");
        details.setCardNumber("1234567812345678");
        details.setExpiryMonth("12");
        details.setExpiryYear("2030");
        details.setCvv("123");

        request.setAmount(BigDecimal.valueOf(1000));
        request.setCurrency("INR");
        request.setCustomerId("CUST1");
        request.setIdempotencyKey("IDEMP1");
        request.setPaymentMethod(PaymentMethod.CARD);
        request.setPaymentDetails(details);

        return request;
    }

   
    // ---------- IDEMPOTENCY RETURN ----------
    @Test
    void shouldReturnExistingPayment() {

        PaymentRequest request = buildRequest();

        Payment existing = new Payment();
        existing.setAmount(request.getAmount());
        existing.setCustomerId(request.getCustomerId());
        existing.setPaymentMethod(request.getPaymentMethod());

        when(paymentRepository.findByIdempotencyKey("IDEMP1"))
                .thenReturn(Optional.of(existing));

        Payment result = paymentService.createBasePayment(request);

        assertEquals(existing, result);

        verify(paymentRepository, never()).save(any());
    }

    // ---------- IDEMPOTENCY MISMATCH ----------
    @Test
    void shouldThrowOnIdempotencyMismatch() {

        PaymentRequest request = buildRequest();

        Payment existing = new Payment();
        existing.setAmount(BigDecimal.valueOf(500));
        existing.setCustomerId("OTHER");
        existing.setPaymentMethod(PaymentMethod.UPI);

        when(paymentRepository.findByIdempotencyKey("IDEMP1"))
                .thenReturn(Optional.of(existing));

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.createBasePayment(request));
    }

    // ---------- INVALID CURRENCY ----------
    @Test
    void shouldThrowInvalidCurrency() {

        PaymentRequest request = buildRequest();
        request.setCurrency("ABC");

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.createBasePayment(request));
    }

    // ---------- NULL AMOUNT ----------
    @Test
    void shouldThrowNullAmount() {

        PaymentRequest request = buildRequest();
        request.setAmount(null);

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.createBasePayment(request));
    }

    // ---------- NEGATIVE AMOUNT ----------
    @Test
    void shouldThrowNegativeAmount() {

        PaymentRequest request = buildRequest();
        request.setAmount(BigDecimal.valueOf(-10));

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.createBasePayment(request));
    }

    // ---------- INVALID PAYMENT DETAILS ----------
    @Test
    void shouldThrowInvalidPaymentDetails() {

        PaymentRequest request = buildRequest();
        request.setPaymentDetails(new com.payment.dto.request.UpiPaymentDetails());

        assertThrows(IllegalArgumentException.class,
                () -> paymentService.createBasePayment(request));
    }

    // ---------- GET PAYMENT STATUS SUCCESS ----------
    @Test
    void shouldReturnPaymentStatus() {

        Payment payment = new Payment();

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.of(payment));

        when(paymentMapper.toPaymentStatusResponse(payment))
                .thenReturn(null);

        assertDoesNotThrow(() ->
                paymentService.getPaymentStatus(1L));
    }

    // ---------- PAYMENT NOT FOUND ----------
    @Test
    void shouldThrowPaymentNotFound() {

        when(paymentRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(PaymentNotFoundException.class,
                () -> paymentService.getPaymentStatus(1L));
    }

    // ---------- BUILD RESPONSE ----------
    @Test
    void shouldBuildPaymentResponse() {

        Payment payment = new Payment();

        when(paymentMapper.toPaymentResponse(payment))
                .thenReturn(null);

        assertDoesNotThrow(() ->
                paymentService.buildPaymentResponse(payment));
    }
}