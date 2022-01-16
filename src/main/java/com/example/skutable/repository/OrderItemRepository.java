package com.example.skutable.repository;

import com.example.skutable.model.OrderItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, Long> {


    @Query(nativeQuery = true, value = " select * from t_order_item s order by s.drug,s.department,s.sufferer  limit :rowBegin, :rowEnd  ")
    List<OrderItem> pageListOrderItem(@Param("rowBegin") Integer rowBegin, @Param("rowEnd") Integer rowEnd);

    @Query(nativeQuery = true, value = " select sum(s.quantity) from t_order_item s  where s.drug = :drug ")
    Integer sumQuantityByDrug(@Param("drug") String drug);
}
