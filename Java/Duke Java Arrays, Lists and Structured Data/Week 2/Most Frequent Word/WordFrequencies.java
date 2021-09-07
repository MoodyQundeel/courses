import edu.duke.*;
import java.util.*;

public class WordFrequencies {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;
    
    public WordFrequencies() {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }
    
    public void findUnique() {
        FileResource fr = new FileResource();
        myWords.clear();
        myFreqs.clear();
        for (String word : fr.words()) {
            word = word.toLowerCase();
            int index = myWords.indexOf(word);
            if (index == -1) {
                myWords.add(word);
                myFreqs.add(1);
            }
            else {
                int value = myFreqs.get(index);
                myFreqs.set(index, value+1);
            }
        }
    }
    
    public int findIndexOfMax() {
        int mostFrequent = 0; 
        for (int i = 0; i < myFreqs.size(); i++) {
            if (myFreqs.get(i) > mostFrequent) {
                mostFrequent = myFreqs.get(i);
            }
        }
        return myFreqs.indexOf(mostFrequent);
    }
    public void tester() {
        findUnique();
        System.out.println("Number of unique word: " + myWords.size());
        for (int i = 0; i < myWords.size(); i++) {
            System.out.println(myWords.get(i) + " " + myFreqs.get(i));
        }
        System.out.println("The word the occurs most often is " + "\"" + myWords.get(findIndexOfMax()) + "\"" + " it's count is " + myFreqs.get(findIndexOfMax()));
    }
}
