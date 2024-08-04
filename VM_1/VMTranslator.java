import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class VMTranslator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the path of the input VM file: ");
        String inputFilePath = scanner.nextLine();
        
        System.out.print("Enter the path of the output ASM file: ");
        String outputFilePath = scanner.nextLine();
        
        ArrayList<String> asmCode = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader("D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\LABSHEET1\\"+inputFilePath))) {
            String contents;
            while ((contents = reader.readLine()) != null) {
                contents = contents.trim();
                
                // Skip comments and empty lines
                if (contents.startsWith("//") || contents.isEmpty()) {
                    continue;
                } else {
                    if (contents.startsWith("push")) {
                        List<String> splittedContents = split(contents);
                        String memorySegments = splittedContents.get(1);
                        String i = splittedContents.get(2);
                        asmCode.add("// " + contents);
                        
                        switch (memorySegments) {
                            case "static":
                                asmCode.add(VmConstructor.pushStatic(i));
                                break;
                            case "local":
                                asmCode.add(VmConstructor.pushLCL(i));
                                break;
                            case "constant":
                                asmCode.add(VmConstructor.pushconstant(i));
                                break;
                            case "argument":
                                asmCode.add(VmConstructor.pushARG(i));
                                break;
                            case "that":
                                asmCode.add(VmConstructor.pushThat(i));
                                break;
                            case "this":
                                asmCode.add(VmConstructor.pushThis(i));
                                break;
                            case "temp":
                                asmCode.add(VmConstructor.pushtemp(i));
                                break;
                            case "pointer":
                                asmCode.add(VmConstructor.pushpointer(i));
                                break;
                            default:
                                System.out.println("Unknown memory segment: " + memorySegments);
                                break;
                        }
                    } else if (contents.startsWith("pop")) {
                        List<String> splittedContents = split(contents);
                        String memorySegments = splittedContents.get(1);
                        String i = splittedContents.get(2);
                        asmCode.add("// " + contents);
                        
                        switch (memorySegments) {
                            case "static":
                                asmCode.add(VmConstructor.popStatic(i));
                                break;
                            case "local":
                                asmCode.add(VmConstructor.popLCL(i));
                                break;
                            case "argument":
                                asmCode.add(VmConstructor.popARG(i));
                                break;
                            case "that":
                                asmCode.add(VmConstructor.popThat(i));
                                break;
                            case "this":
                                asmCode.add(VmConstructor.popThis(i));
                                break;
                            case "temp":
                                asmCode.add(VmConstructor.popTemp(i));
                                break;
                            case "pointer":
                                asmCode.add(VmConstructor.popPointer(i));
                                break;
                            default:
                                System.out.println("Unknown memory segment: " + memorySegments);
                                break;
                        }
                    } else {
                        switch (contents) {
                            case "add":
                                asmCode.add("// " + contents);
                                asmCode.add(VmConstructor.add());
                                break;
                            case "neg":
                                asmCode.add("// " + contents);
                                asmCode.add(VmConstructor.neg());
                                break;
                            case "sub":
                                asmCode.add("// " + contents);
                                asmCode.add(VmConstructor.sub());
                                break;
                            case "eq":
                                asmCode.add("// " + contents);
                                asmCode.add(VmConstructor.eq());
                                break;
                            case "not":
                                asmCode.add("// " + contents);
                                asmCode.add(VmConstructor.not());
                                break;
                            case "or":
                                asmCode.add("// " + contents);
                                asmCode.add(VmConstructor.or());
                                break;
                            case "and":
                                asmCode.add("// " + contents);
                                asmCode.add(VmConstructor.and());
                                break;
                            case "lt":
                                asmCode.add("// " + contents);
                                asmCode.add(VmConstructor.lt());
                                break;
                            case "gt":
                                asmCode.add("// " + contents);
                                asmCode.add(VmConstructor.gt());
                                break;
                            default:
                                System.out.println("Unknown command: " + contents);
                                break;
                        }
                    }
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\LABSHEET1\\"+outputFilePath))) {
                for (String line : asmCode) {
                    if (line == null) {
                        continue;
                    }
                    writer.write(line);
                    writer.newLine();
                }
                System.out.println("Translation complete. Output written to: " + outputFilePath);
            } catch (IOException e) {
                e.printStackTrace();  
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        scanner.close();
    }
    // Splits the string into a list of strings
    public static List<String> split(String input) {
        return Arrays.asList(input.split("\\s+"));
    }
}
