package com.sysc3303.communication;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.sysc3303.commons.SerializationUtil;

public class RabbitSender implements Runnable{

    private final static String HOSTNAME = "localhost";
    private SerializationUtil<Message> serializationUtil;
    private String queueName;
    private Message message;

    public RabbitSender(String queueName, Message message){
        this.queueName = queueName;
        this.message = message;
        serializationUtil = new SerializationUtil<>();
    }
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOSTNAME);

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){

            channel.queueDeclare(queueName, false, false, false, null);

            byte[] data = serializationUtil.serialize(message);
            channel.basicPublish("", queueName, null, data);
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
        }
    }
}
