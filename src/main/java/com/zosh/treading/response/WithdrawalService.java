package com.zosh.treading.response;

import com.zosh.treading.model.User;
import com.zosh.treading.model.Withdrawal;
import lombok.With;

import java.util.List;

public interface WithdrawalService {
    Withdrawal requestWithdrawal(Long amount, User user);

    Withdrawal procedWithdrawal(Long withdrawalId,boolean accept) throws Exception;

    List<Withdrawal> getUsersWithdrawalHistory(User user);
    List<Withdrawal> getAllWithdrawalRequest();

}
