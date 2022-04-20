package com.theledgerco.vo;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class BalanceRequest {
    private String bank;
    private String borrower;
    private int afterEMIs;

    @Override
    public String toString() {
        return bank+" "+borrower+" "+afterEMIs;
    }
}
