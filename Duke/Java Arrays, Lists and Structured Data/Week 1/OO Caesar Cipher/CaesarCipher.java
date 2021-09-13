import java.util.*;
import edu.duke.*;

public class CaesarCipher {
    private String alphabet;
    private String shiftedAlphabet;
    private int mainKey;
    public CaesarCipher(int key) {
        alphabet = "abcdefghijklmnopqrstuvwxyz";
        shiftedAlphabet = alphabet.substring(key) + alphabet.substring(0, key);
        mainKey = key;
    }
    
    public String encrypt(String s) {
        StringBuilder encrypted = new StringBuilder(s);
        int idx = 0;
        for (int i = 0; i < s.length(); i++) {
            idx = alphabet.indexOf(Character.toLowerCase(s.charAt(i)));
            if (idx != -1) encrypted.setCharAt(i, shiftedAlphabet.charAt(idx));
        }
        return encrypted.toString();
    }
    
    public String decrypt(String s) {
        CaesarCipher cc = new CaesarCipher(26-mainKey);
        return cc.encrypt(s); 
    }
    
}
