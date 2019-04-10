package com.sysc3303.communication;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.sysc3303.communication.RabbitSender;

public class SpecializedRabbitSender extends RabbitSender {
	
	public SpecializedRabbitSender(String queueName, Message message) {
		super(queueName, message);
		
	}
	
	public void run() {
        try{
            Connection connection = RabbitShared.getConnection();
            Channel channel = connection.createChannel();

            channel.queueDeclare(queueName, false, false, false, null);

            byte[] data = serializationUtil.serialize(message);
            channel.basicPublish("", queueName, null, data);
            System.out.println(" [x] Sent '" + message + "'");
        } catch (Exception e){
        }
    }

}
