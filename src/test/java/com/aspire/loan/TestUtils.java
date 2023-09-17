package com.aspire.loan;

import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.entities.enums.PaymentTermType;
import com.aspire.loan.request.AdminActionRequestDTO;
import com.aspire.loan.request.LoanApplicationRequestDTO;
import com.aspire.loan.request.UserLoanApplicationRequestDTO;
import org.checkerframework.checker.units.qual.A;

import java.math.BigDecimal;

public class TestUtils {
    public static LoanApplicationRequestDTO getLoanApplicationRequestDTO() {
        return new LoanApplicationRequestDTO()
                .setUserId("user1")
                .setAmount(BigDecimal.TEN)
                .setTermType(PaymentTermType.WEEKLY)
                .setPaymentTermCount(10);
    }

    public static LoanApplication getLoanApplication() {
        return new LoanApplication()
                .setId(1L)
                .setUserId("user1")
                .setAmountRequested(BigDecimal.TEN)
                .setTermType(PaymentTermType.WEEKLY)
                .setStatus(LoanApplicationStatus.PENDING)
                .setPaymentTermCount(10);
    }

    public static UserLoanApplicationRequestDTO getUserLoanApplicationRequestDTO() {
        return new UserLoanApplicationRequestDTO()
                .setUserId("user1");
    }

    public static AdminActionRequestDTO getAdminActionRequestDTO() {
        return new AdminActionRequestDTO().setAdminId("adminId")
                .setApprovedAmount(BigDecimal.valueOf(10000))
                .setStatus(LoanApplicationStatus.DECLINED);
    }
}
