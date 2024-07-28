package maquinap;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import asint.TipoUtil;
import asint.SintaxisAbstractaTiny.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;


public class MaquinaP {
   private Scanner input;
   public static class EAccesoIlegitimo extends RuntimeException {} 
   public static class EAccesoAMemoriaNoInicializada extends RuntimeException {
      public EAccesoAMemoriaNoInicializada(int pc,int dir) {
         super("pinst:"+pc+" dir:"+dir); 
      } 
   } 
   public static class EAccesoFueraDeRango extends RuntimeException {} 
   
   private GestorMemoriaDinamica gestorMemoriaDinamica;
   private GestorPilaActivaciones gestorPilaActivaciones;
    
   private class Valor implements Comparable<Valor>{
      public int valorInt() {throw new EAccesoIlegitimo();}
      public float valorReal() {throw new EAccesoIlegitimo();}
      public boolean valorBool() {throw new EAccesoIlegitimo();}
      public String valorCadena() {throw new EAccesoIlegitimo();}


      @Override
      public int compareTo(Valor o) {
         throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
      }
      // I wish we could override operators in java ...
   }

   private class ValorNull extends Valor{
      private int valor;
      public ValorNull() {
         valor = -1; 
      }
      public int valorInt() {return valor;}
      public String toString() {
        return "NULL";
      }
      @Override
      public int compareTo(Valor o) {
         return Integer.compare(this.valor, o.valorInt());
      }
   }

   private class ValorReal extends Valor{
      private float valor;
      public ValorReal(float valor) {
         this.valor = valor; 
      }
      public float valorReal() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
      @Override
      public int compareTo(Valor o) {
         return Float.compare(this.valor, o.valorReal());
      }
   }

   private class ValorInt extends Valor{
      private int valor;
      public ValorInt(int valor) {
         this.valor = valor; 
      }
      public int valorInt() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
      @Override
      public int compareTo(Valor o) {
         return Integer.compare(this.valor, o.valorInt());
      }
   }

   private class ValorCadena extends Valor{
      private String valor;
      public ValorCadena(String valor) {
         this.valor = valor; 
      }
      public String valorCadena() {return valor;}
      public String toString() {
        return valor;
      }
      @Override
      public int compareTo(Valor o) {
         return this.valor.compareTo(o.valorCadena());
      }
   }

   private class ValorBool extends Valor{
      private boolean valor;
      public ValorBool(boolean valor) {
         this.valor = valor; 
      }
      public boolean valorBool() {return valor;}
      public String toString() {
        return String.valueOf(valor);
      }
      @Override
      public int compareTo(Valor o) {
         int val0 = valor ? 1 : 0;
         int val1 = o.valorBool() ? 1 : 0;
         return Integer.compare(val0, val1);
      }
   }

   private List<Instruccion> codigoP;
   private Stack<Valor> pilaEvaluacion;
   private Valor[] datos; 
   private int pc;

   public interface Instruccion {
      void ejecuta();  
   }
   

   private class INl implements Instruccion {
      public void ejecuta() {
         System.out.println();
         pc++;
      }
      public String toString() {return "nl";};
   }

   private class IWrite implements Instruccion {
      public void ejecuta() {
         Valor valor = pilaEvaluacion.pop(); 
         System.out.print(valor);
         pc++;
      }
      public String toString() {return "write";};
   }

   private class IRead implements Instruccion {
      private Tipo t;
      public IRead(Tipo t){
         this.t = t;
      }
      public void ejecuta() {
         String str = input.nextLine();
         if(TipoUtil.REF(t) instanceof Tipo_int){
            //pilaEvaluacion.push(new ValorInt(input.nextInt()));
            //pilaEvaluacion.push(new ValorInt(0));
            //System.out.println("scanned: " + input.nextLine());
            pilaEvaluacion.push(new ValorInt(Integer.parseInt(str)));
         }
         else if(TipoUtil.REF(t) instanceof Tipo_bool){
            pilaEvaluacion.push(new ValorBool(Boolean.parseBoolean(str)));
         }
         // String
         else{
            pilaEvaluacion.push(new ValorCadena(str));
         }
         pc++;
      }
      public String toString() {return "read";};
   }

   private class IPop implements Instruccion {
      public void ejecuta() {
         pilaEvaluacion.pop(); 
         pc++;
      }
      public String toString() {return "pop";};
   }

   private class IMenorI implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         //System.out.println(opnd1 + " <= " + opnd2);
         pilaEvaluacion.push(new ValorBool(opnd1.compareTo(opnd2) <= 0));
         pc++;
      } 
      public String toString() {return "menorI";};
   }

   private class IMenor implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         //(opnd1 + " < " + opnd2);
         //System.out.println(opnd1 + " < " + opnd2);
         pilaEvaluacion.push(new ValorBool(opnd1.compareTo(opnd2) < 0));
         pc++;
      } 
      public String toString() {return "menor";};
   }

   private class IMayorI implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         //System.out.println(opnd1 + " >= " + opnd2);
         pilaEvaluacion.push(new ValorBool(opnd1.compareTo(opnd2) >= 0));
         pc++;
      } 
      public String toString() {return "mayorI";};
   }

   private class IMayor implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         //System.out.println(opnd1 + " > " + opnd2);
         pilaEvaluacion.push(new ValorBool(opnd1.compareTo(opnd2) > 0));
         pc++;
      } 
      public String toString() {return "mayor";};
   }

   private class IIgual implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         //System.out.println(opnd1 + " == " + opnd2);
         if((opnd1 instanceof ValorNull) && (opnd2 instanceof ValorInt)){
            Valor opndval = datos[opnd2.valorInt()];
            //System.out.println("comparing desig address "+opnd2+ " value " + opndval+ " with null");
            pilaEvaluacion.push(new ValorBool(opndval instanceof ValorNull));
         }
         else if((opnd2 instanceof ValorNull) && (opnd1 instanceof ValorInt)){
            Valor opndval = datos[opnd1.valorInt()];
            //System.out.println("comparing desig address "+opnd1+ " value " + opndval+ " with null");
            pilaEvaluacion.push(new ValorBool(opndval instanceof ValorNull));
         }
         else{
            pilaEvaluacion.push(new ValorBool(opnd1.compareTo(opnd2) == 0));
         }
         pc++;
      } 
      public String toString() {return "igual";};
   }

   private class IDistint implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         //System.out.println(opnd1 + " != " + opnd2);
         if((opnd1 instanceof ValorNull) && (opnd2 instanceof ValorInt)){
            Valor opndval = datos[opnd2.valorInt()];
            pilaEvaluacion.push(new ValorBool(!(opndval instanceof ValorNull)));
         }
         else if((opnd2 instanceof ValorNull) && (opnd1 instanceof ValorInt)){
            Valor opndval = datos[opnd1.valorInt()];
            pilaEvaluacion.push(new ValorBool(!(opndval instanceof ValorNull)));
         }
         else{
            pilaEvaluacion.push(new ValorBool(opnd1.compareTo(opnd2) != 0));
         }
         pc++;
      } 
      public String toString() {return "distint";};
   }
   
   private class ISuma implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         // only two possibilities, either int or real
         if (opnd1 instanceof ValorInt){
            pilaEvaluacion.push(new ValorInt(opnd1.valorInt()+opnd2.valorInt()));
         }
         else{
            pilaEvaluacion.push(new ValorReal(opnd1.valorReal()+opnd2.valorReal()));
         }
         
         pc++;
      } 
      public String toString() {return "suma";};
   }

   private class IResta implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         // only two possibilities, either int or real
         if (opnd1 instanceof ValorInt){
            pilaEvaluacion.push(new ValorInt(opnd1.valorInt()-opnd2.valorInt()));
         }
         else{
            pilaEvaluacion.push(new ValorReal(opnd1.valorReal()-opnd2.valorReal()));
         }
         
         pc++;
      } 
      public String toString() {return "resta";};
   }

   private class IMul implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         if (opnd1 instanceof ValorInt){
            pilaEvaluacion.push(new ValorInt(opnd1.valorInt()*opnd2.valorInt()));
         }
         else{
            pilaEvaluacion.push(new ValorReal(opnd1.valorReal()*opnd2.valorReal()));
         }
         pc++;
      } 
      public String toString() {return "multiplicacion";};
   }

   private class IDivision implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         if (opnd1 instanceof ValorInt){
            pilaEvaluacion.push(new ValorInt(opnd1.valorInt()/opnd2.valorInt()));
         }
         else{
            pilaEvaluacion.push(new ValorReal(opnd1.valorReal()/opnd2.valorReal()));
         }
         pc++;
      } 
      public String toString() {return "division";};
   }

   private class IModulo implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorInt(opnd1.valorInt()%opnd2.valorInt()));
         pc++;
      } 
      public String toString() {return "modulo";};
   }
   
   private class IAnd implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorBool()&&opnd2.valorBool()));
         pc++;
      } 
      public String toString() {return "and";};
   }

   private class IOr implements Instruccion {
      public void ejecuta() {
         Valor opnd2 = pilaEvaluacion.pop(); 
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(opnd1.valorBool()||opnd2.valorBool()));
         pc++;
      } 
      public String toString() {return "or";};
   }

   private class IMenosUnario implements Instruccion {
      public void ejecuta() {
         Valor opnd1 = pilaEvaluacion.pop();
         if (opnd1 instanceof ValorInt){
            pilaEvaluacion.push(new ValorInt(-opnd1.valorInt()));
         }
         else{
            pilaEvaluacion.push(new ValorReal(-opnd1.valorReal()));
         }
         pc++;
      } 
      public String toString() {return "menosUnario";};
   }

   private class INegacion implements Instruccion {
      public void ejecuta() {
         Valor opnd1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorBool(!opnd1.valorBool()));
         pc++;
      } 
      public String toString() {return "negacion";};
   }

   private class IApilaNull implements Instruccion {
      public void ejecuta() {
         pilaEvaluacion.push(new ValorNull()); 
         pc++;
      } 
      public String toString() {return "apila-null";};
   }

   private class IApilaInt implements Instruccion {
      private int valor;
      public IApilaInt(int valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(valor)); 
         pc++;
      } 
      public String toString() {return "apila-int("+valor+")";};
   }

   private class IApilaReal implements Instruccion {
      private float valor;
      public IApilaReal(float valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorReal(valor)); 
         pc++;
      } 
      public String toString() {return "apila-real("+valor+")";};
   }

   private class IApilaBool implements Instruccion {
      private boolean valor;
      public IApilaBool(boolean valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorBool(valor)); 
         pc++;
      } 
      public String toString() {return "apila-bool("+valor+")";};
   }

   private class IApilaCadena implements Instruccion {
      private String valor;
      public IApilaCadena(String valor) {
        this.valor = valor;  
      }
      public void ejecuta() {
         pilaEvaluacion.push(new ValorCadena(valor)); 
         pc++;
      } 
      public String toString() {return "apila-cadena("+valor+")";};
   }

   private IInt2Real IINT2REAL;
   private class IInt2Real implements Instruccion {
      public void ejecuta() {
         Valor v1 = pilaEvaluacion.pop();
         pilaEvaluacion.push(new ValorReal((float)v1.valorInt()));
         pc++;
      } 
      public String toString() {return "int2real";};
   }

   private class IIrA implements Instruccion {
      private int dir;
      public IIrA(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
            pc=dir;
      } 
      public String toString() {return "ir-a("+dir+")";};
   }

   private class IIrF implements Instruccion {
      private int dir;
      public IIrF(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
         if(! pilaEvaluacion.pop().valorBool()) { 
            pc=dir;
         }   
         else {
            pc++; 
         }
      } 
      public String toString() {return "ir-f("+dir+")";};
   }

   private class IIrV implements Instruccion {
      private int dir;
      public IIrV(int dir) {
        this.dir = dir;  
      }
      public void ejecuta() {
         if(pilaEvaluacion.pop().valorBool()) { 
            pc=dir;
         }   
         else {
            pc++; 
         }
      } 
      public String toString() {return "ir-v("+dir+")";};
   }
   
   private class ICopia implements Instruccion {
      private int tam;
      public ICopia(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
            int dirOrigen = pilaEvaluacion.pop().valorInt();
            int dirDestino = pilaEvaluacion.pop().valorInt();
            if ((dirOrigen + (tam-1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            if ((dirDestino + (tam-1)) >= datos.length)
                throw new EAccesoFueraDeRango();
            for (int i=0; i < tam; i++) 
                datos[dirDestino+i] = datos[dirOrigen+i]; 
            pc++;
      } 
      public String toString() {return "copy("+tam+")";};
   }
   
   private class IFetch implements Instruccion {
      public void ejecuta() {
        int dir = pilaEvaluacion.pop().valorInt();
        if (dir >= datos.length) throw new EAccesoFueraDeRango();
        if (datos[dir] == null)  throw new EAccesoAMemoriaNoInicializada(pc,dir);
        pilaEvaluacion.push(datos[dir]);
        pc++;
      } 
      public String toString() {return "fetch";};
   }

   private class IStore implements Instruccion {
      public void ejecuta() {
        Valor valor = pilaEvaluacion.pop();
        int dir = pilaEvaluacion.pop().valorInt();
        if (dir >= datos.length) throw new EAccesoFueraDeRango();
        datos[dir] = valor;
        pc++;
      } 
      public String toString() {return "store";};
   }

   private class IAlloc implements Instruccion {
      private int tam;
      public IAlloc(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
        int inicio = gestorMemoriaDinamica.alloc(tam);
        pilaEvaluacion.push(new ValorInt(inicio));
        pc++;
      } 
      public String toString() {return "alloc("+tam+")";};
   }

   private class IDealloc implements Instruccion {
      private int tam;
      public IDealloc(int tam) {
        this.tam = tam;  
      }
      public void ejecuta() {
        int inicio = pilaEvaluacion.pop().valorInt();
        gestorMemoriaDinamica.free(inicio,tam);
        pc++;
      } 
      public String toString() {return "dealloc("+tam+")";};
   }
   
   private class IActiva implements Instruccion {
       private int nivel;
       private int tamdatos;
       private int dirretorno;
       public IActiva(int nivel, int tamdatos, int dirretorno) {
           this.nivel = nivel;
           this.tamdatos = tamdatos;
           this.dirretorno = dirretorno;
       }
       public void ejecuta() {
          int base = gestorPilaActivaciones.creaRegistroActivacion(tamdatos);
          datos[base] = new ValorInt(dirretorno);
          //System.out.println("stored return address " + datos[base]+ " at " + base);
          datos[base+1] = new ValorInt(gestorPilaActivaciones.display(nivel));
          pilaEvaluacion.push(new ValorInt(base+2));
          pc++;
       }
       public String toString() {
          return "activa("+nivel+","+tamdatos+","+dirretorno+")";                 
       }
   }
   
   private class IDesactiva implements Instruccion {
       private int nivel;
       private int tamdatos;
       public IDesactiva(int nivel, int tamdatos) {
           this.nivel = nivel;
           this.tamdatos = tamdatos;
       }
       public void ejecuta() {
          int base = gestorPilaActivaciones.liberaRegistroActivacion(tamdatos);
          gestorPilaActivaciones.fijaDisplay(nivel,datos[base+1].valorInt());
          //System.out.println("return address:"+datos[base]+ " at "+base);
          pilaEvaluacion.push(datos[base]);
          pc++;
       }
       public String toString() {
          return "desactiva("+nivel+","+tamdatos+")";                 
       }

   }
   
   private class IDesapilad implements Instruccion {
       private int nivel;
       public IDesapilad(int nivel) {
         this.nivel = nivel;  
       }
       public void ejecuta() {
         gestorPilaActivaciones.fijaDisplay(nivel,pilaEvaluacion.pop().valorInt());  
         pc++;
       }
       public String toString() {
          return "desapilad("+nivel+")";                 
       }
   }
   
   private class IDup implements Instruccion {
       public void ejecuta() {
           pilaEvaluacion.push(pilaEvaluacion.peek());
           pc++;
       }
       public String toString() {
          return "dup";                 
       }
   }
   
   private class IStop implements Instruccion {
       public void ejecuta() {
           pc = codigoP.size();
       }
       public String toString() {
          return "stop";                 
       }
   }

   private class IError implements Instruccion {
      public void ejecuta() {
         System.out.println("Error null pointer");
         pc = codigoP.size();
      }
      public String toString() {
         return "error";                 
      }
  }
   
   
   private class IApilad implements Instruccion {
       private int nivel;
       public IApilad(int nivel) {
         this.nivel = nivel;  
       }
       public void ejecuta() {
         pilaEvaluacion.push(new ValorInt(gestorPilaActivaciones.display(nivel)));
         pc++;
       }
       public String toString() {
          return "apilad("+nivel+")";                 
       }

   }
   
   private class IIrind implements Instruccion {
       public void ejecuta() {
          pc = pilaEvaluacion.pop().valorInt();  
       }
       public String toString() {
          return "ir-ind";                 
       }
   }

   private class IIndx implements Instruccion {
      private int tam;
      public IIndx(int tam){
         this.tam = tam;
      }
      public void ejecuta() {
         int opnd1 = pilaEvaluacion.pop().valorInt(); 
         int opnd0 = pilaEvaluacion.pop().valorInt();
         pilaEvaluacion.push(new ValorInt(opnd0+tam*opnd1));
         pc++;
      } 
      public String toString() {return "indx("+tam+")";};
   }

   private class IAcc implements Instruccion {
      private int desp;
      public IAcc(int desp){
         this.desp = desp;
      }
      public void ejecuta() {
         int opnd0 = pilaEvaluacion.pop().valorInt();
         pilaEvaluacion.push(new ValorInt(opnd0+desp));
         pc++;
      } 
      public String toString() {return "acc("+desp+")";};
   }



   public Instruccion menorI() {return new IMenorI();}
   public Instruccion menor() {return new IMenor();}
   public Instruccion mayorI() {return new IMayorI();}
   public Instruccion mayor() {return new IMayor();}
   public Instruccion igual() {return new IIgual();}
   public Instruccion distint() {return new IDistint();}
   public Instruccion suma() {return new ISuma();}
   public Instruccion resta() {return new IResta();}
   public Instruccion multiplicacion() {return new IMul();}
   public Instruccion division() {return new IDivision();}
   public Instruccion modulo() {return new IModulo();}
   public Instruccion and() {return new IAnd();}
   public Instruccion or() {return new IOr();}
   public Instruccion menosUnario() {return new IMenosUnario();}
   public Instruccion negacion() {return new INegacion();}
   public Instruccion apila_null() {return new IApilaNull();}
   public Instruccion apila_int(int val) {return new IApilaInt(val);}
   public Instruccion apila_real(float val) {return new IApilaReal(val);}
   public Instruccion apila_bool(boolean val) {return new IApilaBool(val);}
   public Instruccion apila_cadena(String val) {return new IApilaCadena(val);}
   public Instruccion int2real() {return IINT2REAL;}
   public Instruccion apilad(int nivel) {return new IApilad(nivel);}
   public Instruccion fetch() {return new IFetch();}
   public Instruccion store() {return new IStore();}
   public Instruccion copy(int tam) {return new ICopia(tam);}
   public Instruccion ir_a(int dir) {return new IIrA(dir);}
   public Instruccion ir_f(int dir) {return new IIrF(dir);}
   public Instruccion ir_v(int dir) {return new IIrV(dir);}
   public Instruccion ir_ind() {return new IIrind();}
   public Instruccion alloc(int tam) {return new IAlloc(tam);} 
   public Instruccion dealloc(int tam) {return new IDealloc(tam);} 
   public Instruccion activa(int nivel,int tam, int dirretorno) {return new IActiva(nivel,tam,dirretorno);}
   public Instruccion desactiva(int nivel, int tam) {return new IDesactiva(nivel,tam);}
   public Instruccion desapilad(int nivel) {return new IDesapilad(nivel);}
   public Instruccion dup() {return new IDup();}
   public Instruccion stop() {return new IStop();}
   public Instruccion pop() {return new IPop();}
   public Instruccion read(Tipo t) {return new IRead(t);}
   public Instruccion write() {return new IWrite();}
   public Instruccion nl() {return new INl();}
   public Instruccion error() {return new IError();}
   public Instruccion indx(int tam) {return new IIndx(tam);}
   public Instruccion acc(int desp) {return new IAcc(desp);}

   public void emit(Instruccion i) {
      codigoP.add(i); 
   }

   private int tamdatos;
   private int tamheap;
   private int ndisplays;
   public MaquinaP(Reader input, int tamdatos, int tampila, int tamheap, int ndisplays) {
      this.tamdatos = tamdatos;
      this.tamheap = tamheap;
      this.ndisplays = ndisplays;
      this.codigoP = new ArrayList<>();  
      this.input = new Scanner(input);
      if(this.input.hasNextLine()){
         this.input.nextLine();
      }
      pilaEvaluacion = new Stack<>();
      datos = new Valor[tamdatos+tampila+tamheap];
      this.pc = 0;
      gestorPilaActivaciones = new GestorPilaActivaciones(tamdatos,(tamdatos+tampila)-1,ndisplays); 
      gestorMemoriaDinamica = new GestorMemoriaDinamica(tamdatos+tampila,(tamdatos+tampila+tamheap)-1);
   }
   public void ejecuta() {
      while(pc != codigoP.size()) {
         Instruccion is = codigoP.get(pc);
         //System.out.println(is);
         is.ejecuta();
         //System.out.println(is+ " and value at 505:"+datos[506]);
         //codigoP.get(pc).ejecuta();
      } 
   }
   public void muestraCodigo() {
     System.out.println("CodigoP");
     for(int i=0; i < codigoP.size(); i++) {
        System.out.println(" "+i+":"+codigoP.get(i));
     }
   }
   public void muestraEstado() {
     System.out.println("Tam datos:"+tamdatos);  
     System.out.println("Tam heap:"+tamheap); 
     System.out.println("PP:"+gestorPilaActivaciones.pp());      
     System.out.print("Displays:");
     for (int i=1; i <= ndisplays; i++)
         System.out.print(i+":"+gestorPilaActivaciones.display(i)+" ");
     System.out.println();
     System.out.println("Pila de evaluacion");
     for(int i=0; i < pilaEvaluacion.size(); i++) {
        System.out.println(" "+i+":"+pilaEvaluacion.get(i));
     }
     System.out.println("Datos");
     for(int i=0; i < datos.length; i++) {
        System.out.println(" "+i+":"+datos[i]);
     }
     System.out.println("PC:"+pc);
   }
   
   public static void main(String[] args) {
       MaquinaP m = new MaquinaP(null,5,10,10,2);
        
          /*
            int x;
            proc store(int v) {
             x = v
            }
           &&
            call store(5)
          */
            
       
       m.emit(m.activa(1,1,8));
       m.emit(m.dup());
       m.emit(m.apila_int(0));
       m.emit(m.suma());
       m.emit(m.apila_int(5));
       m.emit(m.store());
       m.emit(m.desapilad(1));
       m.emit(m.ir_a(9));
       m.emit(m.stop());
       m.emit(m.apila_int(0));
       m.emit(m.apilad(1));
       m.emit(m.apila_int(0));
       m.emit(m.suma());
       m.emit(m.copy(1));
       m.emit(m.desactiva(1,1));
       m.emit(m.ir_ind());       
       m.muestraCodigo();
       m.ejecuta();
       m.muestraEstado();
   }
}
