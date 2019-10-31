/**
 
  This class provides instance variables and methods for ingredients.
  @Author Michael Kirkham
  @Version 3/3/2016
  
 */ 

public class Ingredient 
{
  
  private String name;
  private String amount;
  private String unit;
  
  public Ingredient(String x, String y, String z)
  {
    name = x;
    amount = y;
    unit = z;
  }
  
  public String getName()
  {
    return name;
  }
  
  public String getAmount()
  {
    return amount;
  }
  
  public String getUnit()
  {
    return unit;
  }
  
}
