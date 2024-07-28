import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;

import asint.Impresion;
import asint.SintaxisAbstractaTiny.Prog;
import asint.Vinculacion;
import asint.PreTipado;
import asint.SemanticErrors;
import asint.Tipado;
import asint.AsigEspacio;
import asint.BISReader;
import asint.Etiquetado;
import asint.Generacion;

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
import maquinap.MaquinaP;

public class DomJudge {

	public static void main(String[] args) throws Exception  {
		
		//Reader input = new BISReader(new FileInputStream("input.txt"));
		Reader input = new BISReader(System.in);

		//int parser = bufferedReader.read();
		int parser = input.read();
		Prog prog = null;
		//System.out.println(parser);
		//ConstructorAST asint = null;
		try {
			if(parser=='a') { // ASCENDENTE 		
				AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
				AnalizadorSintacticoTinyDJAsc asint = new AnalizadorSintacticoTinyDJAsc(alex);
				//System.out.println("CONSTRUCCION AST ASCENDENTE");
				prog =(Prog) asint.debug_parse().value;
				//prog.procesa(new Impresion());
			}
			else {
				//System.out.println("CONSTRUCCION AST DESCENDENTE");
				AnalizadorSintacticoTinyDJ asint = new AnalizadorSintacticoTinyDJ(input);
	            asint.disable_tracing();
	            prog=asint.inicial();					
				//prog.procesa(new Impresion());					   	            
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

		if (prog != null){
			new Vinculacion().procesa(prog);

			if(SemanticErrors.noHayError()) {
				new PreTipado().procesa(prog);
			}
			else{
				SemanticErrors.printErrorsDJ("Errores_vinculacion");
				System.exit(0);
			}

			if(SemanticErrors.noHayError()) {
			new Tipado().procesa(prog);
			}
			else{
				SemanticErrors.printErrorsDJ("Errores_pretipado");
				//SemanticErrors.printErrors();
				System.exit(0);
			}

			if(SemanticErrors.noHayError()) {
				new AsigEspacio().procesa(prog);
				new Etiquetado().procesa(prog);
				MaquinaP m = new MaquinaP(input,500,5000,5000,10);
				new Generacion(m).procesa(prog);
				//m.muestraCodigo();
				m.ejecuta();
				// print a newline for the zsh shell so that it displays correctly in the console
				System.out.println();
			}
			else{
					SemanticErrors.printErrorsDJ("Errores_tipado");
					//SemanticErrors.printErrors();
					System.exit(0);
			}
		}
	}
}
