package com.payment.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.payment.entity.PaymentAudit;
import com.payment.repository.AuditRepository;

@ExtendWith(MockitoExtension.class)
class AuditServiceTest {

    @Mock
    private AuditRepository repository;

    @InjectMocks
    private AuditService auditService;

    @Test
    void shouldSaveAuditLogSuccessfully() {

        String txId = "TXN123";
        String action = "PAYMENT_CREATED";
        String user = "admin";

        auditService.log(txId, action, user);

        ArgumentCaptor<PaymentAudit> captor =
                ArgumentCaptor.forClass(PaymentAudit.class);

        verify(repository, times(1)).save(captor.capture());

        PaymentAudit savedAudit = captor.getValue();

        assertNotNull(savedAudit);
        assertEquals(txId, savedAudit.getTransactionId());
        assertEquals(action, savedAudit.getAction());
        assertEquals(user, savedAudit.getPerformedBy());

        assertNotNull(savedAudit.getTimeStamp());

        // verify timestamp is recent (within last few seconds)
        assertTrue(savedAudit.getTimeStamp()
                .isBefore(LocalDateTime.now().plusSeconds(2)));
    }
}
