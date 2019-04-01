package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

class ButtonPair implements Serializable {
    public boolean down;
    public boolean up;

    @JsonCreator
    public ButtonPair(@JsonProperty("downState") boolean downState, @JsonProperty("upState") boolean upState) {
        this.down = downState;
        this.up = upState;
    }
}

public class GUIFloorMessage extends Message implements Serializable {
    public final ButtonPair buttons;
    public final int passengerFloor;

    public GUIFloorMessage(boolean downState, boolean upState, int passengerFloor) {
        super(OpCodes.FLOOR_UPDATE_GUI.getOpCode());
        this.buttons = new ButtonPair(downState, upState);
        this.passengerFloor = passengerFloor;
    }

    @JsonCreator
    public GUIFloorMessage(@JsonProperty("downState") boolean downState, @JsonProperty("upState") boolean upState, @JsonProperty("passengerFloor") int passengerFloor, @JsonProperty("buttons") ButtonPair buttons) {
        super(OpCodes.FLOOR_UPDATE_GUI.getOpCode());
        this.buttons = new ButtonPair(downState, upState);
        this.passengerFloor = passengerFloor;
    }

    @Override
    public String toString() {
        return "Floor: " + this.passengerFloor + " Down button: " + this.buttons.down
                + " Up button: " + this.buttons.up;
    }
}
