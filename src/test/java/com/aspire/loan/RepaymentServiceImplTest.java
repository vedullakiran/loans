package com.aspire.loan;

import com.aspire.loan.entities.Repayment;
import com.aspire.loan.repositories.RepaymentRepository;
import com.aspire.loan.services.impl.RepaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RepaymentServiceImplTest {

    @InjectMocks
    private RepaymentServiceImpl repaymentService;

    private RepaymentRepository repaymentRepository;

    @BeforeEach
    public void setup() {
        repaymentRepository = mock(RepaymentRepository.class);
        repaymentService = new RepaymentServiceImpl(repaymentRepository);
    }

    @Test
    public void testGetAllRepayments() {
        Long loanId = 1L;
        List<Repayment> repayments = new ArrayList<>();
        when(repaymentRepository.getRepaymentByLoanId(loanId)).thenReturn(repayments);

        List<Repayment> retrievedRepayments = repaymentService.getAllRepayments(loanId);

        assertNotNull(retrievedRepayments);
        assertEquals(repayments, retrievedRepayments);
    }

    @Test
    public void testGetRepaymentById() {
        Long repaymentId = 1L;
        Repayment repayment = new Repayment();
        repayment.setId(repaymentId);

        when(repaymentRepository.findById(repaymentId)).thenReturn(Optional.of(repayment));

        Repayment retrievedRepayment = repaymentService.getRepaymentById(repaymentId);

        assertNotNull(retrievedRepayment);
        assertEquals(repaymentId, retrievedRepayment.getId());
    }

    @Test
    public void testCreateRepayment() {
        Repayment repayment = new Repayment();
        when(repaymentRepository.save(repayment)).thenReturn(repayment);

        Repayment createdRepayment = repaymentService.createRepayment(repayment);

        assertNotNull(createdRepayment);
        assertEquals(repayment, createdRepayment);
    }
}

