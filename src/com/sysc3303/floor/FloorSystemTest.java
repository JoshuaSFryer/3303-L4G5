package com.sysc3303.floor;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.sysc3303.commons.Direction;

import junit.framework.Assert;


@SuppressWarnings("deprecation")
class FloorSystemTest {
	@Test
	void test() {
		FloorSystem floorSystem = new FloorSystem();
		FloorGeneral floor = floorSystem.getFloorList().get(2);
		//floor.getLamps().turnBothLampOFF();
		//System.out.print("Passenger is on Floor 1\n");
		//System.out.println("Expected: Down light = true");
		floor.getButtons().setDownButtonLight(true);
		
		try {
			floorSystem.floorArrival(1, Direction.UP);
		} catch (InterruptedException e){
			fail();
		}
		//Assert.assertEquals(false, floor.getLamps().isDownLamp());
		//Assert.assertEquals(false, floor.getButtons().isDownButtonLight());
	}
}


	