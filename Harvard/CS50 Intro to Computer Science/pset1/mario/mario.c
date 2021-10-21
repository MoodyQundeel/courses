#include <stdio.h>
#include <cs50.h>

int main(void) 
{
    int height;
    
    do 
    {
        height = get_int("Height: ");
    }
    while (height <= 0 ||  height > 8);
    // i accounts for each row
    for (int i = 1; i <= height; i++) 
    {
        // j accounts for the spaces before left side
        for (int j = height - i; j != 0; j--)
        {
            printf(" ");
        }
        // x accounts for the left hashes
        for (int x = i; x != 0; x--)
        {
            printf("#");
        }
        // accounts for the constant double space
        printf("  ");
        
        // account for the right hashes
        for (int y = i; y != 0; y--) 
        {
            printf("#");
        }
        // goes to the next row
        printf("\n");
    }
    
}

    