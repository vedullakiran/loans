package com.aspire.loan;

import com.aspire.loan.entities.Loan;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.PaymentTerm;
import com.aspire.loan.entities.enums.PaymentTermStatus;
import com.aspire.loan.repositories.PaymentTermRepository;
import com.aspire.loan.services.impl.PaymentTermServiceImpl;
import com.aspire.loan.strategy.paymentTerm.PaymentTermGeneratorFactory;
import com.aspire.loan.strategy.paymentTerm.PaymentTermGeneratorStrategy;
import com.aspire.loan.strategy.paymentTerm.WeeklyPaymentTermGeneratorGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@SpringBootTest
public class PaymentTermServiceTest {

    @InjectMocks
    private PaymentTermServiceImpl paymentTermService;

    private PaymentTermGeneratorFactory paymentTermGeneratorFactory;

    private PaymentTermRepository paymentTermRepository;
    private PaymentTermGeneratorStrategy paymentTermGeneratorStrategy;

    @BeforeEach
    public void setup() {
        paymentTermGeneratorFactory = mock(PaymentTermGeneratorFactory.class);
        paymentTermRepository = mock(PaymentTermRepository.class);
        paymentTermGeneratorStrategy = mock(WeeklyPaymentTermGeneratorGenerator.class);
        paymentTermService = new PaymentTermServiceImpl(paymentTermGeneratorFactory, paymentTermRepository);
    }

    @Test
    public void testCreatePaymentTerms() {
        Loan loan = new Loan();
        LoanApplication application = new LoanApplication();
        List<PaymentTerm> paymentTerms = new ArrayList<>();
        when(paymentTermGeneratorFactory.getPaymentTermGenerator(any())).thenReturn(paymentTermGeneratorStrategy);
        when(paymentTermRepository.saveAll(paymentTerms)).thenReturn(paymentTerms);

        List<PaymentTerm> createdPaymentTerms = paymentTermService.createPaymentTerms(loan, application);

        assertNotNull(createdPaymentTerms);
        assertEquals(paymentTerms, createdPaymentTerms);
    }

    @Test
    public void testGetPaymentTermsForLoanByStatus() {
        Long loanId = 1L;
        Set<PaymentTermStatus> statusSet = new HashSet<>(Arrays.asList(PaymentTermStatus.PENDING, PaymentTermStatus.PAID));
        List<PaymentTerm> paymentTerms = new ArrayList<>();
        when(paymentTermRepository.findByLoanIdAndStatusInOrderByDueDateAsc(loanId, statusSet)).thenReturn(paymentTerms);

        List<PaymentTerm> retrievedPaymentTerms = paymentTermService.getPaymentTermsForLoanByStatus(loanId, statusSet);

        assertNotNull(retrievedPaymentTerms);
        assertEquals(paymentTerms, retrievedPaymentTerms);

        retrievedPaymentTerms = paymentTermService.getPaymentTermsForLoanByStatus(loanId, new HashSet<>());
        assertNotNull(retrievedPaymentTerms);
        assertEquals(paymentTerms, retrievedPaymentTerms);
    }

    @Test
    public void testUpdatePaymentTerms() {
        List<PaymentTerm> paymentTerms = new ArrayList<>();
        when(paymentTermRepository.saveAll(paymentTerms)).thenReturn(paymentTerms);

        List<PaymentTerm> updatedPaymentTerms = paymentTermService.updatePaymentTerms(paymentTerms);

        assertNotNull(updatedPaymentTerms);
        assertEquals(paymentTerms, updatedPaymentTerms);
    }
}

