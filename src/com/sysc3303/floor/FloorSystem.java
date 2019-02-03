/*
 * FloorClass creates an Array of number of Floors. For example 5 Floors.
 * It does the following thing
 * 
 * 	1. It takes the message from Scheduler System and turns on the Lamps accordingly
 * 	2. It sends a message to the simulator stating the arrival of the floor
 */
package com.sysc3303.floor;

import java.util.ArrayList;
import java.util.List;

import com.sysc3303.commons.Direction;

import static com.sysc3303.floor.FloorMessageHandler.floorPort;


public class FloorSystem {
	//TODO change this number to properties file value
	private static int totalNumberofFloors = 5;
	private List<FloorGeneral> floorList;
	private FloorMessageHandler floorMessageHandler;

	//Constructor
	public FloorSystem(){
		floorList = new ArrayList<>();
		floorMessageHandler = new FloorMessageHandler(floorPort, this);
		
		for (int i=0; i< FloorSystem.totalNumberofFloors; i++) {
			floorList.add(new FloorGeneral(i+1));
		}
	}
	
	/*
	 * Getter Method To get The Floor List
	 */
	
	public List<FloorGeneral> getFloorList() {
		return floorList;
	}
	
	
	//Main method Not is use right Now. Left for Future testing
    public static void main(String[] args){
        FloorSystem floorSystem = new FloorSystem();
        while(true){
        }
    }

	/*
	 * floorArrival method does the follwoing things
	 * 	1. It takes the message from the scheduler
	 * 	2. It sends message to the simulator
	 * 	3. It turns off the lamp once the Elevator Has arrived in the destination Floor
	 * 
	 * @param arrivedFloor Number and Directio
	 */
	public void floorArrival (int arrivalFloor,  Direction direction) throws InterruptedException {
		
		//If the Elevator has Arrived on the passenger floor where requested
		FloorGeneral arriveFloor = floorList.get(arrivalFloor-1);
		
		if (direction == Direction.UP) {
			//turning uplight off
			arriveFloor.getLamps().turnUpLampON();
			arriveFloor.getButtons().setUpButtonLight(false);
			arriveFloor.getLamps().turnUpLampOFF();
		}
		else if(direction == Direction.DOWN) {
			//turning uplight off
			arriveFloor.getLamps().turnDownLampOFF();
			arriveFloor.getButtons().setDownButtonLight(false);
			arriveFloor.getLamps().turnUpLampOFF();
		}
		floorMessageHandler.sendFloorArrival(arrivalFloor, direction);
	}

	/*
	 * Method buttonPress does the following things
	 * 	1. It sends the information to Scheduler about which button from which floor has been pressedn
	 * 	2. It takes the message from the Simulator or input file
	 * 	3. It turns on the lamp based on the direction of the Elevator 
	 */

	public void buttonPress(int requestFloor, Direction buttonDirection) {
		System.out.println(buttonDirection + " button pressed on floor " + requestFloor);
		FloorGeneral arriveFloor = floorList.get(requestFloor-1);
		if(buttonDirection == Direction.UP) {
			arriveFloor.getButtons().setUpButtonLight(true);
		}
		else if(buttonDirection == Direction.DOWN) {
			arriveFloor.getButtons().setDownButtonLight(true);
		}
		//send floor button request
		floorMessageHandler.sendFloorButton(requestFloor, buttonDirection);
	}


}


