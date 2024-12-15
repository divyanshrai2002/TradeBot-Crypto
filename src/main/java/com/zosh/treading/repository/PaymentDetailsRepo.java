package com.zosh.treading.repository;

import com.zosh.treading.model.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentDetailsRepo extends JpaRepository<PaymentDetails,Long> {
    PaymentDetails findByUserId(Long userId);
}
