package com.sysc3303.elevator;

public class Motor {
	
	private final int INCREMENT = 1; // 1 cm
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
