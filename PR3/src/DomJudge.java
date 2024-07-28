import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import asint.Impresion;
import asint.SintaxisAbstractaTiny.Prog;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import c_ast_ascendente.AnalizadorLexicoTiny;

import c_ast_ascendente.AnalizadorSintacticoTinyDJAsc;
import c_ast_ascendente.ALexOperations.ECaracterInesperado;
import c_ast_ascendente.GestionErroresTiny.ErrorSintactico;
//import c_ast_ascendente.ConstructorAST;
import c_ast_descendente.AnalizadorSintacticoTinyDJ;
// import c_ast_descendente.ConstructorASTsTiny;
import c_ast_descendente.ParseException;
import c_ast_descendente.TokenMgrError;

public class DomJudge {

	public static void main(String[] args) throws Exception  {
		
		//Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		
		Reader input = new InputStreamReader(System.in);	
		BufferedReader bufferedReader = new BufferedReader(input);
        
         
		int parser = bufferedReader.read();
		//System.out.println(parser);
		//ConstructorAST asint = null;
		try {		
			
			if(parser=='a') { // ASCENDENTE 		
				AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(bufferedReader);
				AnalizadorSintacticoTinyDJAsc asint = new AnalizadorSintacticoTinyDJAsc(alex);
				
				System.out.println("CONSTRUCCION AST ASCENDENTE");
				
				Prog prog =(Prog) asint.debug_parse().value;
				// asint.debug_parse();
				// Prog prog = (Prog)asint.parse().value;				
				
				System.out.println("IMPRESION RECURSIVA");
				System.out.print(prog);
				
				System.out.println("IMPRESION INTERPRETE");
				prog.imprime();
				
				System.out.println("IMPRESION VISITANTE");					
				prog.procesa(new Impresion());
					
				
				
			}
			else {
				System.out.println("CONSTRUCCION AST DESCENDENTE");
				AnalizadorSintacticoTinyDJ asint = new AnalizadorSintacticoTinyDJ(bufferedReader);
	            asint.disable_tracing();
	            Prog prog=asint.inicial();
	            
				System.out.println("IMPRESION RECURSIVA");
				System.out.print(prog);
				
				System.out.println("IMPRESION INTERPRETE");
				prog.imprime();
				
				System.out.println("IMPRESION VISITANTE");					
				prog.procesa(new Impresion());					   	            
			}			
			
			
		} 
		catch(TokenMgrError e) {
			System.out.println("ERROR_LEXICO"); 
		}		
		catch(ECaracterInesperado e2) {
			System.out.println("ERROR_LEXICO"); 
		}		
		catch(ParseException e) {
			System.out.println("ERROR_SINTACTICO"); 
		}
		catch(ErrorSintactico e) {
			System.out.println("ERROR_SINTACTICO"); 
		}
	}
}
