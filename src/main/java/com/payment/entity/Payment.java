package com.payment.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.payment.entity.enums.PaymentMethod;
import com.payment.entity.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments", uniqueConstraints= {
		@UniqueConstraint(columnNames="idempotency_key")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Payment {
 
	 @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	 
	@Column(unique=true,nullable=false, name="idempotency_key")
	 private String idempotencyKey;
 
	@Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
 

	@Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
 
    @Column(nullable = false , precision=14,scale=2)
    private BigDecimal amount;
 
    @Column(nullable = false, length=3)
    private String currency;
 
    @Column(nullable = false, length=50)
    private String customerId;
    
    @Column(name="retry_count")
    private Integer retryCount;
   
 
    private String transactionId;
 
    private String gatewayReference;
 
    private String remarks;
 
    @Column(name = "created_at")
    private LocalDate createdAt;
 
    @Column(name = "updated_at")
    private LocalDate updatedAt;
 
    @PrePersist
    public void prePersist() {

        this.createdAt = LocalDate.now();

        this.updatedAt = LocalDate.now();
        
        if(retryCount==null) {
        	retryCount=0;
        }

    }
    
    @PreUpdate
    public void preUpdate() {
    	this.updatedAt=LocalDate.now();
    }
 
	 }
 
