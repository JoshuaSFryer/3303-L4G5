package com.sysc3303.commons;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

public class Message implements Serializable{
	private static final long serialVersionUID = 1097867564019283746L;
	private Date   requestTime;
	private int    floorNumber;
	private int    destinationFloor;
	private String direction;
	
	public Message(Date requestTime, int floorNumber, String direction, int destinationFloor) {
		this.requestTime      = requestTime;
		this.floorNumber      = floorNumber;
		this.direction        = direction;
		this.destinationFloor = destinationFloor;
	}
	
	public Date getRequestTime() {
		return requestTime;
	}
	
	public String toStringRequestTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss.S");
		return formatter.format(requestTime);
	}
	
	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}
	
	public int getFloorNumber() {
		return floorNumber;
	}
	
	public void setFloorNumber(int floorNumber) {
		this.floorNumber = floorNumber;
	}
	
	public int getDestinationFloor() {
		return destinationFloor;
	}
	
	public void setDestinationFloor(int destinationFloor) {
		this.destinationFloor = destinationFloor;
	}
	
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	public String toString() {
		String output = toStringRequestTime() + " " + floorNumber + " " + direction + " " + destinationFloor;
		return output;
	}
}
