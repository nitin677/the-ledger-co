package com.theledgerco.command;

import com.theledgerco.core.Ledger;
import com.theledgerco.core.Loan;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data @SuperBuilder
public class LoanCommand extends LedgerCommand {
    private Double principal;
    private Float interest;
    private int term;

    @Override
    public String execute() {
        Loan loan = Loan.builder().bank(bank).borrower(borrower).principal(principal)
                        .interest(interest).term(term).build();
        Ledger.getInstance().addLoan(loan);
        return "";
    }
}
