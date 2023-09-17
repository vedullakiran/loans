package com.aspire.loan.controllers;


import com.aspire.loan.request.LoanApplicationRequestDTO;
import com.aspire.loan.request.UserLoanApplicationRequestDTO;
import com.aspire.loan.response.LoanApplicationResponseDTO;
import com.aspire.loan.services.LoanApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/loan/application")
@RequiredArgsConstructor
public class UserController {
    private final LoanApplicationService loanApplicationServiceImpl;

    @PostMapping
    public ResponseEntity<LoanApplicationResponseDTO> createLoanApplication(@RequestBody LoanApplicationRequestDTO requestDTO, @RequestHeader String userId) {
        requestDTO.setUserId(userId);
        LoanApplicationResponseDTO createdLoanApplication = loanApplicationServiceImpl.createLoanApplication(requestDTO);
        return ResponseEntity.ok(createdLoanApplication);
    }

    @GetMapping("/all")
    public ResponseEntity<List<LoanApplicationResponseDTO>> getUserLoanApplications(@RequestHeader String userId) {
        List<LoanApplicationResponseDTO> userLoanApplications = loanApplicationServiceImpl.getUserLoanApplications(userId);
        return ResponseEntity.ok(userLoanApplications);
    }

    @GetMapping("/{loanApplicationId}")
    ResponseEntity<LoanApplicationResponseDTO> getLoanApplication(@PathVariable Long loanApplicationId) {
        LoanApplicationResponseDTO loanApplication = loanApplicationServiceImpl.getLoanApplicationById(loanApplicationId);
        return ResponseEntity.ok(loanApplication);
    }
}
