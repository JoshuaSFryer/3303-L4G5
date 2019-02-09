package commons;

import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

import static java.lang.Thread.sleep;

public class CommunicationHandlerTest{
    @Test
    public void test1() throws InterruptedException{
        int elevatorPorts = 7000;
        int schedulerPort = 7001;
        int floorPort = 7002;

        // Three message Handlers
        TestMessageHandler elevatorMessageHandler = new TestMessageHandler(elevatorPorts);
        TestMessageHandler schedulerMessageHandler = new TestMessageHandler(schedulerPort);
        TestMessageHandler floorMessageHandler = new TestMessageHandler(floorPort);

        elevatorMessageHandler.sendElevatorStateMessage(new ElevatorVector(1, Direction.DOWN, 5), 1);
        elevatorMessageHandler.sendElevatorButtonMessage(5, 1, new Date());
        schedulerMessageHandler.sendGoToFloorMessage(5);
        schedulerMessageHandler.sendFloorArrivalMessage(5, Direction.DOWN, 1);
        floorMessageHandler.sendFloorButtonMessage(5, Direction.DOWN, new Date());

        sleep(4000);

        Assert.assertEquals(1, elevatorMessageHandler.callCount);
        Assert.assertEquals(3, schedulerMessageHandler.callCount);
        Assert.assertEquals(1, floorMessageHandler.callCount);
    }
}