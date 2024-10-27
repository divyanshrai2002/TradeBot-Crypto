package com.zosh.treading.Controller;

import com.zosh.treading.model.Order;
import com.zosh.treading.model.User;
import com.zosh.treading.model.Wallet;
import com.zosh.treading.model.WalletTransaction;
import com.zosh.treading.service.UserService;
import com.zosh.treading.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    @GetMapping("/api/wallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        Wallet wallet= walletService.getUserWallet(user);

        return new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }
   @PutMapping("/api/wallet/${walletId}/transfer")
    public ResponseEntity<Wallet> walletToWalletTransfet(@RequestHeader("Authorization")
                                                         String jwt,
                                                         @PathVariable Long walletId, @RequestBody WalletTransaction req) throws Exception {
        User senderUser=userService.findUserProfileByJwt(jwt);

        Wallet receiverWallet=walletService.findWalletById(walletId);

        Wallet wallet=walletService.walletToWalletTransfer(senderUser,receiverWallet, req.getAmount());

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);

    }
    @PutMapping("/api/wallet/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt,
                                                  @PathVariable Long orderId) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);

        Order order=orderService.getOrderById(orderId);

        Wallet wallet= walletService.payOrderPayment(order,user);

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);

    }
}
