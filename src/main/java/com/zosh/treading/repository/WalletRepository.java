package com.zosh.treading.repository;

import com.zosh.treading.model.Wallet;
import com.zosh.treading.service.WalletService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Wallet findByUserId(Long userId);
}
