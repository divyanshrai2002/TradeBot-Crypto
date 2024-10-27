package com.zosh.treading.service;

import com.zosh.treading.domain.VerificationType;
import com.zosh.treading.model.ForgotPasswordToken;
import com.zosh.treading.model.User;

public interface ForgotPasswordService {
    ForgotPasswordToken createToken(
                               User user,
                               String id,String otp,
                               VerificationType verificationType,
                               String sendTo);

    ForgotPasswordToken findById(String id);

    ForgotPasswordToken findByUser(Long userid);

    void deleteToken(ForgotPasswordToken token);

}
