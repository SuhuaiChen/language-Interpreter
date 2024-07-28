package alex;

// Un unico caracter leido
// MAS, MENOS, POR, DIV, PAP, PCIERRE, IGUAL, COMA, EOF
// +     -      *    /    (       )      =      ,   EOF 

public class UnidadLexicaUnivaluada extends UnidadLexica {
	
	   
	
	public UnidadLexicaUnivaluada(int fila, int columna, ClaseLexica clase) {
		super(fila,columna,clase);  
	}
	
	public String lexema() { throw new UnsupportedOperationException(); }
	
	public String toString() {  
		return super.print();
	}   
}
