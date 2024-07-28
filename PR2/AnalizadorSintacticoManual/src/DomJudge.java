import asint.AnalizadorSintacticoEval;
import asint.AnalizadorSintacticoEvalDJ;
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
			AnalizadorSintacticoEval asint = new AnalizadorSintacticoEvalDJ(input);
			asint.analiza();
		}
		catch(ErrorSintactico e) {
			System.out.println("ERROR_SINTACTICO"); 
		}
		catch(ErrorLexico e) {
			System.out.println("ERROR_LEXICO"); 
		}
	}
}