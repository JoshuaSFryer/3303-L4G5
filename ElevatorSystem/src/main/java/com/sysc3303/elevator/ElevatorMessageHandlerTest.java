package com.sysc3303.elevator;

import com.sysc3303.communication.FloorButtonMessage;
import org.junit.BeforeClass;

import com.sysc3303.commons.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

class ElevatorMessageHandlerTest {
	static Elevator e;
	//FIXME This is just going to be fully broken
    //FIXME now with elevator system this test doesn't make much sense and currently won't work
	@BeforeClass
	static void makeElevator() {
	    ElevatorSystem elevatorSystem = new ElevatorSystem(5,
                3,
                Integer.parseInt(ConfigProperties.getInstance().getProperty("elevatorPort")));
		e = new Elevator( 10, 0, elevatorSystem.getMessageHandler());
	}
	
	@Test
	void testReceived() {
		/*try {
			Message goodMsg = new GoToFloorMessage(4);
			assertTrue(true);
		} catch (Exception e) {
			fail("Unexpected exception");
		}*/
		FloorButtonMessage badMsg = new FloorButtonMessage(0, null, null);
		
		try {
			e.getMessageHandler().received(badMsg);
			fail("Expected an exception");
		} catch (IllegalArgumentException e) {
			// Pass the test
			assertTrue(true);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception type");
		}
		
	}
	
	@Test
	void testSendElevatorState() {
		ElevatorVector v = new ElevatorVector(0, Direction.UP, 5);
		e.getMessageHandler().sendElevatorState(v, e.elevatorID);
	}

	@Test
	void testSendElevatorButton() {
		e.pressButton(4);
	}

}
