import java.util.ArrayList;
/**

  This class merge sorts information in an array list.
  @Author Michael Kirkham
  @Version 4/13/2016

 */

public class MergeSort 
{
  
  public static void sort(ArrayList<Employee> a, String x)
  {
    if (a.size() <= 1) 
    {
      return;
    }
    ArrayList<Employee> first = new ArrayList<Employee>();
    ArrayList<Employee> second = new ArrayList<Employee>();
    
    for (int i = 0; i < (a.size() / 2); i++)
    {
      first.add(a.get(i));
    }
    for (int j = (a.size() / 2); j < a.size(); j++)
    {
      second.add(a.get(j));
    }
    sort(first, x);
    sort(second, x);
    merge(first, second, a, x);
  }
  
  private static void merge(ArrayList<Employee> first, ArrayList<Employee> second, ArrayList<Employee> a, String x)
  {
    int i = 0;
    int j = 0;
    int k = 0;
    
      if (x.equals("salary"))
      {
        while (i < first.size() && j < second.size())
        {
          if (first.get(i).getSalary() < second.get(j).getSalary())
          {
            a.set(k, first.get(i));
            i++;
          }
          else
          {
            a.set(k, second.get(j));
            j++;
          }
          k++;
        }
      }
      else if (x.equals("name"))
      {
        while (i < first.size() && j < second.size())
        {
          if (first.get(i).getName().compareTo(second.get(j).getName()) < 0)
          {
            a.set(k, first.get(i));
            i++;
          }
          else
          {
            a.set(k, second.get(j));
            j++;
          }
          k++;
        }
      }
      else
      {
        while (i < first.size() && j < second.size())
        {
          if (first.get(i).getDepartment().compareTo(second.get(j).getDepartment()) < 0)
          {
            a.set(k, first.get(i));
            i++;
          }
          else
          {
            a.set(k, second.get(j));
            j++;
          }
          k++;
        }
      }
    while (i < first.size())
    {
      a.set(k, first.get(i));
      i++;
      k++;
    }
    while (j < second.size())
    {
      a.set(k, second.get(j));
      j++;
      k++;
    }
  }
}













