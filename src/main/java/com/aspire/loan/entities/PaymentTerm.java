package com.aspire.loan.entities;


import com.aspire.loan.entities.enums.PaymentTermStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "payment_terms")
public class PaymentTerm extends BaseEntity {
    private Long loanId;
    private Date dueDate;
    private BigDecimal termAmount;
    private PaymentTermStatus status;
    private BigDecimal amountPaid;

    public PaymentTerm setId(Long id) {
        super.setId(id);
        return this;
    }
}
