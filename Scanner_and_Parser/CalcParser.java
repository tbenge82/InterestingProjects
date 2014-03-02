package project2;

import java.io.*;

/**
 * @author Thomas Benge 
 * CSCI 431 
 * Program 2: Making a parser 
 * 10/11/2013
 */
public class CalcParser implements Tokens {

    private CalcScanner scanner;
    int nextToken;

    public CalcParser(CalcScanner scanner) {

        this.scanner = scanner;
    }

    public static void main(String[] args) {

        try (PushbackReader reader = new PushbackReader(new FileReader(args[0]))) {

            CalcScanner scan = new CalcScanner(reader);
            CalcParser parser = new CalcParser(scan);
            parser.nextToken = scan.nextToken();

            String testResult = parser.parse();
            System.out.println(testResult);

        } catch (FileNotFoundException e) {
            System.out.println("The file couldn't be found.");
        } catch (Exception e) {
            System.out.println("An unexpected error occured");
        }
    }
    
    boolean test = true;
    public String parse() throws IOException {

        program();
        match(EOF);
        if (test == true) {
            return "Parse Succesful";
        } else {
            return "Parse Failed.";
        }
    }

    private void match(int token) throws IOException {

        if (token == nextToken) {
            nextToken = scanner.nextToken();
        } else {
            error();
        }
    }

    private void program() throws IOException {
        stmtList();
        match(EOP);
    }

    private void stmtList() throws IOException {
        if (nextToken == ID || nextToken == READ || nextToken == WRITE) {
            stmt();
            stmtList();
        }
    }

    private void stmt() throws IOException {
        if (nextToken == ID) {
            match(ID);
            match(ASSIGNOP);
            expr();
        } else if (nextToken == READ) {
            match(READ);
            match(ID);
        } else if (nextToken == WRITE) {
            match(WRITE);
            expr();
        } else {
            error();
        }
    }

    private void expr() throws IOException {
        term();
        termTail();
    }

    private void termTail() throws IOException {
        if (nextToken == ADDOP) {
            addOp();
            term();
            termTail();
        }
    }

    private void term() throws IOException {
        factor();
        factorTail();
    }

    private void factorTail() throws IOException {
        if (nextToken == MULOP) {
            multOp();
            factor();
            factorTail();
        }
    }

    private void factor() throws IOException {
        if (nextToken == '(') {
            match('(');
            expr();
            match(')');
        } else if (nextToken == ID) {
            match(ID);
        } else if (nextToken == NUMBER) {
            match(NUMBER);
        } else {
            error();
        }
    }

    private void addOp() throws IOException {
        match(ADDOP);
    }

    private void multOp() throws IOException {
        match(MULOP);
    }
    
    //Deems the parse to be unsucessful if there is ever an error.
    private void error() {
        test = false;
    }
}
