package com.sysc3303.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sysc3303.commons.SerializationUtilJSON;

/**
 * A runnable producer that sends a Message to a a message queue on RabbitMQ
 * To learn more about producers and RabbitMQ see https://www.rabbitmq.com/tutorials/tutorial-two-python.html
 *
 * @author Mattias Lightstone
 */
public class RabbitSender implements Runnable{

    protected SerializationUtilJSON<Message> serializationUtil;
    protected String queueName;
    protected Message message;

    /**
     * Constructor
     * @param queueName the name of the queue to produce to
     * @param message the message to add the the queue
     */
    public RabbitSender(String queueName, Message message){
        this.queueName = queueName;
        this.message = message;
        serializationUtil = new SerializationUtilJSON<>();
    }

    /**
     * Connects to the rabbitMQ server and sends the message to it
     */
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
