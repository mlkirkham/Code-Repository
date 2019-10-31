#include<stdio.h>

int main()
{
  char word[5]= "queue";
  char guess[5] = "_____";
  char letter;
  int count;
  int i;

  while(wordGuess(word, guess))
    {
      count = 0;
      printf("Word = %s\n", guess);
      printf("Enter> ");
      scanf(" %c", &letter);

      for(i=0; i<5; i++)
	{
	  if(letter == word[i])
	    {
	      guess[i] = letter;
	      count++;
	    }
	}
      if(count != 0)
        printf("Yes\n");
      else
	printf("No\n");
    }
  
  printf("%s\n", guess);
  printf("You Win\n");

  return 0;
}

int wordGuess(char w[], char g[])
{
  int j;

  for(j=0 ; j<5; j++)
    {
      if(w[j] != g[j])
	return 1;
    }
  return 0;
}
