package com.theledgerco.command;

import com.theledgerco.core.Ledger;
import com.theledgerco.core.Payment;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data @SuperBuilder
public class PaymentCommand extends LedgerCommand {
    private Double paymentAmount;
    private int afterEMIs;

    @Override
    public String execute() {
        Ledger.getInstance().makePayment(Payment.builder().bank(bank).borrower(borrower)
                                                .paymentAmount(paymentAmount).afterEMIs(afterEMIs).build());
        return "";
    }
}
