public class Part3 {
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
    
    public int countGenes(String dna) {
        int count = 0;
        int startIndex = dna.indexOf("ATG");
        String gene = "";
        while (true) {
            gene = findGene(dna.substring(startIndex, dna.length()));
            if (gene == "") break;
            System.out.println(gene);
            count++;
            startIndex = dna.indexOf(gene, startIndex) + gene.length();
        }
        return count;
    }
    
    public void testCountGenes() {
        String dna = "xxxATGyyyTAAxxxATGxxTACxxxxATGxxxTAG";
        System.out.println(countGenes(dna));
        dna = "xxxATGxxxTAAxxxATGxxxTAGxxxATGxxxTGA";
        System.out.println(countGenes(dna));
    }
    
}
