package com.sysc3303.commons;

import java.io.Serializable;
import java.sql.Date;
import java.text.SimpleDateFormat;

/**
 * Message represents data pulled from a single line of the input file.
 * 
 * @see		MessageUtil
 * @author	Yu Yamanaka
 *
 */

public class Message implements Serializable {
	public static final long serialVersionUID = 1097867564019283746L;
	public final Date   	requestTime;
	public final int    	floorNumber;
	public final int    	destinationFloor;
	public final Direction 	direction;
	
	public Message(Date requestTime, int floorNumber, Direction direction, int destinationFloor) {
		this.requestTime      = requestTime;
		this.floorNumber      = floorNumber;
		this.direction        = direction;
		this.destinationFloor = destinationFloor;
	}
	
	/**
	 * Get a formatted string containing the time contained in requestTime, without the date components.
	 * @return the time elements of requestTime
	 */
	public String toStringRequestTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss.S");
		return formatter.format(requestTime);
	}
	
	public String toString() {
		String output = toStringRequestTime() + " " + floorNumber + " " + direction + " " + destinationFloor;
		return output;
	}
}
