import cs50

height = 0

while (height <= 0 or height >= 9):
    height = cs50.get_int('height: ')    

i = height

while(i > 0):
    
    j = height - i + 1
    x = i - 1
    
    while(x > 0):
        print(" ", end="")
        x -= 1
    
    while(j > 0):
        print("#", end="")
        j -= 1
        
    print("  ", end="")
    
    j = height - i + 1
    
    while (j > 0):
        print("#", end="")
        j -= 1
        
    i -= 1
    
    print()