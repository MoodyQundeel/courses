import java.io.*;
import edu.duke.*;
import org.apache.commons.csv.*;

public class Main {
    public void totalBirths(FileResource fr) {
        int totalBirths = 0;
        int totalBoys = 0;
        int totalGirls = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            totalBirths += numBorn;
            if (rec.get(1).equals("M")) {
                totalBoys += numBorn;
            }
            else {
                totalGirls += numBorn;
            }
        }
        System.out.println("total boys = " + totalBoys
                            + " total girls = " + totalGirls
                            + " total births = " + totalBirths);
    }
    
    public int getRankYear(int year, String name, String gender) {
        String fileName = "us_babynames_by_year/yob" + year + ".csv";
        FileResource fr = new FileResource(fileName);
        int rank = 1;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                return rank;
            }
            if (rec.get(1).equals(gender)) {
                rank++;
            }
        }
        return -1;
    }
    
    public int getRankFile(String name, String gender, File f) { 
        FileResource fr = new FileResource(f);
        int rank = 1;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                return rank;
            }
            if (rec.get(1).equals(gender)) {
                rank++;
            }
        }
        return -1;
    }
    
    public String getName(int year, int rank, String gender) {
        String fileName = "us_babynames_by_year/yob" + year + ".csv";
        FileResource fr = new FileResource(fileName);
        int count = 1;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rank == count) {
                return rec.get(0);
            }
            if (rec.get(1).equals(gender)) {
                count++;
            }
        }
        return "NO NAME";
    }
    
    public String whatIsNameInYear(String name, int year, int newYear, String gender) {
        int rank = getRankYear(year, name, gender);
        String newName = getName(newYear, rank, gender);
        return newName;
    }
    
    public int yearOfHighestRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int currentYearWithHighestRank = 0;
        int currentHighestRank = 0;
        for (File f : dr.selectedFiles()) {
            if (getRankFile(name, gender, f) > currentHighestRank) {
                currentYearWithHighestRank = Integer.parseInt(f.getName().substring(3, 7));
            }
        }
        if (currentYearWithHighestRank == 0) {
            return -1;
        }
        else {
            return currentYearWithHighestRank;
        }
    }
    
    public double getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int count = 0;
        int total = 0;
        for (File f : dr.selectedFiles()) {
            if (getRankFile(name, gender, f) == -1) {
                return -1.0;
            }
            total += getRankFile(name, gender, f);
            count++;
        }
        return (double) total / count;
    }
    
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        String fileName = "us_babynames_test/yob2012short.csv";
        FileResource fr = new FileResource(fileName);
        int totalBirths = 0;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(0).equals(name) && rec.get(1).equals(gender)) {
                return totalBirths;
            }
            if (rec.get(1).equals(gender)) {
                totalBirths += Integer.parseInt(rec.get(2));
            }
        }
        return totalBirths;
    }
    
    public void test() {
        System.out.println("total births before name were " + getTotalBirthsRankedHigher(2012, "Ethan", "M"));
    }
}
