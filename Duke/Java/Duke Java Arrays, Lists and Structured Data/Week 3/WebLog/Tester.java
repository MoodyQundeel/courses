import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        // complete method
        LogAnalyzer analyzer = new LogAnalyzer();
        analyzer.readFile("short-test_log.txt");
        analyzer.printAll();
    }
    
    public void testUniqueIp() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log.txt");
        System.out.println("Number of Uniques Ips: " + la.countUniqueIps());
    }
    
    public void testHigherThanNum() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log.txt");
        la.printAllHigherThanNum(400);
    }
    
    public void testUniqueIpVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log.txt");
        String day = "Mar 24";
        System.out.println("Ips on " + day + ": " + la.uniqueIpVisitsOnDay(day).size());
    }
    
    public void testUniqueIpInRange() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log.txt");
        int low = 200;
        int high = 299;
        System.out.println("Number of Uniques Ips in range " + low + ":" + high + " : " + la.countUniqueIpsInRange(low, high));
    }
    
    public void testCountVisitsperIp() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog2-short_log");
        System.out.println(la.countVisitsperIp());
    }
    
    public void testMostNumberVisitsperIps() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println(la.mostNumberVisitsperIp(la.countVisitsperIp()));
    }
    
    public void testIpsMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println(la.ipsMostVisits(la.countVisitsperIp()));
    }
    
    public void testIpsForDays() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println(la.ipsForDays());
    }
    
    public void testDayWithMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println(la.dayWithMostIpVisits(la.ipsForDays()));
    }
    
    public void testIpsWithMostVistsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        System.out.println(la.ipsWithMostVisitsOnDay(la.ipsForDays(), "Mar 17"));
    }
}
