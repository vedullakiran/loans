package com.aspire.loan.services.impl;

import com.aspire.loan.entities.Repayment;
import com.aspire.loan.exceptions.EntityNotFoundException;
import com.aspire.loan.repositories.RepaymentRepository;
import com.aspire.loan.services.RepaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RepaymentServiceImpl implements RepaymentService {
    private final RepaymentRepository repository;

    @Override
    public List<Repayment> getAllRepayments(Long loanId) {
        return repository.getRepaymentByLoanId(loanId);
    }

    @Override
    public Repayment getRepaymentById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan application not found"));
    }

    @Override
    public Repayment createRepayment(Repayment repayment) {
        return repository.save(repayment);
    }
}
