package com.sysc3303.floor;
import static org.junit.Assert.fail;

import com.sysc3303.commons.Direction;

import org.junit.Test;


@SuppressWarnings("deprecation")
class FloorSystemTest {
	@Test
	void test() {
		FloorSystem floorSystem = new FloorSystem();
		Floor floor = floorSystem.getFloorList().get(2);
		//floor.getLamps().turnBothLampOFF();
		//System.out.print("Passenger is on Floor 1\n");
		//System.out.println("Expected: Down light = true");
		floor.getButtons().setDownButtonLight(true);
		
		try {
			floorSystem.floorArrival(1, Direction.UP, 1);
		} catch (InterruptedException e){
			fail();
		}
		//Assert.assertEquals(false, floor.getLamps().isDownLamp());
		//Assert.assertEquals(false, floor.getButtons().isDownButtonLight());
	}
}


	