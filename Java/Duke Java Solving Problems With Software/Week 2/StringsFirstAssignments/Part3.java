public class Part3 {
    public boolean twoOccurrences(String stringa,String stringb) {
        int count = 0;
        int startIndex = 0;
        while (stringb.indexOf(stringa, startIndex) != -1) {
            count++;
            startIndex = stringb.indexOf(stringa,startIndex) + stringa.length();
        }
        if (count >= 2) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public String lastPart(String stringa, String stringb) {
        int startIndex = stringb.indexOf(stringa);
        if (startIndex == -1) {
            return stringb;
        }
        else {
            return stringb.substring(startIndex + stringa.length(), stringb.length());
        }
    }
    
    public void testing() {
        String s;
        String t;
        s = "by";
        t = "A story by Abby Long";
        System.out.println(s + " " + t + " " + twoOccurrences(s, t));
        s = "taa";
        t = "atgtaaatg";
        System.out.println(s + " " + t + " " + twoOccurrences(s, t));
        s = "an";
        t = "banana";
        System.out.println("the part of " + t + " comes after " + s + " is "+ lastPart(s, t));
        s = "zoo";
        t = "forest";
        System.out.println("the part of " + t + " comes after " + s + " is "+ lastPart(s, t));
        
    }
}
