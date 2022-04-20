package com.theledgerco.core;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LoanAccountTest {

    @AfterEach
    public void cleanUp() {
        Ledger.getInstance().cleanUp();
    }

    @Test
    public void testGetTotalPayable() {
        Loan loan = Loan.builder().bank("IDIDI").borrower("Dale1").principal(10000d).term(5).interest(4f).build();
        LoanAccount loanAccount = LoanAccount.builder().loan(loan).build();
        Assertions.assertEquals(12000, loanAccount.getTotalPayable());
    }

    @Test
    public void testGetEMIAmount() {
        Loan loan = Loan.builder().bank("IDIDI").borrower("Dale1").principal(10000d).term(5).interest(4f).build();
        LoanAccount loanAccount = LoanAccount.builder().loan(loan).build();
        Assertions.assertEquals(200, loanAccount.getEMIAmount());
    }

    @Test
    public void testGetEMIAmountIsCeiling() {
        Loan loan = Loan.builder().bank("IDIDI").borrower("Dale1").principal(5000d).term(1).interest(6f).build();
        LoanAccount loanAccount = LoanAccount.builder().loan(loan).build();
        Assertions.assertEquals(442, loanAccount.getEMIAmount());
    }

    @Test
    public void testGetTotalEMIs() {
        Loan loan = Loan.builder().bank("IDIDI").borrower("Dale1").principal(10000d).term(5).interest(4f).build();
        LoanAccount loanAccount = LoanAccount.builder().loan(loan).build();
        Assertions.assertEquals(60, loanAccount.getTotalEMIs());
    }

    @Test
    public void testGetTotalEMIsIsCeiling() {
        Loan loan = Loan.builder().bank("IDIDI").borrower("Dale1").principal(5000d).term(1).interest(6f).build();
        LoanAccount loanAccount = LoanAccount.builder().loan(loan).build();
        Assertions.assertEquals(12, loanAccount.getTotalEMIs());
    }
}
