public class FloorReceiver {
    private static FloorReceiver instance;

    public static FloorReceiver getInstance() {
        if (instance == null){
            instance = new FloorReceiver();
        }
        return instance;
    }
    public static synchronized void setNull(){}

    public boolean receiveElevatorArrival(int floor, DirectionEnum direction, int elevatorNum){
        System.out.println("Elevator " + elevatorNum + " has arrived at floor " + floor + " traveling " + direction);

        return TriggeredEventMap.getInstance().send(floor, direction, elevatorNum);
    }

    private FloorReceiver(){
    }
}
