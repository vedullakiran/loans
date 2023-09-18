package com.aspire.loan;


import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
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
        LoanApplication loanApplication = new LoanApplication().setId(1L)
                .setAmountRequested(BigDecimal.valueOf(1000))
                .setReviewedAt(new Date())
                .setPaymentTermCount(4);

        Loan loan = new Loan().setId(1L).setLoanApplication(loanApplication);



        // Generate payment terms using the generator
        List<PaymentTerm> paymentTerms = paymentTermGenerator.generatePaymentTerms(loan, loanApplication);

        // Verify that the generated payment terms match the expected count
        assertEquals(4, paymentTerms.size());

        // Verify that payment terms have correct due dates and amounts

        Date nextDueDate = loanApplication.getReviewedAt();
        BigDecimal expectedAmountPerTerm = new BigDecimal("250.00");

        for (int i = 0; i < paymentTerms.size(); i++) {
            PaymentTerm paymentTerm = paymentTerms.get(i);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nextDueDate);

            calendar.add(Calendar.DATE, 7);
            nextDueDate = calendar.getTime();
            // Verify due date
            assertEquals(nextDueDate, paymentTerm.getDueDate());

            // Verify due amount
            assertEquals(expectedAmountPerTerm, paymentTerm.getTermAmount());

            // Verify other properties as needed
            assertEquals(1L, paymentTerm.getLoanId());
        }
    }
}
