public class Part2 {
    public int howMany(String stringa, String stringb) {
        int count = 0;
        int startIndex = 0;
        while(stringb.indexOf(stringa, startIndex) != -1) {
            count++;
            startIndex = stringb.indexOf(stringa, startIndex) + stringa.length();
        }
        return count;
    }
    
    public void testHowmany() {
        String s = "xxx";
        String t = "xxxyyyxxxyyyxxx";
        System.out.println(howMany(s, t));
        
        t = "xxxxxxxyyy";
        System.out.println(howMany(s, t));
    }
}
