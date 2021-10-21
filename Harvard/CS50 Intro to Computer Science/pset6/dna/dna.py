import cs50
import sys
import csv

if (len(sys.argv) != 3):
    print("unvalid arguments")
    sys.exit(1)

dic = []
strs = {}

with open(sys.argv[1]) as database:

    reader = csv.reader(database)

    for row in reader:
        for i in range(1, len(row)):
            strs[row[i]] = 0
        break
    
with open(sys.argv[1]) as database:
    
    reader = csv.DictReader(database)
    
    for row in reader:
        dic.append(row)

with open(sys.argv[2], "r") as f:
    dna = f.read()

for key in strs:
    length = len(key)
    highest_num = 0
    current_highest_num = 0
    for i in range(0, len(dna), 1):
        if (dna[i:i+length] == key):
            highest_num += 1
            i += length
            while(dna[i:i+length] == key):
                highest_num += 1
                i += length
            if (highest_num > current_highest_num):
                current_highest_num = highest_num
            highest_num = 0
    strs[key] = current_highest_num

i = 0

for item in dic:
    for key in item:
        if (key == 'name'):
            continue
        elif (strs[key] == int(item[key])):
            i += 1
    if (i == len(strs)):
        print(item['name'])
        sys.exit(0)
    else:
        i = 0
        
print("No match")