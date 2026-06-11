package com.payment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.payment.entity.PaymentAudit;

@Repository
public interface AuditRepository extends JpaRepository<PaymentAudit ,Long>{

}
