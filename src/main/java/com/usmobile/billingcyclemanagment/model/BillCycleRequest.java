package com.usmobile.billingcyclemanagment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BillCycleRequest {
    private String userId;
    private String phoneNumber;
}
