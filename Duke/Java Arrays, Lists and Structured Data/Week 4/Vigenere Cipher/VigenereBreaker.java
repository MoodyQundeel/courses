import java.util.*;
import edu.duke.*;
import java.io.*;

public class VigenereBreaker {
    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        //WRITE YOUR CODE HERE
        CaesarCracker cc = new CaesarCracker(mostCommon);
        for (int i = 0; i < klength; i++) {
            key[i] = cc.getKey(sliceString(encrypted, i, klength));
        }
        return key;
    }
    
    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dict = new HashSet<String>();
        for (String word : fr.lines()) {
            word = word.toLowerCase();
            dict.add(word);
        }
        return dict;
    }
    
    public char mostCommonCharIn(HashSet<String> dict) {
        HashMap<Character, Integer> count = new HashMap<Character, Integer>();
        for (String word : dict) {
            for (int i = 0; i < word.length(); i++) {
                char c = word.charAt(i);
                if (! count.keySet().contains(c)) {
                    count.put(c, 1);
                }
                else {
                    int value = count.get(c);
                    count.put(c, value+1);
                }
            }
        }
        int highestCount = 0;
        char output = '\0';
        for (char c : count.keySet()) {
            if (count.get(c) > highestCount) {
                highestCount = count.get(c);
                output = c;
            }
        }
        return output;
    }
    
    public int countWords(String message, HashSet<String> dict) {
        int count = 0;
        for (String word : message.split("\\W+")) {
            word = word.toLowerCase();
            if (dict.contains(word)){
                count++;
            }
        }
        return count;
    }
    
    public String breakForLanguage(String encrypted, HashSet<String> dict) {
        VigenereCipher vc;
        int[] key;
        int highestCount = 0;
        int currentCount = 0;
        String output = "";
        char c = mostCommonCharIn(dict);
        for (int i = 1; i < 101; i++) {
            key = tryKeyLength(encrypted, i, c);
            vc = new VigenereCipher(key);
            currentCount = countWords(vc.decrypt(encrypted), dict);
            if (currentCount > highestCount) {
                highestCount = currentCount;
                output = vc.decrypt(encrypted);
            }
        }
        return output;
    }
    
    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        int highestCount = 0;
        int currentCount = 0;
        String decrypted = "";
        String currentString = "";
        String languageGuess = "";
        HashSet<String> dict;
        for (String language : languages.keySet()) {
            dict = languages.get(language);
            currentString = breakForLanguage(encrypted, dict);
            currentCount = countWords(currentString, dict);
            if (currentCount > highestCount) {
                highestCount = currentCount;
                decrypted = currentString;
                languageGuess = language;
            }
        }
        
        System.out.println("Langauge guess: " + languageGuess + "\n \n" +
                            decrypted);
    }
    
    public void breakVigenere() {
        //WRITE YOUR CODE HERE
        HashMap<String, HashSet<String>> dicts = new HashMap<String, HashSet<String>>();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            String language = f.getName();
            FileResource fr = new FileResource(f);
            HashSet<String> dict = readDictionary(fr);
            dicts.put(language, dict);
        }
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        breakForAllLangs(encrypted, dicts);
    }

    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder newString = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i+=totalSlices) {
            newString.append(message.charAt(i));
        }
        return newString.toString();
    }
    
}
