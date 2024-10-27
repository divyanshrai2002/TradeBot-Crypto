package com.zosh.treading.service;

import com.zosh.treading.domain.VerificationType;

import com.zosh.treading.model.User;
import com.zosh.treading.model.VerificationCode;

public interface VerificationCodeService {
    VerificationCode sendVerificationCode(User user, VerificationType verificationtype);


    VerificationCode getVerificaitonCodeById(Long id) throws Exception;

    VerificationCode getVerificationCodeByUser(Long userId);


    void deleteVerificationCodeById(VerificationCode verificationCode);
}
