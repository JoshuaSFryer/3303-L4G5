package com.sysc3303.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sysc3303.commons.SerializationUtilJSON;

/**
 * A runnable publisher that can publish a message to a specific exchange name on the rabbitmq server
 * To find out about exchanges RabbitMQ see https://www.rabbitmq.com/tutorials/tutorial-three-python.html
 *
 * @author Mattias Lightstone
 */
public class RabbitPublisher implements Runnable{

    private SerializationUtilJSON<Message> serializationUtil;
    private String exchangeName;
    private Message message;

    /**
     * Constructor
     * @param exchangeName The name of the exchange to publish to
     * @param message The message to be published to the exchange
     */
    public RabbitPublisher(String exchangeName, Message message){
        this.exchangeName = exchangeName;
        this.message = message;
        serializationUtil = new SerializationUtilJSON<>();
    }

    /**
     * Connects to rabbitMQ and publishes the message to the exchange
     */
    public void run(){
        try{
            Connection connection = RabbitShared.connect();
            Channel channel = connection.createChannel();

            channel.exchangeDeclare(exchangeName, "fanout");

            byte[] data = serializationUtil.serialize(message);
            channel.basicPublish(exchangeName, "", null, data);
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
        }
    }
    // get a random Queue
}
