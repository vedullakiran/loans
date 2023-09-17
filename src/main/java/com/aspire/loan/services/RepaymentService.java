package com.aspire.loan.services;

import com.aspire.loan.entities.Repayment;

import java.util.List;

public interface RepaymentService {
    List<Repayment> getAllRepayments(Long id);

    Repayment getRepaymentById(Long id);

    Repayment createRepayment(Repayment repayment);
}
