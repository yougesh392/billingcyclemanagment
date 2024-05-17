package com.usmobile.billingcyclemanagment.DAO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "billingCycle")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "billing cycle entity")
public class BillingCycleDAO {
    @Id
    private String id;
    @Indexed(unique = true)
    private String phoneNumber;
    private String userId;
    private List<BillingCycle> billingCycles;
    private String cycleStatus;
    @LastModifiedDate
    private Instant lastModifiedDate;
    @CreatedDate
    private Instant createdDate;


}
