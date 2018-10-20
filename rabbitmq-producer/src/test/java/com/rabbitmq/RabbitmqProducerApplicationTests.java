package com.rabbitmq;

import com.rabbitmq.entity.Order;
import com.rabbitmq.mapper.OrderMapper;
import com.rabbitmq.producer.OrderSender;
import com.rabbitmq.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
@EnableScheduling
public class RabbitmqProducerApplicationTests {

    @Autowired
    private OrderSender orderSender;

    @Test
    public void testSend() throws Exception {
        Order order = new Order("1", "orderO", System.currentTimeMillis() + "#" + UUID.randomUUID().toString());
        orderSender.send(order);
    }

    @Autowired
    private OrderService orderService;

    @Test
    public void testCreateOrder() {
        Order order = new Order();
        order.setMessageId(System.currentTimeMillis() + "#" + UUID.randomUUID());
        order.setName("Schuyler");
        order.setId("2018109");
        orderService.createOrder(order);
    }

}
