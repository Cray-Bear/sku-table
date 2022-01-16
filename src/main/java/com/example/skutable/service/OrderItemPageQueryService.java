package com.example.skutable.service;

import com.example.skutable.model.dto.FlowPageResponse;
import com.example.skutable.model.dto.OrderItemSummary;

import java.util.List;

public interface OrderItemPageQueryService {
    FlowPageResponse<OrderItemSummary> flowPageList(Integer rowStart, Integer rowLimit);
}
