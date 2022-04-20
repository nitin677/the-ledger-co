package com.theledgerco.core;

import com.theledgerco.vo.BalanceRequest;
import com.theledgerco.vo.BalanceResponse;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class Ledger {
    private static final Ledger ledger = new Ledger();
    private Map<String, Map<String, LoanAccount>> loanBook = new HashMap<>();

    private Ledger() {
    }

    public synchronized static Ledger getInstance() {
        return ledger;
    }

    public void addLoan(Loan loan) {
        loanBook.computeIfAbsent(loan.getBank(), k -> new HashMap<>())
                .computeIfAbsent(loan.getBorrower(), k -> LoanAccount.builder().loan(loan).build());
    }

    public void makePayment(Payment payment) {
        String bank = payment.getBank();
        String borrower = payment.getBorrower();
        validateRequest(bank, borrower);

        loanBook.get(bank).get(borrower).addPayment(payment);
    }

    public BalanceResponse getBalance(BalanceRequest balanceRequest) {
        String bank = balanceRequest.getBank();
        String borrower = balanceRequest.getBorrower();
        validateRequest(bank, borrower);

        LoanAccount account = loanBook.get(bank).get(borrower);
        int amountPaid = calculateAmountPaid(balanceRequest, account);

        int pendingEMIs = (int) Math.ceil(
                (account.getTotalPayable() - amountPaid)/account.getEMIAmount());
        return BalanceResponse.builder().bank(bank).borrower(borrower)
                              .amountPaid(amountPaid).pendingEMIs(pendingEMIs).build();
    }

    private int calculateAmountPaid(BalanceRequest balanceRequest, LoanAccount account) {
        int amountPaid = account.getEMIAmount() * balanceRequest.getAfterEMIs();
        if (balanceRequest.getAfterEMIs() == account.getTotalEMIs())
            amountPaid = (int) Math.ceil(account.getTotalPayable());

        for (Payment payment : account.getPayments()) {
            if (payment.getAfterEMIs() <= balanceRequest.getAfterEMIs()) {
                amountPaid += payment.getPaymentAmount();
            } else {
                break;
            }
        }
        if (amountPaid > account.getTotalPayable()) {
            amountPaid = (int) Math.ceil(account.getTotalPayable());
        }
        return amountPaid;
    }

    private void validateRequest(String bank, String borrower) {
        if (!loanBook.containsKey(bank) || !loanBook.get(bank).containsKey(borrower)) {
            throw new IllegalArgumentException("No loan exists for borrower "+borrower+" in bank "+bank);
        }
    }

    //used only for test cases, as different tests use the same singleton instance of the ledger
    public LoanAccount getLoanAccount(String bank, String borrower) {
        validateRequest(bank, borrower);
        return loanBook.get(bank).get(borrower);
    }

    //used only for test cases, as different tests use the same singleton instance of the ledger
    public void cleanUp() {
        loanBook.forEach((k,v) -> v.clear());
        loanBook.clear();
    }
}
