package com.aspire.loan.controllers;


import com.aspire.loan.request.AdminActionRequestDTO;
import com.aspire.loan.response.LoanApplicationResponseDTO;
import com.aspire.loan.services.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/loan/application")
@RequiredArgsConstructor
public class AdminController {
    private final LoanApplicationService loanApplicationService;

    @PutMapping("/{loanId}/status")
    public ResponseEntity<LoanApplicationResponseDTO> updateLoanApplicationStatus(@PathVariable Long loanId,
                                                                                  @RequestBody AdminActionRequestDTO actionRequestDTO,
                                                                                  @RequestHeader String adminId) {
        actionRequestDTO.setAdminId(adminId);
        LoanApplicationResponseDTO responseDTO = loanApplicationService.loanApplicationStatusUpdate(loanId, actionRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<LoanApplicationResponseDTO>> getLoanApplications() {
        List<LoanApplicationResponseDTO> responseDTO = loanApplicationService.getFilteredLoanApplications();
        return ResponseEntity.ok(responseDTO);
    }
}
