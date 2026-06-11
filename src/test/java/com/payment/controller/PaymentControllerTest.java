package com.payment.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.payment.dto.request.PaymentRequest;
import com.payment.dto.response.PaymentResponse;
import com.payment.dto.response.PaymentStatusResponse;
import com.payment.entity.Payment;
import com.payment.entity.enums.PaymentStatus;
import com.payment.facade.PaymentFacade;
import com.payment.service.ReportService;

@ExtendWith(MockitoExtension.class)
class PaymentControllerTest {

    @Mock
    private PaymentFacade paymentFacade;

    @Mock
    private ReportService reportService;

    @InjectMocks
    private PaymentController paymentController;

    @Test
    void shouldCreatePaymentSuccessfully() {

        PaymentRequest request = new PaymentRequest();

        PaymentResponse response =
        PaymentResponse.builder()
                .paymentId(1L)
                .amount(BigDecimal.valueOf(1000))
                .currency("INR")
                .status(PaymentStatus.INITIATED)
                .message("Payment Created")
                .build();

        when(paymentFacade.createPayment(request))
                .thenReturn(response);

        ResponseEntity<PaymentResponse> result =
                paymentController.createPayment(request);

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED,
                result.getStatusCode());

        assertEquals(response,
                result.getBody());

        verify(paymentFacade, times(1))
                .createPayment(request);
    }

    @Test
    void shouldGetPaymentStatusSuccessfully() {

        Long paymentId = 1L;

        PaymentStatusResponse response =
                PaymentStatusResponse.builder()
                        .paymentId(1L)
                        .status(PaymentStatus.SUCCESS)
                        .amount(BigDecimal.valueOf(1000))
                        .currency("INR")
                        .transactionReference("TX123")
                        .gatewayReference("GW123")
                        .build();

        when(paymentFacade.getPaymentStatus(paymentId))
                .thenReturn(response);

        ResponseEntity<PaymentStatusResponse> result =
                paymentController.getPaymentStatus(paymentId);

        assertNotNull(result);

        assertEquals(HttpStatus.OK,
                result.getStatusCode());

        assertEquals(response,
                result.getBody());

        verify(paymentFacade, times(1))
                .getPaymentStatus(paymentId);
    }

    @Test
    void shouldReturnReportByStatus() {

        Payment payment = new Payment();
        payment.setStatus(PaymentStatus.SUCCESS);

        List<Payment> payments =
                List.of(payment);

        when(reportService.getByStatus(
                PaymentStatus.SUCCESS))
                .thenReturn(payments);

        ResponseEntity<List<Payment>> result =
                paymentController.report(
                        PaymentStatus.SUCCESS);

        assertNotNull(result);

        assertEquals(HttpStatus.OK,
                result.getStatusCode());

        assertEquals(payments,
                result.getBody());

        verify(reportService, times(1))
                .getByStatus(
                        PaymentStatus.SUCCESS);
    }
}