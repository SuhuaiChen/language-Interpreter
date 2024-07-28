import alex.ALexOperations.ECaracterInesperado;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import alex.AnalizadorLexicoTiny;
import alex.ClaseLexica;
import alex.UnidadLexica;



public class DomJudge {

    public static void main(String[] args) throws FileNotFoundException, IOException {
     Reader input  = new InputStreamReader(System.in);
     AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
     UnidadLexica unidad = null;
     boolean error;
     do {
       error = false;  
       try {  
         unidad = al.yylex();
		 System.out.println(unidad);
       }
       catch(ECaracterInesperado e) {
              System.out.println("ERROR");
              error = true;
       }
     }
     while (error || unidad.clase() != ClaseLexica.EOF);
    }        
} 


// public class DomJudge {
// 	public static void main(String[] args) throws FileNotFoundException, IOException {
// 		AnalizadorLexicoTiny al = null;
	
// 		// INPUT
// 		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
// 		// DOMJUDGE
// 		// Reader input = new InputStreamReader(System.in);
// 		al = new AnalizadorLexicoTiny(input);
		

// 		UnidadLexica unidad = null;
		
// 		do {
// 			unidad = al.yylex();
// 			System.out.println(unidad);
// 		}
// 		while (unidad.clase() != ClaseLexica.EOF);
// 	}        
// } 
 