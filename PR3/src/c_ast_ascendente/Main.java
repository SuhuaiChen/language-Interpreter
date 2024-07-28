package c_ast_ascendente;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {
   public static void main(String[] args) throws Exception {
    	// Reader input = new InputStreamReader(new FileInputStream("data/input.txt"));
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		ConstructorAST asint;
	try {
		asint = new ConstructorAST(alex);
		 //asint.setScanner(alex);
		 System.out.println(asint.parse().value);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 }
}   
   
