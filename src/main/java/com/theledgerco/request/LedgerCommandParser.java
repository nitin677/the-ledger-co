package com.theledgerco.request;

import com.theledgerco.command.BalanceCommand;
import com.theledgerco.command.LedgerCommand;
import com.theledgerco.command.LoanCommand;
import com.theledgerco.command.PaymentCommand;

public class LedgerCommandParser {
    private static final LedgerCommandParser inst = new LedgerCommandParser();

    private LedgerCommandParser() {}

    public synchronized static LedgerCommandParser getInstance() {
        return inst;
    }

    public LedgerCommand parseCommand(String command) {
        LedgerCommand cmd = null;
        String[] commandArgs = command.split(" ");
        switch (LedgerCommand.Values.valueOf(commandArgs[0])) {
            case LOAN:
                validateLoanCommand(command, commandArgs);
                String borrower = commandArgs[2];
                String bank = commandArgs[1];
                Double principal = Double.parseDouble(commandArgs[3]);
                int term = Integer.parseInt(commandArgs[4]);
                float interest = Float.parseFloat(commandArgs[5]);
                cmd = LoanCommand.builder().bank(bank).borrower(borrower).principal(principal)
                                 .term(term).interest(interest).build();
                break;
            case PAYMENT:
                validatePaymentCommand(command, commandArgs);
                borrower = commandArgs[2];
                bank = commandArgs[1];
                Double amount = Double.parseDouble(commandArgs[3]);
                int afterEMIs = Integer.parseInt(commandArgs[4]);
                cmd = PaymentCommand.builder().bank(bank).borrower(borrower).paymentAmount(amount)
                                    .afterEMIs(afterEMIs).build();
                break;
            case BALANCE:
                validateBalanceCommand(command, commandArgs);
                borrower = commandArgs[2];
                bank = commandArgs[1];
                afterEMIs = Integer.parseInt(commandArgs[3]);
                cmd = BalanceCommand.builder().bank(bank).borrower(borrower).afterEMIs(afterEMIs).build();
                break;
        }
        return cmd;
    }

    private void validateLoanCommand(String command, String[] commandArgs) {
        if (commandArgs.length != 6) {
            throw new IllegalArgumentException("Invalid LOAN command passed: " + command +
                                                       ". Expected format: Format - LOAN BANK_NAME BORROWER_NAME PRINCIPAL NO_OF_YEARS RATE_OF_INTEREST");
        }
    }

    private void validatePaymentCommand(String command, String[] commandArgs) {
        if (commandArgs.length != 5) {
            throw new IllegalArgumentException("Invalid PAYMENT command passed: " + command +
                                                       ". Expected format: Format - PAYMENT BANK_NAME BORROWER_NAME LUMP_SUM_AMOUNT EMI_NO");
        }
    }

    private void validateBalanceCommand(String command, String[] commandArgs) {
        if (commandArgs.length != 4) {
            throw new IllegalArgumentException("Invalid BALANCE command passed: " + command +
                                                       ". Expected format: Format - BALANCE BANK_NAME BORROWER_NAME EMI_NO");
        }
    }
}
