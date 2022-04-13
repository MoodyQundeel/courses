import java.util.HashMap;

public class SymbolTable {
    public HashMap<String, String> symbolTable = new HashMap<>();
    private int variableNumber = 0;
    public SymbolTable() {
        symbolTable.put("R0", "0");
        symbolTable.put("R1", "1");
        symbolTable.put("R2", "2");
        symbolTable.put("R3", "3");
        symbolTable.put("R4", "4");
        symbolTable.put("R5", "5");
        symbolTable.put("R6", "6");
        symbolTable.put("R7", "7");
        symbolTable.put("R8", "8");
        symbolTable.put("R9", "9");
        symbolTable.put("R10", "10");
        symbolTable.put("R11", "11");
        symbolTable.put("R12", "12");
        symbolTable.put("R13", "13");
        symbolTable.put("R14", "14");
        symbolTable.put("R15", "15");
        symbolTable.put("SP", "0");
        symbolTable.put("LCL", "1");
        symbolTable.put("ARG", "2");
        symbolTable.put("THIS", "3");
        symbolTable.put("THAT", "4");
        symbolTable.put("SCREEN", "16384");
        symbolTable.put("KBD", "24576");
    }

    public String checkSymbol(String symbol, boolean isLabel, int jumpAddress) {
        String address;
        if (symbolTable.containsKey(symbol)) {
            return symbolTable.get(symbol);
        }
        if (isLabel) {
            address = Integer.toString(jumpAddress);
            symbolTable.put(symbol, address);
        }
        else {
            address = Integer.toString(variableNumber + 16);
            symbolTable.put(symbol, address);
            variableNumber++;
        }
        return address;
    }
}
