package com.sysc3303.commons;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

public class SerializationUtil<T> {
	public byte[] serialize(T object) {
		byte[] stream = null;
	    try (
	    		ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            ObjectOutputStream     oos = new ObjectOutputStream(baos);
	    ) {
	        oos.writeObject(object);
	        stream = baos.toByteArray();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return stream;
	}
	
	@SuppressWarnings("unchecked")
	public T deserialize(byte[] stream, int length) {
		T object = null;
		stream   = Arrays.copyOfRange(stream, 0, length);
		
	    try (ByteArrayInputStream bais = new ByteArrayInputStream(stream);
	            ObjectInputStream ois = new ObjectInputStream(bais);) {
	        object = (T) ois.readObject();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    
	    return object;
	}
}
