/**

  This class is a superclass of permanentemployee and temporaryemployee and sets up methods that 
  will be used by those subclasses.
  @Author Michael Kirkham
  @Version 4/13/2016

 */
public class Employee
{
  private String name;
  private String department;
  private int salary;
  
  public void setName(String x)
  {
    name = x;
  }
  
  public void setDepartment(String x)
  {
    department = x;
  }
  
  public void setSalary(int x)
  {
    salary = x;
  }
  
  public String getName()
  {
    return name;
  }
  
  public String getDepartment()
  {
    return department;
  }
  
  public int getSalary()
  {
    return salary;
  }
  
}
