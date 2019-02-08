package com.sysc3303.scheduler;

import com.sysc3303.communication.GoToFloorMessage;

public class GoToFloorMessageBox {
	
	private GoToFloorMessage goToFloorMessage;
	
	public synchronized void setGoToFloorMessage(GoToFloorMessage goToFloorMessage) {
		while(this.goToFloorMessage != null) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		this.goToFloorMessage = goToFloorMessage;
		
		notifyAll();
	}
	
	public synchronized GoToFloorMessage getGoToFloorMessage() {
		while(this.goToFloorMessage == null) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		GoToFloorMessage goToFloorMessage = this.goToFloorMessage;
		this.goToFloorMessage             = null;
		
		notifyAll();
		return goToFloorMessage;
	}
	
}
