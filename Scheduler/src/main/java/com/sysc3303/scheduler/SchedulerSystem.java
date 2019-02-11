package com.sysc3303.scheduler;

import java.net.UnknownHostException;

import com.sysc3303.commons.ConfigProperties;

public class SchedulerSystem {
	private Scheduler               scheduler;
	private SchedulerMessageHandler schedulerMessageHandler;
	
	public SchedulerSystem(int port) {
		schedulerMessageHandler = new SchedulerMessageHandler(port, this);
		scheduler               = new Scheduler(schedulerMessageHandler);
	}
	
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	public static void main(String[] args) throws UnknownHostException {
		int             port            = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
		SchedulerSystem schedulerSystem = new SchedulerSystem(port);
		
		System.out.println("Starting Scheduler, listening at port " + port);
	}
}
