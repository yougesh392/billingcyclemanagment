package com.usmobile.billingcyclemanagment.DAO;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class BillingCycle {
    public BillingCycle() {
        this.id = UUID.randomUUID().toString();
    }
    private String id;
    private Date startDate;
    private Date endDate;
}
