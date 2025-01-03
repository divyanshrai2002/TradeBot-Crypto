package com.zosh.treading.service;

import com.zosh.treading.domain.VerificationType;
import com.zosh.treading.model.ForgotPasswordToken;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.ForgotPasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ForgotPasswordImpl implements ForgotPasswordService{
    @Autowired
    private ForgotPasswordRepository forgotpasswordRepository;

    @Override
    public ForgotPasswordToken createToken(User user, String id, String otp, VerificationType verificationType, String sendTo) {
        ForgotPasswordToken token=new ForgotPasswordToken();
        token.setUser(user);
        token.setSendTo(sendTo);
        token.setVerificatonType(verificationType);
        token.setId(id);
        return forgotpasswordRepository.save(token);

    }

    @Override
    public ForgotPasswordToken findById(String id) {
        Optional<ForgotPasswordToken> token=forgotpasswordRepository.findById(id);
        return token.orElse(null);
    }

    @Override
    public ForgotPasswordToken findByUser(Long userid) {
        return forgotpasswordRepository.findByUserId(userid);
    }

    @Override
    public void deleteToken(ForgotPasswordToken token) {
        forgotpasswordRepository.delete(token);

    }
}
