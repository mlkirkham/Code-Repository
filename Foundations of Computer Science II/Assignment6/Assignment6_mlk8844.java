import java.util.ArrayList;
import java.util.Scanner;
/**
 
  This program takes in employee information as input, stores it, and outputs the information 
  sorted by ascending yearly salary, department, and name depending on user input. It also 
  sorts by selction or merge sort depending on what the user wants.
  @Author Michael Kirkham
  @Version 4/13/2016
  
 */ 
public class Assignment6_mlk8844
{
  public static void main(String[] args)
  {  
    ArrayList<Employee> employeeArray = new ArrayList<Employee>();
    Scanner in = new Scanner(System.in);
    boolean contCheck = false;
    String empName;
    String empDesignation;
    String empDepartment;
    int empSalary;
    Employee tempEmployee;
    boolean empSortBool = false;
    String sortChoice;
    String sortMethod;
    
    //gets employee information
    while(contCheck == false)
    {
      System.out.print("Input employee name ('none' to stop): ");
      empName = in.next();
      if(empName.equals("none"))
      {
        contCheck = true;
      }
      else
      {
        System.out.print("Input employee designation ('temporary' or 'permanent'): ");
        empDesignation = in.next();
        System.out.print("Input employee department: ");
        empDepartment = in.next();
        System.out.print("Input employee salary: ");
        empSalary = in.nextInt();
        if(empDesignation.equals("temporary"))
        {
          TemporaryEmployee newTempEmployee = new TemporaryEmployee(empName, empDepartment, empSalary);
          employeeArray.add(newTempEmployee);
        }
        else
        {
          PermanentEmployee newPermEmployee = new PermanentEmployee(empName, empDepartment, empSalary);
          employeeArray.add(newPermEmployee);
        }
      }
    }
    
    System.out.print("Input sort choice 'name', 'department', 'salary'.");
    sortChoice = in.next();
    System.out.print("Input sort method 'selection', 'merge'.");
    sortMethod = in.next();
    
    //selection sorts employees by name, department, or salary
    if (sortMethod.equals("selection"))
    {
      SelectionSort.selSort(employeeArray, sortChoice);
    }
    //merge sorts employees by name, department, or salary
    else if (sortMethod.equals("merge"))
    {
      MergeSort.sort(employeeArray, sortChoice);
    }
    
    
    //prints name, department, and salary of each employee
    for(int i = 0; i < employeeArray.size(); i++)
    {
      System.out.print(employeeArray.get(i).getName() + " ");
      System.out.print(employeeArray.get(i).getDepartment() + " ");
      System.out.println(employeeArray.get(i).getSalary());
    }
  }
}