package com.rabbitmq.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.entity.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Map;

@Component
public class OrderReceiver {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "order-queue", durable = "true"),
            exchange = @Exchange(value = "order-exchange", durable = "true", type = "topic"),
            key = "order.#"
        )
    )
    @RabbitHandler
    public void orderMassage(@Payload Order order, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        System.out.println("--------接收到消息，开始消费--------");
        System.out.println("订单号为: " + order.getId());
        //确认签收消息
        Long delivery = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(delivery, false);
    }

}
