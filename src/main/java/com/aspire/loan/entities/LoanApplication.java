package com.aspire.loan.entities;


import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.entities.enums.PaymentTermType;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "loan_application")
public class LoanApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private BigDecimal amountRequested;
    private BigDecimal amountApproved;
    private PaymentTermType termType;
    private Integer paymentTermCount;
    private String reviewedBy;
    private Long reviewedAt;
    private LoanApplicationStatus status;

    @Column(name = "createdAt", nullable = false, updatable = false)
    private long createdAt;
    private long updatedAt;
    private String createdBy;
    private String updatedBy;


    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        // TODO: update createdBy field
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = System.currentTimeMillis();
        // TODO: update  updatedBy field
    }

    public void approve() {
        if (status == LoanApplicationStatus.PENDING) {
            status = LoanApplicationStatus.APPROVED;
        } else {
            throw new IllegalStateException("Loan application status cannot be changed.");
        }
    }

    public void decline() {
        if (status == LoanApplicationStatus.PENDING) {
            status = LoanApplicationStatus.DECLINED;
        } else {
            throw new IllegalStateException("Loan application status cannot be changed.");
        }
    }
}
