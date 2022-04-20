package com.theledgerco.command;

import com.theledgerco.vo.BalanceRequest;
import com.theledgerco.vo.BalanceResponse;
import com.theledgerco.core.Ledger;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@SuperBuilder @Data
public class BalanceCommand extends LedgerCommand {
    private int afterEMIs;

    @Override
    public String execute() {
        BalanceResponse balance = Ledger.getInstance().getBalance(
                BalanceRequest.builder().bank(bank).borrower(borrower).afterEMIs(afterEMIs).build());
        return balance.toString();
    }
}
