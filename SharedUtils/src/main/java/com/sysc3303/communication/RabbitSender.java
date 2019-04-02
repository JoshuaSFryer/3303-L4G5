package com.sysc3303.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.SerializationUtilJSON;

public class RabbitSender implements Runnable{

    protected SerializationUtilJSON<Message> serializationUtil;
    protected String queueName;
    protected Message message;

    public RabbitSender(String queueName, Message message){
        this.queueName = queueName;
        this.message = message;
        serializationUtil = new SerializationUtilJSON<>();
    }
    public void run() {
        try{
            Connection connection = RabbitShared.connect();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);

            byte[] data = serializationUtil.serialize(message);
            channel.basicPublish("", queueName, null, data);
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
        }
    }
}
