package com.sysc3303.elevator;

public abstract class ElevatorState {
    private Elevator parent;

    public ElevatorState(Elevator parent) {
        this.parent = parent;
    }

    public void entryAction() {

    }

    public void exitAction() {

    }

    public void stateTransition() {

    }
}

class Idle extends ElevatorState {

    public Idle(Elevator parent) {
        super(parent);
    }

}

class MovingUp extends ElevatorState {

    public MovingUp(Elevator parent) {
        super(parent);
    }

    @Override
    public void entryAction() {
        super.entryAction();
    }
}
