package cs1632;

import static org.junit.Assert.*;
import org.junit.Test;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class MainTest {

    private String runMainWithInput(String input) {
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream oldOut = System.out;
        System.setOut(new PrintStream(out));
        System.setIn(in);
        try {
            Main.main(new String[]{});
        } finally {
            System.setOut(oldOut);
        }
        return out.toString();
    }

    @Test
    public void testAdditionPrefixExpression() {
        String input = "+ 2 * 3 4 ;";
        String output = runMainWithInput(input);
        assertTrue(output.contains("Result: 14"));
    }

    @Test
    public void testSubtractionPrefixExpression() {
        String input = "- * 2 3 4 ;";
        String output = runMainWithInput(input);
        assertTrue(output.contains("Result: 2"));
    }

    @Test
    public void testDivisionAndAdditionExpression() {
        String input = "+ / 10 2 * 3 3 ;";
        String output = runMainWithInput(input);
        assertTrue(output.contains("Result: 14"));
    }


    // I used Gemini to help me fix a type error in Tokenizer (line 45) which was causing
    // testDivisionAndAdditionExpression() to fail.
    //Google. (2026). Gemini (Gemini 3.1 Pro) [Large language model]. https://gemini.google.com

    @Test
    public void testMultipleEquationsSequence() {
        String input = "+ 1 2 ;\n* 4 5 ;";
        String output = runMainWithInput(input);
        assertTrue(output.contains("Result: 3"));
        assertTrue(output.contains("Result: 20"));
    }
}