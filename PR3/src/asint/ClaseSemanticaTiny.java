package asint;

//import asint.SintaxisAbstractaTiny.Exp;

public class ClaseSemanticaTiny extends SintaxisAbstractaTiny{
    public ClaseSemanticaTiny() {
        super();
    }
    
        
    public Exp mkop(String op, Exp opnd1, Exp opnd2) {
    	//System.out.println("Operacion: "+op+".");
    	//op=op.toLowerCase();
        switch(op) {
        	case "=": return asig(opnd1, opnd2); 		// 0
        	case "<=": return menorI(opnd1, opnd2); 	// 1
        	case "<": return menor(opnd1, opnd2); 		// 1
        	case ">=": return mayorI(opnd1, opnd2); 	// 1
        	case ">": return mayor(opnd1, opnd2); 		// 1
        	case "==": return igual(opnd1, opnd2); 		// 1
        	case "!=": return distint(opnd1, opnd2); 	// 1
            case "+": return suma(opnd1,opnd2); 		// 2
            case "-": return resta(opnd1,opnd2); 		// 2
            case "and": return and(opnd1, opnd2); 		// 3
            case "or": return or(opnd1, opnd2); 		// 3
            case "*": return mul(opnd1,opnd2); 			// 4
            case "/": return div(opnd1,opnd2); 			// 4 
            case "%": return mod(opnd1,opnd2); 			// 4
            default: throw new UnsupportedOperationException("Bad op");
        }
    }
    
    public Exp mkopUn(String op, Exp opnd1) {
    	//System.out.println(op);
        switch(op) {
            case "not": return negacion(opnd1);		// 5
            case "-": return menosUnario(opnd1); 	// 5
            case "^": return indireccion(opnd1); 	// 6
            
            default: throw new UnsupportedOperationException("Bad op");
        }
    }
    
}
