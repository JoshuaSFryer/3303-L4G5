package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Messages are the data type that can be sent between different Communication Handlers
 * Messages are an abstract class that should be subclassed for each type of message to be sent
 * @author	Mattias Lightstone
 */

public abstract class Message implements Serializable {
	public static final long serialVersionUID = 1097867564019283746L;
	// Opcodes are used to identify the type of message
	private byte   	opcode;

	@JsonCreator
	public Message(@JsonProperty("opcode") byte opcode){
	    this.opcode = opcode;
	}

	public String toString(){
	    return "Message with opcode: " + opcode;
	}

	public byte getOpcode(){
		return opcode;
	}

	public void setOpcode(byte opcode){
		this.opcode = opcode;
	}

	public String summary(){
	    return "" + opcode;
	}
}
