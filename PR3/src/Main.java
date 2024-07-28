import java.io.FileNotFoundException;
import java.io.Reader;

import asint.Impresion;
import asint.SintaxisAbstractaTiny.Prog;

import java.io.FileInputStream;
import java.io.InputStreamReader;

import c_ast_ascendente.AnalizadorLexicoTiny;

import c_ast_ascendente.AnalizadorSintacticoTinyDJAsc;
//import c_ast_ascendente.ConstructorAST;
import c_ast_descendente.AnalizadorSintacticoTinyDJ;
// import c_ast_descendente.ConstructorASTsTiny;
import c_ast_descendente.ParseException;

public class Main {

	public static void main(String[] args) {
		
		if (args.length != 3) {
            System.out.println("Uso: java Main.java <archivo.txt> opc opp");
            System.out.println("- Constructor AST 'opc':\n'desc': descendente\n'asc': ascendente.");
            System.out.println("- Patron 'opp':\n'rec': recursivo\n'int': interprete\n'vis': visitante");
            return;
		}
		
		//ConstructorAST asint = null;
		try {		
			String archivo=args[0];
			String constructor=args[1];
			String patron=args[2];
			Reader input = new InputStreamReader(new FileInputStream(archivo));
			if(constructor.equals("asc")) {				
				AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
				AnalizadorSintacticoTinyDJAsc asint = new AnalizadorSintacticoTinyDJAsc(alex);
				
				System.out.println("CONSTRUCCION AST ASCENDENTE");
				
				Prog prog =(Prog) asint.debug_parse().value;
				// asint.debug_parse();
				// Prog prog = (Prog)asint.parse().value;
				
				switch (patron) {
				case "rec":
					System.out.print(prog);
					break;
				case "int":	
					//Prog a=asint.inicial();
					System.out.println("IMPRESION RECURSIVA");
					System.out.print(prog);
					System.out.println("IMPRESION INTERPRETE");
					prog.imprime();
					System.out.println("IMPRESION VISITANTE");					
					prog.procesa(new Impresion());
					break;
				case "vis":					
		        	prog.procesa(new Impresion());
					break;
				default:
					System.out.println("El patron tiene que ser 'rec', 'int' o 'vis'");
					return;
				}
				
				
			}
			else {
				AnalizadorSintacticoTinyDJ asint = new AnalizadorSintacticoTinyDJ(input);
	            asint.disable_tracing();
	            switch (patron) {
				case "rec":
					System.out.print(asint.inicial());
					break;
				case "int":					
					Prog a=asint.inicial();
					System.out.println("IMPRESION RECURSIVA");
					System.out.print(a);
					System.out.println("IMPRESION INTERPRETE");
					a.imprime();
					System.out.println("IMPRESION VISITANTE");					
					a.procesa(new Impresion());
					break;
				case "vis":					
					asint.inicial().procesa(new Impresion());  
					
					break;
				default:
					System.out.println("El patron tiene que ser 'rec', 'int' o 'vis'");
					return;
				}	            	            
			}			
			
			
		} 
		catch (FileNotFoundException e) { e.printStackTrace(); } 
		catch (ParseException e) { System.out.println("ERROR_SINTACTICO"); } 
		catch (Exception e) { e.printStackTrace(); }
	}
}
