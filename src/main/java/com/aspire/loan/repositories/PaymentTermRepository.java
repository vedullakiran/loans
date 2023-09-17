package com.aspire.loan.repositories;

import com.aspire.loan.entities.PaymentTerm;
import com.aspire.loan.entities.enums.PaymentTermStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PaymentTermRepository extends JpaRepository<PaymentTerm, Long> {
    List<PaymentTerm> findByLoanIdAndStatusInOrderByDueDateAsc(Long loanId, Set<PaymentTermStatus> statusSet);

    List<PaymentTerm> findByLoanIdOrderByDueDateAsc(Long loanId);
}
