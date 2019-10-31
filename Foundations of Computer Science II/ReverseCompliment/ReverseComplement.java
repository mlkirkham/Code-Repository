import java.util.Scanner;

/**
 
  This program reads in two sequnces of dna strands and gives back whether the strands are reverse complements or not.
  @Author Michael Kirkham
  @Version 2/8/2016
  
 */ 

public class ReverseComplement
{
  //main method
  public static void main(String[] args)
  {
    Scanner in = new Scanner(System.in);
    boolean strBool = true;
    
    System.out.print("Enter the first sequence: ");
    String firstSequence = in.nextLine();
    
    System.out.print("Enter the second sequence: ");
    String secondSequence = in.nextLine();

    if (isReverseCompliment(firstSequence, secondSequence) == true)
    {
      System.out.print("These are reverse complements");
    }
    else
    {
      System.out.print("These are not reverse complements");
    }
  }
  //method to get boolean value
  public static boolean isReverseCompliment(String sOne, String sTwo)
  {   
    int seqOneLength = sOne.length();
    int seqTwoLength = sTwo.length();
    boolean revCompBool;
    char[] charArray = new char[seqOneLength];
    //while to change characters and flip around
    while (seqOneLength >= 1)
    {
      char tempChar = sTwo.charAt(seqOneLength-1);
      tempChar = changeCharacter(tempChar);
      charArray[seqTwoLength-seqOneLength] = tempChar;
      
      seqOneLength--;
    }

    String charString = new String(charArray);
    
    if (charString.equals(sOne))
    {
      revCompBool = true;
    }
    else
    {
      revCompBool = false;
    }

    return revCompBool;
  }
  //method to change the characters
  public static char changeCharacter(char x)
  {
    char changedChar = ' ';
    if (x == 'A')
    {
      changedChar = 'T';
    }
    if (x == 'T')
    {
      changedChar = 'A';
    }
    if (x == 'C')
    {
      changedChar = 'G';
    }
    if (x == 'G')
    {
      changedChar = 'C';
    }
    return changedChar;
  }
}





