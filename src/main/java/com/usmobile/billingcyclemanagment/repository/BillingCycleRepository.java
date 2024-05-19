package com.usmobile.billingcyclemanagment.repository;

import com.usmobile.billingcyclemanagment.DAO.BillingCycleDAO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

public interface BillingCycleRepository extends MongoRepository<BillingCycleDAO, String> {
    @Query("{ 'userId': ?0, 'phoneNumber': ?1 }")
    Optional<BillingCycleDAO> findByUserIdAndPhoneNumber(String userId, String phoneNumber);

}
