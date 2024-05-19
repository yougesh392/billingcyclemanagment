package com.usmobile.billingcyclemanagment.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DailyUsageRequest {
    private String userId;
    private String phoneNumber;
    private String cycleId;
}
