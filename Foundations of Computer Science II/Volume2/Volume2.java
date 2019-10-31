import java.util.Scanner;

/**
 
  This program prints the price per ounce for a certain number pack of cans.
  @Author: Michael Kirkham
  @Version: 1/22/2016
  
 */ 

public class Volume2
{
  public static void main(String[] args)
  { 
    
    Scanner in = new Scanner(System.in);
    
    // Get itemCalculated
    
    System.out.print("What item are you calculating?");
    String itemCalc = in.nextLine();
    
    // Get cansPerPack
    
    System.out.print("How many cans are in a pack? ");
    int cansPerPack = in.nextInt();
    
    // Read price per pack
    
    System.out.print("Please enter the price for a ");
    System.out.print(cansPerPack);
    System.out.print("-pack: ");
    double packPrice = in.nextDouble();
    
    // Read can volume
    
    System.out.print("Please enter the volume for each can (in ounces): ");
    double canVolume = in.nextDouble();
    
    // Compute pack volume
    
    double packVolume = canVolume * cansPerPack;
    
    // Compute and print price per ounce
    
    double pricePerOunce = packPrice / packVolume;
    
    System.out.print("Price per ounce of ");
    System.out.print(itemCalc);
    System.out.printf(": %8.2f", pricePerOunce);
    System.out.println();
     
  }
}