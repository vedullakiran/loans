package com.aspire.loan;

import com.aspire.loan.command.StatusUpdateCommand;
import com.aspire.loan.command.StatusUpdateCommandFactory;
import com.aspire.loan.command.impl.DeclineCommand;
import com.aspire.loan.entities.LoanApplication;
import com.aspire.loan.entities.enums.LoanApplicationStatus;
import com.aspire.loan.repositories.LoanApplicationRepository;
import com.aspire.loan.request.AdminActionRequestDTO;
import com.aspire.loan.request.LoanApplicationRequestDTO;
import com.aspire.loan.response.LoanApplicationResponseDTO;
import com.aspire.loan.services.LoanApplicationService;
import com.aspire.loan.services.impl.LoanApplicationServiceImpl;
import com.aspire.loan.validation.AccessControlValidation;
import com.aspire.loan.validation.LoanApplicationValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class LoanApplicationServiceTest {

    @InjectMocks
    private LoanApplicationService loanApplicationService;

    private LoanApplicationRepository loanApplicationRepository;
    private StatusUpdateCommandFactory statusUpdateCommandFactory;
    private LoanApplicationValidation loanApplicationValidation;

    private StatusUpdateCommand statusUpdateCommand;
    private AccessControlValidation accessControlValidation;

    @BeforeEach
    public void setup() {
        loanApplicationValidation = mock(LoanApplicationValidation.class);
        statusUpdateCommandFactory = mock(StatusUpdateCommandFactory.class);
        loanApplicationRepository = mock(LoanApplicationRepository.class);
        accessControlValidation = mock(AccessControlValidation.class);
        statusUpdateCommand = mock(DeclineCommand.class);
        loanApplicationService = new LoanApplicationServiceImpl(loanApplicationRepository, statusUpdateCommandFactory,
                loanApplicationValidation,accessControlValidation );
    }

    @Test
    public void testCreateLoanApplication() {
        LoanApplicationRequestDTO request = TestUtils.getLoanApplicationRequestDTO();

        LoanApplication loanApplication = TestUtils.getLoanApplication();

        when(loanApplicationRepository.save(any(LoanApplication.class))).thenReturn(loanApplication);
        LoanApplicationResponseDTO response = loanApplicationService.createLoanApplication(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(LoanApplicationStatus.PENDING, response.getStatus());

        verify(loanApplicationValidation, times(1)).validateLoanApplicationRequest(request);
        verify(loanApplicationRepository, times(1)).save(any(LoanApplication.class));
    }

    @Test
    public void testGetLoanApplicationById() {
        Long loanApplicationId = 1L;
        LoanApplication loanApplication = TestUtils.getLoanApplication();
        String userId = "userId";
        when(loanApplicationRepository.findById(loanApplicationId)).thenReturn(Optional.of(loanApplication));

        LoanApplicationResponseDTO response = loanApplicationService.getLoanApplicationById(loanApplicationId, userId);

        assertNotNull(response);
        assertEquals(loanApplicationId, response.getId());
        assertEquals(LoanApplicationStatus.PENDING, response.getStatus());

        verify(loanApplicationRepository, times(1)).findById(loanApplicationId);
    }

    @Test
    public void testGetUserLoanApplications() {
        String userId = "user1";
        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setId(1L);
        loanApplication.setStatus(LoanApplicationStatus.DECLINED);

        when(loanApplicationRepository.findByUserId(anyString())).thenReturn(Collections.singletonList(loanApplication));

        List<LoanApplicationResponseDTO> responseList = loanApplicationService.getUserLoanApplications(userId);

        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        LoanApplicationResponseDTO response = responseList.get(0);
        assertEquals(1L, response.getId());
        assertEquals(LoanApplicationStatus.DECLINED, response.getStatus());

        verify(loanApplicationRepository, times(1)).findByUserId(userId);
    }

    @Test
    public void testLoanApplicationStatusUpdate() {
        Long loanApplicationId = 1L;
        AdminActionRequestDTO requestDTO = TestUtils.getAdminActionRequestDTO();

        LoanApplication loanApplication = new LoanApplication();
        loanApplication.setId(loanApplicationId);
        loanApplication.setStatus(LoanApplicationStatus.DECLINED);

        when(loanApplicationRepository.findById(loanApplicationId)).thenReturn(Optional.of(loanApplication));
        when(statusUpdateCommandFactory.getCommand(any())).thenReturn(statusUpdateCommand);
//        doNothing().when(statusUpdateCommand.execute(any(), any()));

        LoanApplicationResponseDTO response = loanApplicationService.loanApplicationStatusUpdate(loanApplicationId, requestDTO);
        assertNotNull(response);
        assertEquals(loanApplicationId, response.getId());
        assertEquals(LoanApplicationStatus.DECLINED, response.getStatus());

        verify(loanApplicationValidation, times(1)).validateAdminActionRequest(requestDTO, loanApplication);
        verify(statusUpdateCommandFactory, times(1)).getCommand(LoanApplicationStatus.DECLINED);
        verify(loanApplicationRepository, times(1)).save(loanApplication);
    }

    @Test
    public void testGetFilteredLoanApplications() {
        // Create some sample loan applications for testing
        LoanApplication loanApp1 = new LoanApplication();
        loanApp1.setId(1L);
        loanApp1.setStatus(LoanApplicationStatus.PENDING);

        LoanApplication loanApp2 = new LoanApplication();
        loanApp2.setId(2L);
        loanApp2.setStatus(LoanApplicationStatus.APPROVED);

        List<LoanApplication> loanApplications = new ArrayList<>();
        loanApplications.add(loanApp1);
        loanApplications.add(loanApp2);

        when(loanApplicationRepository.findAll()).thenReturn(loanApplications);

        List<LoanApplicationResponseDTO> responseList = loanApplicationService.getFilteredLoanApplications();

        verify(loanApplicationRepository, times(1)).findAll();

        // Verify that the responseList contains the expected number of elements
        assertEquals(2, responseList.size());
    }

}

