import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class DomJudge {

	public static void main(String[] args) {		
		AnalizadorLexicoTiny al = null;
		
		
		try {
			// INPUT
			//Reader input = new InputStreamReader(new FileInputStream("input.txt"));
			// DOMJUDGE
			Reader input = new InputStreamReader(System.in);
			al = new AnalizadorLexicoTiny(input);
		} catch (IOException e) {
			e.printStackTrace();
		}

		UnidadLexica unidad = null;
		//UnidadLexica unidad = new UnidadLexicaUnivaluada(0, 0, ClaseLexica.AND);
		
		do {
			try {
				unidad = al.sigToken();				
				System.out.println(unidad);
				
			} catch (RuntimeException e) {
				System.out.println("ERROR");
			} catch (IOException e) {
				e.printStackTrace();
			} 	
			
		}
		while (unidad == null || unidad.clase() != ClaseLexica.EOF);
	}

}
