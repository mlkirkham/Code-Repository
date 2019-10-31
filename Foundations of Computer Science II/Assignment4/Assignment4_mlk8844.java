import java.util.ArrayList;
import java.util.Scanner;

/**
 
  This program takes in recipes in a list of ingredients and allows the user to search for recipes
  which have certain ingredients.
  @Author Michael Kirkham
  @Version 3/3/2016
  
 */ 

public class Assignment4_mlk8844
{
  public static void main(String[] args)
  {
    
    ArrayList<Recipe> recipeArray = new ArrayList<Recipe>();
    Scanner in = new Scanner(System.in);
    boolean recipeCheck = false;
    boolean ingCheck;
    boolean listCheck = false;
    String recipeName;
    String ingName;
    String ingAmount;
    String ingUnit;
    String nameCheck;
    int ingListInt = 0;
    
    while(recipeCheck == false)
    {
      System.out.print("Input recipe name ('none' to stop): ");
      recipeName = in.nextLine();
      if(recipeName.equals("none"))
      {
        recipeCheck = true;
      }
      else
      {
        ingCheck = false;
        Recipe newRecipe = new Recipe(recipeName);
        recipeArray.add(newRecipe);
        while(ingCheck == false)
        {
          System.out.print("Input ingredient name ('none' to stop): ");
          ingName = in.nextLine();
          if(ingName.equals("none"))
          {
            ingCheck = true;
          }
          else
          {
            System.out.print("Input ingredient amount: ");
            ingAmount = in.nextLine();
            System.out.print("Input ingredient unit: ");
            ingUnit = in.nextLine();
            Ingredient newIngredient = new Ingredient(ingName, ingAmount, ingUnit);
            newRecipe.getIngredient(newIngredient);
          }
        } 
      }
    }
    while(listCheck == false)
    {
      ingListInt = 0;
      System.out.print("Input ingredient list ('done' to stop): ");
      nameCheck = in.nextLine();
      if(nameCheck.equals("done"))
      {
        listCheck = true;
      }
      else
      {
        for(int i = 0; i < recipeArray.size(); i++)
        {
          if(recipeArray.get(i).ingredientList(nameCheck))
          {
            System.out.println(recipeArray.get(i).getName());
            ingListInt++;
          }
        }
        System.out.println(ingListInt + " recipes found");
      }
    }
  }
}














