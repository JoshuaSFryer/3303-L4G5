
public class selectCar {
	/**
	 * Select an elevator to handle a floor request
	 * @return the selected elevator's ID
	 */
	public int SelectElevator() {
		int call = floorRequest.floor;
		int FS = 1;
		int newFS;
		int N = totalNumberOfFloor;
		elevatorId selectedCar = 0; 
		int d;
		for (elevatorVector c:elevatorVectorList ) {
			
			d= abs(c.currentFloor - call.floor);
			
			if (c.currentDirection == IDLE) {
				newFS = N + 1 - d;
				
			}else if (c.currentDirection == DOWN) {
				if(call.floor > c.currentFloor) {
					newFS = 1;
					
				}else if(call.direction == DOWN 
						&& call.floor < c.currentFloor) {
					newFS = N + 2 - d;
					
				}else if(call.direction != DOWN
						&& call.floor < c.currentFloor) {
					newFS = N + 1 - d;
					
				}
			}else if (c.currentDirection == UP) {
				if(call.floor < c.currentFloor) {
					newFS = 1;
					
				}else if(call.direction == UP 
						&& call.floor > c.currentFloor) {
					newFS = N + 2 - d;
					
				}else if(call.direction != UP
						&& call.floor > c.currentFloor) {
					newFS = N + 1 - d;
					
				}
			}
			
			if(newFS > FS ) {
				selectedCar = elevatorId;
				FS = newFS;
			}	
		}
		
		return selectedCar;
	}
	

}
