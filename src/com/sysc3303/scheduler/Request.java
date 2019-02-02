package com.sysc3303.scheduler;

import java.util.ArrayList;

public class Request {
	//This list stores the raw data from floor, need to add <type>!!!!!!!!!!!!!!!!!!!!
		private ArrayList floorRequestList;
		//This list stores the raw data from elevator,need to add <type>!!!!!!!!!!!!!!!!!!!!
		private ArrayList elevatorRequestList;
		
		public synchronized FloorRequest getFloorRequest(int i) {
			return floorRequestList.get(i);
		}
		
		
}
