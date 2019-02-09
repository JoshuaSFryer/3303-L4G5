package com.sysc3303.elevator;

import com.sysc3303.communication.FloorButtonMessage;
import org.junit.BeforeClass;

import com.sysc3303.commons.*;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

class ElevatorMessageHandlerTest {
	static Elevator e;
	@BeforeClass
	static void makeElevator() {
		e = new Elevator(25534, 10, 0);
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
