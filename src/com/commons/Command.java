public class Command{
  private String methodName;
  private Object[] params;

  public Command(String methodName, Object[] params){
    self.params = params;
    self.methodName = methodName;
  }

  public Object[] getParams(){
    return self.params;
  }

  public String getMethodName(){
    return self.methodName;
  }
}
