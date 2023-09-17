package com.aspire.loan.controllers;


import com.aspire.loan.entities.PaymentTerm;
import com.aspire.loan.entities.Repayment;
import com.aspire.loan.entities.enums.LoanStatus;
import com.aspire.loan.entities.enums.PaymentTermStatus;
import com.aspire.loan.request.RepaymentRequestDTO;
import com.aspire.loan.response.LoanResponseDTO;
import com.aspire.loan.services.LoanService;
import com.aspire.loan.services.PaymentTermService;
import com.aspire.loan.services.RepaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {
    private final LoanService loanService;
    private final PaymentTermService paymentTermService;
    private final RepaymentService repaymentService;

    @GetMapping("/{loanId}")
    ResponseEntity<LoanResponseDTO> getLoanApplication(@PathVariable Long loanId, @RequestHeader String userId) {
        LoanResponseDTO loan = loanService.getLoanById(loanId, userId);
        return ResponseEntity.ok(loan);
    }

    @GetMapping("/{loanId}/paymentTerms")
    ResponseEntity<List<PaymentTerm>> getPaymentTerms(@PathVariable Long loanId, @RequestParam(name = "statuses", required = false) Set<PaymentTermStatus> statuses) {
        List<PaymentTerm> paymentTerms = paymentTermService.getPaymentTermsForLoanByStatus(loanId, statuses);
        return ResponseEntity.ok(paymentTerms);
    }

    @GetMapping("/{loanId}/repayments")
    ResponseEntity<List<Repayment>> getRepayments(@PathVariable Long loanId) {
        List<Repayment> loanRepayments = repaymentService.getAllRepayments(loanId);
        return ResponseEntity.ok(loanRepayments);
    }

    @PostMapping("/{loanId}/repayments")
    ResponseEntity<LoanResponseDTO> repayLoan(@PathVariable Long loanId, @RequestBody RepaymentRequestDTO requestDTO,  @RequestHeader String userId) {
        requestDTO.setUserId(userId);
        LoanResponseDTO loanResponseDTO = loanService.repayLoan(loanId, requestDTO);
        return ResponseEntity.ok(loanResponseDTO);
    }

    @GetMapping
    ResponseEntity<List<LoanResponseDTO>> getAllLoans(@RequestHeader String userId ,@RequestParam(name = "statuses", required = false) Set<LoanStatus> statuses) {
        List<LoanResponseDTO> loanResponseDTO = loanService.getLoansForUser(userId, statuses);
        return ResponseEntity.ok(loanResponseDTO);
    }

}
