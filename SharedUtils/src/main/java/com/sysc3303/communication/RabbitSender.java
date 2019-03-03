package com.sysc3303.communication;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.SerializationUtilJSON;

public class RabbitSender implements Runnable{

    private final static String HOSTNAME = "localhost";
    private SerializationUtilJSON<Message> serializationUtil;
    private String queueName;
    private Message message;

    public RabbitSender(String queueName, Message message){
        this.queueName = queueName;
        this.message = message;
        serializationUtil = new SerializationUtilJSON<>();
    }
    public void run() {
        ConnectionFactory factory = new ConnectionFactory();
        String hostname;
        if (Boolean.parseBoolean(ConfigProperties.getInstance().getProperty("local"))){
            hostname = "localhost";
        }
        else {
            hostname = ConfigProperties.getInstance().getProperty("rabbitAddress");
        }
        factory.setHost(hostname);

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
