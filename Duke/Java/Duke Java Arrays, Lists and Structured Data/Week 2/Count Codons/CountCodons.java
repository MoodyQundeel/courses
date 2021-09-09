import edu.duke.*;
import java.util.*;

public class CountCodons {
    private HashMap<String, Integer> codonCount;
    
    public CountCodons() {
        codonCount = new HashMap<String, Integer>();
    }
    
    private void buildCodonMap(int start, String dna) {
        codonCount.clear();
        for (int i = start; i < dna.length()-2; i+=3) {
            String codon = dna.substring(i, i+3);
            if (codonCount.keySet().contains(codon)) {
                codonCount.put(codon, codonCount.get(codon)+1);
            }
            else {
                codonCount.put(codon, 1);
            }
        }
    }
    
    private String getMostCommonCodon() {
        int largestCount = 0;
        String codon = "";
        for (String key : codonCount.keySet()) {
            if (codonCount.get(key) > largestCount) {
                largestCount = codonCount.get(key);
                codon = key;
            }
        }
        return codon;
    }
    
    private void printCodonCounts(int start, int end) {
        for (String key : codonCount.keySet()) {
            int count = codonCount.get(key);
            String codon = key;
            if (count >= start && count <= end) {
                System.out.println(codon + ": " + count);
            }
        }
    }
    public void test() {
        FileResource fr = new FileResource();
        String dna = fr.asString().trim();
        
        for (int i = 0; i < 3; i++) {
            buildCodonMap(i, dna);
            String mostCommonCodon = getMostCommonCodon();
            System.out.println("Total number of unique Codon: " + codonCount.size());
            System.out.println("Most common codon: " + mostCommonCodon + " it's count: " + codonCount.get(mostCommonCodon));
            printCodonCounts(1, 10000);
        }
    }
}
