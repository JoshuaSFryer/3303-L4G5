package com.sysc3303.analyzer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;

/**
 * Telemetry data for button press and arrival time
 * stored and processed
 */
public class TelemetryArrivalMap {
	private HashMap<Integer, HashMap<Direction, HashMap<Long, Long>>> floorBtnArrivalMap;
	private HashMap<Integer, HashMap<Integer, HashMap<Long, Long>>>   elevatorBtnArrivalMap;
    private TelemetryMath                                             math;
    private final long                                                PLACE_HOLDER = -1;

    public TelemetryArrivalMap() {
    	floorBtnArrivalMap    = new HashMap<Integer, HashMap<Direction, HashMap<Long, Long>>>();
    	elevatorBtnArrivalMap = new HashMap<Integer, HashMap<Integer, HashMap<Long, Long>>>();
        math                  = TelemetryMath.getInstance();
    }
    
	/**
	 * adds floor button pressed time to map
	 * 
	 * @param direction
	 * @param floor
	 * @param pressedTime
	 */
	public void addFloorBtnPressTime(Direction direction, int floor, long pressedTime) {
		if(!floorBtnArrivalMap.containsKey(floor)) {
			HashMap<Direction, HashMap<Long, Long>> directionArrivalMap = new HashMap<Direction, HashMap<Long, Long>>();
 			floorBtnArrivalMap.put(floor, directionArrivalMap);
			
		}
		
		HashMap<Direction, HashMap<Long, Long>> directionArrivalMap = floorBtnArrivalMap.get(floor);
		
		if(!directionArrivalMap.containsKey(direction)) {
			HashMap<Long, Long> arrivalTimeMap = new HashMap<Long, Long>();
			directionArrivalMap.put(direction, arrivalTimeMap);
			
		}
	    
		HashMap<Long, Long> arrivalTimeMap = directionArrivalMap.get(direction);
	
		arrivalTimeMap.put(PLACE_HOLDER, pressedTime);
	}
	
	/**
	 * adds arrival time of elevator to that floor to map,
	 * then prints telemetry analysis between floor button press
	 * and elevator arrival
	 * @param direction
	 * @param floor
	 * @param arrivalTime
	 */
	public void addFloorArrivalTime(Direction direction, int floor, long arrivalTime) {
		HashMap<Direction, HashMap<Long, Long>> directionArrivalMap = floorBtnArrivalMap.get(floor);
		if(directionArrivalMap != null) {
			HashMap<Long, Long> arrivalTimeMap = directionArrivalMap.get(direction);
			if(arrivalTimeMap != null) {
				if(arrivalTimeMap.containsKey(PLACE_HOLDER)) {
					long pressedTime = arrivalTimeMap.get(PLACE_HOLDER);
					arrivalTimeMap.remove(PLACE_HOLDER);
					arrivalTimeMap.put(arrivalTime, pressedTime);
					printFloorArrivalAnalysis(floor, direction);
				}
			}
		}
	}

	/**
	 * adds elevator button pressed time
	 * to map
	 * 
	 * @param elevatorId
	 * @param floor
	 * @param pressedTime
	 */
	public synchronized void addElevatorButnPressTime(int elevatorId, int floor, long pressedTime) {
		if(!elevatorBtnArrivalMap.containsKey(elevatorId)) {
			HashMap<Integer, HashMap<Long, Long>> floorTimeMap = new HashMap<Integer, HashMap<Long, Long>>();
			elevatorBtnArrivalMap.put(elevatorId, floorTimeMap);
		}
		
		HashMap<Integer, HashMap<Long, Long>> floorTimeMap = elevatorBtnArrivalMap.get(elevatorId);
		
		if(!floorTimeMap.containsKey(floor)) {
			HashMap<Long, Long> arrivalTimeMap = new HashMap<Long, Long>();
			floorTimeMap.put(floor, arrivalTimeMap);
		}
		
		HashMap<Long, Long> arrivalTimeMap = floorTimeMap.get(floor);
		
		arrivalTimeMap.put(PLACE_HOLDER, pressedTime);
	}
	
	/**
	 * adds elevator arrival time to map
	 * and prints telemetry analysis between
	 * elevator button press and arrival at floor
	 * 
	 * @param elevatorId
	 * @param floor
	 * @param arrivalTime
	 */
	public synchronized void addElevatorArrivalTime(int elevatorId, int floor, long arrivalTime) {
		HashMap<Long, Long> arrivalTimeMap = elevatorBtnArrivalMap.get(elevatorId).get(floor);
		long pressedTime                   = arrivalTimeMap.get(PLACE_HOLDER);
		
		arrivalTimeMap.remove(PLACE_HOLDER);
		arrivalTimeMap.put(arrivalTime, pressedTime);
		printElevatorArrivalAnalysis(elevatorId);
	}

	/**
	 * returns telemetry list for specific elevatorId
	 * 
	 * @param elevatorId
	 * @return
	 */
	private List<Long> getElevatorTelemetryList(int elevatorId) {
		HashMap<Integer, HashMap<Long, Long>> floorTimeMap          = elevatorBtnArrivalMap.get(elevatorId);
		List<Long>                            elevatorTelemetryList = new ArrayList<Long>();
		
		floorTimeMap.forEach((floor, arrivalTimeMap)-> {
			arrivalTimeMap.forEach((arrivalTime, pressedTime)->{
				if(arrivalTime != PLACE_HOLDER) {
					long difference = arrivalTime - pressedTime;
					elevatorTelemetryList.add(difference);
				}
				
			});
		});
		
		return elevatorTelemetryList;
	}

	private List<Long> getAllElevatorTelemetry(){
		List<Long> elevatorTelemetryList = new ArrayList<>();
	    int num_elevators = Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfElevators"));
	    for (int i = 0; i<num_elevators; i++){
	        try{
	        elevatorTelemetryList.addAll(getElevatorTelemetryList(i));
            } catch (Exception e){
            }
		}
	    return elevatorTelemetryList;
	}

	private List<Long> getAllFloorTelemetry(){
		List<Long> floorTelemetryList = new ArrayList<>();
		int num_floors = Integer.parseInt(ConfigProperties.getInstance().getProperty("numberOfFloors"));
		for (int i = 0; i<num_floors; i++){
		    try{
				floorTelemetryList.addAll(getFloorTelemetryList(i, Direction.UP));
			} catch (Exception e){
			}
		    try{
				floorTelemetryList.addAll(getFloorTelemetryList(i, Direction.DOWN));
			} catch (Exception e){
			}
		}
		return floorTelemetryList;
	}
	/**
	 * returns floor telemetry for specific floor
	 * 
	 * @param floor
	 * @param direction
	 * @return
	 */
	private List<Long> getFloorTelemetryList(int floor, Direction direction) {
		HashMap<Long, Long> floorTelemetryMap  = floorBtnArrivalMap.get(floor).get(direction);
		List<Long>          floorTelemetryList = new ArrayList<Long>();
		
		floorTelemetryMap.forEach((arrivalTime, pressedTime)->{
			if(arrivalTime != PLACE_HOLDER) {
				long difference = arrivalTime - pressedTime;
				floorTelemetryList.add(difference);
			}
		});
		return floorTelemetryList;
	}
	
	/**
	 * prints telemetry analysis for specific elevators 
	 * and button press to floor arrival
	 * 
	 * @param elevatorId
	 */
	private void printElevatorArrivalAnalysis(int elevatorId) {
		List<Long> list = getAllElevatorTelemetry();
        long       mean = (long) math.getMean(list);
        long       var  = (long) math.getVariance(list, mean);
        long	   max  = (long) math.getMax(list);
		System.out.println("\n");
        System.out.println("Elevator " + elevatorId + " Button Press to Floor Arrival:");
        System.out.println("\tMean: " + mean + "ms");
		System.out.println("\tVariance: " + var + "ms^2");
		System.out.println("\tMax: " + max + "ms");
		System.out.println("\n");
    }

	/**
	 * prints telemetry analysis for specific floor 
	 * and direction button press and elevator arrival
	 * 
	 * @param floor
	 * @param direction
	 */
	private void printFloorArrivalAnalysis(int floor, Direction direction) {
		List<Long> list =  getAllFloorTelemetry();
        long       mean = (long) math.getMean(list);
        long       var  = (long) math.getVariance(list, mean);
		long	   max  = (long) math.getMax(list);
		System.out.println("\n");
        System.out.println("Floor " + floor + ", Direction " + direction + " Button Press to Floor Arrival:");
		System.out.println("\tMean: " + mean + "ms");
		System.out.println("\tVariance: " + var + "ms^2");
		System.out.println("\tMax: " + max + "ms");
		System.out.println("\n");
    }
}
