package com.usmobile.billingcyclemanagment.util;

import com.usmobile.billingcyclemanagment.model.BillCycleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ValidationUtil {
    private static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^[0-9]{10}$");
    }

    private static boolean isValidUserId(String userId) {
        return userId != null && userId.matches("^[0-9a-fA-F]{24}$");
    }

    public static void validateBillCycleRequest(BillCycleRequest billCycleRequest) {
        if (billCycleRequest.getUserId() ==null || !isValidUserId(billCycleRequest.getUserId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid userId");
        }
        if (billCycleRequest.getPhoneNumber() == null || !isValidPhoneNumber(billCycleRequest.getPhoneNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid phoneNumber");
        }
    }

}
