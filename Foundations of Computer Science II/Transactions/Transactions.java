import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * 
 * This program outputs bank transactions read from a file according to user input.
 * @Author Michael Kirkham
 * @Version 2/15/2016
 * 
*/

public class Transactions
{
  
  public static void main(String[] args) throws FileNotFoundException
  {
    
    Scanner in = new Scanner(System.in);
    
    System.out.print("Please input the path to the transactions file: ");
    File transactionsFile = new File(in.nextLine());
    Scanner fileIn = new Scanner (transactionsFile);

    System.out.print("Please input the user to display transactions for: ");
    String userName = in.nextLine();
   
    double transactionsSum = 0.0;
    
    while (fileIn.hasNext())
    {
      fileIn.useDelimiter(",");
      String currentUser = fileIn.next();
//      System.out.print(currentUser);
//      System.out.print("\n");
      String currentAction = fileIn.next();
//      System.out.print(currentAction);
//      System.out.print("\n");
      double currentAmount = fileIn.nextDouble();
//      System.out.print(currentAmount);
//      System.out.print("\n");
      
      if (currentUser.equals(userName))
      {
//        System.out.print("found name \n");
        if (currentAction.equals("deposit"))
        {
//          System.out.print("Depositing... \n");
          transactionsSum = transDeposit(transactionsSum, currentAmount);
        }
        else 
        {
//          System.out.print("Withdrawing... \n");
          transactionsSum = transWithdrawal(transactionsSum, currentAmount);
        }
      }
      
//      if (fileIn.next().equals(userName))
//      {
//        System.out.print(" found name \n");
//        if (fileIn.next().equals("deposit"))
//        {
//          System.out.print( "Depositing...");
//          transactionsSum = transDeposit(transactionsSum, fileIn.nextDouble());
//        }
//        else
//        {
//          System.out.print("Withdrawing...");
//          transactionsSum = transWithdrawal(transactionsSum, fileIn.nextDouble());
//        }
//      }
//      else
//      {
//        fileIn.nextLine();
//        System.out.print("skips line");
//      }
    }
    fileIn.close();
    System.out.print("Total: ");
    System.out.print(transactionsSum);
  }
  
  public static double transDeposit(double tSum, double tDeposit)
  {
    tSum = tSum + tDeposit;
    System.out.printf(" %.2f\n", tDeposit);
    return tSum;
  }
  
  public static double transWithdrawal(double tSum, double tWithdrawal)
  {
    tSum = tSum - tWithdrawal;
    System.out.printf("-%.2f\n", tWithdrawal);
    return tSum;
  }  
    
}