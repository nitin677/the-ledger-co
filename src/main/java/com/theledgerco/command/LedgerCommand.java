package com.theledgerco.command;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Data
public abstract class LedgerCommand {
    public enum Values {LOAN, PAYMENT, BALANCE;}

    protected String bank;
    protected String borrower;

    public abstract String execute();
}
