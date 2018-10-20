package com.rabbitmq.entity;

import java.io.Serializable;

/**
 * @author schuyler
 */
public class Order implements Serializable {


    private static final long serialVersionUID = 6165921570511430824L;

    private String id;

    private String name;

    /**存储消息发送的唯一标识*/
    private String messageId;

    public Order() {
    }

    public Order(String id, String name, String massageId) {
        this.id = id;
        this.name = name;
        this.messageId = massageId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
