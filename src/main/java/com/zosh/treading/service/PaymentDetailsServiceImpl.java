package com.zosh.treading.service;

import com.zosh.treading.model.PaymentDetails;
import com.zosh.treading.model.User;
import com.zosh.treading.repository.PaymentDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PaymentDetailsServiceImpl implements PaymentDetailsService {

    @Autowired
    private PaymentDetailsRepo paymentDetailsRepo;

    @Override
    public PaymentDetails addPaymentDetails(String accountNumber, String accountHolderName, String ifsc, String bankName, User user) {
        // Create a new PaymentDetails object
        PaymentDetails paymentDetails = new PaymentDetails();
        paymentDetails.setAccountHolderNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);

        // Save the payment details to the database
        return paymentDetailsRepo.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        // Fetch payment details for the given user from the database
        return paymentDetailsRepo.findByUserId(user.getId());
    }
}
