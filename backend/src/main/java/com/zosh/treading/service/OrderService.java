package com.zosh.treading.service;

import com.zosh.treading.domain.OrderType;
import com.zosh.treading.model.Coin;
import com.zosh.treading.model.Order;
import com.zosh.treading.model.OrderItem;
import com.zosh.treading.model.User;

import java.util.List;

public interface OrderService {

    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol);


    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;

}

