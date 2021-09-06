import java.util.*;
import edu.duke.*;

public class CaesarCipherTwo {
    private String alphabet;
    private String shiftedAlphabet1;
    private String shiftedAlphabet2;
    private int mainKey1;
    private int mainKey2;
    public CaesarCipherTwo(int key1, int key2) {
        alphabet = "abcdefghijklmnopqrstuvwxyz";
        shiftedAlphabet1 = alphabet.substring(key1) + alphabet.substring(0, key1);
        shiftedAlphabet2 = alphabet.substring(key2) + alphabet.substring(0, key2);
        mainKey1 = key1;
        mainKey2 = key2;
    }
    
    public String encrypt(String s) {
        StringBuilder encrypted = new StringBuilder(s);
        int idx = 0;
        for (int i = 0; i < encrypted.length(); i+=2) {
            idx = alphabet.indexOf(Character.toLowerCase(encrypted.charAt(i)));
            if (idx != -1) encrypted.setCharAt(i, shiftedAlphabet1.charAt(idx));
        }
        for (int i = 1; i < encrypted.length(); i+=2) {
            idx = alphabet.indexOf(Character.toLowerCase(encrypted.charAt(i)));
            if (idx != -1) encrypted.setCharAt(i, shiftedAlphabet2.charAt(idx));
        }
        return encrypted.toString();
    }
    
    public String decrypt(String s) {
        StringBuilder decrypted = new StringBuilder(s);
        int idx = 0;
        for (int i = 0; i < decrypted.length(); i+=2) {
            idx = shiftedAlphabet1.indexOf(Character.toLowerCase(decrypted.charAt(i)));
            if (idx != -1) decrypted.setCharAt(i, alphabet.charAt(idx));
        }
        for (int i = 1; i < decrypted.length(); i+=2) {
            idx = shiftedAlphabet2.indexOf(Character.toLowerCase(decrypted.charAt(i)));
            if (idx != -1) decrypted.setCharAt(i, alphabet.charAt(idx));
        }
        return decrypted.toString();
    }
}
