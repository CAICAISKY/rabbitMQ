package com.rabbitmq.service;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.constants.Constants;
import com.rabbitmq.entity.BrokerMessageLog;
import com.rabbitmq.entity.Order;
import com.rabbitmq.mapper.BrokerMessageLogMapper;
import com.rabbitmq.mapper.OrderMapper;
import com.rabbitmq.producer.RabbitmqOrderSender;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Autowired
    private RabbitmqOrderSender rabbitmqOrderSender;

    public void createOrder(Order order) {
        //将消息数据录入数据库
        orderMapper.insertOrder(order);
        //构建消息日志对象
        BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
        brokerMessageLog.setMessageId(order.getMessageId());
        brokerMessageLog.setMessage(JSON.toJSONString(order));
        brokerMessageLog.setStatus("0");
        brokerMessageLog.setNextRetry(DateUtils.addMinutes(new Date(), Constants.ORDER_TIMEOUT));
        //将消息日志对象录入数据库
        brokerMessageLogMapper.insertBrokerMessageLog(brokerMessageLog);
        //发送消息
        rabbitmqOrderSender.sendOrder(order);
    }
}
