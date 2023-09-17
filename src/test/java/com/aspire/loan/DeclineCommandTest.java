package com.aspire.loan;

import static org.junit.jupiter.api.Assertions.*;

import com.aspire.loan.command.impl.DeclineCommand;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.enums.LoanApplicationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeclineCommandTest {

    private DeclineCommand declineCommand;

    @BeforeEach
    public void setup() {
        declineCommand = new DeclineCommand();
    }

    @Test
    public void testExecute() {
        // Create a sample loan application
        LoanApplication loanApplication = new LoanApplication().setStatus(LoanApplicationStatus.PENDING);

        // Execute the command
        declineCommand.execute(loanApplication, null);

        // Verify that the loan application was declined
        assertEquals(LoanApplicationStatus.DECLINED, loanApplication.getStatus());
    }

    @Test
    public void testGetStatus() {
        assertEquals(LoanApplicationStatus.DECLINED, declineCommand.getStatus());
    }
}
