package com.usmobile.billingcyclemanagment.service.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usmobile.billingcyclemanagment.model.BillCycleRequest;
import com.usmobile.billingcyclemanagment.model.DailyUsageRequest;
import com.usmobile.billingcyclemanagment.model.UsageHistoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

@Service
public class DailyUsageService {
    private static final Logger log = LoggerFactory.getLogger(DailyUsageService.class);
    private final RestTemplate restTemplate;

    private static String url = "http://localhost:8082/api/dailyusage";


    public DailyUsageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void createUsageHistory(DailyUsageRequest dailyUsageRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DailyUsageRequest> request = new HttpEntity<>(dailyUsageRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            // handle the response
            log.debug("Daily Usage history create for phone number {}: Status code: {}" + dailyUsageRequest.getPhoneNumber(),
                    response.getStatusCode());
        } else {
            log.debug("Error occurred: " + response.getStatusCode());
        }
    }

    public UsageHistoryResponse getDailyUsageForCycleId(DailyUsageRequest dailyUsageRequest) throws JsonProcessingException {
        HttpHeaders headers = new HttpHeaders();
        ObjectMapper mapper = new ObjectMapper();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<DailyUsageRequest> request = new HttpEntity<>(dailyUsageRequest, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(url + "/currentbillingcycle", request, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            return mapper.readValue(response.getBody(), UsageHistoryResponse.class);
        } else {
            log.debug("Error occurred: " + response.getStatusCode());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usage history failed to fetch with status code: "
                    + response.getStatusCode());
        }
    }
}
