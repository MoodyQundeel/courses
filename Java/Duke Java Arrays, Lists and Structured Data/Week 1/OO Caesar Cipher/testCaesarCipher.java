import edu.duke.*;
import java.util.*;

public class testCaesarCipher {
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
    
    public int getLetterWithHighestCount(int[] counts) {
        int idx = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > counts[idx]) {
                idx = i;
            }
        }
        return idx;
    }
    
    public String breakCaesarCipher(String s) {
        int[] counts = countLetters(s);
        int letterWithHighestCount = getLetterWithHighestCount(counts);
        int key = letterWithHighestCount - 4;
        if (key < 4) {
            key = 26 - (4 - letterWithHighestCount);
        }
        CaesarCipher cc = new CaesarCipher(key);
        s = cc.decrypt(s);
        return s;
    }
    
    public void simpleTests() {
        FileResource fr = new FileResource();
        String s = fr.asString();
        CaesarCipher cc = new CaesarCipher(18);
        s = cc.encrypt(s);
        System.out.println(s);
        s = cc.decrypt(s);
        System.out.println(s);
        s = cc.encrypt(s);
        s = breakCaesarCipher(s);
        System.out.println(s);
    }
}
