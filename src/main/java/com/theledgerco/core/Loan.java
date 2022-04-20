package com.theledgerco.core;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data @Builder
public class Loan {
    @NonNull
    private String borrower;
    @NonNull
    private String bank;
    @NonNull
    private Double principal;
    @NonNull
    private float interest;
    @NonNull
    private int term;
}
