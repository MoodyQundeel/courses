import java.util.HashMap;

public class Code {

    public String translateA(int value) {
        StringBuilder output = new StringBuilder("0" + Integer.toBinaryString(value));
        while (output.length() != 16) {
            output.insert(0, "0");
        }
        return output.toString();
    }

    public String translateC(HashMap<String, String> code) {

        StringBuilder output = new StringBuilder();
        output.append("111");

        String comp = code.get("comp");

        if (comp.contains("M")) {
            output.append("1");
            comp = comp.replace('M', 'A');
        }
        else output.append("0");

        switch (comp) {
            case "0" -> output.append("101010");
            case "1" -> output.append("111111");
            case "-1" -> output.append("111010");
            case "D" -> output.append("001100");
            case "A" -> output.append("110000");
            case "!D" -> output.append("001101");
            case "!A" -> output.append("110001");
            case "-D" -> output.append("001111");
            case "-A" -> output.append("110011");
            case "D+1" -> output.append("011111");
            case "A+1" -> output.append("110111");
            case "D-1" -> output.append("001110");
            case "A-1" -> output.append("110010");
            case "D+A" -> output.append("000010");
            case "D-A" -> output.append("010011");
            case "A-D" -> output.append("000111");
            case "D&A" -> output.append("000000");
            case "D|A" -> output.append("010101");
        }

        if (code.get("dest") == null) {
            output.append("000");
        }
        else {
            switch(code.get("dest")) {
                case "M" ->  output.append("001");
                case "D" ->  output.append("010");
                case "MD" ->  output.append("011");
                case "A" ->  output.append("100");
                case "AM" ->  output.append("101");
                case "AD" ->  output.append("110");
                case "ADM" ->  output.append("111");
            }
        }

        if (code.get("jmp") == null) {
            output.append("000");
        }
        else {
            switch(code.get("jmp")) {
                case "JGT" ->  output.append("001");
                case "JEQ" ->  output.append("010");
                case "JGE" ->  output.append("011");
                case "JLT" ->  output.append("100");
                case "JNE" ->  output.append("101");
                case "JLE" ->  output.append("110");
                case "JMP" ->  output.append("111");
            }
        }

        return output.toString();
    }
}
