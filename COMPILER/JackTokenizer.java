package COMPILER;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

enum TokenType {
    KEYWORD, SYMBOL, IDENTIFIER, INT_CONST, STRING_CONST
}

public class JackTokenizer implements AutoCloseable {
    private BufferedReader input;
    private ArrayList<String> tokens = new ArrayList<>();
    private String tokenType;
    private int currToken = -1;

    public JackTokenizer(Path path) throws IOException {
        input = new BufferedReader(new FileReader(path.toString()));
        StringBuilder fileRaw = new StringBuilder();
        String line;
        while ((line = input.readLine()) != null) {
            line = line.replaceAll("//.*", "");
            fileRaw.append(line.trim());
        }
        fileRaw.append(String.valueOf((char) input.read()));
        String commentless = removeBlockComments(fileRaw.toString());
        Pattern tokenPattern = Pattern.compile("class|method|function|constructor|int|boolean|char|void|var|static|field|let|do|if|else|while|return|true|false|null|this|[{}\\[\\].,;()+\\-*/&|<>=~]|\"[^\"\n]*\"|[0-9]+|[\\w_]+");
        Matcher tokenSpotter = tokenPattern.matcher(commentless);
        while (tokenSpotter.find()) {
            tokens.add(tokenSpotter.group());
        }
    }

    public boolean hasMoreTokens() {
        return currToken < tokens.size() - 1;
    }

    public void advance() {
        if (hasMoreTokens()) {
            currToken++;
        }
        String token = tokens.get(currToken);
        if (token.matches("class|method|function|constructor|int|boolean|char|void|var|static|field|let|do|if|else|while|return|true|false|null|this")) {
            tokenType = TokenType.KEYWORD.name();
        } else if (token.matches("[{}\\[\\].,;()+\\-*/&|<>=~]")) {
            tokenType = TokenType.SYMBOL.name();
        } else if (token.matches("\"[^\"\n]*\"")) {
            tokenType = TokenType.STRING_CONST.name();
        } else if (token.matches("[0-9]+")) {
            tokenType = TokenType.INT_CONST.name();
        } else if (token.matches("[\\w_]+")) {
            tokenType = TokenType.IDENTIFIER.name();
        }
    }

    public String tokenType() {
        return tokenType;
    }

    public String keyword() {
        if (tokenType.equals(TokenType.KEYWORD.name())) {
            return tokens.get(currToken);
        }
        return "NOT A KEYWORD!";
    }

    public String symbol() {
        if (tokenType.equals(TokenType.SYMBOL.name())) {
            return tokens.get(currToken);
        }
        return "NOT A SYMBOL!";
    }

    public String identifier() {
        if (tokenType.equals(TokenType.IDENTIFIER.name())) {
            return tokens.get(currToken);
        }
        return "NOT AN IDENTIFIER!";
    }

    public int intVal() {
        if (tokenType.equals(TokenType.INT_CONST.name())) {
            return Integer.parseInt(tokens.get(currToken));
        }
        return -1;
    }

    public String stringVal() {
        if (tokenType.equals(TokenType.STRING_CONST.name())) {
            return tokens.get(currToken);
        }
        return "NOT A STRINGVAL!";
    }

    private String removeBlockComments(String s) {
        if (!s.contains("/**")) {
            return s;
        }
        String out = "";
        boolean commentMode = false;
        for (int i = 0; i < s.length() - 1; i++) {
            String commentTest = s.substring(i, i + 2);
            if (!commentMode) {
                if (commentTest.equals("/*")) {
                    commentMode = true;
                } else {
                    out += s.substring(i, i + 1);
                }
            } else if (commentMode && commentTest.equals("*/")) {
                commentMode = false;
                i++;
            }
        }
        return out;
    }

    @Override
    public void close() throws Exception {
        input.close();
    }

    public static void main(String[] args) throws Exception {
         {
        String inputFilePath = "D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\LABSHEET2\\COMPILER\\Jack_Tokenizer.jack";
        String outputFilePath = "D:\\SEM2\\22AIE113-Elements of Computing Systems -2\\LABSHEET2\\COMPILER\\Tokenizer_Output.txt";
    
            try (JackTokenizer tokenizer = new JackTokenizer(Paths.get(inputFilePath));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
    
            while (tokenizer.hasMoreTokens()) {
                tokenizer.advance();
                String token = tokenizer.symbol();
                String tokenType = tokenizer.tokenType();
                String tokenName = "";
                if (tokenType.equals(TokenType.KEYWORD.name())) {
                    tokenName = tokenizer.keyword();
                } else if (tokenType.equals(TokenType.IDENTIFIER.name())) {
                    tokenName = tokenizer.identifier();
                }
                writer.write("Token: " + token + "\n");
                writer.write("Token Type: " + tokenType + "\n");
                writer.write("Token Name: " + tokenName + "\n");
            }
    
            System.out.println("Output written to " + outputFilePath);
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
}    