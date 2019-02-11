package com.sysc3303.gui;

import com.sysc3303.communication.*;

/**
 * TestCLI is a console-output mock for the GUI. It is meant to act as a
 * bare-bones implementation of the methods for testing purposes, without any
 * reference to Swing. Use it while the GUI is still in development.
 */
public class TestCLI {

    private GUIMessageHandler handler;

    public TestCLI() {
        this.handler = GUIMessageHandler.getInstance();
    }

    public void moveElevator() {

    }



    public static void main(String[] args) {
        TestCLI t = new TestCLI();
    }

}
