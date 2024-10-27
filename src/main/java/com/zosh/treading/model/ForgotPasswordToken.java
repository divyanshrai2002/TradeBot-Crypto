package com.zosh.treading.model;

import com.zosh.treading.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ForgotPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @OneToOne
    private  User user;

    private String Otp;
    private VerificationType verificatonType;
    private  String sendTo;

}
