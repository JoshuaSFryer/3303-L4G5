//Just to make life little easier, it's gonna get better at next iteration
//This class shall be merged with the floor class..after getting all the data from the 
// scheduler

package com.sysc3303.floor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.sysc3303.commons.Direction;
import com.sysc3303.constants.Constants;

import static com.sysc3303.floor.FloorMessageHandler.floorPort;


public class FloorSystem {
	//TODO change this number to properties file value
	private static int totalNumberofFloors = 5;
	static int passengerIsOnFloor;
	static String direction = "None";
	private List<FloorGeneral> floorList;
	private FloorMessageHandler floorMessageHandler;

	public FloorSystem(){
		floorList = new ArrayList<>();
		floorMessageHandler = new FloorMessageHandler(floorPort, this);
		
		for (int i=0; i< FloorSystem.totalNumberofFloors; i++) {
			floorList.add(new FloorGeneral(i+1));
		}
	}
	
	public List<FloorGeneral> getFloorList() {
		return floorList;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		}
	
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
	 * Method buttonPress deals with the information coming from Simulator SubSystem
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

	public void main(){
	    FloorSystem floorSystem = new FloorSystem();
		while(true){
		}
	}
	
}


