package com.rabbitmq.task;

import com.alibaba.fastjson.JSON;
import com.rabbitmq.constants.Constants;
import com.rabbitmq.entity.BrokerMessageLog;
import com.rabbitmq.entity.Order;
import com.rabbitmq.mapper.BrokerMessageLogMapper;
import com.rabbitmq.producer.RabbitmqOrderSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RetryMessageTasker {

    @Autowired
    private RabbitmqOrderSender rabbitmqOrderSender;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    @Scheduled(cron = "30/30 * * * * ?")
    public void reSend() {
        System.out.println("-------------开始定时任务-------------");
        List<BrokerMessageLog> brokerMessageLogs = brokerMessageLogMapper.query4StatusAndTimeout();
        brokerMessageLogs.forEach(brokerMessageLog -> {
            if (brokerMessageLog.getTryCount() >= 3) {
                //如果重新发送超过三次，就设置为发送失败
                brokerMessageLog.setStatus(Constants.ORDER_SENDFAILURE);
                brokerMessageLogMapper.updateStatus(brokerMessageLog);
            } else {
                //更新次数
                brokerMessageLog.setTryCount(brokerMessageLog.getTryCount() + 1);
                brokerMessageLogMapper.updateTryCount(brokerMessageLog);
                //将消息从json字符串转为order
                Order resendOrder = JSON.parseObject(brokerMessageLog.getMessage(), Order.class);
                try {
                    rabbitmqOrderSender.sendOrder(resendOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("----------------异常----------------");
                }
            }
        });
    }
}
