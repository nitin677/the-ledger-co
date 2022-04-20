package com.theledgerco.request;

import com.theledgerco.core.Ledger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LedgerCommandHandlerTest {

    @AfterEach
    public void cleanUp() {
        Ledger.getInstance().cleanUp();
    }

    @Test
    public void testSubmitInvalidCommand() {
        Assertions.assertThrows(IllegalArgumentException.class,
                                () -> LedgerCommandHandler.getInstance().submitCommand("INVALID 123"));
    }

    @Test
    public void testSubmitInvalidLoanCommandFormat() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                                () -> LedgerCommandHandler.getInstance().submitCommand("LOAN 123"));
        Assertions.assertTrue(e.getMessage().startsWith("Invalid LOAN command passed"));
    }

    @Test
    public void testSubmitInvalidBalanceCommandFormat() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                                                             () -> LedgerCommandHandler.getInstance().submitCommand("BALANCE 123"));
        Assertions.assertTrue(e.getMessage().startsWith("Invalid BALANCE command passed"));
    }

    @Test
    public void testSubmitInvalidPaymentCommandFormat() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                                                             () -> LedgerCommandHandler.getInstance().submitCommand("PAYMENT 123"));
        Assertions.assertTrue(e.getMessage().startsWith("Invalid PAYMENT command passed"));
    }

    @Test
    public void testSubmitLoanAndBalanceCommands() {
        String loanResponse = LedgerCommandHandler.getInstance().submitCommand("LOAN IDIDI Dale 10000 5 4");
        String balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE IDIDI Dale 5");
        Assertions.assertEquals("IDIDI Dale 1000 55", balanceResponse);

        balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE IDIDI Dale 40");
        Assertions.assertEquals("IDIDI Dale 8000 20", balanceResponse);

        LedgerCommandHandler.getInstance().submitCommand("LOAN MBI Harry 2000 2 2");
        balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE MBI Harry 12");
        Assertions.assertEquals("MBI Harry 1044 12", balanceResponse);

        balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE MBI Harry 0");
        Assertions.assertEquals("MBI Harry 0 24", balanceResponse);

        balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE MBI Harry 24");
        Assertions.assertEquals("MBI Harry 2080 0", balanceResponse);
    }

    @Test
    public void testSubmitLoanPaymentAndBalanceCommands() {
        Ledger.getInstance().cleanUp();
        String loanResponse = LedgerCommandHandler.getInstance().submitCommand("LOAN IDIDI Dale 5000 1 6");
        LedgerCommandHandler.getInstance().submitCommand("PAYMENT IDIDI Dale 1000 5");

        String balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE IDIDI Dale 3");
        Assertions.assertEquals("IDIDI Dale 1326 9", balanceResponse);

        balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE IDIDI Dale 6");
        Assertions.assertEquals("IDIDI Dale 3652 4", balanceResponse);

        LedgerCommandHandler.getInstance().submitCommand("PAYMENT IDIDI Dale 1000 7");
        balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE IDIDI Dale 7");
        Assertions.assertEquals("IDIDI Dale 5094 1", balanceResponse);

        balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE IDIDI Dale 8");
        Assertions.assertEquals("IDIDI Dale 5300 0", balanceResponse);

        balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE IDIDI Dale 6");
        Assertions.assertEquals("IDIDI Dale 3652 4", balanceResponse);

        LedgerCommandHandler.getInstance().submitCommand("LOAN MBI Harry 10000 3 7");
        LedgerCommandHandler.getInstance().submitCommand("PAYMENT MBI Harry 5000 10");
        balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE MBI Harry 12");
        Assertions.assertEquals("MBI Harry 9044 10", balanceResponse);

        LedgerCommandHandler.getInstance().submitCommand("LOAN UON Shelly 15000 2 9");
        LedgerCommandHandler.getInstance().submitCommand("PAYMENT UON Shelly 7000 12");
        balanceResponse = LedgerCommandHandler.getInstance().submitCommand("BALANCE UON Shelly 12");
        Assertions.assertEquals("UON Shelly 15856 3", balanceResponse);
    }
}
