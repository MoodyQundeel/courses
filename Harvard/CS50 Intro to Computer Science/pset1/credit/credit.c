#include <stdio.h>
#include <cs50.h>

int main(void) 
{
 
    long number = get_long("number: ");
    long numClone = number;
    int everySecondDigit;
    int Sum1 = 0;
    int Sum2 = 0;
    int numDigits = 0;
    
    for (int i = 0; numClone > 0; numClone /= 10)
    {
        numDigits++;
    }
    
    numClone = number;
    
    for (int i = 0; i < numDigits / 2; i++)
    {
        everySecondDigit = numClone % 10;
        numClone /= 100;
        Sum1 += everySecondDigit;
    }
    
    numClone = number;
    numClone /= 10;
    
    for (int i = 0; i < numDigits / 2; i++)
    {
        everySecondDigit = (numClone % 10 * 2);
        numClone /= 100;
        
        if (everySecondDigit / 10 < 0) 
        {
            Sum2 += everySecondDigit;
        }
        else 
        {
            Sum2 += everySecondDigit % 10;
            everySecondDigit /= 10;
            Sum2 += everySecondDigit % 10;
        }
    }
    
    if (numDigits == 15) 
    {
        Sum2 += 3;
    }
    
    if (numDigits == 13)
    {
        Sum2 += 4;
    }
    
    int checkSum = (Sum1 + Sum2) % 10;
    
    if (checkSum != 0)
    {
        printf("INVALID\n");
    }
    else if (numDigits == 15 && ((number / 10000000000000) == 34 || (number / 10000000000000) == 37))
    {
        printf("AMEX\n");
    }
    else if (numDigits == 13)
    {
        printf("VISA\n");
    }
    else if (numDigits == 16 && (number / 100000000000000) > 50 && (number / 100000000000000) < 56) 
    {
        printf("MASTERCARD\n");
    }
    else if (numDigits == 16 && (number / 1000000000000000) == 4)
    {
        printf("VISA\n");
    }
    else 
    {
        printf("INVALID\n");
    }
}