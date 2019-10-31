/**

  This class stores information on temporary employees and has output options for that information.
  @Author Michael Kirkham
  @Version 3/21/2016

 */
public class TemporaryEmployee extends Employee
{
  private int salary;
  
  public TemporaryEmployee(String x, String y, int z)
  {
    super.setName(x);
    super.setDepartment(y);
    super.setSalary(z * 1920);
  }
  
  public String getName()
  {
    return super.getName();
  }
  
  public String getDepartment()
  {
    return super.getDepartment();
  }
  
  public int getSalary()
  {
    return super.getSalary();
  }
    
}
