package com.theledgerco.core;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data @Builder
@EqualsAndHashCode
public class Payment {
    @NonNull
    private String bank;
    @NonNull
    private String borrower;
    @NonNull
    private Double paymentAmount;
    @NonNull
    private int afterEMIs;
}
