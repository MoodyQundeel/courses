import edu.duke.*;
import java.util.*;

public class WordPlay {
    public boolean isVowel(char c) {
        c = Character.toLowerCase(c);
        String vowels = "aeiou";
        if (vowels.indexOf(c) != -1) {
            return true;
        }
        return false;
    }
    
    public String replaceVowels(String phrase, char c) {
        StringBuilder newString = new StringBuilder(phrase);
        for (int i = 0; i < newString.length(); i++) {
            if (isVowel(newString.charAt(i))) {
                newString.setCharAt(i, c);
            }
        }
        return newString.toString();
    }
    
    public String emphasize(String phrase, char c) {
        StringBuilder newString = new StringBuilder(phrase);
        for (int i = 0; i < newString.length(); i++) {
            if (newString.charAt(i) == c) {
             
                if (i % 2 == 0) {
                    newString.setCharAt(i, '*'); 
                }
                else {
                    newString.setCharAt(i, '+');
                }
            } 
        }
        return newString.toString();
    }
}
