package alex;

import java.util.HashMap;
import java.util.Map;

public abstract class UnidadLexica {
	private ClaseLexica clase;
	private int fila;
	private int columna;
	private Map<ClaseLexica, String> hashMap;
	
	public UnidadLexica(int fila, int columna, ClaseLexica clase) {
		this.fila = fila;
		this.columna = columna;
		this.clase = clase;
		init();
	}
	
	private void init() {
		hashMap = new HashMap<>();
		hashMap.put(ClaseLexica.PAP, "(");
		hashMap.put(ClaseLexica.PCIERRE, ")");
		hashMap.put(ClaseLexica.ASIG, "=");		
		hashMap.put(ClaseLexica.MAS, "+");
		hashMap.put(ClaseLexica.MENOS, "-");
		hashMap.put(ClaseLexica.POR, "*");
		hashMap.put(ClaseLexica.DIV, "/");
		hashMap.put(ClaseLexica.EVAL, "@");
		hashMap.put(ClaseLexica.EOF, "EOF");
		hashMap.put(ClaseLexica.GT, ">");
		hashMap.put(ClaseLexica.LT, "<");
		hashMap.put(ClaseLexica.PYC, ";");
		hashMap.put(ClaseLexica.LLAP, "{");
		hashMap.put(ClaseLexica.LLCIERRE, "}");
		
		
		hashMap.put(ClaseLexica.MOD, "%");
		hashMap.put(ClaseLexica.CAP, "[");
		hashMap.put(ClaseLexica.CCIERRE, "]");
		hashMap.put(ClaseLexica.PUNTO, ".");
		hashMap.put(ClaseLexica.COMA, ",");
		hashMap.put(ClaseLexica.CIRCUNFLEJO, "^");
		hashMap.put(ClaseLexica.AMP, "&");
		
		hashMap.put(ClaseLexica.TRUE, "<true>");
		hashMap.put(ClaseLexica.FALSE, "<false>");
		hashMap.put(ClaseLexica.AND, "<and>");
		hashMap.put(ClaseLexica.OR, "<or>");
		hashMap.put(ClaseLexica.NOT, "<not>");
		hashMap.put(ClaseLexica.BOOL, "<bool>");
		hashMap.put(ClaseLexica.ENT, "<int>");
		hashMap.put(ClaseLexica.REAL, "<real>");
		
		
		
		
		
		
		hashMap.put(ClaseLexica.STRING, "<string>");
		hashMap.put(ClaseLexica.NULL, "<null>");
		hashMap.put(ClaseLexica.PROC, "<proc>");
		hashMap.put(ClaseLexica.IF, "<if>");
		hashMap.put(ClaseLexica.ELSE, "<else>");
		hashMap.put(ClaseLexica.WHILE, "<while>");
		hashMap.put(ClaseLexica.STRUCT, "<struct>");
		hashMap.put(ClaseLexica.NEW, "<new>");
		hashMap.put(ClaseLexica.DELETE, "<delete>");
		hashMap.put(ClaseLexica.READ, "<read>");
		hashMap.put(ClaseLexica.WRITE, "<write>");
		hashMap.put(ClaseLexica.NL, "<nl>");
		hashMap.put(ClaseLexica.TYPE, "<type>");
		hashMap.put(ClaseLexica.CALL, "<call>");
		
		
		

		
		
		
	}
	
	public String print() {
		return hashMap.get(clase);
	}
	
	public ClaseLexica clase () {return clase;}
	public abstract String lexema();
	public int fila() {return fila;}
	public int columna() {return columna;}
}
