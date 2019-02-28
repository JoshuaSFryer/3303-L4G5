package com.sysc3303.communication;

public enum OpCodes {
    FLOOR_BUTTON((byte)0),
    FLOOR_ARRIVAL((byte)1),
    GO_TO_FLOOR((byte)2),
    ELEVATOR_STATE((byte)3),
    ELEVATOR_BUTTON((byte)4),
    FLOOR_CLICK_SIMULATION((byte)5),
    ELEVATOR_CLICK_SIMULATION((byte)6),
    ELEVATOR_UPDATE_GUI((byte)7),
    FLOOR_UPDATE_GUI((byte)8),
    DOOR_STICK((byte)9),
    ELEVATOR_STICK((byte)10)
    ;

    private final byte opCode;
    OpCodes(byte opCode){
        this.opCode = opCode;
    }

    public byte getOpCode() {
        return opCode;
    }
}
