package com.usmobile.billingcyclemanagment.controller;

import com.usmobile.billingcyclemanagment.model.BillCycleRequest;
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
    @GetMapping
    @Operation(summary = "Retrive billing cycle for the user", description = "retrive billing cycle for the user")
public ResponseEntity getBillingCycle(@RequestParam BillCycleRequest billCycleRequest) {
        return new ResponseEntity<>(billingCycleService.getBillingCycle(billCycleRequest), HttpStatus.OK);
    }


    @PostMapping
    @Operation(summary = "Create billing cycle", description = "create a billing cycle for the user")
    public ResponseEntity createBillingCycle(@RequestBody BillCycleRequest billCycleRequest) throws Exception {
        billingCycleService.createBillingCycle(billCycleRequest);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
