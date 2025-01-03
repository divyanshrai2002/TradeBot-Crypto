package com.zosh.treading.Controller;

import com.zosh.treading.domain.PaymentMethod;
import com.zosh.treading.model.PaymentOrder;
import com.zosh.treading.model.User;
import com.zosh.treading.response.PaymentResponse;
import com.zosh.treading.service.PaymentService;
import com.zosh.treading.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")

public class PaymentController {
    @Autowired
    private UserService userService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/api/payment/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(
            @PathVariable PaymentMethod paymentMethod,
            @PathVariable Long amount,
            @RequestHeader("Authorization")String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        PaymentResponse paymentResponse;

        PaymentOrder order= paymentService.createOrder(user,amount,paymentMethod);
        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse=paymentService.createRazorpayPaymentLing(user,amount);

        }
        else{
            paymentResponse=paymentService.createStripePaymentLing(user,amount,order.getId());

        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);

    }


}
