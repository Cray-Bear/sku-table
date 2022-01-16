package com.example.skutable.model.dto;

import com.example.skutable.model.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class OrderItemSummary {

    private String drug;
    private Integer quantity;
    private List<OrderItem> data;
}
