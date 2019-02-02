package com.sysc3303.commons;

import java.io.Serializable;

/**
 * Message represents data pulled from a single line of the input file.
 * 
 * @author	Yu Yamanaka
 *
 */

public abstract class Message implements Serializable {
	public static final long serialVersionUID = 1097867564019283746L;
	public final byte   	opcode;

	public Message(byte opcode){
	    this.opcode = opcode;
	}

	/**
	 * Get a formatted string containing the time contained in requestTime, without the date components.
	 * @return the time elements of requestTime
	 */
	public String toString(){
	    return "Message with opcode: " + opcode;
	}

	public byte getOpcode(){
		return opcode;
	}
}
