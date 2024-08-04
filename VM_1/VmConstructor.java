

public class VmConstructor {
    public static String pushStatic(String i) {
        return "@Foo."+i+"\n" +
                "D=M\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public  static String pushLCL(String i){
        return "@LCL\n" +
                "D=M\n" +
                "@"+i + "\n" +
                "A=D+A\n" +
                "D=M\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String pushconstant(String i){
        return "@"+i+"\n" +
                "D=A\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String pushARG(String i){
        return "@ARG\n" +
                "D=M\n" +
                "@"+i+"\n" +
                "A=D+A\n" +
                "D=M\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String pushThat(String i){
        return "@THAT\n" +
                "D=M\n" +
                "@"+i+"\n" +
                "A=D+A\n" +
                "D=M\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String pushThis(String i){
        return "@THIS\n" +
                "D=M\n" +
                "@"+i+"\n" +
                "A=D+A\n" +
                "D=M\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String pushtemp(String i) {
        return "@5\n" +
                "D=A\n" +
                "@" + i + "\n" +
                "A=D+A\n" +
                "D=M\n" +
                "@SP\n" +
                "A=M\n" +
                "M=D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String pushpointer(String i){
        return " ";
    }
    public static String popLCL(String i) {
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@LCL\n" +
                "A=M\n" +
                "A=A+"+i+"\n" +
                "M=D";
    }

    public static String popARG(String i) {
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@ARG\n" +
                "A=M+"+i+"\n" +
                "M=D";
    }
    public static String popThis(String i) {
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@THIS\n" +
                "A=M+"+i+"\n" +
                "M=D";
    }

    public static String popThat(String i) {
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@THAT\n" +
                "A=M+"+i+"\n" +
                "M=D";
    }

    public static String popPointer(String i) {
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@" + (i.equals("0") ? "THIS" : "THAT") + "\n" +
                "M=D";
    }

    public static String popStatic(String i) {
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@Foo." + i + "\n" +
                "M=D";
    }

    public static String popTemp(String i) {
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@R5\n" +
                "A=M+" + i + "\n" +
                "M=D";
    }
    public static String add(){
        return "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "M=M+D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String neg() {
        return "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "M=-M\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String sub() {
        return "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "M=M-D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String eq( ) {
        return "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@SP\n" +
                "AM=M-1\n" +
                "D=M-D\n" +
                "@TRUE\n" +
                "D;JEQ\n" +
                "@SP\n" +
                "A=M\n" +
                "M=0\n" +
                "@CONTINUE\n" +
                "0;JMP\n" +
                "(TRUE)\n" +
                "@SP\n" +
                "A=M\n" +
                "M=-1\n" +
                "(CONTINUE)\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String not() {
        return "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "M=!M\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String or() {
        return "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "M=M|D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String and() {
        return "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "M=M&D\n" +
                "@SP\n" +
                "M=M+1";
    }
    public static String lt() {
        return "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@SP\n" +
                "AM=M-1\n" +
                "D=M-D\n" +
                "@TRUE\n" +
                "D;JLT\n" +
                "@SP\n" +
                "A=M\n" +
                "M=0\n" +
                "@CONTINUE\n" +
                "0;JMP\n" +
                "(TRUE)\n" +
                "@SP\n" +
                "A=M\n" +
                "M=-1\n" +
                "(CONTINUE)\n" +
                "@SP\n" +
                "M=M";
    }
    public static String gt(){
        return "@SP\n" +
                "M=M-1\n" +
                "A=M\n" +
                "D=M\n" +
                "@SP\n" +
                "AM=M-1\n" +
                "D=M-D\n" +
                "@TRUE\n" +
                "D;JGT\n" +
                "@SP\n" +
                "A=M\n" +
                "M=0\n" +
                "@CONTINUE\n" +
                "0;JMP\n" +
                "(TRUE)\n" +
                "@SP\n" +
                "A=M\n" +
                "M=-1\n" +
                "(CONTINUE)\n" +
                "@SP\n" +
                "M=M+1";
    }

}
