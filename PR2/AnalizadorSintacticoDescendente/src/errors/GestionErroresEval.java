package errors;

import alex.ClaseLexica;
import java.util.Set;

public class GestionErroresEval {
	
	public static class ErrorLexico extends RuntimeException {
	       public ErrorLexico(String msg) {
	           super(msg);
	       }
	   } 
	   public static class ErrorSintactico extends RuntimeException {
	       public ErrorSintactico(String msg) {
	           super(msg);
	       }
	   } 
   
	public void errorLexico(int fila, int col, String lexema) {
		// System.out.println("ERROR fila "+fila+","+col+": Caracter inexperado: "+lexema); 
		
		//System.out.println("ERROR_LEXICO");
		//System.exit(1);
		throw new ErrorLexico("ERROR_LEXICO");
   }  
	
   public void errorSintactico(int fila, int col, ClaseLexica encontrada, 
                               Set<ClaseLexica> esperadas) {
		/*System.out.print("ERROR fila "+fila+","+col+": Encontrado "+encontrada+" Se esperada: ");
		for(ClaseLexica esperada: esperadas)
		System.out.print(esperada+" ");
		System.out.println();
		System.exit(1);*/

		//System.out.println("ERROR_SINTACTICO");
		//System.exit(1);
		throw new ErrorSintactico("ERROR_SINTACTICO");
   }
   public void errorFatal(Exception e) {
       System.out.println(e);
	   e.printStackTrace();
	   System.exit(1);
   }
}
