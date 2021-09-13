import edu.duke.*;
import org.apache.commons.csv.*;
import java.io.*;

public class Main {
    public CSVRecord getLowerOfTwo(CSVRecord one, CSVRecord two, String column) {
        double columnOne = Double.parseDouble(one.get(column));
        double columnTwo = Double.parseDouble(two.get(column));
        if (columnOne < columnTwo) {
            return one;
        }
        else {
            return two;
        }
    }
    
    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord lowestSoFar = null;
        for (CSVRecord record : parser) {
            if (lowestSoFar == null) {
                lowestSoFar = record;
            }
            else {
                if (Double.parseDouble(record.get("TemperatureF")) > -1000) {
                    lowestSoFar = getLowerOfTwo(lowestSoFar, record, "TemperatureF");
                }
            }
        }
        return lowestSoFar;
    }
    
    public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = coldestHourInFile(parser);
        System.out.println("Coldest hour in file was " + csv.get("TemperatureF"));
    }
    
    public String fileWithLowestTemperature() {
        DirectoryResource dr = new DirectoryResource();
        double currTemp = 1000;
        double lowestTemp = 1000;
        File fileWithLowestTemp = null;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            if (fileWithLowestTemp == null) {
                fileWithLowestTemp = f;
                lowestTemp = Double.parseDouble(coldestHourInFile(parser).get("TemperatureF"));
            }
            else {
                currTemp = Double.parseDouble(coldestHourInFile(parser).get("TemperatureF"));
                if (currTemp < lowestTemp) {
                    fileWithLowestTemp = f;
                    lowestTemp = currTemp;
                }
            }
        }
        FileResource fr = new FileResource(fileWithLowestTemp);
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldestHourInFile = coldestHourInFile(parser);
        System.out.println("Coldest day was in file " + fileWithLowestTemp.getName());
        System.out.println("Coldest temperature on that day was " + coldestHourInFile.get("TemperatureF"));
        System.out.println("All the Temperature on the coldest day were:");
        parser = fr.getCSVParser();
        for (CSVRecord record : parser) {
            System.out.println(record.get("DateUTC") + " " + record.get("TemperatureF"));
        }
        return fileWithLowestTemp.getName();
    }
    
    public void testFileWithLowestTemperature() {
        fileWithLowestTemperature();
    }
    
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowestSoFar = null;
        for (CSVRecord record : parser) {
            if (record.get("Humidity").contains("N/A")) {
                continue;
            }
            else {
                if (lowestSoFar == null) {
                    lowestSoFar = record;
                }
                
                else {
                    lowestSoFar = getLowerOfTwo(lowestSoFar, record, "Humidity");
                }
            }
        }
        return lowestSoFar;
    }
    
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord csv = lowestHumidityInFile(parser);
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public CSVRecord lowestHumidityInManyFiles() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord lowestSoFar = null;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVParser parser = fr.getCSVParser();
            CSVRecord currentHumidity = lowestHumidityInFile(parser);
            if (lowestSoFar == null) {
                lowestSoFar = currentHumidity;
            }
            else {
                lowestSoFar = getLowerOfTwo(lowestSoFar, currentHumidity, "Humidity");
            }
        }
        return lowestSoFar;
    }
    
    public void testLowestHumidityInManyFiles() {
        CSVRecord csv = lowestHumidityInManyFiles();
        System.out.println("Lowest Humidity was " + csv.get("Humidity") + " at " + csv.get("DateUTC"));
    }
    
    public double averageTemperatureInFile(CSVParser parser) {
        int count = 0;
        double totalTemp = 0;
        for (CSVRecord record : parser) { 
            count++;
            totalTemp += Double.parseDouble(record.get("TemperatureF"));
        }
        return totalTemp/count;
    }
    
    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double averageTemp = averageTemperatureInFile(parser);
        System.out.println("Average temperature in file is " + averageTemp);
    }
    
    public double averageTemperatureWithHighHumidityInFile(CSVParser parser, int value) {
        int count = 0;
        double totalTemp = 0;
        for (CSVRecord record : parser) { 
            if (Double.parseDouble(record.get("Humidity")) >= value) {
                count++;
                totalTemp += Double.parseDouble(record.get("TemperatureF"));
            }
        }
        if (count == 0) return 0;
        return totalTemp/count;
    }
    
    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double averageTemp = averageTemperatureWithHighHumidityInFile(parser, 80);;
        if (averageTemp == 0) {
            System.out.println("No temperatures with that humidity");
        }
        else {
            System.out.println("Average Temp when high Humidity is " + averageTemp);
        }
    }
}
