package com.example.skutable.web;

import com.example.skutable.model.OrderItem;
import com.example.skutable.model.dto.FlowPageRequest;
import com.example.skutable.model.dto.FlowPageResponse;
import com.example.skutable.model.dto.OrderItemSummary;
import com.example.skutable.model.dto.PageInfo;
import com.example.skutable.service.OrderItemPageQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {


    @Autowired
    private OrderItemPageQueryService orderItemPageQueryService;

    @RequestMapping("/pageList")
    public FlowPageResponse<OrderItemSummary> flowPageList(FlowPageRequest request) {

        if (Objects.isNull(request)) {
            request = new FlowPageRequest();
            request.setRowStart(0);
            request.setRowLimit(20);
        }

        if (request.getRowStart() < 0) {
            request.setRowStart(0);
        }

        if (request.getRowLimit() < 0) {
            request.setRowLimit(20);
        }
        return orderItemPageQueryService.flowPageList(request.getRowStart(), request.getRowLimit());
    }


    @RequestMapping("/print")
    public List<OrderItemSummary> print() {
        List<OrderItemSummary> result = new ArrayList<>();
        int rowStart = 0;
        while (true) {
            FlowPageResponse<OrderItemSummary> res = orderItemPageQueryService.flowPageList(rowStart, 40);
            if (CollectionUtils.isEmpty(res.getData())) {
                break;
            }
            result.addAll(res.getData());
            rowStart = res.getPage().getCurRowStart();
        }
        return result;
    }
}
