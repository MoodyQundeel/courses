import edu.duke.*;
import org.apache.commons.csv.*;

public class Main {
    public String countryInfo(CSVParser parser, String country) {
        for (CSVRecord record : parser) {
            if (record.get("Country").contains(country)) {
                return country + ": " + record.get("Exports") + ": " + record.get("Value (dollars)");
            }
        }
        return "NOT FOUND";
    }
    
    public void listExportersTwoProducts(CSVParser parser, String exportItem1, String exportItem2) {
        int count = 0;
        System.out.println("countries that export: " + exportItem1 + " and " + exportItem2);
        for (CSVRecord record : parser) {
            String exports = record.get("Exports");
            if (exports.contains(exportItem1) && exports.contains(exportItem2)) {
                count++;
                System.out.println(count + "-" + record.get("Country"));
            }
        }
    }
    
    public int numberOfExporters(CSVParser parser, String exportItem) {
        int count = 0;
        for (CSVRecord record : parser) {
            if (record.get("Exports").contains(exportItem)) {
                count++;
            }
        }
        return count;
    }
    
    public void bigExporters(CSVParser parser, String amount) {
        for (CSVRecord record : parser) {
            if (amount.length() < record.get("Value (dollars)").length()) {
                System.out.println(record.get("Country") + " " + record.get("Value (dollars)"));
            }
        }
    }
    
    public void testing() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        String returnedValue = countryInfo(parser, "Nauru");
        System.out.println(returnedValue);
        parser = fr.getCSVParser();
        listExportersTwoProducts(parser, "fish", "nuts");
        parser = fr.getCSVParser();
        int numberOfExporters = numberOfExporters(parser, "sugar");
        System.out.println("number of exporters: " + numberOfExporters);
        parser = fr.getCSVParser();
        bigExporters(parser, "$999,999,999,999");
    }
}
