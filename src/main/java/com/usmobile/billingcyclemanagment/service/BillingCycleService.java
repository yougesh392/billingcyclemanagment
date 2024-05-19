package com.usmobile.billingcyclemanagment.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.usmobile.billingcyclemanagment.DAO.BillingCycle;
import com.usmobile.billingcyclemanagment.DAO.BillingCycleDAO;
import com.usmobile.billingcyclemanagment.model.BillCycleRequest;
import com.usmobile.billingcyclemanagment.model.BillingCycleResponse;
import com.usmobile.billingcyclemanagment.model.DailyUsageRequest;
import com.usmobile.billingcyclemanagment.model.UsageHistoryResponse;
import com.usmobile.billingcyclemanagment.repository.BillingCycleRepository;
import com.usmobile.billingcyclemanagment.service.external.DailyUsageService;
import com.usmobile.billingcyclemanagment.util.TimeUtil;
import com.usmobile.billingcyclemanagment.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class BillingCycleService {
    private final BillingCycleRepository billingCycleRepository;
    private final DailyUsageService dailyUsageService;

    @Autowired
    public BillingCycleService(BillingCycleRepository billingCycleRepository, DailyUsageService dailyUsageService) {
        this.billingCycleRepository = billingCycleRepository;
        this.dailyUsageService = dailyUsageService;
    }

    public UsageHistoryResponse getBillingCycleDailyUsage(BillCycleRequest billCycleRequest) throws JsonProcessingException {
        ValidationUtil.validateBillCycleRequest(billCycleRequest);
        BillingCycleDAO billingCycleDAO = billingCycleRepository.findByUserIdAndPhoneNumber(billCycleRequest.getUserId(), billCycleRequest.getPhoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Billing cycle not found"));

        if (!billingCycleDAO.getPhoneNumber().equals(billCycleRequest.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number does not match with the user id");
        }
        if(!billingCycleDAO.getCycleStatus().equalsIgnoreCase("active")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Billing cycle is not active");
        }
        List<BillingCycle> billingCycles = billingCycleDAO.getBillingCycles();
        BillingCycle currentBillingCycle = billingCycles.get(billingCycles.size() - 1);

        DailyUsageRequest dailyUsageRequest = new DailyUsageRequest();
        dailyUsageRequest.setUserId(billCycleRequest.getUserId());
        dailyUsageRequest.setPhoneNumber(billCycleRequest.getPhoneNumber());
        dailyUsageRequest.setCycleId(currentBillingCycle.getId());

        return dailyUsageService.getDailyUsageForCycleId(dailyUsageRequest);
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


        billingCycleDAO = billingCycleRepository.save(billingCycleDAO);

        DailyUsageRequest dailyUsageRequest = new DailyUsageRequest();
        dailyUsageRequest.setUserId(billingCycle.getUserId());
        dailyUsageRequest.setPhoneNumber(billingCycle.getPhoneNumber());
        dailyUsageRequest.setCycleId(billingCycleDAO.getBillingCycles().get(0).getId());

        dailyUsageService.createUsageHistory(dailyUsageRequest);


    }

    public BillingCycleResponse getBillingCycle(BillCycleRequest billCycleRequest) {
        ValidationUtil.validateBillCycleRequest(billCycleRequest);
        BillingCycleDAO billingCycleDAO = billingCycleRepository.findByUserIdAndPhoneNumber(billCycleRequest.getUserId(), billCycleRequest.getPhoneNumber())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Billing cycle not found"));

        if (!billingCycleDAO.getPhoneNumber().equals(billCycleRequest.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Phone number does not match with the user id");
        }

        BillingCycleResponse response = new BillingCycleResponse();
        response.setBillingCycles(billingCycleDAO.getBillingCycles());
        return response;
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
