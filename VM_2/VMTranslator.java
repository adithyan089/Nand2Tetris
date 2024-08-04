package VM_2;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;


public class VMTranslator {
    public static void main(String[] args) {
        ArrayList<String> asm = new ArrayList<>();
        String filename = "D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\LABSHEET2\\VM_2\\VM_Trial.vm";
        try (BufferedReader read = new BufferedReader(new FileReader(filename))) {
            String contents;
            while ((contents = read.readLine()) != null) {
                contents = contents.trim(); // Trim the contents
                if (contents.startsWith("//") || contents.isEmpty()) {
                    continue;
                } else {
                    if (contents.startsWith("push")) {
                        List<String> split_contents = split(contents);
                        String memorysegment = split_contents.get(1);
                        String i = split_contents.get(2);
                        asm.add("//" + contents);
                        switch (memorysegment) {
                            case "constant":asm.add(VmConstructor.pushconstant(i));break;
                            case "local":asm.add(VmConstructor.pushLCL(i)); break;
                            case "argument":asm.add(VmConstructor.pushARG(i)); break;
                            case "static":asm.add(VmConstructor.pushStatic(i)); break;
                            case "this":asm.add(VmConstructor.pushThis(i)); break;
                            case "that":asm.add(VmConstructor.pushThat(i)); break;
                            case "pointer":asm.add(VmConstructor.pushpointer(i)); break;
                            case "temp":asm.add(VmConstructor.pushtemp(i)); break;
                            default: System.out.println("Error in line " + contents);System.exit(-1);
                        }
                       
                    } else if (contents.startsWith("pop")) {
                        List<String> split_contents = split(contents);
                        String memorysegment = split_contents.get(1);
                        String i = split_contents.get(2);
                        asm.add("//" + contents);
                        switch (memorysegment) {
                            case "local":asm.add(VmConstructor.popLCL(i)); break;
                            case "argument":asm.add(VmConstructor.popARG(i)); break;
                            case "static":asm.add(VmConstructor.popStatic(i)); break;
                            case "this":asm.add(VmConstructor.popThis(i)); break;
                            case "that":asm.add(VmConstructor.popThat(i)); break;
                            case "pointer":asm.add(VmConstructor.popPointer(i)); break;
                            case "temp":asm.add(VmConstructor.popTemp(i)); break;
                            default: System.out.println("Error in line " + contents);System.exit(-1);
                        }
                    }else if (contents.startsWith("function")) {
                        List<String> split_contents = split(contents);
                        String functionName = split_contents.get(1);
                        String numLocals = split_contents.get(2);
                        asm.add("// " + contents);
                        asm.add(VmConstructor.function(functionName, numLocals));
                    } else if (contents.startsWith("call")) {
                        List<String> split_contents = split(contents);
                        String functionName = split_contents.get(1);
                        String numArgs = split_contents.get(2);
                        asm.add("// " + contents);
                        asm.add(VmConstructor.call(functionName, numArgs));
                    } else if (contents.startsWith("return")) {
                        asm.add("// " + contents);
                        asm.add(VmConstructor.returnFunction());
                    }else {
                        switch (contents) {
                            case "add":asm.add("//" + contents);asm.add(VmConstructor.add());break;
                            case "neg":asm.add("//" + contents);asm.add(VmConstructor.neg());break;
                            case "sub":asm.add("//" + contents);asm.add(VmConstructor.sub());break;
                            case "eq":asm.add("//" + contents);asm.add(VmConstructor.eq());break;
                            case "not":asm.add("//" + contents);asm.add(VmConstructor.not());break;
                            case "or":asm.add("//" + contents);asm.add(VmConstructor.or());break;
                            case "and":asm.add("//" + contents);asm.add(VmConstructor.and());break;
                            case "lt":asm.add("//" + contents);asm.add(VmConstructor.lt());break;
                            case "gt":asm.add("//" + contents);asm.add(VmConstructor.gt());break;
                        }
                    }
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\LABSHEET2\\VM_2\\VM_Output.asm"))) {
                for (String print : asm) {
                    if (print == null) {
                        continue;
                    }
                    writer.write(print);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                System.out.println("Translation Completed!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> split(String i) {
        return Arrays.asList(i.split("\\s+"));
    }
}