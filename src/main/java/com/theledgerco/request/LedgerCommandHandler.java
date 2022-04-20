package com.theledgerco.request;

import com.theledgerco.command.LedgerCommand;

public class LedgerCommandHandler {
    private static final LedgerCommandHandler inst = new LedgerCommandHandler();
    private LedgerCommandHandler() {}

    public synchronized static LedgerCommandHandler getInstance() {
        return inst;
    }

    public String submitCommand(String command) {
        LedgerCommand ledgerCommand = LedgerCommandParser.getInstance().parseCommand(command);
        String response = LedgerCommandExecutor.getInstance().executeCommand(ledgerCommand);
        return response;
    }

}
