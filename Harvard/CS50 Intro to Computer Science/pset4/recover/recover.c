#include <stdio.h>
#include <stdlib.h>
#include <stdint.h>

typedef uint8_t BYTE;
int main(int argc, char *argv[])
{
    if (argc != 2)
    {
        printf("invalid arguments\n");
        return 1;
    }
    
    FILE *raw = fopen(argv[1], "r");
    
    if (raw == NULL)
    {
        printf("couldn't open file\n");
        return 1;
    }
    
    char *filename = malloc(8);
    BYTE buffer[512];
    
    int i = 0;
            
    while (fread(&buffer, sizeof(BYTE), 512, raw))
    {
        if (buffer[0] == 0xff && buffer[1] == 0xd8 && buffer[2] == 0xff && (buffer[3] & 0xf0) == 0xe0)
        {
            sprintf(filename, "%03i.jpg", i);
            FILE *out = fopen(filename, "w");
            fwrite(&buffer, sizeof(BYTE), 512, out);
            while (fread(&buffer, sizeof(BYTE), 512, raw))
            {
                if (buffer[0] == 0xff && buffer[1] == 0xd8 && buffer[2] == 0xff && (buffer[3] & 0xf0) == 0xe0)
                {
                    fclose(out);
                    i++;
                    sprintf(filename, "%03i.jpg", i);
                    out = fopen(filename, "w");
                }
                fwrite(&buffer, sizeof(BYTE), 512, out);
            }
        }
    }
    free(filename);
}