package alex;

public class ALexOperations {

	public static class ECaracterInesperado extends RuntimeException {
		public ECaracterInesperado(String msg) {
			super(msg);
		}
	}
	
	private AnalizadorLexicoTiny alex;
	
	public ALexOperations(AnalizadorLexicoTiny alex) {
		this.alex = alex;   
	}

	// De tiny(0)
	public UnidadLexica unidadId() {
		return new UnidadLexicaMultivaluada(alex.fila(),alex.columna(),ClaseLexica.IDEN,alex.lexema());     
	}   

	public UnidadLexica unidadLiteralEnt() {
		return new UnidadLexicaMultivaluada(alex.fila(),alex.columna(),ClaseLexica.LITERALENTERO,alex.lexema());     
	}    
	public UnidadLexica unidadLiteralReal() {
		return new UnidadLexicaMultivaluada(alex.fila(),alex.columna(),ClaseLexica.LITERALREAL,alex.lexema());     
	}    
	public UnidadLexica unidadMas() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.MAS);     
	}    
	public UnidadLexica unidadMenos() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.MENOS);     
	}    
	public UnidadLexica unidadPor() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.POR);     
	}    
	public UnidadLexica unidadDiv() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.DIV);     
	}    
	public UnidadLexica unidadPAp() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.PAP);     
	}    
	public UnidadLexica unidadPCierre() {
	   return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.PCIERRE);     
	}  	  
	public UnidadLexica unidadComa() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.COMA);     
	}    
	public UnidadLexica unidadEof() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.EOF);     
	}  
	
	public UnidadLexica unidadLLAP() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.LLAP);     
	} 
	public UnidadLexica unidadLLCIERRE() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.LLCIERRE);     
	} 
	public UnidadLexica unidadEVAL() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.EVAL);     
	}
	public UnidadLexica unidadPYC() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.PYC);     
	} 
	
	public UnidadLexica unidadLT() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.LT);     
	} 
	public UnidadLexica unidadGT() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.GT);     
	} 
	public UnidadLexica unidadASIG() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.ASIG);     
	} 
	
	public UnidadLexica unidadLE() {
		return new UnidadLexicaMultivaluada(alex.fila(),alex.columna(),ClaseLexica.LE,alex.lexema());     
	} 
	public UnidadLexica unidadGE() {
		return new UnidadLexicaMultivaluada(alex.fila(),alex.columna(),ClaseLexica.GE,alex.lexema());
	} 
	public UnidadLexica unidadNE() {
		return new UnidadLexicaMultivaluada(alex.fila(),alex.columna(),ClaseLexica.NE,alex.lexema());
	}
	public UnidadLexica unidadSEP() {
		return new UnidadLexicaMultivaluada(alex.fila(),alex.columna(),ClaseLexica.SEP,alex.lexema());
	}
	public UnidadLexica unidadEQ() {
		return new UnidadLexicaMultivaluada(alex.fila(),alex.columna(),ClaseLexica.EQ,alex.lexema());
	}
	
	public UnidadLexica unidadE() {
		return new UnidadLexicaMultivaluada(alex.fila(),alex.columna(),ClaseLexica.E,alex.lexema());     
	}

	// Nuevo y palabras reservadas

	public UnidadLexica unidadLiteralCadena() {
		return new UnidadLexicaMultivaluada(alex.fila(),alex.columna(),ClaseLexica.LITERALCADENA,alex.lexema());     
	}

	public UnidadLexica unidadCAp() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.CAP);     
	}

	public UnidadLexica unidadCCierre() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.CCIERRE);     
	} 

	public UnidadLexica unidadCircunflejo() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.CIRCUNFLEJO);     
	}

	public UnidadLexica unidadIf() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.IF);     
	}

	public UnidadLexica unidadNl() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.NL);     
	}

	public UnidadLexica unidadOr() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.OR);     
	}

	public UnidadLexica unidadAnd() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.AND);     
	}

	public UnidadLexica unidadNew() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.NEW);     
	}

	public UnidadLexica unidadNot() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.NOT);     
	}

	public UnidadLexica unidadBool() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.BOOL);     
	}

	public UnidadLexica unidadCall() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.CALL);     
	}

	public UnidadLexica unidadElse() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.ELSE);     
	}

	public UnidadLexica unidadNull() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.NULL);     
	}

	public UnidadLexica unidadProc() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.PROC);     
	}

	public UnidadLexica unidadRead() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.READ);     
	}

	public UnidadLexica unidadEnt() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.ENT);     
	}

	public UnidadLexica unidadReal() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.REAL);     
	}

	public UnidadLexica unidadTrue() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.TRUE);     
	}

	public UnidadLexica unidadType() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.TYPE);     
	}

	public UnidadLexica unidadFalse() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.FALSE);     
	}

	public UnidadLexica unidadWhile() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.WHILE);     
	}

	public UnidadLexica unidadWrite() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.WRITE);     
	}

	public UnidadLexica unidadDelete() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.DELETE);     
	}

	public UnidadLexica unidadString() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.STRING);     
	}

	public UnidadLexica unidadPunto() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.PUNTO);     
	}

	public UnidadLexica unidadAmpersand() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.AMP);     
	}

	public UnidadLexica unidadModulo() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.MOD);     
	}

	public UnidadLexica unidadStruct() {
		return new UnidadLexicaUnivaluada(alex.fila(),alex.columna(),ClaseLexica.STRUCT);     
	}

	public void error()  {
		throw new ECaracterInesperado("***"+alex.fila()+","+alex.columna()+": Caracter inexperado: "+alex.lexema());
	}
	
}