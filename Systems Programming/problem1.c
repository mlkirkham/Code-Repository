#include <stdio.h>

void circleCirc(double a);
void circleArea(double b);
void sphereVol(double c);

int main(void)
{
  void (*f[3])(double) = { circleCirc, circleArea, sphereVol };

  printf("Enter:\n0: Circumference of a circle\n1: Area of a circle\n2: Volume of a sphere\n3: End\n");
  size_t choice;
  scanf("%u", &choice);

  while (choice >= 0 && choice < 3)
  {
    printf("Enter a radius:\n");
    double radius;
    scanf("%lf", &radius);
    (*f[choice])(radius);

    printf("Enter:\n0: Circumference of a circle\n1: Area of a circle\n2: Volume of a sphere\n3: End\n");
    scanf("%u", &choice);
  }

  puts("Program execution completed.");
}

void circleCirc(double a)
{
  double answer1 = 2*a*3.14;
  printf("The circumference of the circle with radius %lf is %lf\n\n", a, answer1);
}

void circleArea(double b)
{
  double answer2 = 3.14*(b*b);
  printf("The area of the circle with radius %lf is %lf\n\n", b, answer2);
}

void sphereVol(double c)
{
  double answer3 = (4/3)*3.14*(c*c*c);
  printf("The volume of the sphere with radius %lf is %lf\n\n", c, answer3);
}
