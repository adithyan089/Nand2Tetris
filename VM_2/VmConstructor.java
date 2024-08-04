package VM_2;
public class VmConstructor {
    private static int labelCount = 0;
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

    private static String pushSegmentPointer(String segment) {
        return "@" + segment + "\n" +
               "D=M\n" +
               "@SP\n" +
               "A=M\n" +
               "M=D\n" +
               "@SP\n" +
               "M=M+1\n";
    }

    public static String label(String contents) {
        String[] parts = contents.split(" ");
        return "(" + parts[1] + ")";
    }

    public static String gotoLabel(String contents) {
        String[] parts = contents.split(" ");
        return "@" + parts[1] + "\n0;JMP";
    }

    public static String ifGoto(String contents) {
        String[] parts = contents.split(" ");
        return "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@" + parts[1] + "\n" +
                "D;JNE";
    }

    public static String function(String functionName, String numLocals) {
        StringBuilder sb = new StringBuilder();
        sb.append("(").append(functionName).append(")\n");
        int n = Integer.parseInt(numLocals);
        for (int i = 0; i < n; i++) {
            sb.append(pushconstant("0")).append("\n");
        }
        return sb.toString();
    }

    public static String call(String functionName, String numArgs) {
        String returnLabel = "RETURN_LABEL" + labelCount++;
        return "@" + returnLabel + "\n" +
               "D=A\n" +
               "@SP\n" +
               "A=M\n" +
               "M=D\n" +
               "@SP\n" +
               "M=M+1\n" +
               pushSegmentPointer("LCL") +
               pushSegmentPointer("ARG") +
               pushSegmentPointer("THIS") +
               pushSegmentPointer("THAT") +
               "@SP\n" +
               "D=M\n" +
               "@5\n" +
               "D=D-A\n" +
               "@" + numArgs + "\n" +
               "D=D-A\n" +
               "@ARG\n" +
               "M=D\n" +
               "@SP\n" +
               "D=M\n" +
               "@LCL\n" +
               "M=D\n" +
               "@" + functionName + "\n" +
               "0;JMP\n" +
               "(" + returnLabel + ")";
    }
    public static String returnFunction() {
        return "@LCL\n" +
                "D=M\n" +
                "@R13\n" +
                "M=D\n" +  // R13 = LCL
                "@5\n" +
                "D=A\n" +
                "@R13\n" +
                "A=M-D\n" +
                "D=M\n" +
                "@R14\n" +
                "M=D\n" +  // R14 = return address
                "@SP\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@ARG\n" +
                "A=M\n" +
                "M=D\n" +  // *ARG = pop()
                "@ARG\n" +
                "D=M+1\n" +
                "@SP\n" +
                "M=D\n" +  // SP = ARG + 1
                "@R13\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@THAT\n" +
                "M=D\n" +  // THAT = *(LCL-1)
                "@R13\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@THIS\n" +
                "M=D\n" +  // THIS = *(LCL-2)
                "@R13\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@ARG\n" +
                "M=D\n" +  // ARG = *(LCL-3)
                "@R13\n" +
                "AM=M-1\n" +
                "D=M\n" +
                "@LCL\n" +
                "M=D\n" +  // LCL = *(LCL-4)
                "@R14\n" +
                "A=M\n" +
                "0;JMP";  // goto return address
    }
    

}