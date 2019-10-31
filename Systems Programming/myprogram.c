#include<stdio.h>
#include<string.h>

int main()
{
  char name[10];

  printf("What is your name? ");
  scanf("%s", name);
  printf("Hello %s", name);

  return 0;
}
