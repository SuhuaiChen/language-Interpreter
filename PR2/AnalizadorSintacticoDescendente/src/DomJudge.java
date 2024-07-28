import asint.AnalizadorSintacticoTiny;
import asint.AnalizadorSintacticoTinyDJ;
import asint.ParseException;
import asint.TokenMgrError;
import errors.GestionErroresEval.ErrorLexico;
import errors.GestionErroresEval.ErrorSintactico;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class DomJudge{
	
	
	public static void main(String[] args) throws Exception {
		try{  
			Reader input = new InputStreamReader(new FileInputStream("input.txt"));
			//Reader input = new InputStreamReader(System.in);
			AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(input);
			asint.programa();
		}
		catch(ParseException e) {
			System.out.println("ERROR_SINTACTICO"); 
		}
		catch(TokenMgrError e) {
			System.out.println("ERROR_LEXICO"); 
		}
	}
}