package com.sysc3303.gui;

import com.sysc3303.commons.Direction;

import java.util.Random;

/**
 * Class to send test messages to the GUI message handler.
 * UNUSED.
 */
public class SenderStub {
    //private GUIMessageHandler handler = GUIMessageHandler.getInstance(6666);
    private StubMessageHandler handler = StubMessageHandler.getInstance(6666);
    Random random  = new Random();

//    private void sendFloorMessage() {
//        int randFloor = random.nextInt(7);
//        boolean randUp = (Math.random() > 0.5);
//        boolean randDown = (Math.random() > 0.5);
//        handler.sendFloorUpdate(randDown, randUp, randFloor);
//    }

    private void sendElevatorMessage() {
        int randFloor = random.nextInt(7);
        Direction dir = Direction.UP;
        int r = random.nextInt(2);
        boolean open;
        if (r == 0) {
            open = false;
        } else {
            open = true;
        }

        int ID = random.nextInt(3);
        handler.sendElevatorUpdate(randFloor, dir, open, ID);
    }

    public static void main(String[] args) {
        SenderStub stub = new SenderStub();
        for(int i=0; i<5; i++) {
            //stub.sendFloorMessage();
            stub.sendElevatorMessage();
            System.out.println("Sending");
        }
    }
}
