package com.sysc3303.communication;

import com.sysc3303.commons.ConfigProperties;
import com.sysc3303.commons.Direction;
import com.sysc3303.commons.ElevatorVector;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static java.lang.Thread.sleep;

public class RabbitSenderReceiverTest {
    static TestMessageHandler messageHandler;
    static RabbitReceiver receiver;

    @BeforeClass
    public static void setUpClass(){
        messageHandler = new TestMessageHandler(2000);
    }


//    // These 5 tests are manual tests and thus are commented out for automation purposes
//    @Test
//    public void testSendElevatorClick(){
//        RabbitSender sender = new RabbitSender(ConfigProperties.getInstance().getProperty("elevatorQueueName"), new ElevatorClickSimulationMessage(1, 1));
//        new Thread(sender).run();
//    }
//
//    @Test
//    public void testSendDoorStick(){
//        RabbitSender sender = new RabbitSender(ConfigProperties.getInstance().getProperty("elevatorQueueName"), new DoorStickMessage(1, 20));
//        new Thread(sender).run();
//    }
//
//    @Test
//    public void testSendElevatorStick(){
//        RabbitSender sender = new RabbitSender(ConfigProperties.getInstance().getProperty("elevatorQueueName"), new ElevatorStickMessage(1, 20));
//        new Thread(sender).run();
//    }
//
//    @Test
//    public void testSendFloorClick(){
//        RabbitSender sender = new RabbitSender(ConfigProperties.getInstance().getProperty("floorQueueName"), new FloorClickSimulationMessage(1, Direction.UP));
//        new Thread(sender).run();
//    }
//
//    @Test
//    public void testReceive() throws Exception{
//        RabbitReceiver rabbitReceiver = new RabbitReceiver(messageHandler, ConfigProperties.getInstance().getProperty("observerQueueName"));
//        new Thread(rabbitReceiver).start();
//        sleep(30000);
//    }

    @Test
    public void testRegularMessage() throws InterruptedException{
        RabbitReceiver receiver = new RabbitReceiver(messageHandler, "regular_test");
        // create a sender
        RabbitSender sender = new RabbitSender("regular_test", new FloorArrivalMessage(1, Direction.UP, 1));

        (new Thread(receiver)).run();
        // start the sender
        (new Thread(sender)).run();

        //sleep 2 seconds to allow time to receive
        sleep(2000);
    }
    @Test
    public void testNestedMessage() throws InterruptedException{
        RabbitReceiver receiver = new RabbitReceiver(messageHandler, "nested_test");
        // create a sender
        RabbitSender sender = new RabbitSender("nested_test", new ElevatorStateMessage(new ElevatorVector(1, Direction.UP, 1), 1));

        (new Thread(receiver)).run();
        // start the receiver
        (new Thread(receiver)).run();
        // start the sender
        (new Thread(sender)).run();
        //sleep 2 seconds to allow time to receive
        sleep(2000);
    }
}