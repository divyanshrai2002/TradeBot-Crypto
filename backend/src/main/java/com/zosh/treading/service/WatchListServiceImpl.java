package com.zosh.treading.service;

import com.zosh.treading.model.Coin;
import com.zosh.treading.model.User;
import com.zosh.treading.model.WatchList;
import com.zosh.treading.repository.WatchListRepo;
import io.micrometer.common.util.internal.logging.WarnThenDebugLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl implements WatchListService {
    @Autowired
    private WatchListRepo watchListRepo;


    @Override
    public WatchList findUserWatchList(Long userId) throws Exception {
        WatchList watchList=watchListRepo.findByUserId(userId);

        if(watchList==null){
            throw new Exception("watchlist is not found");


        }
            return watchList;

    }

    @Override
    public WatchList createWatchList(User user) {
        WatchList watchList=new WatchList();
        watchList.setUser(user);

        return watchListRepo.save(watchList);
    }

    @Override
    public WatchList findById(Long id) throws Exception {
        Optional<WatchList> watchListOptional=watchListRepo.findById(id);
        if(watchListOptional.isEmpty()){
            throw new Exception("watchlist not found");
        }
        return watchListOptional.get();
    }

    @Override
    public Coin addItemToWatchlist(Coin coin, User user) throws Exception {
        WatchList watchList=findUserWatchList(user.getId());

        if(watchList.getCoins().contains(coin)){
            watchList.getCoins().remove(coin);
        }
        else watchList.getCoins().add(coin);
         watchListRepo.save(watchList);
         return coin;
    }
}
