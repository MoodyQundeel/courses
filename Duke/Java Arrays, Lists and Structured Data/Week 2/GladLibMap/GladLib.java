import edu.duke.*;
import java.util.*;

public class GladLib {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> usedWords;
    private ArrayList<String> categoriesUsed;
    private int wordsReplaced;
    private Random myRandom;
    
    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";
    
    public GladLib(){
        myMap = new HashMap<String, ArrayList<String>>();
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
        int wordsReplaced = 0;
        usedWords = new ArrayList<String>();
        categoriesUsed = new ArrayList<String>();
    }
    
    public GladLib(String source){
        myMap = new HashMap<String, ArrayList<String>>();
        initializeFromSource(source);
        myRandom = new Random();
        int wordsReplaced = 0;
        usedWords = new ArrayList<String>();
        categoriesUsed = new ArrayList<String>();
    }
    
    private void initializeFromSource(String source) {
        String[] categories = {"adjective", "noun", "color", "country",
                               "name", "animal", "timeframe", "verb", "fruit"};
        for (String category : categories) {
            String fname = source+"/"+category+".txt";
            myMap.put(category, readIt(fname));
        }
    }
    
    private String randomFrom(ArrayList<String> source){
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }
    
    private String getSubstitute(String label) {
        if (label.equals("number")){
            return ""+myRandom.nextInt(50)+5;
        }
        if (! categoriesUsed.contains(label)) {
            categoriesUsed.add(label);
        }
        return randomFrom(myMap.get(label));
    }
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        wordsReplaced++;
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        while (true) {
            String sub = getSubstitute(w.substring(first+1,last));
            if (! usedWords.contains(sub)) {
                usedWords.add(sub);
                return prefix+sub+suffix;
            }
        }
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        else {
            FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        }
        return list;
    }
    
    public void makeStory(){
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate2.txt");
        printOut(story, 60);
        System.out.println("Number of words replaced: " + wordsReplaced);
        System.out.println("Number of possible words: " + totalWordsInMap());
        System.out.println("Number of words considered: " + totalWordsConsidered());
        usedWords.clear();
    }
    
    public int totalWordsInMap() {
        int count = 0;
        for (String key : myMap.keySet()) {
            ArrayList<String> arr = myMap.get(key);
            for (String word : arr) {
                count++;
            }
        }
        return count;
    }
    
    public int totalWordsConsidered() {
        int count = 0;
        for (String key : myMap.keySet()) {
            if (categoriesUsed.contains(key)) {
                ArrayList<String> arr = myMap.get(key);
                for (String word : arr) {
                    count++;
                }
            }
        }
        return count;
    }

}
