package com.sysc3303.scheduler;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.elevator.ElevatorMessageHandlerMock;
import com.sysc3303.floor.FloorMessageHandlerMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static java.lang.Thread.sleep;

public class SchedulerEndpointTest {

    private static int schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
    private static int elevatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("elevatorPort"));
    private static int floorPort= Integer.parseInt(ConfigProperties.getInstance().getProperty("floorPort"));
    private  static int  simulatorPort= Integer.parseInt(ConfigProperties.getInstance().getProperty("simulatorPort"));

    private FloorMessageHandlerMock floorMock;
    private ElevatorMessageHandlerMock elevatorMock;

    @BeforeClass
    public static void classSetUp(){
    }

    @Before
    public void setUp(){
        Scheduler scheduler = new Scheduler();
        floorMock = FloorMessageHandlerMock.getInstance(floorPort);
        elevatorMock = ElevatorMessageHandlerMock.getInstance(elevatorPort);
    }

    @Test
    public void simpleElevatorButtonTest() throws InterruptedException{
        elevatorMock.sendElevatorButton(1,2);
        sleep(10000);
    }
}
