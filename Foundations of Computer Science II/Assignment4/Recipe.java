import java.util.ArrayList;

/**
 
  This class provides an instance variable of ingredients and methods
  for adding and searching ingredients.
  @Author Michael Kirkham
  @Version 3/3/2016
  
 */ 
  
public class Recipe
{
  private ArrayList<Ingredient> ingArray = new ArrayList<Ingredient>();
  private String name;
  
  public Recipe(String x)
  {
    name = x;
  }
  
  public String getName()
  {
    return name;
  }
  
  public void getIngredient(Ingredient ing)
  {
    ingArray.add(ing);
  }
  
  public boolean ingredientList(String y)
  {
    boolean boolCheck = false;
    for(int j = 0; j < ingArray.size(); j++)
    {
      if(ingArray.get(j).getName().equals(y) == true)
      {
        boolCheck = true;
      }
    }
    return boolCheck;
  }
}
