package com.sysc3303.scheduler;

import com.sysc3303.communication.Message;

//TODO remove it later, this class is just here to ignore compile error
public class UnStuckMessage extends Message{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnStuckMessage(byte opcode) {
		super(opcode);
		// TODO Auto-generated constructor stub
	}

	public int getElevatorId() {
		return -1;
	}
}
