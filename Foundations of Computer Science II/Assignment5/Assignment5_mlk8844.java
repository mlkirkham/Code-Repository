import java.util.ArrayList;
import java.util.Scanner;
/**
 
  This program takes in employee information as input, stores it, and outputs the information 
  sorted by ascending yearly salary.
  @Author Michael Kirkham
  @Version 3/21/2016
  
 */ 
public class Assignment5_mlk8844
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
    
    //sorts employees by salary in ascending order
    while(empSortBool == false)
    {
      for(int i = 1; i < employeeArray.size(); i++)
      {
        if(employeeArray.get(i-1).getSalary() > employeeArray.get(i).getSalary())
        {
          tempEmployee = employeeArray.get(i-1);
          employeeArray.set(i-1, employeeArray.get(i));
          employeeArray.set(i, tempEmployee);
          empSortBool = false;
        }
        else
        {
          empSortBool = true;
        }
      }
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