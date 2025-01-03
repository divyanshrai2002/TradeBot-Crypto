package com.zosh.treading.service;

import com.zosh.treading.model.Coin;
import com.zosh.treading.model.User;
import com.zosh.treading.model.WatchList;

public interface WatchListService {
    WatchList findUserWatchList(Long userId) throws Exception;
    WatchList createWatchList(User user);
    WatchList findById(Long id) throws Exception;

    Coin addItemToWatchlist(Coin coin, User user) throws Exception;
}
