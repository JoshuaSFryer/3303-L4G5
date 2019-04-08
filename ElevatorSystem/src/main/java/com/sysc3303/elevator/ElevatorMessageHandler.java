package com.sysc3303.elevator;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.ElevatorVector;
import com.sysc3303.commons.Direction;
import com.sysc3303.communication.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.logging.FileHandler;

import org.apache.log4j.Logger;



public class ElevatorMessageHandler extends MessageHandler {
    private static ElevatorMessageHandler instance;

    private InetAddress     schedulerAddress;
    private InetAddress     uiAddress;
    private ElevatorSystem  context;
    private Logger          log = Logger.getLogger(ElevatorMessageHandler.class);

    // Read ports from the configuration file.
    static int schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
    static int uiPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("guiPort"));
    static String elevatorQueueName = ConfigProperties.getInstance().getProperty("elevatorQueueName");
    //FileHandler fileHandler =new

    /**
     * Create an instance of this handler if one does not exist. Whether a new
     * instance is created or not, return a reference to the instance.
     * @param receivePort   The port for this handler to listen on.
     * @param context       The parent Elevator.
     * @return              A reference to this handler.
     */
    public static ElevatorMessageHandler getInstance(int receivePort, ElevatorSystem context){
        if (instance == null){
            instance = new ElevatorMessageHandler(receivePort, context);
        }
        return instance;
    }

    /**
     * Class constructor.
     * @param receivePort   The port for this handler to listen on.
     * @param context       The parent Elevator.
     */
    private ElevatorMessageHandler(int receivePort, ElevatorSystem context){
        super(receivePort);
        this.context = context;
        try{
            if (Boolean.parseBoolean(ConfigProperties.getInstance().getProperty("local"))){
                schedulerAddress = uiAddress = InetAddress.getLocalHost();
            }
            else{
                schedulerAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("schedulerAddress"));
                uiAddress = InetAddress.getByName(ConfigProperties.getInstance().getProperty("uiAddress"));
            }
        }catch(UnknownHostException e){
            e.printStackTrace();
        }
        RabbitReceiver rabbitReceiver = new RabbitReceiver(this, elevatorQueueName);
        (new Thread(rabbitReceiver, "elevator queue receiver")).start();
    }

    @Override
    public void received(Message message){
        super.received(message);
        Elevator e;
        switch (message.getOpcode()){
            case 2:
            	GoToFloorMessage goToFloorMessage = (GoToFloorMessage) message;
            	// get elevator based on the id in the message
            	log.info("Recieved Go To Floor Message");
            	log.info(goToFloorMessage);
            	
            	e = context.getElevators().get(goToFloorMessage.getElevatorId());
            	// send it to the floor in the message
                e.receiveMessageFromScheduler(goToFloorMessage.getDestinationFloor());
                break;
            case 6:
            	System.out.println("recieved click simulation message, sending to scheduler");
            	
                ElevatorClickSimulationMessage elevatorClickSimulationMessage = (ElevatorClickSimulationMessage) message;
               
                log.info("recieved click simulation message, sending to scheduler");
                log.info(elevatorClickSimulationMessage);
                // get elevator based on the id in the message
                e = context.getElevators().get(elevatorClickSimulationMessage.getElevatorId());
                
                // send press the button in that elevator
                e.pressButton(elevatorClickSimulationMessage.getFloor());
                break;
            case 9:
                //System.out.println("received door stick message: No functionality has been added to handle this yet");
                DoorStickMessage doorStickMessage = (DoorStickMessage) message;
                System.out.println("Received door stick message for elevator: " + doorStickMessage.getElevatorId());
                e = context.getElevators().get(doorStickMessage.getElevatorId());
                e.stickDoors(doorStickMessage.getNumSecondsStuck());
                break;
            case 10:
                //System.out.println("received elevator stick message: No functionality has been added to handle this yet");
                log.info("recieved elevator stick message");
                ElevatorStickMessage elevatorStickMessage = (ElevatorStickMessage) message;
                System.out.println("Received elevator stick message for elevator: " + elevatorStickMessage.getElevatorId());

                e = context.getElevators().get(elevatorStickMessage.getElevatorId());
                e.stickElevator();
                break;
            default:
            	// throw new BadMessageTypeException("This message cannot be handled by this module!");
            	System.out.println("This message type is not handled by this module!");
            	break;
                
        }
    }

    /**
     * Send an elevator's status to the scheduler.
     * @see ElevatorVector
     * @param elevatorVector    Information about the elevator's movement
     * @param elevatorId        The ID of the elevator in question.
     */
    public void sendElevatorState(ElevatorVector elevatorVector, int elevatorId){
        ElevatorStateMessage elevatorStateMessage = new ElevatorStateMessage(elevatorVector, elevatorId);
       
        log.info("Sending ElevatorStateMessage to scheduler");
        log.info(elevatorStateMessage.toString());
        send(elevatorStateMessage, schedulerAddress, schedulerPort);
    }

    /**
     * Notify the scheduler that a button within the elevator has been pressed,
     * meaning that a passenger within is requesting to visit the corresponding
     * floor.
     * @param destinationFloor  The floor to go to.
     * @param elevatorId        The ID of the elevator in question.
     */
    public void sendElevatorButton(int destinationFloor, int elevatorId, long pressedTime){
    	System.out.println("Elevator button pressed, notifying scheduler");
    	log.info("Sending ElevatorButtonMessage to scheduler");
     
        ElevatorButtonMessage elevatorButtonMessage = new ElevatorButtonMessage(destinationFloor, elevatorId, new Date(), pressedTime);
        
        log.info(elevatorButtonMessage);
        send(elevatorButtonMessage, schedulerAddress, schedulerPort);
    }

    /**
     * Notify the UI that it needs to update.
     * @param elevatorID        The ID of the relevant elevator.
     * @param currentFloor      The floor the elevator is currently on.
     * @param dir               The Direction in which the elevator is travelling.
     * @param open              True if the elevator's doors are open, false otherwise.
     */
    public void updateUI(int elevatorID, int currentFloor, Direction dir, boolean open) {
        GUIElevatorMoveMessage msg = new GUIElevatorMoveMessage(elevatorID, currentFloor, dir, open);
        String guiQueueName = ConfigProperties.getInstance().getProperty("guiQueueName");
        RabbitSender sender = new SpecializedRabbitSender(guiQueueName, msg);
        new Thread(sender).run();
    }

    /**
     * Notify the scheduler that an elevator has gotten stuck.
     * @param elevatorID    The ID of the elevator.
     */
    public void sendElevatorStuck(int elevatorID) {
    	System.out.println("I'M STUCK I'M STUCK");
    	// Send stuck message to the Scheduler...
        StuckMessage msg = new StuckMessage(elevatorID);
        send(msg, schedulerAddress, schedulerPort);
        // ...and the UI.
        String guiQueueName = ConfigProperties.getInstance().getProperty("guiQueueName");
        RabbitSender guiSender = new SpecializedRabbitSender(guiQueueName, msg);
        new Thread(guiSender).start();
    }

    /**
     * Notify the scheduler that an elevator is no longer stuck and can be
     * used.
     * @param elevatorID    The ID of the elevator.
     */
    public void sendElevatorUnstuck(int elevatorID) {
    	// Send unstuck message to the Scheduler...
        UnStuckMessage msg = new UnStuckMessage(elevatorID);
        send(msg, schedulerAddress, schedulerPort);
        // ...and the UI.
        String guiQueueName = ConfigProperties.getInstance().getProperty("guiQueueName");
        RabbitSender guiSender = new SpecializedRabbitSender(guiQueueName, msg);
        new Thread(guiSender).start();

    }
}

