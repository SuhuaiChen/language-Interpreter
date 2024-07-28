import java.io.FileInputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.io.InputStreamReader;
import java.io.IOException;

public class AnalizadorLexicoTiny {
	
	public static class ECaracterInesperado extends RuntimeException {
		public ECaracterInesperado(String msg) {
			super(msg);
		}
	}

	private Reader input;
	private StringBuffer lex;
	private int sigCar;
	private int filaInicio;
	private int columnaInicio;
	private int filaActual;
	private int columnaActual;
	private static String NL = System.getProperty("line.separator");
   
	private static enum Estado {
		INICIO, REC_POR, REC_DIV, REC_PAP, REC_PCIERR, REC_ASIG,
		REC_MAS, REC_MENOS, REC_ID, REC_ENT, REC_EOF,
		
		
		
		REC_0, 
		
		REC_IDECP, REC_IDEC, REC_0DEC, //REC_DEC, REC_DEC0,
		
		REC_GT, REC_GE, REC_LT, REC_LE, REC_EQ, REC_EX, REC_NE,
		REC_LLAP, REC_LLCIERRE, REC_SEPT, REC_SEP,	
		
		REC_E, REC_MASE, REC_MENOSE, REC_ENTE, REC_ENT0,
		
		REC_PYC, REC_EVAL, REC_COMT, REC_COM 
	}

	private Estado estado;

	public AnalizadorLexicoTiny(Reader input) throws IOException {
		this.input = input;
		lex = new StringBuffer();
		sigCar = input.read();
		filaActual=1;
		columnaActual=1;
	}
   
	public UnidadLexica sigToken() throws IOException {
		estado = Estado.INICIO;
		filaInicio = filaActual;
		columnaInicio = columnaActual;
		lex.delete(0,lex.length());
		while(true) {
			switch(estado) {
			case INICIO: // Estado no final, si no reconoce caracter finaliza con error
				if(hayLetra()) transita(Estado.REC_ID);
				else if (hayDigitoPos()) transita(Estado.REC_ENT);
				else if (hayCero()) transita(Estado.REC_0);
				else if (haySuma()) transita(Estado.REC_MAS);
				else if (hayResta()) transita(Estado.REC_MENOS);
				else if (hayMul()) transita(Estado.REC_POR);
				else if (hayDiv()) transita(Estado.REC_DIV);
				else if (hayPAp()) transita(Estado.REC_PAP);
				else if (hayPCierre()) transita(Estado.REC_PCIERR);
				else if (hayIgual()) transita(Estado.REC_ASIG); // CAMBIAR
				//else if (hayComa()) transita(Estado.REC_COMA);
				else if (hayAlmohadilla()) transitaIgnorando(Estado.REC_COMT);
				else if (haySep()||hayNL()) transitaIgnorando(Estado.INICIO);
				else if (hayEOF()) transita(Estado.REC_EOF);
				
				else if (hayPYC()) transita(Estado.REC_PYC);
				else if (hayEVAL()) transita(Estado.REC_EVAL);
				else if (hayAMP()) transita(Estado.REC_SEPT);
				else if (hayLLAP()) transita(Estado.REC_LLAP);
				else if (hayLLCIERRE()) transita(Estado.REC_LLCIERRE);
				else if (hayLT()) transita(Estado.REC_LT);
				else if (hayGT()) transita(Estado.REC_GT);
				else if (hayEX()) transita(Estado.REC_EX);									
				else error();
				break;
			case REC_ID: // lee letras y numeros
				if (hayLetra() || hayDigito()) transita(Estado.REC_ID);
				else return unidadId();               
				break;
			case REC_ENT: // lee numeros
				if (hayDigito()) transita(Estado.REC_ENT);
				else if (hayPunto()) transita(Estado.REC_IDECP);
				else if(hayE()) transita(Estado.REC_E);
				else return unidadEnt();
				break;
			case REC_0:
				if (hayPunto()) transita(Estado.REC_IDECP);
				else if(hayE()) transita(Estado.REC_E);
				else return unidadEnt();
				break;
			case REC_MAS:
				if (hayDigitoPos()) transita(Estado.REC_ENT);
				else if(hayCero()) transita(Estado.REC_0);
				else return unidadMas();
				break;
			case REC_MENOS: 
				if (hayDigitoPos()) transita(Estado.REC_ENT);
				else if(hayCero()) transita(Estado.REC_0);
				else return unidadMenos();
				break;
			case REC_POR: return unidadPor();
			case REC_DIV: return unidadDiv();              
			case REC_PAP: return unidadPAp();
			case REC_PCIERR: return unidadPCierre();
			//case REC_ASIG: return unidadIgual();
			//case REC_COMA: return unidadComa();
			// Comentario lee todo el comentario, sin almacenarlo. Lee un salto de linea, vuelve al inicio.
			case REC_COMT:
				if(hayAlmohadilla()) transitaIgnorando(Estado.REC_COM);
				// After half of an hour of thinking, now I doublt the necesity to make this modification. So I commented it out
				// Maybe #hola -> ERROR ola is actually the right behavior???
				// This will output ERROR hola
				// And I don't think there is a better work-around... 
				// In REC_COMT, h is already read. If we don't transit to INICIO, the letter h will be ignored because of LEX.delete()
				// else {
				// 	System.out.println("ERROR");
				// 	transita(Estado.INICIO);
				// }
				else error();
				break;
			case REC_COM: 
				if (hayNL()) transitaIgnorando(Estado.INICIO);
				else if (hayEOF()) transita(Estado.REC_EOF);
				else transitaIgnorando(Estado.REC_COM);
				break;
			case REC_EOF: return unidadEof();
			
			// NUEVO --------------------------------------------
			case REC_IDECP: // No final.
				if (hayDigito()) transita(Estado.REC_IDEC);
				else error();
				break;
			case REC_IDEC: 
				if (hayDigitoPos()) transita(Estado.REC_IDEC);
				else if (hayCero()) transita(Estado.REC_0DEC);
				else if(hayE()) transita(Estado.REC_E);
				else return unidadReal();
				break;
			case REC_0DEC: 
				if (hayDigitoPos()) transita(Estado.REC_IDEC);
				else if (hayCero()) transita(Estado.REC_0DEC); 
				else error();
				break;	
				
			// --------------------------------------------------
			
			case REC_LLAP: return unidadLLAP();
			case REC_LLCIERRE: return unidadLLCIERRE();
			case REC_EVAL: return unidadEVAL();
			case REC_PYC: return unidadPYC();
			
			
			case REC_SEPT: // NO FINAL
				if(hayAMP()) transita(Estado.REC_SEP);
				else error();
				break;
			case REC_LT: 
				if(hayIgual()) transita(Estado.REC_LE);
				else return unidadLT();
				break;
			case REC_GT: 
				if(hayIgual()) transita(Estado.REC_GE); 
				else return unidadGT();
				break;
			case REC_EX: 
				if(hayIgual()) transita(Estado.REC_NE);
				else error();
				break;
			case REC_ASIG: 
				if(hayIgual()) transita(Estado.REC_EQ);
				else return unidadASIG();
				break;				
			case REC_LE: return unidadLE();
			case REC_GE: return unidadGE();
			case REC_NE: return unidadNE();
			case REC_SEP: return unidadSEP();
			case REC_EQ: return unidadEQ();
			case REC_E:
				if(haySuma()) transita(Estado.REC_MASE);
				else if(hayResta()) transita(Estado.REC_MENOSE);
				if(hayDigitoPos()) transita(Estado.REC_ENTE);
				else if(hayCero()) transita(Estado.REC_ENT0);
				else error();	
				break;
			case REC_MASE: 
				if(hayDigitoPos()) transita(Estado.REC_ENTE);
				else if(hayCero()) transita(Estado.REC_ENT0);
				else error();
				break;
			case REC_MENOSE:
				if(hayDigitoPos()) transita(Estado.REC_ENTE);
				else if(hayCero()) transita(Estado.REC_ENT0);
				else error();	
				break;
			case REC_ENTE:
				if(hayDigito()) transita(Estado.REC_ENTE);				
				else return unidadE();
				break;			
			case REC_ENT0: return unidadE();
			}
		}    
	}
   
	private void transita(Estado sig) throws IOException {
		lex.append((char)sigCar);
		sigCar();         
		estado = sig;
	}
	
	private void transitaIgnorando(Estado sig) throws IOException {
		sigCar();         
		filaInicio = filaActual;
     	columnaInicio = columnaActual;
     	estado = sig;
	}
   
	private void sigCar() throws IOException {
    	sigCar = input.read();
    	if (sigCar == NL.charAt(0)) saltaFinDeLinea();
    	if (sigCar == '\n') { // salto de linea aumenta fila, columna = 0
    		filaActual++;
    		columnaActual=0;
    	}
    	else columnaActual++; // aumenta la columna
	}
	
	private void saltaFinDeLinea() throws IOException {
		for (int i=1; i < NL.length(); i++) {
			sigCar = input.read();
			if (sigCar != NL.charAt(i)) error();
		}
		sigCar = '\n';
	}
   
	private boolean hayLetra() {return sigCar == '_' || sigCar >= 'a' && sigCar <= 'z' ||
                                      sigCar >= 'A' && sigCar <= 'Z';}
	private boolean hayDigitoPos() {return sigCar >= '1' && sigCar <= '9';}
	private boolean hayCero() {return sigCar == '0';}
	private boolean hayDigito() {return hayDigitoPos() || hayCero();}
	private boolean haySuma() {return sigCar == '+';}
	private boolean hayResta() {return sigCar == '-';}
	private boolean hayMul() {return sigCar == '*';}
	private boolean hayDiv() {return sigCar == '/';}
	private boolean hayPAp() {return sigCar == '(';}
	private boolean hayPCierre() {return sigCar == ')';}
	private boolean hayIgual() {return sigCar == '=';}
	//private boolean hayComa() {return sigCar == ',';}
	private boolean hayPunto() {return sigCar == '.';}
	private boolean hayAlmohadilla() {return sigCar == '#';}
	private boolean haySep() {return sigCar == ' ' || sigCar == '\t' || sigCar=='\n';}
	private boolean hayNL() {return sigCar == '\r' || sigCar == '\b' || sigCar == '\n';}
	private boolean hayEOF() {return sigCar == -1;}
	
	private boolean hayPYC() {return sigCar == ';';}
	private boolean hayEVAL() {return sigCar == '@';}
	private boolean hayAMP() {return sigCar == '&';}
	private boolean hayLLAP() {return sigCar == '{';}
	private boolean hayLLCIERRE() {return sigCar == '}';}
	private boolean hayLT() {return sigCar == '<';}
	private boolean hayGT() {return sigCar == '>';}
	private boolean hayEX() {return sigCar == '!';}
	
	private boolean hayE() {return sigCar == 'e' || sigCar == 'E';}
	
	
	
	
	
	// PALABRAS RESERVADAS TRUE, FALSE, AND, OR, NOT, BOOL,ENT, REAL
	private UnidadLexica unidadId() {
		String word=lex.toString();
		String wordLC = word.toLowerCase();
		Map<String, ClaseLexica> mWord = new HashMap<String,ClaseLexica>();						
		String[] pReservadas = {"true", "false","and","or","not","bool","int","real"};
		ClaseLexica[] clases = {ClaseLexica.TRUE, ClaseLexica.FALSE, ClaseLexica.AND, 
				ClaseLexica.OR, ClaseLexica.NOT, ClaseLexica.BOOL, ClaseLexica.ENT, ClaseLexica.REAL};
		for(int i=0;i<pReservadas.length;i++) {
			mWord.put(pReservadas[i],clases[i]);
		}
		
		if(mWord.containsKey(wordLC)) {
			return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,mWord.get(wordLC));
		}
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.IDEN,word);
		
		
		
		/*
		// SE PUEDE MEJORAR LA EFICIENCIA PERO ASI QUEDA MAS LIMPIO EL CODIGO
		
		int m=pReservadas.length;
		int i, j;
		for(i=0;i<m;i++) {
			if(wordA[0]==pReservadas[i].charAt(0) || wordA[0]==pReservadas[i].charAt(0)-32) {
				for(j=1;j<pReservadas[i].length();j++) {
					if(wordA[j]!=pReservadas[i].charAt(j) && 
							wordA[j]!=pReservadas[i].charAt(j)-32) break;
				}
				if(j==pReservadas[i].length()) 
					return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,clases[i]);
			}
		}*/
		
		
		/*switch(lex.toString()) {
         	case "true":  
         		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.TRUE);
         	case "false":  
         		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.FALSE);    
         	case "and":  
         		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.AND);    
         	case "or":  
         		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.OR);    
         	case "not":  
         		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.NOT);    
         	case "bool":  
         		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.BOOL);    
         	case "int":  
         		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.ENT);    
         	case "real":  
         		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.REAL);    
         	default:    
         		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.IDEN,lex.toString());     
		}*/
	}  
   
	private UnidadLexica unidadEnt() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.LITERALENTERO,lex.toString());     
	}    
	private UnidadLexica unidadReal() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.LITERALREAL,lex.toString());     
	}    
	private UnidadLexica unidadMas() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAS);     
	}    
	private UnidadLexica unidadMenos() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOS);     
	}    
	private UnidadLexica unidadPor() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.POR);     
	}    
	private UnidadLexica unidadDiv() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.DIV);     
	}    
	private UnidadLexica unidadPAp() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PAP);     
	}    
	private UnidadLexica unidadPCierre() {
	   return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PCIERRE);     
	}  	  
	private UnidadLexica unidadEof() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EOF);     
	}  
	
	private UnidadLexica unidadLLAP() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LLAP);     
	} 
	private UnidadLexica unidadLLCIERRE() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LLCIERRE);     
	} 
	private UnidadLexica unidadEVAL() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EVAL);     
	}
	private UnidadLexica unidadPYC() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PYC);     
	} 
	
	private UnidadLexica unidadLT() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LT);     
	} 
	private UnidadLexica unidadGT() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.GT);     
	} 
	private UnidadLexica unidadASIG() {
		return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.ASIG);     
	} 
	
	private UnidadLexica unidadLE() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.LE,lex.toString());     
	} 
	private UnidadLexica unidadGE() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.GE,lex.toString());
	} 
	private UnidadLexica unidadNE() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.NE,lex.toString());
	}
	private UnidadLexica unidadSEP() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.SEP,lex.toString());
	}
	private UnidadLexica unidadEQ() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.EQ,lex.toString());
	}
	
	private UnidadLexica unidadE() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.E,lex.toString());     
	}
	
		
	private void error() throws RuntimeException {
		int curCar = sigCar;
		try {
			sigCar();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		throw new ECaracterInesperado("("+filaActual+','+columnaActual+"):Caracter inexperado:"+(char)curCar);
		//throw new RuntimeException("ERROR");
		//System.exit(1);
	}

	public static void main(String arg[]) throws IOException {
		System.out.println("AQUI NO ES, ES EN DOMJUDGE");
		/*Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
		UnidadLexica unidad;
		// lee el input.txt, hasta el final del fichero, o error.
		do {
			unidad = al.sigToken();			
			//System.out.println(unidad.print());
		}
		while (unidad.clase() != ClaseLexica.EOF);*/
	} 
}