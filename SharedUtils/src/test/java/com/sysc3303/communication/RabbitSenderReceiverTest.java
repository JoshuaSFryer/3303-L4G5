package com.sysc3303.communication;

import com.sysc3303.commons.Direction;
import org.junit.Test;

import static java.lang.Thread.sleep;

public class RabbitSenderReceiverTest {
    @Test
    public void test1() throws InterruptedException{
        // create a receiver
        TestMessageHandler messageHandler = new TestMessageHandler(2000);
        RabbitReceiver receiver = new RabbitReceiver(messageHandler, "test_queue");
        // create a sender
        RabbitSender sender = new RabbitSender("test_queue", new FloorArrivalMessage(1, Direction.UP, 1));

        // start the receiver
        (new Thread(receiver)).run();
        // start the sender
        (new Thread(sender)).run();
        //sleep for some seconds
        sleep(2000);
    }
}