package com.example.skutable.runner;

import com.example.skutable.model.OrderItem;
import com.example.skutable.repository.OrderItemRepository;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class DataMockCommand implements CommandLineRunner {

    @Autowired
    private OrderItemRepository repository;

    @Override
    public void run(String... args) throws Exception {
        List<String> drugs = Arrays.asList("苹果", "红枣", "李子", "甘草");
        List<Integer> quantitys = Arrays.asList(1, 3, 5, 7, 11, 13, 19);
        List<String> departments = Arrays.asList("科室1", "科室2", "科室3");
        List<String> sufferers = Arrays.asList("张三", "李四");

        long count = repository.count();

        if (count > 30) {
            return;
        }
        for (int i = 0; i < 36; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setDrug(drugs.get(RandomUtils.nextInt(0, 3)));
            orderItem.setQuantity(quantitys.get(RandomUtils.nextInt(0, 7)));
            orderItem.setDepartment(departments.get(RandomUtils.nextInt(0, 2)));
            orderItem.setSufferer(sufferers.get(RandomUtils.nextInt(0, 1)));
            repository.save(orderItem);
        }


    }
}
