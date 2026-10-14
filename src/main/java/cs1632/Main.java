package cs1632;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    private static Boolean endsSequence(Token tok) {
        if (tok == null) return true;
        if (tok instanceof OpToken) {
            if (((OpToken) tok).getOperator() == ';') return true;
            if (((OpToken) tok).getOperator() == '.') return true;
        }
        return false;
    }
    private static ArrayList<Token> getNextSequence(Tokenizer toknizr) throws IOException {
        ArrayList<Token> tokens = new ArrayList<>();
        Token tok;
        do {
            tok = toknizr.nextToken();
            if (tok != null) tokens.add(tok);
        } while (!endsSequence(tok));
        if (tok == null) return null;
        if (tok instanceof OpToken) {
            if (((OpToken) tok).getOperator() == '.') {
                return null;
            }
        }
        return tokens;
    }

    public static int evaluatePrefix(ArrayList<Token> tokens, int[] idx) {
        if (idx[0] >= tokens.size()) {
            return 0;
        }
        Token tok = tokens.get(idx[0]++);
        if (tok instanceof NumToken) {
            return ((NumToken) tok).value;
        } else if (tok instanceof OpToken) {
            OpToken opTok = (OpToken) tok;
            int op = opTok.getOperator();
            if (op == ';') {
                return 0;
            }
            int left = evaluatePrefix(tokens, idx);
            int right = evaluatePrefix(tokens, idx);
            switch (op) {
                case '+':
                    return left + right;
                case '-':
                    return left - right;
                case '*':
                    return left * right;
                case '/':
                    if (right == 0) {
                        throw new ArithmeticException("Divide by zero");
                    }
                    return left / right;
                default:
                    throw new IllegalArgumentException("Unknown operator: " + (char) op);
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        try {
            Tokenizer myScanner = new Tokenizer(System.in);
            ArrayList<Token> tokenSequence;
            ArrayList<Token> postfixSequence = null;
            do {
                System.out.print("Equation: ");
                tokenSequence = getNextSequence(myScanner);
                if (tokenSequence != null) {
                    int[] idx = {0};
                    int result = evaluatePrefix(tokenSequence, idx);
                    System.out.println("Result: " + result);
                }
            } while (tokenSequence != null);
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}