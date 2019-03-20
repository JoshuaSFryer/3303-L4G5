package com.sysc3303.scheduler;

import java.util.HashMap;
import java.util.Iterator;

public class Timer {
	private final long          SEC_IN_NANO  = 1000000000;
	private final double        SEC_IN_MILLI = 1000000.00;
	private HashMap<Long, Long> time        = new HashMap<>();
	private String              name;
	
	public Timer(String name) {
		this.name = name;
	}
	
	public void insert(long startTime, long endTime) {
		time.put(startTime, endTime);
	}
	
	/* (non-Javadoc)
	 * prints timer in nano sec and sec
	 * @see java.lang.Object#toString()
	 */
	@SuppressWarnings("rawtypes")
	public String toString() {
		Iterator it     = time.entrySet().iterator();
		String   output = name + " timer: \n";
		int      counter = 0;
		
		while (it.hasNext()) {
	        HashMap.Entry pair      = (HashMap.Entry)it.next();
	        long          startTime = (long) pair.getKey();
	        long          endTime   = (long) pair.getValue();
	        long          nanoTime  = endTime - startTime;
	        double        milliTime = nanoTime / SEC_IN_MILLI;
	        
	        counter++;
	        output += "\t" + counter + "- " + nanoTime + " NanoSeconds\n" +
		              "\t" + counter + "- " + milliTime + " MilliSeconds\n";
	    }
	
		return output;
	}
}
