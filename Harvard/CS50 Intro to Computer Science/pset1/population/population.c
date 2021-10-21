#include <stdio.h>
#include <cs50.h>

int main(void)
{
    int startSize;
    int endSize;
    
    // prompt the user for start size until they enter number more than 9
    do 
    {
        startSize = get_int("start_size: ");
    }
    while (startSize < 9);
    
    // prompt the user for end size until they enter number more than or equal to startSize 
    do 
    { 
        endSize = get_int("end_size: ");
    }
    while (endSize < startSize);
    
    int years = 0;
    
    // calculate the number of years to reach endSize
    for (int population = startSize; population < endSize; population += ((population / 3) - (population / 4))) 
    {
        years++;
    }
    
    // show the number of years
    printf("Years: %i\n", years);
}