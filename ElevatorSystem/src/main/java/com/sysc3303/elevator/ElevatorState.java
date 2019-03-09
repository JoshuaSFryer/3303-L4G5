package com.sysc3303.elevator;

public class ElevatorState {
	public static final int DOORSOPENTIME = 100; // ms
	Elevator context;

	public ElevatorState(Elevator context) {
		this.context = context;
		// Any Entry actions should be placed in the state's constructor.
	}

	public void changeState(ElevatorState newState) {
		// Any Exit actions for a state should be placed in this method.
		context.setState(newState);
	}

	public void goToFloor(int targetFloor) {
		System.out.println("Method not valid in this current ElevatorState.");
	}

	public void openDoors() {
		System.out.println("Method not valid in this current ElevatorState.");
	}

	public void closeDoors() {
		System.out.println("Method not valid in this current ElevatorState.");
	}

}


class MovingUp extends ElevatorState {

	public MovingUp(Elevator context) {
		super(context);
	}

	@Override
	public void goToFloor(int targetFloor) {
		context.goToFloor(targetFloor);
	}

	@Override
	public void changeState(ElevatorState newState) {
		context.stopElevator();
		super.changeState(newState);
	}
}

class MovingDown extends ElevatorState {
	public MovingDown(Elevator context) {
		super(context);
	}

	@Override
	public void goToFloor(int targetFloor) {
		context.goToFloor(targetFloor);
	}

	@Override
	public void changeState(ElevatorState newState) {
		context.stopElevator();
		super.changeState(newState);
	}
}

class Idle extends ElevatorState {

	public Idle(Elevator context) {
		super(context);
	}

	@Override
	public void goToFloor(int targetFloor) {
		context.goToFloor(targetFloor);
	}

	@Override
	public void openDoors() {
		context.openDoors();
	}

	@Override
	public void closeDoors() {
		context.closeDoors();
	}

}

class OpeningDoors extends ElevatorState {
	public OpeningDoors(Elevator context) {
		super(context);

		context.openDoors();
		// Wait a short amount of time while doors open.
		super.changeState(new DoorsOpen(context));
	}
}

class DoorsOpen extends ElevatorState {
	public DoorsOpen(Elevator context) {
		super(context);

		// Hold doors open for a set amount of time.
		try {
			Thread.sleep(DOORSOPENTIME);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		super.changeState(new ClosingDoors(context));
	}
}

class ClosingDoors extends ElevatorState {
	public ClosingDoors(Elevator context) {
		super(context);

		context.closeDoors();
		// Wait a short amount of time while doors close.
		super.changeState(new Idle(context));
	}
}
