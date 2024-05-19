package com.usmobile.billingcyclemanagment.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UsageHistory{
    private Date usageDate;
    private int usedInMb;
}
