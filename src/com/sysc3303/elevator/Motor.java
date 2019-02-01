package com.sysc3303.elevator;

public class Motor {
	
	private final double INCREMENT = 0.1;
	private Elevator parent;
	
	public Motor(Elevator parent) {
		this.parent = parent;
	}
	
	public void moveUp() {
		parent.setCurrentHeight(parent.getCurrentHeight() + INCREMENT);
	}
	
	public void moveDown() {
		parent.setCurrentHeight(parent.getCurrentHeight() - INCREMENT);
	}
	
	public void stop() {
		
	}
}
