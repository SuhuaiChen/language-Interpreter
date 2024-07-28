package c_ast_ascendente;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

public class AnalizadorSintacticoTinyDJAsc extends ConstructorAST {
    public void debug_message(String msg) {}
    public void debug_shift(Symbol token) {
        // switch (token.value.toString()) {
        //     case "and":
        //         System.out.println("<and>");
        //         break;
        //     case "or":
        //         System.out.println("<or>");
        //         break;
        //     case "not":
        //         System.out.println("<not>");
        //         break;
        //     default:
        //         System.out.println(token.value);
        //         break;
        // }
            
    }
    public AnalizadorSintacticoTinyDJAsc(Scanner alex) {
        super(alex);
    }
}
