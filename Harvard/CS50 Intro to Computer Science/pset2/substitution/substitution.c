#include <stdio.h>
#include <cs50.h>
#include <string.h>
#include <ctype.h>

int main(int argc, string argv[])
{
    
    int x = 0;
   
    if (argc <= 1 || argc > 2) 
    {
        printf("ERROR: UNVALID ARGUMENT");
        return 1;
    }
   
    string key = argv[1];
    
    int n = strlen(key);
    
    for (int i = 0; i < n; i++) 
    {
        key[i] = toupper(key[i]);
    }
   
    for (int i = 0; i < n; i++)
    {
        for (int j = 0; j < i; j++) 
        {
            if (key[i] == key[j]) 
            {
                x++;
            }
        }
        
        if ((key[i] < 'A' || key[i] > 'Z') || n != 26 || x > 0)
        {
            printf("ERROR: UNVALID ARGUMENT");
            return 1;
        }
    }
    
    string plaintext = get_string("plaintext: ");
    
    string ciphertext = plaintext;
    
    int y = strlen(ciphertext);
    
    for (int i = 0; i < y; i++)
    {
        if (islower(plaintext[i]))
        {
            ciphertext[i] = tolower(key[(int)plaintext[i] - 97]);
        }
        else if (isupper(plaintext[i]))
        {
            ciphertext[i] = toupper(key[(int)plaintext[i] - 65]);
        }
    }
    
    printf("ciphertext: %s\n", ciphertext);
    
    return 0;
}