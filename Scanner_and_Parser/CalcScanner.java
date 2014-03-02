
package project2;

import java.io.*;

public class CalcScanner implements Tokens {

	private PushbackReader reader;

	// The parser creates the PushbackReader pointing
	// to the source code file and passes it to this
	// constructor.
	//
	public CalcScanner(PushbackReader reader) {
	
		this.reader = reader;
                
	}
	
	// nextToken() reads a single lexeme and returns the
	// appropriate token.
	//
	public int nextToken() throws IOException {
		
		StringBuilder lexbuild = new StringBuilder();
		String lexeme = "";
		
		int c = reader.read();
		// skip whitespace
		while (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                c = reader.read();
            }
		// in case source code is missing $$
		if (c == -1) {
                return EOF;
            }
		if (c == '(' || c == ')') {
                return c;
            }
		if (c == '+' || c == '-') {
                return ADDOP;
            }
		if (c == '*' || c == '/') {
                return MULOP;
            }
		if (c == ':') {
			c = reader.read();
			if (c == '=') {
                        return ASSIGNOP;
                    }
			else {
				reader.unread(c);
				return ERROR;
			}
		}
		if (c == '$') {
			c = reader.read();
			if (c == '$') {
                        return EOP;
                    }
			else {
				reader.unread(c);
				return ERROR;
			}
		}
		// read a number
		if (Character.isDigit(c)) {
			c = reader.read();
			while (Character.isDigit(c)) {
                        c = reader.read();
                    }
			reader.unread(c);
			return NUMBER;
		}
		// read an identifier
		if (Character.isLetter(c)) {
			lexbuild.append((char) c);
			c = reader.read();
			while (Character.isLetterOrDigit(c)) {
				lexbuild.append((char) c);
				c = reader.read();
			}
			reader.unread(c);
			lexeme = lexbuild.toString();
			if (lexeme.equals("read")) {
                        return READ;
                    }
			if (lexeme.equals("write")) {
                        return WRITE;
                    }
			return ID;
		}
		return ERROR;
	}

}
