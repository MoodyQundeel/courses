public class Part2 {
    public String findSimpleGene(String dna, String startCodon, String stopCodon) {
        if (Character.isLowerCase(dna.charAt(0))) {
            startCodon = startCodon.toLowerCase();
            stopCodon = stopCodon.toLowerCase();
        }
        int startIndex = dna.indexOf(startCodon);
        if (startIndex == -1) {
            return "";
        }
        int stopIndex = dna.indexOf(stopCodon, startIndex + 3);
        if (stopIndex == -1) {
            return "";
        }
        String gene = dna.substring(startIndex, stopIndex + 3);
        if (gene.length() % 3 == 0) {
            return gene;
        }
        else {
            return "";
        }
    }
    
    public void testSimpleGene() {
        String dna;
        String startCodon = "ATG";
        String stopCodon = "TAA";
        dna = "TCATAATAATAG";
        System.out.println(dna);
        System.out.println("gene = " + findSimpleGene(dna, startCodon, stopCodon));
        dna = "TCAATGTGATAG";
        System.out.println(dna);
        System.out.println("gene = " + findSimpleGene(dna, startCodon, stopCodon));
        dna = "TCAATGTCATAATAG";
        System.out.println(dna);
        System.out.println("gene = " + findSimpleGene(dna, startCodon, stopCodon));
        dna = "TCAATGTCTAATAG";
        System.out.println(dna);
        System.out.println("gene = " + findSimpleGene(dna, startCodon, stopCodon));
        dna = "TCATGAATA";
        System.out.println(dna);
        System.out.println("gene = " + findSimpleGene(dna, startCodon, stopCodon));
    }
}
