package com.sysc3303.simulator;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Event {
    private static DateFormat df = new SimpleDateFormat("kk:mm:ss", Locale.CANADA);

    private Date timestamp;
    private int floor, elevatorButton;
    private DirectionEnum direction;

    public Event(Date timestamp, int floor, int elevatorButton, DirectionEnum direction) {
        this.timestamp = timestamp;
        this.floor = floor;
        this.direction = direction;
        this.elevatorButton = elevatorButton;
    }

    public Event(String[] stringArray) throws ParseException{
        this.timestamp = parseDate(stringArray[0]);
        this.floor = Integer.parseInt(stringArray[1]);
        this.direction = DirectionEnum.valueOf(stringArray[2].toUpperCase());
        this.elevatorButton = Integer.parseInt(stringArray[3]);
    }

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getElevatorButton() {
        return elevatorButton;
    }

    public void setElevatorButton(int elevatorButton) {
        this.elevatorButton = elevatorButton;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
        this.direction = direction;
    }
}
