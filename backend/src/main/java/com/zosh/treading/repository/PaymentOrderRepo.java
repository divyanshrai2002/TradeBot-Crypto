package com.zosh.treading.repository;

import com.zosh.treading.model.PaymentOrder;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentOrderRepo extends JpaRepository<PaymentOrder,Long> {
}
