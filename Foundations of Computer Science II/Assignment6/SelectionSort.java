import java.util.ArrayList;
/**

  This class selection sorts information in an array list.
  @Author Michael Kirkham
  @Version 4/13/2016

 */

public class SelectionSort 
{
  
  public static void selSort(ArrayList<Employee> a, String x)
  {
    int min;
    Employee tempEmployee;
    for (int i = 0; i < a.size(); i++)
    {
      if (x.equals("salary"))
      {
         min = minPosInt(a, i);
      }
      else
      {
         min = minPosString(a, i, x);
      }
      tempEmployee = a.get(i);
      a.set(i, a.get(min));
      a.set(min, tempEmployee);
    }
  }

  public static int minPosInt(ArrayList<Employee> a, int b)
  {
    int x = b;
    for (int i = b + 1; i < a.size(); i++)
    {
      if (a.get(i).getSalary() < a.get(x).getSalary())
      {
        x = i;
      }
    }
    return x;
  }
  
  public static int minPosString(ArrayList<Employee> a, int b, String s)
  {
    int x = b;
    for (int i = b + 1; i < a.size(); i++)
    {
      if (s.equals("name"))
      {
        if (a.get(i).getName().compareTo(a.get(x).getName()) < 0)
        {
          x = i;
        }
      }
      else
      {
        if (a.get(i).getDepartment().compareTo(a.get(x).getDepartment()) < 0)
        {
          x = i;
        }
      }           
    }
    return x;
  }
}
