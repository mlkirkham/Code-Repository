#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#define MAX_STRINGS 20
#define LENGTH 20

void InsertionSort(int total, char list[MAX_STRINGS][LENGTH]);

int main()
{
  int i;
  int location = 0;
  int j;
  int done = 0;
  int doneString = 0;
  int strings = 0;
  char input[400];
  char wordArray[MAX_STRINGS][LENGTH];
  char temp1[LENGTH];
  char letter;

  /* Get input */
  printf("Enter a line of up to %d wordsto be sorted, terminated by a period:\n", MAX_STRINGS);
  scanf("%399[^\n]", input);
  
  for(i = 0; i < MAX_STRINGS && done == 0; i++)
  {
    memset(&temp1[0], 0, LENGTH);
    doneString = 0;
    for(j = 0; j < LENGTH && doneString == 0; j++)
      {
	letter = input[location];
	if(letter == '.')
	  {
	    done = 1;
	  }
	if(letter == ' ' || letter == '.')
	  {
	    doneString = 1;
	    strings++;
	  }
	else
	  {
	    temp1[j] = letter;
	  }
	location++;
      }
    strcpy(wordArray[i], temp1);
  }

  InsertionSort(strings, wordArray);     /* Call sorting routine */

  /* Print sorted list */
  printf("\nThe input set, in ascending order:\n");
  for(i = 0; i < strings; i++)
    printf("%s\n", wordArray[i]);
}

void InsertionSort(int total, char list[MAX_STRINGS][LENGTH])
{
  int i;
  int j;
  char temp2[LENGTH];

  for(i = 0; i < total; i++)
  {
    for(j = 0; j < total - 1; j++)
    {
      if(strcmp(list[j], list[j+1]) > 0)
      {
        strcpy(temp2, list[j]);
        strcpy(list[j], list[j+1]);
        strcpy(list[j+1], temp2);
      }
    }
  }
}
