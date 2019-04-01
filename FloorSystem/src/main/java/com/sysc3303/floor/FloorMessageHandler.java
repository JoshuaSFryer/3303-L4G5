package com.sysc3303.floor;

import com.sysc3303.commons.*;
import com.sysc3303.communication.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class FloorMessageHandler extends MessageHandler {
    //TODO you need to add the port numbers that will be associated with scheduler
    private InetAddress schedulerAddress;
    private InetAddress simulatorAddress;
    private InetAddress guiAddress;
    private FloorSystem floorSystem;

    static int schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
    static int floorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("floorPort"));
    static int simulatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("simulatorPort"));
    static int guiPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("guiPort"));
    static String floorQueueName = ConfigProperties.getInstance().getProperty("floorQueueName");

    private static FloorMessageHandler instance;

    public static FloorMessageHandler getInstance(int receivePort, FloorSystem floorSystem){
        if (instance == null){
            return new FloorMessageHandler(receivePort, floorSystem);
        }
        return instance;
    }

    private FloorMessageHandler(int receivePort, FloorSystem floorSystem){
        super(receivePort);
        this.floorSystem = floorSystem;
        //TODO currently for localhost this is how it looks
        try{
            if (Boolean.parseBoolean(ConfigProperties.getInstance().getProperty("local"))){
                schedulerAddress = simulatorAddress = guiAddress = InetAddress.getLocalHost();
            }
            else{
                schedulerAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("schedulerAddress"));
                simulatorAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("simulatorAddress"));
                guiAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("GUIAddress"));
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        RabbitReceiver rabbitReceiver = new RabbitReceiver(this, floorQueueName);
        (new Thread(rabbitReceiver, "elevator queue receiver")).start();
    }

    @Override
    public synchronized void received(Message message){
        super.received(message);
    	System.out.println("From Floor");
    	System.out.println("received message!");
    	System.out.println(message.toString());
    	// Functionality to receive message
        switch (message.getOpcode()){
            case 1:
                // What happens when you receive FloorArrival
            	FloorArrivalMessage floorArrivalMessage = (FloorArrivalMessage) message;
            	try {
            			floorSystem.floorArrival(floorArrivalMessage.getFloor(),
                                floorArrivalMessage.getCurrentDirection(),
                                floorArrivalMessage.getElevatorId());
            	} catch (InterruptedException e) {
            		e.printStackTrace();
            	}
                break;
            case 5:
                // What happens when you receive FloorButtonSimulationMessage
                FloorClickSimulationMessage floorClickSimulationMessage = (FloorClickSimulationMessage) message;
                floorSystem.buttonPress(floorClickSimulationMessage.getFloor(), floorClickSimulationMessage.getDirection());
                break;
            default:
                // TODO what happens when you get an invalid opcode
        }
    }

    public void sendFloorButton(int floor, Direction direction, long pressedTime){
        System.out.println("\nSending floor button press to " +
                "scheduler \n\tFloor: " + floor + "\n\tDirection: "+direction);
        FloorButtonMessage floorButtonMessage = new FloorButtonMessage(floor, direction, new Date(), pressedTime);
        send(floorButtonMessage, schedulerAddress, schedulerPort);
        try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void sendFloorArrival(int floor, Direction direction, int elevatorId) {
        System.out.println("\nSending floor arrival to " +
                "simulator \n\tFloor: " + floor + "\n\tDirection: "+direction +"\n\tElevator ID: " + elevatorId);
    	FloorArrivalMessage floorArrivalMessage = new FloorArrivalMessage(floor, direction, elevatorId);
    	send(floorArrivalMessage, simulatorAddress, simulatorPort);
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    public void updateUI(boolean downState, boolean upState, int floor) {
        GUIFloorMessage msg = new GUIFloorMessage(downState, upState, floor);
        String guiQueueName = ConfigProperties.getInstance().getProperty("guiQueueName");
        RabbitSender sender = new RabbitSender(guiQueueName, msg);
        new Thread(sender).start();
    }
}
