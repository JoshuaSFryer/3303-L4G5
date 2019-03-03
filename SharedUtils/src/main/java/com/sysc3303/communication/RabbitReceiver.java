package com.sysc3303.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.sysc3303.commons.SerializationUtil;

public class RabbitReceiver implements Runnable {

    private final static boolean AUTO_ACK = true;
    private final static String HOSTNAME = "localhost";
    private MessageHandler messageHandler;
    private SerializationUtil<Message> serializationUtil;
    private String queueName;

    public RabbitReceiver(MessageHandler messageHandler, String queueName){
        this.messageHandler = messageHandler;
        this.queueName = queueName;
        serializationUtil = new SerializationUtil<>();
    }

    public void run(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOSTNAME);
        try{
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                Message message = serializationUtil.deserialize(delivery.getBody(), delivery.getBody().length);

                System.out.println(" [x] Received '" + message + "'");

                try {
                    receive(message);
                } finally {
                    System.out.println(" [x] Done");
                }
            };
            channel.basicConsume(queueName, AUTO_ACK, deliverCallback, consumerTag -> {});
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void receive(Message message) {
        messageHandler.received(message);
    }
}
