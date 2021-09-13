import edu.duke.*;
import java.util.*;
import java.io.*;

public class WordsInFiles {
    private HashMap<String, ArrayList<String>> wordCount;
    
    public WordsInFiles() {
        wordCount = new HashMap<String, ArrayList<String>>();
    }
    
    private void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f);
        for (String word : fr.words()) {
            if (wordCount.keySet().contains(word)) {
                if (!wordCount.get(word).contains(f.getName())) {
                    wordCount.get(word).add(f.getName());
                }
            }
            else {
                ArrayList<String> newWord = new ArrayList<String>();
                newWord.add(f.getName());
                wordCount.put(word, newWord);
            }
        }
    }
    
    private void buildWordFileMap() {
        wordCount.clear();
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }
    
    public int maxNumber() {
        int largestCount = 0;
        for (String key : wordCount.keySet()) {
            int currentCount = 0;
            ArrayList<String> word = wordCount.get(key);
            for (String fileName : word) {
                currentCount++;
            }
            if (currentCount > largestCount) {
                largestCount = currentCount;
            }
        }
        return largestCount;
    }
    
    public ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> words = new ArrayList<String>();
        for (String key : wordCount.keySet()) {
            ArrayList<String> fileNames = wordCount.get(key);
            if (fileNames.size() == number) {
                words.add(key);
            }
        }
        return words;
    }
    
    public void printFilesIn(String word) {
        ArrayList<String> fileNames = wordCount.get(word);
        if (fileNames != null) {
            for (String fileName : fileNames) {
                System.out.println(fileName);
            }
        }
        else {
            System.out.println("WORD NOT FOUND");
        }
    }
    
    public void test() {
        wordCount.clear();
        buildWordFileMap();
        int maxNumber = maxNumber();
        printFilesIn("red");
    }
}
    