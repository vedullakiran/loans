package com.aspire.loan.repositories;

import com.aspire.loan.entities.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {
    List<Repayment> getRepaymentByLoanId(Long id);
}
