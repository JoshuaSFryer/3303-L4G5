package com.sysc3303.floor;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;
import com.sysc3303.scheduler.SchedulerMessageHandlerMock;
import com.sysc3303.simulator.SimulatorMessageHandlerMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

import static java.lang.Thread.sleep;

public class FloorSystemIntegrationTest {


    private static int schedulerPort;
    private static int elevatorPort;
    private static int floorPort;
    private static int simulatorPort;

    private static FloorSystem floorSystem;

    @BeforeClass
    public static void classSetUp() throws IOException{

        schedulerPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
        elevatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("elevatorPort"));
        floorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("floorPort"));
        simulatorPort = Integer.parseInt(ConfigProperties.getInstance().getProperty("simulatorPort"));
        floorSystem = new FloorSystem();
    }

    @Before
    public void setUp(){
    }

    @Test
    public void testArrivalMessage() throws InterruptedException{
        SchedulerMessageHandlerMock schedulerMessageHandlerMock = SchedulerMessageHandlerMock.getInstance(8100);
        SimulatorMessageHandlerMock simulatorMessageHandlerMock = SimulatorMessageHandlerMock.getInstance(simulatorPort);

        simulatorMessageHandlerMock.sendFloorButtonClickSimulation(1, Direction.DOWN);
        sleep(1000);
        simulatorMessageHandlerMock.sendFloorButtonClickSimulation(5, Direction.UP);
        sleep(1000);
        simulatorMessageHandlerMock.sendFloorButtonClickSimulation(3, Direction.DOWN);
        sleep(5000);
    }

    @Test
    public void testButtonClickMessage(){

    }
}
