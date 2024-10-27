package com.zosh.treading.request;

import com.zosh.treading.domain.OrderType;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private String  CoinId;
    private double quantity;
    private OrderType orderType;
}
