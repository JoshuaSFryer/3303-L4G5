import java.net.URL;

import static java.lang.Thread.sleep;

public class Runner {
    public static void main(String args[]) throws InterruptedException{
        EventMaker eventMaker = new EventMaker();
        URL url = Thread.currentThread().getContextClassLoader().getResource("simulator/src/test/resources/simpleTestEvents.txt");
        System.out.println(System.getProperty("user.dir"));
        eventMaker.addEventsFromFileToTimer("simulator/src/test/resources/simpleTestEvents.txt");
        sleep(6000);
        FloorReceiver.getInstance().receiveElevatorArrival(5, DirectionEnum.DOWN, 1);
        FloorReceiver.getInstance().receiveElevatorArrival(2, DirectionEnum.UP, 2);
    }
}
