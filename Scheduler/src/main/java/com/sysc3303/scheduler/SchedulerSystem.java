package com.sysc3303.scheduler;

import java.net.UnknownHostException;

import com.sysc3303.commons.ConfigListener;
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
		if(args.length > 0){
			if(args[0] .equals("config")){
				new ConfigListener().run();
			}
		}

		int             port            = Integer.parseInt(ConfigProperties.getInstance().getProperty("schedulerPort"));
		SchedulerSystem schedulerSystem = new SchedulerSystem(port);
		
		DOMConfigurator.configure(SchedulerSystem.class.getResource("/log4j.xml"));
		schedulerSystem.printRunning(port);
	}
}