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
		FloorGeneral floor = floorSystem.getFloorList().get(0);
		floor.getLamps().turnBothLampOFF();
		floor.getButtons().setDownButtonLight(true);
		try {
			floorSystem.floorArrival(1, Direction.DOWN);
		} catch (InterruptedException e){
			fail();
		}
		Assert.assertEquals(false, floor.getLamps().isDownLamp());
		Assert.assertEquals(false, floor.getButtons().isDownButtonLight());
	}
}
