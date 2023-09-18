package com.aspire.loan.entities;


import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.entities.enums.PaymentTermType;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "loan_application")
public class LoanApplication extends BaseEntity {
    private String userId;
    private BigDecimal amountRequested;
    private PaymentTermType termType;
    private Integer paymentTermCount;
    private String reviewedBy;
    private LoanApplicationStatus status;
    private String createdBy;
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reviewedAt;

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

    public LoanApplication setId(Long id) {
        super.setId(id);
        return this;
    }
}
