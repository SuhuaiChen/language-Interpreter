package asint;

import java.io.Reader;

// TODO ESPACIO AL IMPRIMIR
public class AnalizadorSintacticoTinyDJ extends AnalizadorSintacticoTiny {
    private void imprime(Token t) {
        switch(t.kind) {
        	case TRUE: System.out.println("<true>"); break;
        	case FALSE: System.out.println("<false>"); break;
        	case and: System.out.println("<and>"); break;
        	case or: System.out.println("<or>"); break;
        	case not: System.out.println("<not>"); break;
        	case bool: System.out.println("<bool>"); break;
        	case real: System.out.println("<real>"); break;
        	case INT: System.out.println("<int>"); break;
        	case string: System.out.println("<string>"); break;
        	case NULL: System.out.println("<null>"); break;
        	case proc: System.out.println("<proc>"); break;
        	case IF: System.out.println("<if>"); break;
        	case ELSE: System.out.println("<else>"); break;
        	case WHILE: System.out.println("<while>"); break;
        	case struct: System.out.println("<struct>"); break;
        	case NEW: System.out.println("<new>"); break;
        	case delete: System.out.println("<delete>"); break;
        	case read: System.out.println("<read>"); break;
        	case write: System.out.println("<write>"); break;
        	case nl: System.out.println("<nl>"); break;
        	case type: System.out.println("<type>"); break;
        	case call: System.out.println("<call>"); break;
        	
            
            case EOF: System.out.println("<EOF>"); break;
            default: System.out.println(t.image);
        }
    }
    
    public AnalizadorSintacticoTinyDJ(Reader r) {
        super(r);
        disable_tracing();
    }
    final protected void trace_token(Token t, String where) {
        imprime(t);
    }
}
