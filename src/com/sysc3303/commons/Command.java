package com.sysc3303.commons;

public class Command {
	private String   methodName;
	private Object[] params;
	
	public Command(String methodName, Object[] params) {
		this.params     = params;
		this.methodName = methodName;
	}
	
	public Object[] getParams() {
		return params;
	}
	
	public String getMethodName() {
		return methodName;
	}
}
