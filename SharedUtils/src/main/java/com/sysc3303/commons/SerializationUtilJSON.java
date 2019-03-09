package com.sysc3303.commons;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sysc3303.communication.Message;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SerializationUtilJSON<T> {
    public byte[] serialize(Message object) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        byte[] stream = null;
        try{
            String jsonString = mapper.writeValueAsString(object);
            stream = jsonString.getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return stream;
    }

    @SuppressWarnings("unchecked")
    public T deserialize(byte[] stream, Class<T> cls) {
        String raw = new String(stream, StandardCharsets.UTF_8);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        T object = null;

        try{
            object = mapper.readValue(raw, cls);
        } catch (IOException e){
            e.printStackTrace();
        }
        return object;
    }
}

