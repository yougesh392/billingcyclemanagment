package com.usmobile.billingcyclemanagment.model;

import com.usmobile.billingcyclemanagment.DAO.BillingCycle;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BillingCycleResponse {
    List<BillingCycle> billingCycles;
}
