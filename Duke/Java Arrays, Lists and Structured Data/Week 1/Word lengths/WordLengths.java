import edu.duke.*;

public class WordLengths {
    public void countWordLengths(FileResource resource, int[] counts) {
        for (String word : resource.words()) {
            int length = word.length();
            if (!Character.isLetter(word.charAt(0))) {
                length--;
            }
            if (!Character.isLetter(word.charAt(word.length() - 1))) {
                length--;
            }
            if (length > counts.length) {
                counts[counts.length-1]++;
            }
            else {
                counts[length]++;
            }
        }
    }
    
    public void testCountWordsLengths() {
        FileResource fr = new FileResource();
        int[] counts = new int[31];
        countWordLengths(fr, counts);
        for (int i = 0; i < counts.length; i++) {
            System.out.println("number of words with length " + i + " = " + counts[i]);
        }
    }
}
