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
public class Repayment extends BaseEntity {
    private Long loanId;
    private BigDecimal amount;
    private String createdBy;

    public Repayment setId(Long id) {
        super.setId(id);
        return this;
    }
}
