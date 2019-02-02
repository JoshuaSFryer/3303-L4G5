package com.commons;

public class Command{
  private String methodName;
  private Object[] params;

  public Command(String methodName, Object[] params){
    this.params = params;
    this.methodName = methodName;
  }

  public Object[] getParams(){
    return this.params;
  }

  public String getMethodName(){
    return this.methodName;
  }
}
