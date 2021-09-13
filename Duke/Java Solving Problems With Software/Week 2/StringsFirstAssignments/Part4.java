import edu.duke.*;
public class Part4 {
    public String getYoutube() {
        String link = "";
        URLResource url = new URLResource("https://www.dukelearntoprogram.com//course2/data/manylinks.html");
        for (String word : url.words()) {
            if (word.toLowerCase().indexOf("youtube.com") != -1) {
                link = word.substring(word.indexOf("\"") + 1, word.lastIndexOf("\""));
                System.out.println(link);
            }
        }
        
        return link;   
    }

}
