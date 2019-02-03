package com.sysc3303.floor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.FloorArrivalMessage;
import com.sysc3303.commons.Message;

/**
 * MessageUtil scrapes an input file, given its filepath
 * @author Yu Yamanaka, Joshua Fryer
 *
 */
public class MessageUtil {
	/**
	 * Scrape an input file, given its path, and create an ArrayList of Messages.
	 * Each Message is pulled from one row of the file.
	 * @param filePath
	 * @return 		ArrayList containing several Messages
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	public ArrayList<Message> createMessageArr(String filePath) 
			throws FileNotFoundException, IOException, ParseException {
		
		ArrayList<Message> messageArr = new ArrayList<Message>();
		SimpleDateFormat   formatter  = new SimpleDateFormat("hh:mm:ss.S");
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[]     splittedStr      = line.split("\\s+");
		       Date     	time             = new Date(formatter.parse(
		    		   							splittedStr[0]).getTime());
		       int      	floor            = Integer.parseInt(splittedStr[1]);
		       Direction	direction        = getDirection(splittedStr[2]);
		       int      	destinationFloor = Integer.parseInt(splittedStr[3]);
		       Message  	message          = new FloorArrivalMessage(floor, direction);
		       messageArr.add(message);
		    }
		}
		
		return messageArr;
	}
	
	/**
	 * Given a string, determine whether it maps to UP or DOWN values of the
	 * Direction enum, and return a Direction of that value.
	 * @param s		The string to test.
	 * @return 		A Direction containing Up or Down.
	 * @throws		IllegalArgumentException if the string does not map to
	 * 				either "up" or "down".
	 * @see Direction
	 */
	private Direction getDirection(String s) {
		String lower = s.toLowerCase();
		if(lower.equals("up")) {
			return Direction.UP;
		}
		else if(lower.equals("down")) {
			return Direction.DOWN;
		}
		else {
			String errorString = "Invalid direction in file! Direction read was:" + s;
			throw new IllegalArgumentException(errorString);
		}
	}
}
