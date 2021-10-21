#include <stdio.h>
#include <cs50.h>
#include <string.h>
#include <ctype.h>
#include <math.h>

int count_letters(string text);
int count_words(string text);
int count_sentences(string text);

int main(void)
{
    string text = get_string("Text: ");

    int letters = count_letters(text);

    int words = count_words(text);

    int sentences = count_sentences(text);

    float grade = round(0.0588 * 100 * letters / words - 0.296 *  100 * sentences / words - 15.8);
    
    if (grade < 16 && grade > 0) 
    {
        printf("Grade %i\n", (int) grade);
    }
    else if (grade < 0) 
    {
        printf("Before Grade 1\n");
    }
    else if (grade >= 16)
    {
        printf("Grade 16+\n");    
    }
    
}
int count_letters(string text)
{
    int letters = 0;

    // assign a variable to the length of the text
    int n = strlen(text);

    // convert the text to lower case for accuracy
    for (int i = 0; i < n; i++)
    {
        text[i] = tolower(text[i]);
    }

    for (int i = 0; i < n; i++)
    {
        if (text[i] >= 97 && text[i] <= 122)
        {
            letters++;
        }
    }

    return letters;
}

int count_words(string text)
{
    int words = 1;

    int n = strlen(text);

    for (int i = 0; i < n; i++)
    {
        if (text[i] == 32)
        {
            words++;
        }
    }

    return words;
}

int count_sentences(string text)
{
    int sentences = 0;

    int n = strlen(text);

    for (int i = 0; i < n; i++)
    {
        if (text[i] == 46 || text[i] == 33 || text[i] == 63)
        {
            sentences ++;
        }
    }

    return sentences;
}