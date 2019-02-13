package com.sysc3303.elevator;
/*
import java.util.EnumSet;
import java.util.Set;

public enum ElevatorState {
	IDLE {
		@Override
		public Set<ElevatorState> nextStates() {
			return EnumSet.of(MOVINGUP, MOVINGDOWN, OPENINGDOORS);
		}
	},
	
	MOVINGUP {
		@Override
		public Set<ElevatorState> nextStates() {
			return EnumSet.of(IDLE);
		}
	},
	
	MOVINGDOWN {
		@Override
		public Set<ElevatorState> nextStates() {
			return EnumSet.of(IDLE);
		}
	},
	
	OPENINGDOORS {
		@Override
		public Set<ElevatorState> nextStates() {
			return EnumSet.of(DOORSOPEN);
		}
	},
	
	DOORSOPEN {
		@Override
		public Set<ElevatorState> nextStates() {
			return EnumSet.of(DOORSCLOSING);
		}
	},
	
	DOORSCLOSING {
		@Override
		public Set<ElevatorState> nextStates() {
			return EnumSet.of(IDLE);
		}
	};
	
	public Set<ElevatorState> nextStates() {
		return EnumSet.noneOf(ElevatorState.class);
	}
}
*/

abstract interface ElevatorState {
	public void entryAction(Elevator context);
	public void doAction(Elevator context);
	public void exitAction(Elevator context);
}

class Idle implements ElevatorState {
	public void entryAction(Elevator context) {
		
	}
	
	public void doAction(Elevator context) {
		
	}
	
	public void exitAction(Elevator context) {
		
	}
}

class MovingUp implements ElevatorState {
	public void entryAction(Elevator context) {
		//context.moveUp();
	}
	
	public void doAction(Elevator context) {
		
	}
	
	public void exitAction(Elevator context) {
		//context.stop();
	}
}

class MovingDown implements ElevatorState {
	public void entryAction(Elevator context) {
		//context.moveDown();
	}
	
	public void doAction(Elevator context) {
		
	}
	
	public void exitAction(Elevator context) {
		//context.stop();
	}
}

class OpeningDoors implements ElevatorState {
	public void entryAction(Elevator context) {
		context.openDoors();
	}
	
	public void doAction(Elevator context) {
		
	}
	
	public void exitAction(Elevator context) {
		
	}
}

class DoorsOpen implements ElevatorState {
	public void entryAction(Elevator context) {
		
	}
	
	public void doAction(Elevator context) {
		try {
			Thread.sleep(20000);
		} catch(InterruptedException e) {
			// TODO: interrupt handling? or just let this pass through and break
			// early?
		}
		// After time elapses, force the state to change.
		context.setState(new ClosingDoors());
	}
	
	public void exitAction(Elevator context) {
		
	}
}

class ClosingDoors implements ElevatorState {
	public void entryAction(Elevator context) {
		context.closeDoors();
		context.setState(new Idle());
	}
	
	public void doAction(Elevator context) {
		
	}
	
	public void exitAction(Elevator context) {
		
	}
}
