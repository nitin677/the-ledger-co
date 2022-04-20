package com.theledgerco.request;

import com.theledgerco.command.BalanceCommand;
import com.theledgerco.command.LedgerCommand;
import com.theledgerco.command.LoanCommand;
import com.theledgerco.command.PaymentCommand;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class LedgerCommandParserTest {

    @Test
    public void testLoanCommandParsingSuccessful() {
        LedgerCommand cmd = LedgerCommandParser.getInstance().parseCommand("LOAN IDIDI Dale 10000 5 4");
        Assertions.assertTrue(cmd instanceof LoanCommand);
        LoanCommand loanCmd = (LoanCommand)cmd;
        Assertions.assertTrue(loanCmd.getBank().equals("IDIDI"));
        Assertions.assertTrue(loanCmd.getBorrower().equals("Dale"));
        Assertions.assertTrue(loanCmd.getPrincipal() == 10000d);
        Assertions.assertTrue(loanCmd.getTerm() == 5);
        Assertions.assertTrue(loanCmd.getInterest() == 4f);
    }

    @Test
    public void testPaymentCommandParsingSuccessful() {
        LedgerCommand cmd = LedgerCommandParser.getInstance().parseCommand("PAYMENT IDIDI Dale 1000 5");
        Assertions.assertTrue(cmd instanceof PaymentCommand);
        PaymentCommand paymentCommand = (PaymentCommand)cmd;
        Assertions.assertTrue(paymentCommand.getBank().equals("IDIDI"));
        Assertions.assertTrue(paymentCommand.getBorrower().equals("Dale"));
        Assertions.assertTrue(paymentCommand.getPaymentAmount() == 1000d);
        Assertions.assertTrue(paymentCommand.getAfterEMIs() == 5);
    }

    @Test
    public void testBalanceCommandParsingSuccessful() {
        LedgerCommand cmd = LedgerCommandParser.getInstance().parseCommand("BALANCE IDIDI Dale 3");
        Assertions.assertTrue(cmd instanceof BalanceCommand);
        BalanceCommand balanceCommand = (BalanceCommand)cmd;
        Assertions.assertTrue(balanceCommand.getBank().equals("IDIDI"));
        Assertions.assertTrue(balanceCommand.getBorrower().equals("Dale"));
        Assertions.assertTrue(balanceCommand.getAfterEMIs() == 3);
    }

    @Test
    public void testParseInvalidLoanCommandFormat() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                                                             () -> LedgerCommandParser.getInstance().parseCommand("LOAN 123"));
        Assertions.assertTrue(e.getMessage().startsWith("Invalid LOAN command passed"));
    }

    @Test
    public void testParseInvalidBalanceCommandFormat() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                                                             () -> LedgerCommandParser.getInstance().parseCommand("BALANCE 123"));
        Assertions.assertTrue(e.getMessage().startsWith("Invalid BALANCE command passed"));
    }

    @Test
    public void testParseInvalidPaymentCommandFormat() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                                                             () -> LedgerCommandParser.getInstance().parseCommand("PAYMENT 123"));
        Assertions.assertTrue(e.getMessage().startsWith("Invalid PAYMENT command passed"));
    }

    @Test
    public void testParseInvalidCommand() {
        Assertions.assertThrows(IllegalArgumentException.class,
                                () -> LedgerCommandParser.getInstance().parseCommand("INVALID 123"));
    }

    @Test
    public void testParseInvalidLoanCommandWithoutSpace() {
        IllegalArgumentException e = Assertions.assertThrows(IllegalArgumentException.class,
                                                             () -> LedgerCommandParser.getInstance().parseCommand("LOAN IDIDI;Dale;10000;5;4"));
        Assertions.assertTrue(e.getMessage().startsWith("Invalid LOAN command passed"));
    }
}
