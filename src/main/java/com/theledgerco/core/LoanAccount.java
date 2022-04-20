package com.theledgerco.core;

import java.util.Set;
import java.util.TreeSet;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class LoanAccount {
    private Loan         loan;
    private final Set<Payment> payments = new TreeSet<Payment>((o1, o2) -> o1.getAfterEMIs() - o2.getAfterEMIs());

    public void addPayment(Payment payment) {
        payments.add(payment);
    }

    public Double getTotalPayable() {
        return loan.getPrincipal() + loan.getPrincipal() * loan.getInterest() * loan.getTerm()/100;
    }

    public int getEMIAmount() {
        Double emi = getTotalPayable() / (loan.getTerm()*12);
        return (int) Math.ceil(emi);
    }

    public int getTotalEMIs() {
        return (int) Math.ceil(getTotalPayable()/getEMIAmount());
    }
}
