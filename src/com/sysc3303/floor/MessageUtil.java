package com.sysc3303.floor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import com.sysc3303.commons.Message;

public class MessageUtil {
	public ArrayList<Message> createMessageArr(String filePath) throws FileNotFoundException, IOException, ParseException {
		ArrayList<Message> messageArr = new ArrayList<Message>();
		SimpleDateFormat   formatter  = new SimpleDateFormat("hh:mm:ss.S");
		
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		       String[] splittedStr      = line.split("\\s+");
		       Date     time             = new Date(formatter.parse(splittedStr[0]).getTime());
		       int      floor            = Integer.parseInt(splittedStr[1]);
		       String   direction        = splittedStr[2];
		       int      destinationFloor = Integer.parseInt(splittedStr[3]);
		       Message  message          = new Message(time, floor, direction, destinationFloor);
		       messageArr.add(message);
		    }
		}
		
		return messageArr;
	}
}
