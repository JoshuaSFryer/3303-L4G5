package com.sysc3303.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.SerializationUtilJSON;

/**
 * A runnable receiver that listens and consumes from a message queue on RabbitMQ
 * To learn more about consumers and RabbitMQ see https://www.rabbitmq.com/tutorials/tutorial-two-python.html
 *
 * @author Mattias Lightstone
 */
public class RabbitReceiver implements Runnable {

    private final static boolean AUTO_ACK = true;
    private MessageHandler messageHandler;
    private SerializationUtilJSON<Message> serializationUtil;
    private String queueName;

    /**
     * Constructor
     * @param messageHandler The messagehandler whose receive message is called on consumption of a message
     * @param queueName The queuename to listen to
     */
    public RabbitReceiver(MessageHandler messageHandler, String queueName){
        this.messageHandler = messageHandler;
        this.queueName = queueName;
        serializationUtil = new SerializationUtilJSON<>();
    }

    /**
     * Runs the receiver and waits for a request. When one is received it calls the messageHandler's received message
     * The receiver will continue to run util interrupted.
     */
    public void run(){
        try{
            Connection connection = RabbitShared.connect();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                Message message = serializationUtil.deserialize(delivery.getBody(), Message.class);

                System.out.println(" [x] Received '" + message + "'");

                try {
                    receive(message);
                } finally {
                    System.out.println("\n");
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
