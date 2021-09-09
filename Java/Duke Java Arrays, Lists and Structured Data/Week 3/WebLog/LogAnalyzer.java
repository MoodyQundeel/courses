import java.util.*;
import edu.duke.*;

public class LogAnalyzer {
    private ArrayList<LogEntry> records;
     
    public LogAnalyzer() {
        // complete constructor
        records = new ArrayList<LogEntry>();
    }
        
    public void readFile(String filename) {
        // complete method
        FileResource fr = new FileResource(filename);
        for (String line : fr.lines()) {
            WebLogParser parser = new WebLogParser();
            records.add(parser.parseEntry(line));
        }
    }
        
    public void printAll() {
        for (LogEntry le : records) {
            System.out.println(le);
        }
    }
     
    public int countUniqueIps() {
        ArrayList<String> uniqueIps = new ArrayList<String>();
        for (LogEntry le : records) {
            String ip = le.getIpAddress();
            if (! uniqueIps.contains(ip)) {
                uniqueIps.add(ip);
            }
        }
        return uniqueIps.size();
    }
     
    public void printAllHigherThanNum(int num) {
        for (LogEntry le : records) {
            if (le.getStatusCode() > num) {
                System.out.println(le);
            }
        }
    }
    
    public ArrayList<String> uniqueIpVisitsOnDay(String someday) {
        ArrayList<String> uniqueIps = new ArrayList<String>();
        for (LogEntry le : records) {
            String ip = le.getIpAddress();
            String date = le.getAccessTime().toString().substring(4, 10);
            if (! uniqueIps.contains(ip) && date.contains(someday)) {
                uniqueIps.add(ip);
            }
        }
        return uniqueIps;
    }
    
    public int countUniqueIpsInRange(int low, int high) {
        ArrayList<String> uniqueIps = new ArrayList<String>();
        for (LogEntry le : records) {
            String ip = le.getIpAddress();
            int status = le.getStatusCode();
            if (! uniqueIps.contains(ip) && status >= low && status <= high) {
                uniqueIps.add(ip);
            }
        }
        return uniqueIps.size();
    }
    
    public HashMap<String, Integer> countVisitsperIp() {
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (LogEntry le : records) {
            String ip = le.getIpAddress();
            if (! counts.containsKey(ip)) {
                counts.put(ip, 1);
            }
            else {
                counts.put(ip, counts.get(ip)+1);
            }
        }
        return counts;
    }
    
    public int mostNumberVisitsperIp(HashMap<String, Integer> counts) {
        int currentHighestCount = 0;
        for (String key : counts.keySet()) {
            if (counts.get(key) > currentHighestCount) {
                currentHighestCount = counts.get(key);
            }
        }
        return currentHighestCount;
    }
    
    public ArrayList<String> ipsMostVisits(HashMap<String,Integer> counts) {
        ArrayList<String> ips = new ArrayList<String>();
        int mostVisits = mostNumberVisitsperIp(counts);
        for (String key : counts.keySet()) {
            if (counts.get(key) == mostVisits) {
                ips.add(key);
            }
        }
        return ips;
    }
    
    public HashMap<String, ArrayList<String>> ipsForDays() {
        HashMap <String, ArrayList<String>> ipsperDay = new HashMap<String, ArrayList<String>>();
        for (LogEntry le : records) {
            String day = le.getAccessTime().toString().substring(4,10);
            if (! ipsperDay.containsKey(day)) {
                ArrayList<String> ips = new ArrayList<String>();
                ips.add(le.getIpAddress());
                ipsperDay.put(day, ips);
            }
            else {
                ArrayList<String> ips = ipsperDay.get(day);
                ips.add(le.getIpAddress());
                ipsperDay.put(day, ips);
            }
        }
        return ipsperDay;
    }
    
    public String dayWithMostIpVisits(HashMap<String, ArrayList<String>> ipsperDay) {
        int count = 0;
        String day = "";
        for (String key : ipsperDay.keySet()) {
            if (ipsperDay.get(key).size() > count) {
                count = ipsperDay.get(key).size();
                day = key;
            }
        }
        return day;
    }
    
    public ArrayList<String> ipsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> ipsperDay, String day) {
        ArrayList<String> ipsOnThatDay = new ArrayList<String>();
        for (String key : ipsperDay.keySet()) {
            if (key.equals(day)) {
                ipsOnThatDay = ipsperDay.get(key);
                break;
            }
        }
        HashMap<String, Integer> counts = new HashMap<String, Integer>();
        for (String ip : ipsOnThatDay) {
            if (! counts.containsKey(ip)) {
                counts.put(ip, 1);
            }
            else {
                counts.put(ip, counts.get(ip)+1);
            }
        }
        ArrayList<String> ipsMostVisits = ipsMostVisits(counts);
        return ipsMostVisits;
    }
}
