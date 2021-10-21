#include <ctype.h>
#include <cs50.h>
#include <stdio.h>
#include <string.h>

// Points assigned to each letter of the alphabet
int POINTS[] = {1, 3, 3, 2, 1, 4, 2, 4, 1, 8, 5, 1, 3, 1, 1, 3, 10, 1, 1, 1, 1, 4, 4, 8, 4, 10};

int compute_score(string word);

int main(void)
{
    // Get input words from both players
    string word1 = get_string("Player 1: ");
    string word2 = get_string("Player 2: ");

    // Score both words
    int score1 = compute_score(word1);
    int score2 = compute_score(word2);
    
    if (score1 == score2) 
    {
        printf("Tie!\n");
    }
    else if (score1 > score2)
    {
        printf("Player 1 Wins!\n");
    }
    else
    {
        printf("Player 2 Wins!\n");
    }
}

int compute_score(string word)
{
    // assign a variable to the length of the string
    int n = strlen(word);
    
    // convert entire string to lowercase
    for (int i = 0; i < n; i++)
    {
        word[i] = tolower(word[i]);
    }
    
    int score = 0;
    
    for (int i = 0; i < n; i++)
    {
       
        if (word[i] >= 97 && word[i] <= 122)
        {
            // add the points of the given letter to score
            score += POINTS[(int) word[i] - 97]; 
        }
    }
    return score;
}