//Just to make life little easier, it's gonna get better at next iteration
//This class shall be merged with the floor class..after getting all the data from the 
// scheduler

package com.sysc3303.floor;

import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.scene.traversal.Direction;

public class FloorSystem {
	
	private static int totalNumberofFloors = 5;
	static int passengerIsOnFloor;
	static String direction = "None";
	private List<FloorGeneral> floorList;
	
	public FloorSystem(){
		floorList = new ArrayList<>();
		for (int i=0; i< FloorSystem.totalNumberofFloors; i++) {
			floorList.add(new FloorGeneral(i+1));
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		}
	
	public void floorArrival (int arrivalFloor, Direction direction) {
		
		//If the Elevator has Arrived on the passenger floor where requested
		FloorGeneral arriveFloor = floorList.get(arrivalFloor-1);
		if (direction == Direction.UP) {
			//turning uplight off
			arriveFloor.getLamps().turnUpLampOFF();
			// send arrival message to Simulator
		}
		else if(direction == Direction.DOWN) {
			//turning uplight off
			arriveFloor.getLamps().turnDownLampOFF();
			// send arrival message to simulator
		}
	}

	/*
	 * Method buttonPress deals with the information coming from Simulator SubSystem
	 */
	
	public void buttonPress(int requestFloor, Direction buttonDirection) {
		
		FloorGeneral arriveFloor = floorList.get(requestFloor-1);
		if(buttonDirection == Direction.UP) {
			arriveFloor.getButtons().setUpButtonLight(true);
		}
		else if(buttonDirection == Direction.DOWN) {
			arriveFloor.getButtons().setDownButtonLight(true);
		}
		//send floor button request
	}

			
		
		
}


