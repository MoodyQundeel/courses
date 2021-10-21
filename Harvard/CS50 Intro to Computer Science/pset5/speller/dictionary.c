// Implements a dictionary's functionality

#include <stdbool.h>
#include <stdio.h>
#include <strings.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include "dictionary.h"

// Represents a node in a hash table
typedef struct node
{
    char word[LENGTH + 1];
    struct node *next;
}
node;

// Number of buckets in hash table
const unsigned int N = 676;
int words = 0;

// Hash table
node *table[N];

// Returns true if word is in dictionary, else false
bool check(const char *word)
{
    // TODO
    int index = hash(word);
    
    for (node *tmp = table[index]; tmp != NULL; tmp = tmp->next)
    {
        if (strcasecmp(word, tmp->word) == 0)
        {
            return true;
        }
    }
    return false;
}

// Hashes word to a number
unsigned int hash(const char *word)
{
    int index = 0;
    if (strlen(word) == 1)
    {
        index = (tolower(word[0]) - 97) * 26 + tolower(word[0]) - 97;    
    }
    else if (word[1] == 39)
    {
        index = (tolower(word[0]) - 97) * 26 + tolower(word[2]) - 97;
    }
    else
    {
        index = (tolower(word[0]) - 97) * 26 + tolower(word[1]) - 97;   
    }
    
    return index;
}

// Loads dictionary into memory, returning true if successful, else false
bool load(const char *dictionary)
{
    // TODO
    FILE *dic = fopen(dictionary, "r");
    
    if (dic == NULL)
    {
        return false;
    }
    
    char word[46];
    
    node *n;
    
    while (fscanf(dic, "%s", word) != EOF)
    {
        n = malloc(sizeof(node));
        n->next = NULL;
    
        if (n == NULL)
        {
            return false;
        }
        
        strcpy(n->word, word);
        words++;
        
        
        if (table[hash(n->word)] == NULL)
        {
            table[hash(n->word)] = n;
        }
        
        else
        {
            n->next = table[hash(n->word)];
            table[hash(n->word)] = n;
        }
    }
    
    fclose(dic);
    return true;
}

// Returns number of words in dictionary if loaded, else 0 if not yet loaded
unsigned int size(void)
{
    // TODO
    return words;
    
}

// Unloads dictionary from memory, returning true if successful, else false
bool unload(void)
{
    // TODO
    node *tmp;
    for (int i = 0; i <= 675; i++)
    {
        while (table[i] != NULL)
        {
            tmp = table[i]->next;
            free(table[i]);
            table[i] = tmp;
        }
    }
    
    return true;
}
