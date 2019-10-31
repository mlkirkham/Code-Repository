/**

  This class stores information on permanent employees and has output options for that information. 
  @Author Michael Kirkham
  @Version 3/21/2016

 */
public class PermanentEmployee extends Employee
{
  
  public PermanentEmployee(String x, String y, int z)
  {
    super.setName(x);
    super.setDepartment(y);
    super.setSalary(z);
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
