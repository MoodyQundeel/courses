public class Part1 {
    public String findSimpleGene(String dna) {
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1) {
            return "";
        }
        int stopIndex = dna.indexOf("TAA", startIndex + 3);
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
        String dna = "TCATAATAATAG";
        System.out.println(dna);
        System.out.println("gene = " + findSimpleGene(dna));
        dna = "TCAATGTGATAG";
        System.out.println(dna);
        System.out.println("gene = " + findSimpleGene(dna));;
        dna = "TCAATGTCATAATAG";
        System.out.println(dna);
        System.out.println("gene = " + findSimpleGene(dna));
        dna = "TCAATGTCTAATAG";
        System.out.println(dna);
        System.out.println("gene = " + findSimpleGene(dna));
        dna = "TCATGAATA";
        System.out.println(dna);
        System.out.println("gene = " + findSimpleGene(dna));
    }
}
