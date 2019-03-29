package com.sysc3303.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.SerializationUtilJSON;

public class RabbitPublisher implements Runnable{

    private SerializationUtilJSON<Message> serializationUtil;
    private String exchangeName;
    private Message message;

    public RabbitPublisher(String exchangeName, Message message){
        this.exchangeName = exchangeName;
        this.message = message;
        serializationUtil = new SerializationUtilJSON<>();
    }

    public void run(){
        ConnectionFactory factory = new ConnectionFactory();
        String hostname;
        if (Boolean.parseBoolean(ConfigProperties.getInstance().getProperty("rabbitCloud"))){
            hostname = ConfigProperties.getInstance().getProperty("rabbitCloudAddress");
        }
        else if (Boolean.parseBoolean(ConfigProperties.getInstance().getProperty("local"))){
            hostname = "localhost";
        }
        else {
            hostname = ConfigProperties.getInstance().getProperty("rabbitAddress");
        }
        factory.setHost(hostname);

        try(Connection connection = factory.newConnection();
            Channel channel = connection.createChannel()){

            channel.exchangeDeclare(exchangeName, "fanout");

            byte[] data = serializationUtil.serialize(message);
            channel.basicPublish(exchangeName, "", null, data);
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
        }
    }
    // get a random Queue
}
