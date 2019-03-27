package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ConfigUpdateMessage extends Message {
    private String[][] configChanges;

    @JsonCreator
    public ConfigUpdateMessage(@JsonProperty("configChanges") String[][] configChanges) {
        // the opcode for this message type is 5
        super(OpCodes.CONFIG.getOpCode());
        this.configChanges = configChanges;
    }

    @Override
    public String toString() {
        String str = "Config Changes";
        for (int i = 0; i < configChanges.length; i++) {
            str += "\n\t[" + configChanges[i][0] + ", " + configChanges[i][1] + "]";
        }
        return str;
    }

    @Override
    public String summary() {
        return "ConfigChanges";
    }

    public String[][] getConfigChanges() {
        return configChanges;
    }
}
