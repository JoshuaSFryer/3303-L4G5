package com.sysc3303.gui;

import java.util.Random;

public class SenderStub {
    //private GUIMessageHandler handler = GUIMessageHandler.getInstance(6666);
    private StubMessageHandler handler = StubMessageHandler.getInstance(6666);
    Random random  = new Random();

    private void sendFloorMessage() {
        int randFloor = random.nextInt(7);
        boolean randUp = (Math.random() > 0.5);
        boolean randDown = (Math.random() > 0.5);
        handler.sendFloorUpdate(randDown, randUp, randFloor);
    }

    public static void main(String[] args) {
        SenderStub stub = new SenderStub();
        for(int i=0; i<10; i++) {
            stub.sendFloorMessage();
        }
    }
}
