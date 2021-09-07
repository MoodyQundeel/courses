import edu.duke.*;
import java.util.*;

public class CharactersInPlay {
    private ArrayList<String> characters;
    private ArrayList<Integer> count;
    public CharactersInPlay() {
        characters = new ArrayList<String>();
        count = new ArrayList<Integer>();
    }
    public void update(String person) {
        int index = characters.indexOf(person);
        if (index == -1) {
            characters.add(person);
            count.add(1);
        }
        else {
            int value = count.get(index);
            count.set(index, value+1);
        }
    }
    
    public void findAllCharacters() {
        characters.clear();
        count.clear();
        FileResource fr = new FileResource();
        for (String line : fr.lines()) {
            int index = line.indexOf(".");
            if (index != -1) {
                String s = line.substring(0, index);
                update(s);
            }
        }
    }
    
    public void charactersWithNumParts(int num1, int num2) {
        for (int i = 0; i < characters.size(); i++) {
            int number = count.get(i);
            String character = characters.get(i);
            if (number >= num1 && number <= num2) {
                System.out.println("Character " + characters.get(i) + " appeared " + count.get(i) + " times.");
            }
        }
    }
    
    public void test() {
        findAllCharacters();
        charactersWithNumParts(10, 15);
    }
}
