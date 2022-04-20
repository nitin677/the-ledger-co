package com.theledgerco.request;

import com.theledgerco.command.LedgerCommand;

public class LedgerCommandExecutor {
    private static final LedgerCommandExecutor inst = new LedgerCommandExecutor();

    private LedgerCommandExecutor(){}

    public synchronized static LedgerCommandExecutor getInstance() {
        return inst;
    }

    public String executeCommand(LedgerCommand ledgerCommand) {
        return ledgerCommand.execute();
    }
}
