package com.aspire.loan;


import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.PaymentTerm;
import com.aspire.loan.strategy.paymentTerm.WeeklyPaymentTermGeneratorGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

public class WeeklyPaymentTermGeneratorGeneratorTest {

    @InjectMocks
    private WeeklyPaymentTermGeneratorGenerator paymentTermGenerator;

    @BeforeEach
    public void setup() {
        paymentTermGenerator = new WeeklyPaymentTermGeneratorGenerator();
    }

    @Test
    public void testGeneratePaymentTerms() {
        // Create a sample loan and loan application
        Loan loan = new Loan();
        loan.setId(1L);
        loan.setUserId("user123");
        loan.setAmountSanctioned(new BigDecimal(1000));

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setPaymentTermCount(4);
        loanApplication.setCreatedAt(Instant.now().toEpochMilli());

        // Generate payment terms using the generator
        List<PaymentTerm> paymentTerms = paymentTermGenerator.generatePaymentTerms(loan, loanApplication);

        // Verify that the generated payment terms match the expected count
        assertEquals(4, paymentTerms.size());

        // Verify that payment terms have correct due dates and amounts
        long nextDueDate = Instant.ofEpochMilli(loanApplication.getCreatedAt()).plus(7, ChronoUnit.DAYS).toEpochMilli();
        BigDecimal expectedAmountPerTerm = new BigDecimal("250.00");

        for (int i = 0; i < paymentTerms.size(); i++) {
            PaymentTerm paymentTerm = paymentTerms.get(i);
            // Verify due date
            assertEquals(nextDueDate, paymentTerm.getDueDate());
            nextDueDate = Instant.ofEpochMilli(nextDueDate).plus(7, ChronoUnit.DAYS).toEpochMilli();

            // Verify due amount
            assertEquals(expectedAmountPerTerm, paymentTerm.getTermAmount());

            // Verify other properties as needed
            assertEquals(1L, paymentTerm.getLoanId());
        }
    }
}
