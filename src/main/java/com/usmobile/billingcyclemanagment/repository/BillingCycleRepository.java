package com.usmobile.billingcyclemanagment.repository;

import com.usmobile.billingcyclemanagment.DAO.BillingCycleDAO;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface BillingCycleRepository extends MongoRepository<BillingCycleDAO, String> {
    Optional<BillingCycleDAO> findByUserIdAndPhoneNumber(String userId, String phoneNumber);
}
