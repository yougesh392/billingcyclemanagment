package com.usmobile.billingcyclemanagment.service;


import com.usmobile.billingcyclemanagment.DAO.BillingCycle;
import com.usmobile.billingcyclemanagment.DAO.BillingCycleDAO;
import com.usmobile.billingcyclemanagment.model.BillCycleRequest;
import com.usmobile.billingcyclemanagment.repository.BillingCycleRepository;
import com.usmobile.billingcyclemanagment.util.TimeUtil;
import com.usmobile.billingcyclemanagment.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BillingCycleService {
    private final BillingCycleRepository billingCycleRepository;

    @Autowired
    public BillingCycleService(BillingCycleRepository billingCycleRepository) {
        this.billingCycleRepository = billingCycleRepository;
    }

    public void createBillingCycle(BillCycleRequest billingCycle) {
        BillingCycleDAO billingCycleDAO = new BillingCycleDAO();
        billingCycleDAO.setUserId(billingCycle.getUserId());

        BillingCycle newBillingCycle = new BillingCycle();
        newBillingCycle.setStartDate(TimeUtil.getCycleStartDate());
        newBillingCycle.setEndDate(TimeUtil.getCycleEndDate());
        billingCycleDAO.setBillingCycles(List.of(newBillingCycle));

        billingCycleDAO.setCycleStatus("active");
        billingCycleDAO.setPhoneNumber(billingCycle.getPhoneNumber());
        billingCycleRepository.save(billingCycleDAO);

    }

    public BillingCycleDAO getBillingCycle(BillCycleRequest billCycleRequest) {
        ValidationUtil.validateBillCycleRequest(billCycleRequest);
        return billingCycleRepository.findByUserIdAndPhoneNumber(billCycleRequest.getUserId(),
                        billCycleRequest.getPhoneNumber())
                .orElseThrow(() -> new RuntimeException("Billing Cycle not found"));
    }

    /*
     * scheduler to reset the billing cycle at the end of each cycle period
     * */
    @Scheduled(fixedRate = 24 * 60 * 60 * 1000) // 1 day
    public void checkAndResetBillingCycles() {
        Date today = TimeUtil.getCycleStartDate();
        List<BillingCycleDAO> allBillingCycles = billingCycleRepository.findAll();

        for (BillingCycleDAO billingCycle : allBillingCycles) {
            List<BillingCycle> billingCycles = billingCycle.getBillingCycles();
            boolean cycleEnded = false;
            for (BillingCycle billCycle : billingCycles) {
                // cycle start day is same as today.
                if (billCycle.getEndDate().before(today)) {
                    cycleEnded = true;
                    break;
                }
            }
            if (cycleEnded) {
                BillingCycle newBillingCycle = new BillingCycle();
                newBillingCycle.setStartDate(TimeUtil.getCycleStartDate());
                newBillingCycle.setEndDate(TimeUtil.getCycleEndDate());
                billingCycles.add(newBillingCycle);
                billingCycle.setBillingCycles(billingCycles);
                billingCycleRepository.save(billingCycle);

            }
        }

    }
}
