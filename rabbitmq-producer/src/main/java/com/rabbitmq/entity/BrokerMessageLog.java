package com.rabbitmq.entity;

import org.omg.PortableServer.ServantActivator;

import java.io.Serializable;
import java.util.Date;

public class BrokerMessageLog {
    private String messageId;
    private String message;
    private Integer tryCount = 0;
    private String status;
    private Date nextRetry;

    public BrokerMessageLog() {
    }

    public BrokerMessageLog(String messageId, String message, Integer tryCount, String status, Date nextRetry) {
        this.messageId = messageId;
        this.message = message;
        this.tryCount = tryCount;
        this.status = status;
        this.nextRetry = nextRetry;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTryCount() {
        return tryCount;
    }

    public void setTryCount(Integer tryCount) {
        this.tryCount = tryCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getNextRetry() {
        return nextRetry;
    }

    public void setNextRetry(Date nextRetry) {
        this.nextRetry = nextRetry;
    }
}
