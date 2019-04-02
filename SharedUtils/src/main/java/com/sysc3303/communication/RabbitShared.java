package com.sysc3303.communication;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sysc3303.commons.ConfigProperties;

public class RabbitShared {
	
	private static Connection connection;
	
	public static Connection getConnection() throws Exception {
		if (connection == null){
			connection = connect();
		}
		return connection;
			
	}

    public static Connection connect() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setConnectionTimeout(10000);
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
        Connection connection;
        
        connection = factory.newConnection();
        //try {
        //    connection = factory.newConnection();
        //} catch (Exception e){
        //    hostname = ConfigProperties.getInstance().getProperty("rabbitCloudBackup");
        //    ConfigProperties.getInstance().setProperty("rabbitCloudAddress", hostname);
        //    factory.setHost(hostname);
        //    System.out.println("main rabbit server is offline connecting to backup");
        //    connection = factory.newConnection();
        //}
        return connection;
    }
}
