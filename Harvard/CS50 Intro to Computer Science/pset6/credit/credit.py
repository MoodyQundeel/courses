from cs50 import get_string

credit = get_string("number: ")
sum1 = 0
sum2 = 0
length = len(credit)

if (length % 2 == 0):    
    
    for i in range(0, length, 2):
        if (len(str(int(credit[i]) * 2)) > 1):
            sum1 += int(str(int(credit[i]) * 2)[0])
            sum1 += int(str(int(credit[i]) * 2)[1])
        elif (len(str(int(credit[i]) * 2)) == 1):
            sum1 += int(credit[i]) * 2
                
    for i in range(1, length, 2):
        sum2 += int(credit[i])

elif (length % 2 != 0):
    
    for i in range(1, length, 2):
        if (len(str(int(credit[i]) * 2)) > 1):
            sum1 += int(str(int(credit[i]) * 2)[0])
            sum1 += int(str(int(credit[i]) * 2)[1])
        elif (len(str(int(credit[i]) * 2)) == 1):
            sum1 += int(credit[i]) * 2
                
    for i in range(0, length, 2):
        sum2 += int(credit[i])
    
total = sum1 + sum2

if (total % 10 != 0):
    print("INVALID")
    
elif (total % 10 == 0):
    if (length == 15 and credit[0] == '3' and (credit[1] == '4' or credit[1] == '7')):
        print("AMEX")
    elif (length == 13 or (length == 16 and credit[0] == "4")):
        print("VISA")
    else:
        print("MASTERCARD")