package com.zosh.treading.Controller;

import com.zosh.treading.model.Coin;
import com.zosh.treading.model.User;
import com.zosh.treading.model.WatchList;
import com.zosh.treading.repository.WatchListRepo;
import com.zosh.treading.service.CoinService;
import com.zosh.treading.service.UserService;
import com.zosh.treading.service.WatchListService;
import com.zosh.treading.service.WatchListServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ssl.SslProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchList")
public class WatchListController {
    @Autowired
    private WatchListService watchListService;

    @Autowired
    private UserService userService;

    @Autowired
    private CoinService coinService;

    @GetMapping("/user")
    public ResponseEntity<WatchList> getUserWatchList(
            @RequestHeader("Authorization")String jwt)throws Exception{
        User user=userService.findUserProfileByJwt(jwt);
        WatchList watchList=watchListService.findUserWatchList(user.getId());
        return ResponseEntity.ok(watchList);
    }

    @GetMapping("/{watchListId}")
    public ResponseEntity<WatchList> getWatchListById(
            @PathVariable Long watchListId)throws  Exception{
        WatchList watchList=watchListService.findById(watchListId);
        return ResponseEntity.ok(watchList);
    }

    @PatchMapping("/add/coin/{coinId}")
    public ResponseEntity<Coin> addItemToWatchList(
            @RequestHeader("Authorization")String jwt,
            @PathVariable String coinId)throws Exception {
            User user = userService.findUserProfileByJwt(jwt);
            Coin coin=coinService.findById(coinId);
            Coin addedCoin=watchListService.addItemToWatchlist(coin,user);
            return ResponseEntity.ok(addedCoin);
    }

}
