package com.usmobile.billingcyclemanagment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UsageHistoryResponse {
    @JsonProperty("usageHistory")
    private List<UsageHistory> usageHistory;
}
