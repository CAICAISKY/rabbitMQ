package com.rabbitmq.mapper;

import com.rabbitmq.entity.BrokerMessageLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrokerMessageLogMapper {
    int updateStatus(BrokerMessageLog brokerMessageLog);
    int insertBrokerMessageLog(BrokerMessageLog brokerMessageLog);
    List<BrokerMessageLog> query4StatusAndTimeout();
    int updateTryCount(BrokerMessageLog brokerMessageLog);
}
