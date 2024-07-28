package alex;
import java.util.HashMap;
import java.util.Map;

public abstract class UnidadLexica {
	
	private ClaseLexica clase;
	private int fila;
	private int columna;
	private Map<ClaseLexica, String> hashMap;
	
	private boolean tmp;
   
	public UnidadLexica(int fila, int columna, ClaseLexica clase) {
		this.fila = fila;
		this.columna = columna;
		this.clase = clase;
		this.tmp=false;
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
		hashMap.put(ClaseLexica.EOF, "<EOF>");
		hashMap.put(ClaseLexica.GT, ">");
		//hashMap.put(ClaseLexica.GE, ">=");
		hashMap.put(ClaseLexica.LT, "<");
		//hashMap.put(ClaseLexica.LE, "<=");
		//hashMap.put(ClaseLexica.EQ, "==");
		//hashMap.put(ClaseLexica.NE, "!=");
		hashMap.put(ClaseLexica.PYC, ";");
		hashMap.put(ClaseLexica.LLAP, "{");
		hashMap.put(ClaseLexica.LLCIERRE, "}");
		//hashMap.put(ClaseLexica.SEP, "&&");
		
		hashMap.put(ClaseLexica.TRUE, "<true>");
		hashMap.put(ClaseLexica.FALSE, "<false>");
		hashMap.put(ClaseLexica.AND, "<and>");
		hashMap.put(ClaseLexica.OR, "<or>");
		hashMap.put(ClaseLexica.NOT, "<not>");
		hashMap.put(ClaseLexica.BOOL, "<bool>");
		hashMap.put(ClaseLexica.ENT, "<int>");
		hashMap.put(ClaseLexica.REAL, "<real>");
		
		
		
		
		/*IDEN
		  
		 SEP, E,
		
		TRUE, FALSE, AND, OR, NOT, BOOL,ENT, REAL*/
	}
   
	public ClaseLexica clase () { return clase; }   
	public abstract String lexema();   
	public int fila() { return fila; }   
	public int columna() { return columna; }
	
	public String print() {
		return hashMap.get(clase);
	}
	
	
}
