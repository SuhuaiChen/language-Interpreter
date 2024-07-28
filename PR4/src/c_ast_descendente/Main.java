package c_ast_descendente;
import java.io.FileReader;

import asint.Impresion;
public class Main{
   public static void main(String[] args) throws Exception {
      //ConstructorASTsEval asint = new ConstructorASTsEval(new FileReader(args[0]));
      //ConstructorASTsTiny asint = new ConstructorASTsTiny(new FileReader(args[0]));
	  ConstructorASTsTiny asint = new ConstructorASTsTiny(new FileReader("input.txt"));
	  
	   
      asint.disable_tracing();
      
      // RECURSIVO
      System.out.println(asint.inicial());
      
      // INTERPRETE
      // asint.inicial().imprime();
      
      // VISITANTE
      // asint.inicial().procesa(new Impresion());
      
      
   }
}