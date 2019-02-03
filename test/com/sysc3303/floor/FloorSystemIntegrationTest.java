package com.sysc3303.floor;

import com.sysc3303.commons.Direction;
import com.sysc3303.constants.Constants;
import com.sysc3303.scheduler.SchedulerMessageHandlerMock;
import com.sysc3303.simulator.SimulatorMessageHandlerMock;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static java.lang.Thread.sleep;

public class FloorSystemIntegrationTest {


    private static int schedulerPort;
    private static int elevatorPort;
    private static int floorPort;
    private static int simulatorPort;

    private static FloorSystem floorSystem;

    @BeforeClass
    public static void classSetUp() throws FileNotFoundException,IOException{
        Properties properties = new Properties();
        InputStream inputStream = new FileInputStream(Constants.CONFIG_PATH);
        properties.loadFromXML(inputStream);

        schedulerPort = Integer.parseInt(properties.getProperty("schedulerPort"));
        elevatorPort = Integer.parseInt(properties.getProperty("elevatorPort"));
        floorPort = Integer.parseInt(properties.getProperty("floorPort"));
        simulatorPort = Integer.parseInt(properties.getProperty("simulatorPort"));
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
