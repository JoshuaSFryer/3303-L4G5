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
    ELEVATOR_STICK((byte)10),
    STUCK((byte)11),

    UNSTUCK((byte)12),
    TEL_ELEVATOR((byte)13),
    TEL_FLOOR((byte)14),
    TEL_ARRIVAL((byte)15),
    CONFIG((byte)16),
    TEL_FLOOR_BTN((byte)17),
    TEL_FLOOR_ARV((byte)18),
    TEL_ELEV_BTN((byte)19),
    TEL_ELEV_ARV((byte)20)
    ;

    private final byte opCode;
    OpCodes(byte opCode){
        this.opCode = opCode;
    }

    public byte getOpCode() {
        return opCode;
    }
}
