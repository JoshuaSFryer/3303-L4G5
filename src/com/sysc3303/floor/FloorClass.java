//Just to make life little easier, it's gonna get better at next iteration
//This class shall be merged with the floor class..after getting all the data from the 
// scheduler

package com.sysc3303.floor;

public class FloorClass {
	
	int totalNumberofFloors = 5;
	static int passengerIsOnFloor;
	static String direction = "None";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		FloorGeneral Floor1  = new FloorGeneral();
		FloorGeneral Floor2 = new FloorGeneral();
		FloorGeneral Floor3 = new FloorGeneral();
		FloorGeneral Floor4 = new FloorGeneral();
		FloorGeneral Floor5 = new FloorGeneral();
		
		
		// say after parsing the message from scheduler, we get the passenger is on 3rd floor
		
		
		
		
		//After Parsing I need the information in this block
		
		passengerIsOnFloor = 5;
		direction = "up";
		

		if (direction.equals("up") || direction.equals("down")){
		
			System.out.println("Passenger is on Floor " + passengerIsOnFloor +
							", Request made to go " + direction + "-wards.");	
		}
		
		else if (direction.equals("elevatorArrived")) {
			System.out.println("Your Elevator Has Arrived!");
		}
		
		
		if (passengerIsOnFloor == 1) {
			
				if (direction.equals("down")) {
					System.out.println("You can't go down because You are on the Ground Floor");
				}
				else {
					Floor1.setButton(direction);
					Floor1.setDirectionForLamp(direction);
				}
		}
		else if (passengerIsOnFloor == 2) {
			Floor2.setButton(direction);
			Floor2.setDirectionForLamp(direction);
		}
		else if (passengerIsOnFloor == 3) {
			Floor3.setButton(direction);
			Floor3.setDirectionForLamp(direction);
		}
		else if (passengerIsOnFloor == 4) {
			Floor4.setButton(direction);
			Floor4.setDirectionForLamp(direction);
		}
		
		else if (passengerIsOnFloor == 5) {
			
				if (direction.equals("up")) {
					System.out.println("You can't go up because you are on the Top Floor");
				}
				else {
					Floor5.setButton(direction);
					Floor5.setDirectionForLamp(direction);
				}
		}
		
		
		}

			
		
		
}


