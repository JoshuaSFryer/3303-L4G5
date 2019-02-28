package com.sysc3303.simulator;
import com.sysc3303.commons.Direction;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Mattias Lightstone
 *
 * Event is a data structure created to represent one user of the elevator, requesting it at a floor at a specific time
 * then once the elevator arrives, entering and pressing a floor button
 */
public abstract class Event {
    // defining the date format
    private static DateFormat df = new SimpleDateFormat("kk:mm:ss", Locale.CANADA);

    protected Date timestamp;
    protected String type;

    public Event(String[] eventStringArray) throws ParseException{
        this.timestamp = parseDate(eventStringArray[0]);
        this.type = eventStringArray[1];
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Parses the date from a string representation of a time in the format HH:MM:SS to a Date object
     *
     * @param dateString a string containing the time of the event
     * @return the current date with the time of the event as a Date object
     * @throws ParseException when parsing of the string fails
     */
    private Date parseDate(String dateString) throws ParseException{
//        Calendar temp_cal = Calendar.getInstance();
        Calendar temp = Calendar.getInstance();
        temp.setTime(df.parse(dateString));

        Calendar finalTime = Calendar.getInstance();
        finalTime.set(Calendar.HOUR_OF_DAY, temp.get(Calendar.HOUR_OF_DAY));
        finalTime.set(Calendar.MINUTE, temp.get(Calendar.MINUTE));
        finalTime.set(Calendar.SECOND, temp.get(Calendar.SECOND));

        return finalTime.getTime();
    }


}
