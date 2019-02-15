package com.sysc3303.elevator;

import com.sysc3303.commons.ConfigProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * The main class for the Elevator system.
 * Contains all the elevators and handles communication for them
 */
public class ElevatorSystem {
    private List<Elevator>         elevators      = new ArrayList<>();
    private ElevatorMessageHandler messageHandler;
    private Logger                 log            = Logger.getLogger(ElevatorSystem.class);


    /**
     * Elevator system constructor creates an elevator system with a message handler, and the number of floors specified
     * @param num_elevators the number of elevators in the system
     * @param num_floors the number floors in the elevator system
     * @param elevatorSystemPort the port for the message handler
     */
    public ElevatorSystem(int num_elevators, int num_floors, int elevatorSystemPort){
        ElevatorMessageHandler messageHandler = ElevatorMessageHandler.getInstance(elevatorSystemPort, this);
        
        DOMConfigurator.configure(ElevatorSystem.class.getResource("/log4j.xml"));
        log.info("ElevatorSystem starting at port " + elevatorSystemPort);
        
        for (int i = 0; i<num_elevators; i++){
            elevators.add(new Elevator(num_floors, i, messageHandler));
        }
    }

    public List<Elevator> getElevators(){
        return elevators;
    }

    public ElevatorMessageHandler getMessageHandler() {
        return messageHandler;
    }

    /**
     * Creates a new Elevator system with open ports ready for input
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        boolean                    running           = true;

        // Create a new Elevator instance.
        int      port     = Integer.parseInt(ConfigProperties.getInstance().getProperty("elevatorPort"));
        int     num_floors = Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfFloors"));
        int num_elevators = Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfElevators"));
        ElevatorSystem elevatorSystem = new ElevatorSystem(num_elevators, num_floors, port);
      
        System.out.println("Starting Elevator System, with " + num_elevators +
                " elevators and " + num_floors + " floors");
        while(running) {
        }
    }
}

