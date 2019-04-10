package com.sysc3303.communication;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Properties;

/**
 * Message that is used to send a new configuration to a node
 */
public class ConfigUpdateMessage extends Message {
    private Properties properties;

    /**
     * Constructs the new message to be sent
     * @param properties the properties object that should be sent
     */
    @JsonCreator
    public ConfigUpdateMessage(@JsonProperty("properties") Properties properties) {
        // the opcode for this message type is 5
        super(OpCodes.CONFIG.getOpCode());
        this.properties = properties;
    }

    @Override
    public String toString() {
        String str = "Configuration Changes";
        return str;
    }

    @Override
    public String summary() {
        return "ConfigChanges";
    }

    public Properties getProperties() {
        return properties;
    }
}
