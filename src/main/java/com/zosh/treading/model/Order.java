package com.zosh.treading.model;

import com.zosh.treading.domain.OrderStatus;
import com.zosh.treading.domain.OrderType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data

public class Order {


@Id
@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

@ManyToOne
private User user;

@ManyToOne
@Column(nullable = false)
private OrderType orderType;


@Column(nullable = false)
private BigDecimal price;

private LocalDateTime timestamp=LocalDateTime.now();

@Column(nullable = false)
private OrderStatus status;

@OneToOne(mappedBy = "order",cascade = CascadeType.ALL)
private OrderItem orderItem;
}
