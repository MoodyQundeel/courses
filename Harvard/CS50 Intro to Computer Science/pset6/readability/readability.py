from cs50 import get_string
import math
import re

text = get_string("Text: ")
letters = 0
words = 1
sentences = 0
length = len(text)

p = re.compile('[a-zA-Z]')

for i in range(0, length):
    if (text[i] == " "):
        words += 1
    elif (text[i] == "?" or text[i] == "!" or text[i] == "."):
        sentences += 1
    elif(p.match(text[i])):
        letters += 1

grade = 0.0588 * (100/words * letters) - 0.296 * (100/words * sentences) - 15.8

if (grade >= 16):
    print("Grade: 16+")
elif (grade < 1):
    print("Before Grade 1")
else:
    print("Grade: " + str(round(grade)))