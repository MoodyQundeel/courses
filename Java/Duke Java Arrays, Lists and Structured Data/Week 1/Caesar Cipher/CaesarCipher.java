import java.util.*;
import edu.duke.*;

public class CaesarCipher {
    public char encryptChar(char c, int key) {
        String upperCaseAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String upperCaseShiftedAlphabet = upperCaseAlphabet.substring(key) + upperCaseAlphabet.substring(0, key);
        String lowerCaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String lowerCaseShiftedAlphabet = lowerCaseAlphabet.substring(key) + lowerCaseAlphabet.substring(0, key);
        if (Character.isLowerCase(c)) {
            int idx = lowerCaseAlphabet.indexOf(c);
            if (idx != -1) return lowerCaseShiftedAlphabet.charAt(idx);
        }
        else {
            int idx = upperCaseAlphabet.indexOf(c);
            if (idx != -1) return upperCaseShiftedAlphabet.charAt(idx);
        }
        return c;
    }
    
    public String encryptOneKey(String input, int key) {
        StringBuilder encrypted = new StringBuilder(input);
        for (int i = 0; i < encrypted.length(); i++) {
            encrypted.setCharAt(i, encryptChar(encrypted.charAt(i), key));
        }
        return encrypted.toString();
    }
    
    public String encryptTwoKey(String input, int key1, int key2) {
        StringBuilder encrypted = new StringBuilder(input);
        for (int i = 0; i < encrypted.length(); i += 2) {
            encrypted.setCharAt(i, encryptChar(encrypted.charAt(i), key1));
        }
        for (int i = 1; i < encrypted.length(); i += 2) {
            encrypted.setCharAt(i, encryptChar(encrypted.charAt(i), key2));
        }
        return encrypted.toString();
    }
    
    public void testCeasar() {
        FileResource fr = new FileResource();
        String message = fr.asString();
        String encrypted = encryptTwoKey(message, 8, 21);
        System.out.println(encrypted);
    }
}
