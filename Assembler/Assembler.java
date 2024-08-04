package Assembler;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Assembler {
    private static String translateAinstruction(String d, HashMap<String, Integer> sym, HashMap<String, Integer> lab) {
        String[] parts = d.split("@");
        String tail = parts[1];
        if (lab.containsKey(tail)) {
            int ans = lab.get(tail);
            return String.format("%16s", Integer.toBinaryString(ans)).replace(' ', '0');
        }
        int ans = sym.get(tail);
        return String.format("%16s", Integer.toBinaryString(ans)).replace(' ', '0');
    }

    private static String translateCinstruction(String d) {
        HashMap<String, String> jmp = new HashMap<>();
        jmp.put("", "000");
        jmp.put("JGT", "001");
        jmp.put("JEQ", "010");
        jmp.put("JGE", "011");
        jmp.put("JLT", "100");
        jmp.put("JNE", "101");
        jmp.put("JLE", "110");
        jmp.put("JMP", "111");

        HashMap<String, String> dest = new HashMap<>();
        dest.put("", "000");
        dest.put("M", "001");
        dest.put("D", "010");
        dest.put("MD", "011");
        dest.put("A", "100");
        dest.put("AM", "101");
        dest.put("AD", "110");
        dest.put("AMD", "111");

        HashMap<String, String> comp = new HashMap<>();
        comp.put("0", "0101010");
        comp.put("1", "0111111");
        comp.put("-1", "0111010");
        comp.put("D", "0001100");
        comp.put("A", "0110000");
        comp.put("!D", "0001101");
        comp.put("!A", "0110001");
        comp.put("-D", "0001111");
        comp.put("-A", "0110011");
        comp.put("D+1", "0011111");
        comp.put("A+1", "0110111");
        comp.put("D-1", "0001110");
        comp.put("A-1", "0110010");
        comp.put("D+A", "0000010");
        comp.put("D-A", "0010011");
        comp.put("A-D", "0000111");
        comp.put("D&A", "0000000");
        comp.put("D|A", "0010101");
        comp.put("M", "1110000");
        comp.put("!M", "1110001");
        comp.put("-M", "1110011");
        comp.put("M+1", "1110111");
        comp.put("M-1", "1110010");
        comp.put("D+M", "1000010");
        comp.put("D-M", "1010011");
        comp.put("M-D", "1000111");
        comp.put("D&M", "1000000");
        comp.put("D|M", "1010101");

        String comp2 = d;
        String dest2 = "";
        String jmp2 = "";
        String sepe = "";

        if (d.contains(";")) {
            String[] parts = d.split(";");
            comp2 = parts[0];
            jmp2 = parts[1];
        } else if (d.contains("=")) {
            String[] parts = d.split("=");
            dest2 = parts[0];
            comp2 = parts[1];
        }

        comp2 = comp2.trim();
        dest2 = dest2.trim();
        jmp2 = jmp2.trim();

        String compout = comp.get(comp2);
        String destout = dest.get(dest2);
        String jmpout = jmp.get(jmp2);
        return "111" + compout + destout + jmpout;
    }

    public static void main(String[] args) {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader("D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\Assembler\\test.asm"));
            writer = new BufferedWriter(new FileWriter("D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\Assembler\\outp.txt"));

            String line;
            HashMap<String, Integer> symbols = new HashMap<>();
            HashMap<String, Integer> labels = new HashMap<>();
            int nextSymbolAddress = 16;
            int instructionCounter = 0;

            while ((line = reader.readLine()) != null) {
                line = line.trim().split("//")[0].trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("(")) {
                    String label = line.substring(1, line.length() - 1);
                    labels.put(label, instructionCounter);
                } else {
                    instructionCounter++;
                }
            }

            reader.close();
            reader = new BufferedReader(new FileReader("D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\Assembler\\test.asm"));

            while ((line = reader.readLine()) != null) {
                line = line.trim().split("//")[0].trim();
                if (line.isEmpty() || line.startsWith("(")) continue;

                if (line.startsWith("@")) {
                    String symbol = line.substring(1);
                    if (!labels.containsKey(symbol) && !symbols.containsKey(symbol)) {
                        symbols.put(symbol, nextSymbolAddress++);
                    }
                }
            }

            reader.close();
            reader = new BufferedReader(new FileReader("D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\Assembler\\test.asm"));

            while ((line = reader.readLine()) != null) {
                line = line.trim().split("//")[0].trim();
                if (line.isEmpty()) continue;

                if (line.startsWith("@")) {
                    writer.write(translateAinstruction(line, symbols, labels) + "\n");
                } else if (!line.startsWith("(")) {
                    writer.write(translateCinstruction(line) + "\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
