public class Part1 {
    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        int stopIndex = dna.indexOf(stopCodon, startIndex);
        if (stopIndex == -1) {
            return dna.length();
        }
        int diff = stopIndex - startIndex;
        if ((diff % 3) == 0) {
            return stopIndex;
        }
        return dna.length();
    }
    
    public void testFindStopCodon() {
        String dna;
        dna = "xxxyyyzzzTAAxxxyyyzzz";
        System.out.println("stop codon index = " + findStopCodon(dna, 0, "TAA"));
        dna = "xxxyyyzzzxxxyyyzzz";
        System.out.println("stop codon index = " + findStopCodon(dna, 0, "TAA"));
        dna = "xxxyyyzzTAAxxxyyyzzz";
        System.out.println("stop codon index = " + findStopCodon(dna, 0, "TAA"));
    }
    
    public String findGene(String dna) {
        int startIndex = dna.indexOf("ATG");
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
    public void testFindGene() {
        String dna;
        dna = "xxxyyyATGxxxyyyTAAxxxTAG";
        System.out.println(findGene(dna));
        dna = "xxxyyyATGxxxyyTAAxxxxTAG";
        System.out.println(findGene(dna));
        dna = "xxxyyyxxxyyxxx";
        System.out.println(findGene(dna));
    }
    
    public void printAllGenes(String dna) {
        int startIndex = dna.indexOf("ATG");
        String gene = "";
        while (true) {
            gene = findGene(dna.substring(startIndex, dna.length()));
            if (gene == "") break;
            System.out.println(gene);
            startIndex = dna.indexOf(gene, startIndex) + gene.length();
        }
    }
    
    public void testPrintAllGenes() {
        String dna;
        dna = "ATGATCTAATTTATGCTGCAACGGTGAAGA";
        printAllGenes(dna);
    }
}
