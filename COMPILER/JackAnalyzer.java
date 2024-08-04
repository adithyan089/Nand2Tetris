package COMPILER;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JackAnalyzer {
    public static void main(String[] args) {
        String inputFilePath = "D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\LABSHEET2\\COMPILER\\Syntax_Analyzer.jack";
        String outputFilePath = "D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\LABSHEET2\\COMPILER\\Jack_Ouput.xml";

        try {
            ArrayList<String> tokens = new ArrayList<>();
            File inputFile = new File(inputFilePath);
            if (inputFile.isFile() && inputFile.getName().endsWith(".jack")) {
                parseFile(inputFile, tokens);
                writeTokensToFile(tokens, outputFilePath);
                System.out.println("Output written to: " + outputFilePath);
            } else {
                System.out.println("Invalid input file: " + inputFilePath);
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void parseFile(File inputFile, ArrayList<String> tokens) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        String line;
        StringBuilder fileContent = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            line = line.replaceAll("//.*", "").trim(); // Remove single-line comments
            fileContent.append(line);
        }

        Pattern pattern = Pattern.compile("\"(?:\\\\\"|[^\"])*\"|\\b\\w+\\b|[{}\\[\\].,;()+\\-*/&|<>=~]|\\b(?:class|method|function|constructor|int|boolean|char|void|var|static|field|let|do|if|else|while|return|true|false|null|this)\\b");
        Matcher matcher = pattern.matcher(fileContent.toString());

        while (matcher.find()) {
            String token = matcher.group().trim();
            tokens.add(token);
        }

        reader.close();
    }

    private static void writeTokensToFile(ArrayList<String> tokens, String outputFilePath) throws IOException {
        FileWriter writer = new FileWriter(outputFilePath);
        writer.write("<tokens>\n");
        int i = 0;
        while (i < tokens.size()) {
            String token = tokens.get(i);
            if (token.equals("while")) {
                writer.write("<whileStatement>\n");
                i = writeUntilMatch(tokens, i + 1, writer, "}");
                writer.write("</whileStatement>\n");
            } else if (token.equals("let")) {
                writer.write("<letStatement>\n");
                i = writeUntilMatch(tokens, i + 1, writer, ";");
                writer.write("</letStatement>\n");
            } else {
                writer.write("<" + escapeXml(getTokenType(token)) + "> " + escapeXml(token) + " </" + escapeXml(getTokenType(token)) + ">\n");
                i++;
            }
        }
        writer.write("</tokens>");
        writer.close();
    }

    private static int writeUntilMatch(ArrayList<String> tokens, int startIndex, FileWriter writer, String endToken) throws IOException {
        int i = startIndex;
        while (i < tokens.size() && !tokens.get(i).equals(endToken)) {
            String token = tokens.get(i);
            if (token.equals("while")) {
                writer.write("<whileStatement>\n");
                i = writeUntilMatch(tokens, i + 1, writer, "}");
                writer.write("</whileStatement>\n");
            } else if (token.equals("let")) {
                writer.write("<letStatement>\n");
                i = writeUntilMatch(tokens, i + 1, writer, ";");
                writer.write("</letStatement>\n");
            } else {
                writer.write("<" + escapeXml(getTokenType(token)) + "> " + escapeXml(token) + " </" + escapeXml(getTokenType(token)) + ">\n");
                i++;
            }
        }
        return i;
    }

    private static String getTokenType(String token) {
        if (token.matches("\".*\"")) {
            return "stringConstant";
        } else if (token.matches("\\b\\d+\\b")) {
            return "integerConstant";
        } else if (token.matches("\\b(?:class|method|function|constructor|int|boolean|char|void|var|static|field|let|do|if|else|while|return|true|false|null|this)\\b")) {
            return "keyword";
        } else if (token.matches("[{}\\[\\].,;()+\\-*/&|<>=~]")) {
            return "symbol";
        } else {
            return "identifier";
        }
    }

    private static String escapeXml(String input) {
        return input.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&apos;");
    }
}
