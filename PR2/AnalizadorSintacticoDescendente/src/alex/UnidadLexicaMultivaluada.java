package alex;

import alex.ClaseLexica;
import alex.UnidadLexica;

// Mas de 1 caracter leido

public class UnidadLexicaMultivaluada extends UnidadLexica {
	
	private String lexema;
	
	public UnidadLexicaMultivaluada(int fila, int columna, ClaseLexica clase, String lexema) {
		super(fila,columna,clase);  
		this.lexema = lexema;
	}
	
	public String lexema() { return lexema; }
	
	public String toString() {
		//return "[clase:"+clase()+",fila:"+fila()+",col:"+columna()+",lexema:"+lexema()+"]";  
		return lexema(); // DOMJUDGE 
	}
}
