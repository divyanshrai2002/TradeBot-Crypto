package com.zosh.treading.service;

import com.zosh.treading.model.Order;
import com.zosh.treading.model.User;
import com.zosh.treading.model.Wallet;


public interface WalletService {
    Wallet getUserWallet (User user);

    Wallet addBalance(Wallet wallet,Long money);

    Wallet findWalletById(Long id) throws Exception;

    Wallet walletToWalletTransfer(User sender, Wallet receiverWallet,Long amount) throws Exception;

    Wallet payOrderPayment(Order order, User user) throws Exception;
}
