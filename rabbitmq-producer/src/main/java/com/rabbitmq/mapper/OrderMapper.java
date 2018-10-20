package com.rabbitmq.mapper;

import com.rabbitmq.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderMapper {
    Order selectById(String id);

    int insertOrder(Order order);
}
