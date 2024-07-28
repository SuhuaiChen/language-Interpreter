import asint.AnalizadorSintacticoTiny;
import asint.AnalizadorSintacticoTinyDJ;
import errors.GestionErroresTiny.ErrorLexico;
import errors.GestionErroresTiny.ErrorSintactico;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import alex.ALexOperations.ECaracterInesperado;
import alex.AnalizadorLexicoTiny;

public class DomJudge{
	
	
	public static void main(String[] args) throws Exception {
		
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		//Reader input = new InputStreamReader(System.in);		
		AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
		AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(alex);
		//asint.setScanner(alex);
		try {    
			asint.debug_parse();
		}
			catch(ECaracterInesperado e) {
			System.out.println("ERROR_LEXICO"); 
		}
		catch(ErrorSintactico e) {
			System.out.println("ERROR_SINTACTICO"); 
		}
		catch(ClassCastException e) {
			
		}
	}
}