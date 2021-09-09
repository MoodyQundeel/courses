import edu.duke.*;
import java.util.*;

public class testCaesarCipherTwo {
    public int[] countLetters(String input) {
        int[] counts = new int[26];
        String alpha = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < input.length(); i++) {
            char ch = Character.toLowerCase(input.charAt(i));
            int idx = alpha.indexOf(ch);
            if (idx != -1) counts[idx]++;
        }
        return counts;
    }
    
    public int getLetterWithHighestCountIndex(int[] counts) {
        int idx = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > counts[idx]) {
                idx = i;
            }
        }
        return idx;
    }
    
    public String halfOfString(String input, int startIndex) {
        String out = "";
        for (int i = startIndex; i < input.length(); i += 2) {
            out = out + input.charAt(i);
        }
        return out.toString();
    }
    
    public int getKey(String s) {
        int[] counts = countLetters(s);
        int letterWithHighestCount = getLetterWithHighestCountIndex(counts);
        int key = letterWithHighestCount - 4;
        if (letterWithHighestCount < 4) {
            key = 26 - (4 - letterWithHighestCount);
        }
        return key;
    }
    
    public String decrypt(String s) {
        int key1 = getKey(halfOfString(s, 0));
        int key2 = getKey(halfOfString(s, 1));
        CaesarCipherTwo cc = new CaesarCipherTwo(key1, key2);
        return cc.decrypt(s);
    }
    
    public void simpleTests() {
        FileResource fr = new FileResource();
        String s = fr.asString();
        CaesarCipherTwo cc = new CaesarCipherTwo(17, 3);
        s = cc.encrypt(s);
        System.out.println(s);
        s = cc.decrypt(s);
        System.out.println(s);
        s = cc.encrypt(s);
        System.out.println(decrypt(s));
    }
}
