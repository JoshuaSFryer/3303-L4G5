package com.sysc3303.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.SerializationUtilJSON;

import java.util.Properties;

/**
 * A runnable subscriber that can subscribe to a specifice exchange name on the rabbitmq server
 * To find out about exchanges RabbitMQ see https://www.rabbitmq.com/tutorials/tutorial-three-python.html
 *
 * @author Mattias Lightstone
 */
public class RabbitSubscriber implements Runnable{

    private final static boolean AUTO_ACK = true;
    private MessageHandler messageHandler;
    private SerializationUtilJSON<Message> serializationUtil;
    private String exchangeName;

    /**
     * Constructor
     * @param messageHandler The handler to call received of on message receipt
     * @param exchangeName The name of the exchange to subscribe to
     */
    public RabbitSubscriber(MessageHandler messageHandler, String exchangeName) {
        this.messageHandler = messageHandler;
        this.exchangeName = exchangeName;
        serializationUtil = new SerializationUtilJSON<>();
    }

    /**
     * Runs the rabbit subscriber and waits for messages to be published to the exchange.
     * On publish it receives the message and forwards it to the message handler.
     * Continues running until interrupted
     */
    public void run(){
        // create a new queue with a randomly generated name
        try{
            Connection connection = RabbitShared.connect();
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

