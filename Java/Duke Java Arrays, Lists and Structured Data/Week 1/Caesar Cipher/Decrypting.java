import edu.duke.*;
import java.util.*;

public class Decrypting {
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
    
    public String decrypt(String encrypted) {
        CaesarCipher cc = new CaesarCipher();
        int key = getKey(encrypted);
        return cc.encryptOneKey(encrypted, 26-key);
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
    
    public String decryptTwoKeys(String encrypted) {
        CaesarCipher cc = new CaesarCipher();
        int key1 = getKey(halfOfString(encrypted, 0));
        int key2 = getKey(halfOfString(encrypted, 1));
        System.out.println("key1 = " + key1 + " key2 = " + key2);
        return cc.encryptTwoKey(encrypted, 26-key1, 26-key2);
    }
    
    public void test() {
        FileResource fr = new FileResource();
        System.out.println(decryptTwoKeys(fr.asString()));
    }
}
