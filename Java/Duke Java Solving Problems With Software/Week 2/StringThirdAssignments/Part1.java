import edu.duke.*;
public class Part1 {
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        int stopIndex = dna.indexOf(stopCodon, startIndex);
        if (stopIndex == -1) {
            stopIndex = dna.indexOf(stopCodon.toLowerCase(), startIndex);
        }
        int currIndex = stopIndex;
        if (stopIndex == -1) {
            return dna.length();
        }
        int diff = stopIndex - startIndex;
        if (diff % 3 == 0) {
             return stopIndex;   
        }
        while (stopIndex != -1) {
            stopIndex = dna.indexOf(stopCodon, currIndex+1);
            if (stopIndex == -1) {
                stopIndex = dna.indexOf(stopCodon.toLowerCase(), currIndex+1);    
            }
            currIndex = stopIndex;
            diff = stopIndex - startIndex;
            if (diff % 3 == 0) {
                return stopIndex;   
            }
        }
        return dna.length();
    }
    
    public String findGene(String dna) {
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1) {
            startIndex = dna.indexOf("atg");
        }
        if (startIndex == -1) {
            return "";
        }
        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");
        int minIndex = taaIndex;
        if (taaIndex > tagIndex) {
            minIndex = tagIndex;
        }
        if (tagIndex > tgaIndex) {
            minIndex = tgaIndex;
        }
        if (minIndex >= dna.length()) {
            return "";
        }
        String gene = dna.substring(startIndex, minIndex + 3);
        return gene;
    }
    
    public StorageResource getAllGenes(String dna) {
        int count = 0;
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1) {
            startIndex = dna.indexOf("atg");
        }
        StorageResource sr = new StorageResource();
        String gene = "";
        while (true) {
            gene = findGene(dna.substring(startIndex, dna.length()));
            if (gene == "") break;
            count++;
            sr.add(gene);
            startIndex = dna.indexOf(gene, startIndex) + gene.length();
        }
        System.out.println("number of genes = " + count);
        return sr;
    }
    
    public float cgRatio(String dna) {
        int length = dna.length();
        int i = 0;
        int count = 0;
        while (i < dna.length()) {
            char c = dna.charAt(i);
            if (c == 'C' || c == 'G' || c == 'c' || c == 'g') {
                count++;
            }
            i++;
        }
        float ratio = (float)count / dna.length();
        return ratio;
    }
    
    public int countCTG(String dna) {
        int count = 0;
        int i = 0;
        while (i < dna.length()) {
            System.out.println(dna.substring(i, i+3));
            if (dna.indexOf("CGT", i) != -1 || dna.indexOf("cgt", i) != -1) {
                count++;
            }
            i += 3;
        }
        System.out.println("number of CTG = " + count);
        return count;
    }
    
    public void processGenes(StorageResource sr) {
        int countMoreThanNine = 0;
        int countCGRatio = 0;
        int longestLength = 0;
        for (String s : sr.data()) {
            if (s.length() > longestLength) {
                longestLength = s.length();
            }
            if (s.length() > 60) {
                System.out.println(s);
                countMoreThanNine++;
            }
            if (cgRatio(s) > 0.35) {
                System.out.println(s);
                countCGRatio++;
            }
        }
        System.out.println("number of genes longer than 60 = " + countMoreThanNine);
        System.out.println("number of genes with cg ratio more than 0.35 = " + countCGRatio);
        System.out.println("longest gene = " + longestLength);
    }
    
    public void testing() {
        FileResource fr = new FileResource();
        // processGenes(getAllGenes("ATGAGATGATAA"));
        // processGenes(getAllGenes("ATGTAA"));
        // processGenes(getAllGenes("ATGCGGTAG"));
        // processGenes(getAllGenes("ATGTATTATTAA"));
        // processGenes(getAllGenes("ATGCGACGGTAA"));
        // processGenes(getAllGenes("atgcataaaatgtagcaaatgcataaaatgtag"));
        processGenes(getAllGenes(fr.asString()));
    }
}

