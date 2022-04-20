package com.theledgerco.vo;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BalanceResponse {
    private String bank;
    private String borrower;
    private long amountPaid;
    private int pendingEMIs;

    @Override
    public String toString() {
        return new StringBuffer(bank).append(" ").append(borrower).append(" ")
                                     .append(amountPaid).append(" ").append(pendingEMIs).toString();
    }
}
