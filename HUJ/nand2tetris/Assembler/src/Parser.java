import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Parser {

    public HashMap<String, String> parseC(String line) {
        HashMap<String, String> output = new HashMap<>();

        if (line.contains("=")) {
            output.put("dest",line.substring(0, line.indexOf('=')));
            output.put("comp", line.substring(line.indexOf('=')+1));
            output.put("jmp", null);
            return output;
        }
        else if (line.contains(";")) {
            output.put("jmp", line.substring(line.indexOf(';')+1));
            output.put("comp", line.substring(0, line.indexOf(';')));
            output.put("dest", null);
            return output;
        }
        else return null;
    }

    public void parse(File i, File o) throws IOException {
        String currentLine;
        int commandNumber = 0;
        SymbolTable symbolTable = new SymbolTable();
        Code c = new Code();
        Scanner s = new Scanner(i);
        FileWriter w = new FileWriter(o);

        while(s.hasNextLine()) {
            currentLine = s.nextLine();
            currentLine = currentLine.replaceAll("\\s", "");
            if (currentLine.contains("//")) {
                currentLine = currentLine.substring(0, currentLine.indexOf("//"));
            }
            if (currentLine.length() != 0) {
                if (currentLine.charAt(0) == '(') {
                    String label = currentLine.substring(currentLine.indexOf('(')+1, currentLine.indexOf(')'));
                    symbolTable.checkSymbol(label, true, commandNumber);
                }
                else {
                    commandNumber++;
                }
            }
        }

        s = new Scanner(i);

        while (s.hasNextLine()) {
            currentLine = s.nextLine();
            currentLine = currentLine.replaceAll("\\s", "");
            if (currentLine.contains("//")) {
                currentLine = currentLine.substring(0, currentLine.indexOf("//"));
            }
            if (currentLine.length() != 0 && currentLine.charAt(0) != '(') {
                if (currentLine.charAt(0) == '@') {
                    try {
                        int value = Integer.parseInt(currentLine.substring(1));
                        w.write(c.translateA(value)+"\n");
                    } catch (NumberFormatException e) {
                        String variable = currentLine.substring(1);
                        int address = Integer.parseInt(symbolTable.checkSymbol(variable, false, 0));
                        w.write(c.translateA(address)+"\n");
                    }

                } else {
                    w.write(c.translateC(parseC(currentLine))+"\n");
                }
            }
        }
        w.close();
    }
}
