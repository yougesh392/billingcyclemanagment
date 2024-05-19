package com.usmobile.billingcyclemanagment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.usmobile.billingcyclemanagment.model.BillCycleRequest;
import com.usmobile.billingcyclemanagment.model.UsageHistoryResponse;
import com.usmobile.billingcyclemanagment.service.BillingCycleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/billingcycle")
@Tag(name = "Billing API", description = "Billcycle Managment API")
public class BillingCycleController {
    private final BillingCycleService billingCycleService;

    public BillingCycleController(BillingCycleService billingCycleService) {
        this.billingCycleService = billingCycleService;
    }

    @GetMapping("/dailyusage/{userId}")
    @Operation(summary = "Retrive billing cycle for the user", description = "retrive billing cycle for the user")
    public ResponseEntity<UsageHistoryResponse> getCurrentBillingCycleDailyUsage(@PathVariable String userId,
                                                                                 @RequestParam String phoneNumber) throws JsonProcessingException {
        BillCycleRequest billingCycleRequest = new BillCycleRequest();
        billingCycleRequest.setPhoneNumber(phoneNumber);
        billingCycleRequest.setUserId(userId);
        return new ResponseEntity<UsageHistoryResponse>(billingCycleService.getBillingCycleDailyUsage(billingCycleRequest), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Retrive billing cycle for the user", description = "retrive billing cycle for the user")
    public ResponseEntity getBillingCycle(@PathVariable String userId,
                                          @RequestParam String phoneNumber) {
        BillCycleRequest billingCycleRequest = new BillCycleRequest();
        billingCycleRequest.setPhoneNumber(phoneNumber);
        billingCycleRequest.setUserId(userId);
        return new ResponseEntity<>(billingCycleService.getBillingCycle(billingCycleRequest), HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Create billing cycle", description = "create a billing cycle for the user")
    public ResponseEntity createBillingCycle(@RequestBody BillCycleRequest billCycleRequest) throws Exception {
        billingCycleService.createBillingCycle(billCycleRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
