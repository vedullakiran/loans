package com.aspire.loan.command;

import com.aspire.loan.entities.enums.LoanApplicationStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StatusUpdateCommandFactory {
    private final Map<LoanApplicationStatus, StatusUpdateCommand> commandMap;

    @Autowired
    public StatusUpdateCommandFactory(List<StatusUpdateCommand> commands) {
        this.commandMap = commands.stream()
                .collect(Collectors.toMap(StatusUpdateCommand::getStatus, Function.identity()));
    }

    public StatusUpdateCommand getCommand(LoanApplicationStatus status) {
        return commandMap.get(status);
    }
}
