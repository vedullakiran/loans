package com.aspire.loan.repositories;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.enums.LoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    List<Loan> findByStatusIsInAndLoanApplication_UserId(Set<LoanStatus> status, String userId);

    List<Loan> findByLoanApplication_UserId(String userId);

}
