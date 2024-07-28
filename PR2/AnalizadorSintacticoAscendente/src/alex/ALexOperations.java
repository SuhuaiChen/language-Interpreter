package alex;

import asint.sym;

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
		return new UnidadLexica(alex.fila(),alex.columna(),sym.IDEN,alex.lexema());     
	}   

	public UnidadLexica unidadLiteralEnt() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.LITERALENTERO,alex.lexema());     
	}    
	public UnidadLexica unidadLiteralReal() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.LITERALREAL,alex.lexema());     
	}    
	public UnidadLexica unidadMas() {
	     return new UnidadLexica(alex.fila(), alex.columna(),sym.MAS, "+"); 
	  }    
	public UnidadLexica unidadMenos() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.MENOS,"-");     
	}    
	public UnidadLexica unidadPor() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.POR,"*");     
	}    
	public UnidadLexica unidadDiv() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.DIV,"/");     
	}    
	public UnidadLexica unidadPAp() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.PAP,"(");     
	}    
	public UnidadLexica unidadPCierre() {
	   return new UnidadLexica(alex.fila(),alex.columna(),sym.PCIERRE,")");     
	}  	  
	public UnidadLexica unidadComa() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.COMA,",");     
	}    
	public UnidadLexica unidadEof() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.EOF,"<EOF>");     
	}  
	
	public UnidadLexica unidadLLAP() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.LLAP,"{") ;     
	} 
	public UnidadLexica unidadLLCIERRE() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.LLCIERRE,"}");     
	} 
	public UnidadLexica unidadEVAL() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.EVAL, "@");     
	}
	public UnidadLexica unidadPYC() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.PYC, ";");     
	} 
	
	public UnidadLexica unidadLT() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.LT,"<");     
	} 
	public UnidadLexica unidadGT() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.GT, ">");     
	} 
	public UnidadLexica unidadASIG() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.ASIG,"=");     
	} 
	
	public UnidadLexica unidadLE() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.LE,alex.lexema());     
	} 
	public UnidadLexica unidadGE() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.GE,alex.lexema());
	} 
	public UnidadLexica unidadNE() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.NE,alex.lexema());
	}
	public UnidadLexica unidadSEP() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.SEP,alex.lexema());
	}
	public UnidadLexica unidadEQ() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.EQ,alex.lexema());
	}
	

	// Nuevo y palabras reservadas

	public UnidadLexica unidadLiteralCadena() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.LITERALCADENA,alex.lexema());     
	}

	public UnidadLexica unidadCAp() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.CAP,"[");     
	}

	public UnidadLexica unidadCCierre() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.CCIERRE,"]");     
	} 

	public UnidadLexica unidadCircunflejo() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.CIRCUNFLEJO,"^");     
	}

	public UnidadLexica unidadIf() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.IF,"<if>");     
	}

	public UnidadLexica unidadNl() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.NL,"<nl>");     
	}

	public UnidadLexica unidadOr() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.OR,"<or>");     
	}

	public UnidadLexica unidadAnd() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.AND,"<and>");     
	}

	public UnidadLexica unidadNew() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.NEW,"<new>");     
	}

	public UnidadLexica unidadNot() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.NOT,"<not>");     
	}

	public UnidadLexica unidadBool() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.BOOL,"<bool>");     
	}

	public UnidadLexica unidadCall() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.CALL,"<call>");     
	}

	public UnidadLexica unidadElse() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.ELSE,"<else>");     
	}

	public UnidadLexica unidadNull() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.NULL,"<null>");     
	}

	public UnidadLexica unidadProc() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.PROC,"<proc>");     
	}

	public UnidadLexica unidadRead() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.READ,"<read>");     
	}

	public UnidadLexica unidadEnt() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.ENT,"<int>");     
	}

	public UnidadLexica unidadReal() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.REAL,"<real>");     
	}

	public UnidadLexica unidadTrue() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.TRUE,"<true>");     
	}

	public UnidadLexica unidadType() {		
		return new UnidadLexica(alex.fila(),alex.columna(),sym.TYPE,"<type>");     
	}

	public UnidadLexica unidadFalse() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.FALSE,"<false>");     
	}

	public UnidadLexica unidadWhile() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.WHILE,"<while>");     
	}

	public UnidadLexica unidadWrite() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.WRITE,"<write>");     
	}

	public UnidadLexica unidadDelete() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.DELETE,"<delete>");     
	}

	public UnidadLexica unidadString() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.STRING,"<string>");     
	}

	public UnidadLexica unidadPunto() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.PUNTO,".");     
	}

	public UnidadLexica unidadAmpersand() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.AMP,"&");     
	}

	public UnidadLexica unidadModulo() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.MOD,"%");     
	}

	public UnidadLexica unidadStruct() {
		return new UnidadLexica(alex.fila(),alex.columna(),sym.STRUCT,"<struct>");     
	}

	public void error()  {
		throw new ECaracterInesperado("***"+alex.fila()+","+alex.columna()+": Caracter inexperado: "+alex.lexema());
	}
	
}