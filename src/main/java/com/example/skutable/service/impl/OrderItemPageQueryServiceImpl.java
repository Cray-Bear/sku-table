package com.example.skutable.service.impl;

import com.example.skutable.model.OrderItem;
import com.example.skutable.model.dto.FlowPageResponse;
import com.example.skutable.model.dto.OrderItemSummary;
import com.example.skutable.model.dto.PageInfo;
import com.example.skutable.repository.OrderItemRepository;
import com.example.skutable.service.OrderItemPageQueryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderItemPageQueryServiceImpl implements OrderItemPageQueryService {


    @Autowired
    private OrderItemRepository repository;



    @Override
    public FlowPageResponse<OrderItemSummary> flowPageList(Integer rowStart, Integer rowLimit) {

        FlowPageResponse<OrderItemSummary> response = new FlowPageResponse<>();
        response.setData(Collections.emptyList());

        PageInfo pageInfo = new PageInfo();
        pageInfo.setPreRowStart(rowStart);
        pageInfo.setCurRowStart(rowStart);
        response.setPage(pageInfo);

        List<OrderItem> orderItemList = repository.pageListOrderItem(rowStart, rowLimit);
        if (CollectionUtils.isEmpty(orderItemList)) {
            return response;
        }

        Map<String, List<OrderItem>> drugGroupListMap = orderItemList.stream().collect(Collectors.groupingBy(OrderItem::getDrug, LinkedHashMap::new, Collectors.toList()));
        int res = rowLimit;
        int curLimitStart = pageInfo.getPreRowStart();
        List<OrderItemSummary> result = new ArrayList<>();
        for (String drug : drugGroupListMap.keySet()) {
            List<OrderItem> data = drugGroupListMap.get(drug);
            int rowDataSize = data.size();

            res = res - (rowDataSize + 2);

            if (res < 0) {
                data = data.subList(0, rowDataSize + res);
            }

            if (res < 2) {
                curLimitStart += data.size();
                result.add(buildOrderItemSummaryByRowData(data));
                break;
            }

            //处理空格
            res -= 1;

            if (res < 3) {

                curLimitStart += data.size();
                result.add(buildOrderItemSummaryByRowData(data));
                break;
            }

            curLimitStart += data.size();
            result.add(buildOrderItemSummaryByRowData(data));
        }
        response.setData(result);
        pageInfo.setCurRowStart(curLimitStart);
        return response;
    }

    private OrderItemSummary buildOrderItemSummaryByRowData(List<OrderItem> data) {
        OrderItemSummary summary = new OrderItemSummary();
        summary.setData(data);
        summary.setDrug(data.get(0).getDrug());
        summary.setQuantity(repository.sumQuantityByDrug(summary.getDrug()));
        return summary;
    }
}
