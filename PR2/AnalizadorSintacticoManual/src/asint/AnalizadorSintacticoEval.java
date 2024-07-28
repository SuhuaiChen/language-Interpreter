package asint;

import alex.UnidadLexica;
import alex.AnalizadorLexicoTiny;
import alex.AnalizadorLexicoTiny.ECaracterInesperado;
import alex.ClaseLexica;
import errors.GestionErroresEval;
import java.io.IOException;
import java.io.Reader;
import java.util.EnumSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AnalizadorSintacticoEval {
   private UnidadLexica anticipo;       // token adelantado
   private AnalizadorLexicoTiny alex;   // analizador léxico 
   private GestionErroresEval errores;  // gestor de errores
   private Set<ClaseLexica> esperados;  // clases léxicas esperadas
   
   public AnalizadorSintacticoEval(Reader input) {
      // se crea el gestor de errores
      errores = new GestionErroresEval();
        
      // se crea el analizador léxico
      try {
		alex = new AnalizadorLexicoTiny(input, errores);
      } catch (IOException e) {
		e.printStackTrace();
      }
      // se fija el gestor de errores en el analizador léxico
      // (debe añadirse el método 'fijaGestionErrores' a
      //  dicho analizador)
      alex.fijaGestionErrores(errores);
      // se crea el conjunto de clases léxicas esperadas
      // (estará incializado a vacío)
      esperados = EnumSet.noneOf(ClaseLexica.class);
      // Se lee el primer token adelantado
      sigToken();                      
   }
   public void analiza() {
      programa();
      empareja(ClaseLexica.EOF);
   }
   private void programa() {	   
	   llaveAp();
	   bloque();   		
	   llaveCierre();
   }
   
   private void llaveAp() { empareja(ClaseLexica.LLAP); }   
   private void llaveCierre() { empareja(ClaseLexica.LLCIERRE); }   
   private void bloque() {	   
	   declaraciones_opt(); // terminao
	   instrucciones_opt();
   }
   
   private void declaraciones_opt() {	   	  
	   switch(anticipo.clase()) { 
	   case ENT: case REAL: case BOOL:    	   
		   declaraciones();
	   	   empareja(ClaseLexica.SEP); // &&       
           break;
       default: //declaraciones_opt → ε
           esperados(ClaseLexica.ENT,ClaseLexica.REAL,ClaseLexica.BOOL);
    	   break;
	   } 
   }       
         
   private void declaraciones() {
	   declaracion();
       rlista_decs();
   }
      
   private void declaracion() {
	   TipoBasico();
	   empareja(ClaseLexica.IDEN);
   }  
   
   private void TipoBasico() {
	   switch (anticipo.clase()) {
	   case ENT:
		   empareja(ClaseLexica.ENT);		   
		   break;
	   case REAL:
		   empareja(ClaseLexica.REAL);		   
		   break;
	   case BOOL:
		   empareja(ClaseLexica.BOOL);		   
		   break;				
		default: // NO DEBERIA DE ENTRAR
		   error();
	   }
   }
   
   private void rlista_decs() {
       switch(anticipo.clase()) {
           case PYC: 
               empareja(ClaseLexica.PYC); // ;
               declaracion();
               rlista_decs();
               break;
           default:
              esperados(ClaseLexica.PYC);
              break;
       }
   }
   
   
   private void instrucciones_opt() {
	   switch(anticipo.clase()) { 
	   case EVAL:    	   
		   instrucciones();	   	          
           break;
       default: //declaraciones_opt → ε
           esperados(ClaseLexica.EVAL);
    	   break;
	   } 
   }       
         
   private void instrucciones() {
	   instruccion();
       rlista_instr();
   }
      
   private void instruccion() {
	   switch(anticipo.clase()) {
       case EVAL: 
    	   empareja(ClaseLexica.EVAL);
    	   E0();
           break;
       default:
          error();
          break;
	   }
	   	   
   }  
         
   private void rlista_instr() {
       switch(anticipo.clase()) {
           case PYC: 
               empareja(ClaseLexica.PYC);
               instruccion();
               rlista_instr();
               break;
           default:
              esperados(ClaseLexica.PYC);
              break;
       }
   }
      
   // E1 FE1
   private void E0() {
	   E1();
       FE1();
   }
   
   // OP0 E0
   private void FE1() {
	   switch(anticipo.clase()) { 
	   case ASIG:    	   
		   empareja(ClaseLexica.ASIG);
	   	   E0();
	   	   break;
       default: //declaraciones_opt → ε
           esperados(ClaseLexica.ASIG);
    	   break;
	   } 
   }
            
   // E2 RE1
   private void E1() {
       E2();
       RE1();
   }
   
   // OP1 E2 RE1
   private void RE1() {	   
       switch(anticipo.clase()) {
           case LT: case GT: case LE: case GE: case EQ: case NE:         
               OP1();
               E2();
               RE1();
               break;
           default: 
        	   esperados(ClaseLexica.LT, ClaseLexica.GT, ClaseLexica.LE,
            		  ClaseLexica.GE, ClaseLexica.EQ, ClaseLexica.NE);
        	   break;
       }    
   }
   
   private void OP1() {
	     switch(anticipo.clase()) {
	         case LT: empareja(ClaseLexica.LT); break;  
	         case GT: empareja(ClaseLexica.GT); break;  
	         case LE: empareja(ClaseLexica.LE); break;  
	         case GE: empareja(ClaseLexica.GE); break;  
	         case EQ: empareja(ClaseLexica.EQ); break;  
	         case NE: empareja(ClaseLexica.NE); break;  
	         default:   // NO DEBERIA DE ENTRAR NUNCA	                          
	             error();
	     }  
   }
	    
   
   // E3 FE3 RE2
   private void E2() {
	   E3();
       FE2();
       RE2();
   }  
   
   
   
   private void FE2() {
	   switch(anticipo.clase()) {
       case MENOS: 
           empareja(ClaseLexica.MENOS);
           E3();           
           break;
       default: 
    	   esperados(ClaseLexica.MENOS);
    	   break;
	   }  
   }   
   
   private void RE2() {
	   switch(anticipo.clase()) {
       case MAS: 
           empareja(ClaseLexica.MAS);
           E3();    
           RE2();
           break;
       default: 
    	   esperados(ClaseLexica.MAS);
    	   break;
	   }  
   }
   
   private void E3() {
	   E4();
	   FE3();
   }
   
   private void FE3() {
	   switch(anticipo.clase()) {
       case AND: 
           empareja(ClaseLexica.AND);
           E3();  
           break;
       case OR:
    	   empareja(ClaseLexica.OR);
    	   E4();
    	   break;
       default: 
    	   esperados(ClaseLexica.AND, ClaseLexica.OR);
    	   break;
	   } 
   }
   
   private void E4() {
	   E5();
	   RE4();
   }
      
   
   private void RE4() {
	   switch(anticipo.clase()) {
       case POR: case DIV:
    	   OP4();
    	   E5();
    	   RE4();
           break;
       default: 
    	   esperados(ClaseLexica.POR, ClaseLexica.DIV);
    	   break;
	   } 
	   
   }
   
   private void OP4() {
	   switch(anticipo.clase()) {
       case POR: empareja(ClaseLexica.POR); break;  
       case DIV: empareja(ClaseLexica.DIV); break;          
       default:   // NO DEBERIA DE ENTRAR NUNCA	                          
           error();
	   } 
   }
   
   private void E5() {
	   switch(anticipo.clase()) {
       case NOT: case MENOS:
    	   OP5();
    	   E5();
    	   break;
       default:  
    	   E6();
	   } 
   }
   
   private void OP5() {
	   switch(anticipo.clase()) {
       case NOT: empareja(ClaseLexica.NOT); break;  
       case MENOS: empareja(ClaseLexica.MENOS); break;          
       default:   // NO DEBERIA DE ENTRAR NUNCA	                          
           error();
	   } 
   }
   
   private void E6() {
	   switch(anticipo.clase()){
	   case IDEN: empareja(ClaseLexica.IDEN); break;  
       case LITERALENTERO: 
    	   empareja(ClaseLexica.LITERALENTERO); 
    	   break;
       case LITERALREAL:  
    	   empareja(ClaseLexica.LITERALREAL); 
    	   break;
       case TRUE: empareja(ClaseLexica.TRUE); break;
       case FALSE: empareja(ClaseLexica.FALSE); break; 
       case PAP: 
    	   empareja(ClaseLexica.PAP); 
    	   E0();
    	   empareja(ClaseLexica.PCIERRE); 
    	   break;
	   default:
		   error();
	   }
   }   
   
   

   private void esperados(ClaseLexica ... esperadas) {
       for(ClaseLexica c: esperadas) {
           esperados.add(c);
       }
   }
   
   private void empareja(ClaseLexica claseEsperada) {
      if (anticipo.clase() == claseEsperada) {
    	  System.out.println(anticipo);
    	  sigToken();      
      }
      else {
          esperados(claseEsperada);
          error();
      }
   }
   
   private void sigToken() {
      try {
        //anticipo = alex.yylex();    	
    	anticipo = alex.sigToken();
        //System.out.println(anticipo + " Clase lexica: " + anticipo.clase());  
    	//System.out.println(anticipo);
        esperados.clear();
      }
      catch(IOException e) {
        //errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), esperados);
      }
      
   }
   
    private void error() {
        //throw new IOException("ERROR_SINTACTICO");
    	errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), esperados);
    }
  
}
