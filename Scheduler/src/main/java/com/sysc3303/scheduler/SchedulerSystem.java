package com.sysc3303.scheduler;

import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import com.sysc3303.commons.ConfigProperties;

/**
 * Starts scheduler and communication handler
 * @author Yu Yamanaka
 *
 */
public class SchedulerSystem {
	private Scheduler               scheduler;
	private SchedulerMessageHandler schedulerMessageHandler;
	private Logger                  log = Logger.getLogger(SchedulerMessageHandler.class);
	
	public SchedulerSystem(int port) {
		schedulerMessageHandler = new SchedulerMessageHandler(port, this);
		scheduler               = new Scheduler(schedulerMessageHandler);
	}
	
	/**
	 * gets scheduler
	 * @return
	 */
	public Scheduler getScheduler() {
		return scheduler;
	}
	
	/**
	 * prints that scheduler is running
	 * @param port
	 */
	public void printRunning(int port) {
		String running = "Starting Scheduler, listening at port " + port;
		
		log.info(running);
		System.out.println(running);
	}
	
	public static void main(String[] args) throws UnknownHostException {
		int             port            = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
		SchedulerSystem schedulerSystem = new SchedulerSystem(port);
		
		DOMConfigurator.configure("./Scheduler/log4j.xml");
		schedulerSystem.printRunning(port);
	}
}