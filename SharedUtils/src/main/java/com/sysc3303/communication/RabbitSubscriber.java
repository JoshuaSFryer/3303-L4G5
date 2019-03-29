package com.sysc3303.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.SerializationUtilJSON;

import java.util.Properties;

public class RabbitSubscriber implements Runnable{

    private final static boolean AUTO_ACK = true;
    private MessageHandler messageHandler;
    private SerializationUtilJSON<Message> serializationUtil;
    private String exchangeName;

    public RabbitSubscriber(MessageHandler messageHandler, String exchangeName) {
        this.messageHandler = messageHandler;
        this.exchangeName = exchangeName;
        serializationUtil = new SerializationUtilJSON<>();
    }
    public void run(){
        // create a new queue with a randomly generated name
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
        try{
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchangeName, "fanout");
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, exchangeName, "");

            System.out.println(" [*] Waiting to receive configuration ");

            DeliverCallback deliverCallback = (consumerTag, delivery) ->{

                Message message = serializationUtil.deserialize(delivery.getBody(), Message.class);

                System.out.println(" [x] Recieved '" + message + "'");

                try {
                    receive(message);
                } finally {
                    System.out.println(" [x] Done");
                }
            };
            channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {});


        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void receive(Message message){
        messageHandler.received(message);
    }
}

