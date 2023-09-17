package com.aspire.loan.entities;

import com.aspire.loan.entities.enums.RepaymentStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;


@Data
@Accessors(chain = true)
@Entity
@Table(name = "repayment")
public class Repayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loanId;
    private BigDecimal amount;

    private long createdAt;
    private String createdBy;

    @PrePersist
    protected void onCreate() {
        this.createdAt = System.currentTimeMillis();
    }
}
