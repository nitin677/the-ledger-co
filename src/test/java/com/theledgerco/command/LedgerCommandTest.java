package com.theledgerco.command;

import com.theledgerco.core.Ledger;
import com.theledgerco.core.Loan;
import com.theledgerco.core.Payment;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class LedgerCommandTest {

    @AfterEach
    public void cleanUp() {
        Ledger.getInstance().cleanUp();
    }

    @Test
    public void testPaymentCommandWithoutLoan() {
        PaymentCommand paymentCommand =
                PaymentCommand.builder().bank("IDIDI").borrower("Dale1").paymentAmount(100d).afterEMIs(1).build();
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class, () -> paymentCommand.execute());
        Assertions.assertTrue(e.getMessage().equals("No loan exists for borrower Dale1 in bank IDIDI"));
    }

    @Test
    public void testBalanceCommandWithoutLoan() {
        BalanceCommand balanceCommand =
                BalanceCommand.builder().bank("IDIDI").borrower("Dale2").afterEMIs(1).build();
        IllegalArgumentException e = Assertions.assertThrows(
                IllegalArgumentException.class, () -> balanceCommand.execute());
        Assertions.assertTrue(e.getMessage().equals("No loan exists for borrower Dale2 in bank IDIDI"));
    }

    @Test
    public void testPaymentCommand() {
        LoanCommand loanCommand = LoanCommand.builder().bank("IDIDI").borrower("Dale3")
                                             .principal(1000d).term(1).interest(5f).build();
        loanCommand.execute();
        PaymentCommand paymentCommand =
                PaymentCommand.builder().bank("IDIDI").borrower("Dale3").paymentAmount(100d).afterEMIs(1).build();
        String result = paymentCommand.execute();
        Assertions.assertTrue(result.equals(""));
        Set<Payment> payments = Ledger.getInstance().getLoanAccount("IDIDI", "Dale3").getPayments();
        Assertions.assertNotNull(payments);
        Assertions.assertEquals(1, payments.size());
    }

    @Test
    public void testBalanceCommand() {
        LoanCommand loanCommand = LoanCommand.builder().bank("IDIDI").borrower("Dale4")
                                             .principal(1000d).term(1).interest(5f).build();
        loanCommand.execute();
        BalanceCommand balanceCommand =
                BalanceCommand.builder().bank("IDIDI").borrower("Dale4").afterEMIs(1).build();
        String result = balanceCommand.execute();
        Assertions.assertEquals("IDIDI Dale4 88 11", result);
    }
}
