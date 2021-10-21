#include "helpers.h"
#include "math.h"
#include "stdio.h"

// Convert image to grayscale
void grayscale(int height, int width, RGBTRIPLE image[height][width])
{
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            float average = (float)(image[i][j].rgbtBlue + image[i][j].rgbtGreen + image[i][j].rgbtRed) / 3;
            image[i][j].rgbtBlue = round(average);
            image[i][j].rgbtGreen = round(average);
            image[i][j].rgbtRed = round(average);
        }
    }
    return;
}

// Reflect image horizontally
void reflect(int height, int width, RGBTRIPLE image[height][width])
{
    for (int i = 0; i < height; i++)
    {
        RGBTRIPLE reversed[width];
        
        for (int j = width - 1, x = 0; j >= 0; j--, x++)
        {
            reversed[x] = image[i][j];
        }
        
        for (int y = 0; y < width; y++)
        {
            image[i][y] = reversed[y];
        }
    }
    return;
}

// Blur image
void blur(int height, int width, RGBTRIPLE image[height][width])
{
    RGBTRIPLE ximage[height][width];
    float averageRed = 0;
    float averageGreen = 0;
    float averageBlue = 0;
    int dividor = 0;

    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            dividor = 0;
            averageRed = 0;
            averageGreen = 0;
            averageBlue = 0;
            
            {
                for (int x = i - 1; x <= i + 1; x++)
                {
                    for (int y = j - 1; y <= j + 1; y++)
                    {
                        if (x >= 0 && x < height && y >= 0 && y < width)
                        {
                            averageRed += image[x][y].rgbtRed;
                            averageGreen += image[x][y].rgbtGreen;
                            averageBlue += image[x][y].rgbtBlue;
                            dividor++;
                        }
                    }
                }
            }
                
        
            if (dividor > 0)
            {
                averageRed /= dividor;
                averageGreen /= dividor;
                averageBlue /= dividor;
    
                ximage[i][j].rgbtRed = round(averageRed);
                ximage[i][j].rgbtGreen = round(averageGreen);
                ximage[i][j].rgbtBlue = round(averageBlue);
            }
        }
    }
    
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            image[i][j] = ximage[i][j];
        }
    }
    
    return;
}

// Detect edges
void edges(int height, int width, RGBTRIPLE image[height][width])
{
    RGBTRIPLE ximage[height][width];
    
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            int gxRed = 0;
            int gyRed = 0;
            int gxGreen = 0; 
            int gyGreen = 0;
            int gxBlue = 0;
            int gyBlue = 0;
            int totalRed = 0;
            int totalBlue = 0;
            int totalGreen = 0;
            
            for (int x = i - 1; x <= i + 1; x++)
            {
                for (int y = j - 1; y <= j + 1; y++)
                {
                    if (x >= 0 && x < height && y >= 0 && y < width)
                    {
                        if (x == i - 1)
                        {
                            if (y == j - 1)
                            {
                                gxRed += -1 * image[x][y].rgbtRed;
                                gyRed += -1 * image[x][y].rgbtRed;
                                gxGreen += -1 * image[x][y].rgbtGreen;
                                gyGreen += -1 * image[x][y].rgbtGreen;
                                gxBlue += -1 * image[x][y].rgbtBlue;
                                gyBlue += -1 * image[x][y].rgbtBlue;
                            }
                            
                            if (y == j)
                            {
                                gxRed += 0;
                                gyRed += -2 * image[x][y].rgbtRed;
                                gxGreen += 0;
                                gyGreen += -2 * image[x][y].rgbtGreen;
                                gxBlue += 0;
                                gyBlue += -2 * image[x][y].rgbtBlue;  
                            }
                            
                            if (y == j + 1)
                            {
                                gxRed += image[x][y].rgbtRed;
                                gyRed += -1 * image[x][y].rgbtRed;
                                gxGreen += image[x][y].rgbtGreen;
                                gyGreen += -1 * image[x][y].rgbtGreen;
                                gxBlue += image[x][y].rgbtBlue;
                                gyBlue += -1 * image[x][y].rgbtBlue;    
                            }
                        }
                        
                        if (x == i)
                        {
                            if (y == j - 1)
                            {
                                gxRed += -2 * image[x][y].rgbtRed;
                                gyRed += 0 * image[x][y].rgbtRed;
                                gxGreen += -2 * image[x][y].rgbtGreen;
                                gyGreen += 0 * image[x][y].rgbtGreen;
                                gxBlue += -2 * image[x][y].rgbtBlue;
                                gyBlue += 0 * image[x][y].rgbtBlue;   
                            }
                            
                            if (y == j)
                            {
                                gxRed += 0 * image[x][y].rgbtRed;
                                gyRed += 0 * image[x][y].rgbtRed;
                                gxGreen += 0 * image[x][y].rgbtGreen;
                                gyGreen += 0 * image[x][y].rgbtGreen;
                                gxBlue += 0 * image[x][y].rgbtBlue;
                                gyBlue += 0 * image[x][y].rgbtBlue;   
                            }
                            
                            if (y == j + 1)
                            {
                                gxRed += 2 * image[x][y].rgbtRed;
                                gyRed += 0 * image[x][y].rgbtRed;
                                gxGreen += 2 * image[x][y].rgbtGreen;
                                gyGreen += 0 * image[x][y].rgbtGreen;
                                gxBlue += 2 * image[x][y].rgbtBlue;
                                gyBlue += 0 * image[x][y].rgbtBlue;   
                            }
                        }
                        
                        if (x == i + 1)
                        {
                            if (y == j - 1)
                            {
                                gxRed += -1 * image[x][y].rgbtRed;
                                gyRed += 1 * image[x][y].rgbtRed;
                                gxGreen += -1 * image[x][y].rgbtGreen;
                                gyGreen += 1 * image[x][y].rgbtGreen;
                                gxBlue += -1 * image[x][y].rgbtBlue;
                                gyBlue += 1 * image[x][y].rgbtBlue;    
                            }
                            
                            if (y == j)
                            {
                                gxRed += 0 * image[x][y].rgbtRed;
                                gyRed += 2 * image[x][y].rgbtRed;
                                gxGreen += 0 * image[x][y].rgbtGreen;
                                gyGreen += 2 * image[x][y].rgbtGreen;
                                gxBlue += 0 * image[x][y].rgbtBlue;
                                gyBlue += 2 * image[x][y].rgbtBlue;     
                            }
                            
                            if (y == j + 1)
                            {
                                gxRed += 1 * image[x][y].rgbtRed;
                                gyRed += 1 * image[x][y].rgbtRed;
                                gxGreen += 1 * image[x][y].rgbtGreen;
                                gyGreen += 1 * image[x][y].rgbtGreen;
                                gxBlue += 1 * image[x][y].rgbtBlue;
                                gyBlue += 1 * image[x][y].rgbtBlue;     
                            }
                        }
                            
                    }
                    
                }
            }
            
            totalRed = round(sqrt(pow(gxRed, 2) + pow(gyRed, 2)));
            totalGreen = round(sqrt(pow(gxGreen, 2) + pow(gyGreen, 2)));
            totalBlue = round(sqrt(pow(gxBlue, 2) + pow(gyBlue, 2)));
            
            if (totalRed > 255)
            {
                totalRed = 255;
            }
            
            if (totalGreen > 255)
            {
                totalGreen = 255;
            }
            
            if (totalBlue > 255)
            {
                totalBlue = 255;
            }
            
            ximage[i][j].rgbtRed = totalRed;
            ximage[i][j].rgbtGreen = totalGreen;
            ximage[i][j].rgbtBlue = totalBlue;
            
            
            
        }    
    }
    
    for (int i = 0; i < height; i++)
    {
        for (int j = 0; j < width; j++)
        {
            image[i][j] = ximage[i][j];
        }
    }
        
    return;
}
