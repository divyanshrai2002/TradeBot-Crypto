package com.zosh.treading.service;

import com.zosh.treading.domain.OrderStatus;
import com.zosh.treading.domain.OrderType;
import com.zosh.treading.model.*;
import com.zosh.treading.repository.OrderItemRepository;
import com.zosh.treading.repository.OrderRepository;
import jakarta.persistence.Transient;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl  implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {
        double price=orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();

        Order order=new Order();
        order.setUser(user);
        order.setOrderItem(orderItem);
        order.setOrderType(orderType);
        order.setPrice(BigDecimal.valueOf(price));
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) throws Exception {

        return orderRepository.findById(orderId).orElseThrow(()-> new Exception("order not found"));
    }

    @Override
    public List<Order> getAllOrdersOfUser(Long userId, OrderType orderType, String assetSymbol) {
        return orderRepository.findByUserId(userId);
    }

    private OrderItem createOrderItem(Coin coin,double quantity, double buyPrice,double sellPrice){
        OrderItem orderItem=new OrderItem();
        orderItem.setCoin(coin);
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);

        return orderItemRepository.save(orderItem);
    }
    @Transactional
    public Order buyAsset(Coin coin,double quantity, User user) throws Exception {
        if(quantity<=0){
            throw new Exception("quantity should be greater than 0");
        }
        double buyPrice= coin.getCurrentPrice();
        OrderItem orderItem=createOrderItem(coin,quantity,buyPrice,0);

        Order order=createOrder(user,orderItem,OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order,user);

        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order savedOrder=orderRepository.save(order);

        return savedOrder;
    }
    @Transactional
    public Order sellAsset(Coin coin,double quantity, User user) throws Exception {
        if(quantity<=0){
            throw new Exception("quantity should be greater than 0");
        }
        double sellPrice= coin.getCurrentPrice();
        double buyPrice=assetTosell.getPrice();
        OrderItem orderItem=createOrderItem(coin,quantity,0,sellPrice);


        Order order=createOrder(user,orderItem,OrderType.SELL);
        orderItem.setOrder(order);

        if(assetToSell.getQuantity()>=quantity){
            order.setStatus(OrderStatus.SUCCESS);
            order.setOrderType(OrderType.SELL);
            Order savedOrder=orderRepository.save(order);

            walletService.payOrderPayment(order,user);

            Asset updatedAsset=assetService.updateAsset(assetToSell.getId(),quantity);
            if(updatedAsset.getQuantity()*coin.getCurrentPrice()<=1){
                assetService.deleteAsset(updatedAsset.getId);
            }
            return savedOrder;
        }
        throw new Exception("Insufficient quantity to sell");



    }


    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception {
        if(orderType==OrderType.BUY){
            return buyAsset(coin,quantity,user);
        }
        else if(orderType.equals(OrderType.SELL)){
            return sellAsset(coin,quantity,user);
        }
        throw new Exception("invalid order type");
    }
}
