public class ElevatorSender {
    private static ElevatorSender instance;

    private ElevatorSender(){
    }

    public static ElevatorSender getInstance() {
        if (instance == null){
            instance = new ElevatorSender();
        }
        return instance;
    }

    public static synchronized void setNull(){}

    public boolean sendElevatorClick(int elevatorNum, int elevatorButton){
        System.out.println("sendElevatorClick has not been implemented yet");
        return true;
    }
}
