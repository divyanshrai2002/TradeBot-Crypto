package com.zosh.treading.Controller;

import com.zosh.treading.domain.OrderType;
import com.zosh.treading.model.Coin;
import com.zosh.treading.model.Order;
import com.zosh.treading.model.User;
import com.zosh.treading.request.CreateOrderRequest;
import com.zosh.treading.service.CoinService;
import com.zosh.treading.service.OrderService;
import com.zosh.treading.service.UserService;
import com.zosh.treading.service.WalletService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;
//    @Autowired
//    private WalletTransactionService walletTransactionService;

    public ResponseEntity<Order> payOrderPayment(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateOrderRequest req
    )throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        Coin coin=coinService.findById(req.getCoinId());

        Order order=orderService.processOrder(coin,req.getQuantity(),req.getOrderType());
        return ResponseEntity.ok(order);
    }

     @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(
            @RequestHeader("Authorization") String jwtToken,
            @PathVariable Long orderId
     )throws Exception{

        User user=userService.findUserProfileByJwt(jwtToken);

        Order order=orderService.getOrderById(orderId);
        if(order.getUser().getId().equals(user.getId())){
            return ResponseEntity.ok(order);
         }
        else{
            throw new Exception("You don't have access");
        }
     }

     @GetMapping()
    public ResponseEntity<List<Order>> getAllOrderForUser(
            @RequestHeader("Authorizaiotion") String jwt,
            @RequestParam(required = false) OrderType order_Type,
            @RequestParam(required = false)String asset_symbol
     )throws Exception{

        Long userId= userService.findUserProfileByJwt(jwt).getId();
      List<Order> userOrders =orderService.getAllOrdersOfUser(userId,order_Type,asset_symbol);
      return ResponseEntity.ok(userOrders);
    }


}
