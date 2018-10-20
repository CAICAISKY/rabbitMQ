package com.rabbitmq.producer;


import com.rabbitmq.entity.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author schuyler
 * 订单发送服务
 */
@Component
public class OrderSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Order order) throws Exception {
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(order.getMessageId());
        /**
         * convertAndSend参数解释
         * 第一个参数: exchange，指定交换机名称，交换机和队列可以在rabbitMQ图形洁面上进行创建
         * 第二个参数: routingKey, 指定路由key
         * 第三个参数: 传输的对象
         * 第四个参数: correlationData，指定消息唯一id
         **/
        rabbitTemplate.convertAndSend("order-exchange", "order.abcd", order, correlationData);
    }
}
