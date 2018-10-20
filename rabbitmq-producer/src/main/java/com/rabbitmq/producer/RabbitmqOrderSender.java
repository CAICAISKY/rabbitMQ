package com.rabbitmq.producer;
import com.rabbitmq.constants.Constants;
import com.rabbitmq.entity.BrokerMessageLog;
import com.rabbitmq.entity.Order;
import com.rabbitmq.mapper.BrokerMessageLogMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate.ConfirmCallback;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author schuyler
 */
@Component
public class RabbitmqOrderSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private BrokerMessageLogMapper brokerMessageLogMapper;

    /**
     * 回调函数
     */
    final ConfirmCallback confirmationCallback = new RabbitTemplate.ConfirmCallback() {

        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String s) {
            System.out.println("correlationData: " + correlationData.getId());
            String messageId = correlationData.getId();
            if (ack) {
                //发送成功，更新对应消息的日志状态
                BrokerMessageLog brokerMessageLog = new BrokerMessageLog();
                brokerMessageLog.setStatus(Constants.ORDER_SEND_SUCCESS);
                brokerMessageLog.setMessageId(messageId);
                brokerMessageLogMapper.updateStatus(brokerMessageLog);
            } else {
                //失败则根据具体情况进行处理，这里不是重点，所以不详细描述
                System.out.println("发送失败，进行处理......");
            }
        }
    };

    /**
     *  发送消息
     */
    public void sendOrder(Order order) {
        //设置回调函数
        rabbitTemplate.setConfirmCallback(confirmationCallback);
        //设置消息唯一ID
        CorrelationData correlationData = new CorrelationData(order.getMessageId());
        rabbitTemplate.convertAndSend("order-exchange", "order.abcd", order, correlationData);
    }
}
