package asint;

//import c_ast_descendente.String;

public class SintaxisAbstractaTiny {
	
	// Default (No hay que tocar)
    public static abstract class Nodo  {
       public Nodo() {
		   fila=col=-2;
       }   
	   private int fila;
	   private int col;
	   public Nodo ponFila(int fila) {
		    this.fila = fila;
            return this;			
	   }
	   public Nodo ponCol(int col) {
		    this.col = col;
            return this;			
	   }
	   public int leeFila() {
		  return fila; 
	   }
	   public int leeCol() {
		  return col; 
	   }
	   
	   public abstract void procesa(Procesamiento p);
    }
    
    // Expresiones
    public static abstract class Exp  extends  Nodo {
       public Exp() {
		   super();
       }   
       
       public String rec() {
    	   return ""+this;
       }
       
       public void imprime() {
    	   System.out.println(this);
       }
       public abstract int prioridad(); 
       public String iden() {throw new UnsupportedOperationException();}
       public String valor() {throw new UnsupportedOperationException();}
       public Exp opnd0() {throw new UnsupportedOperationException();}
       public Exp opnd1() {throw new UnsupportedOperationException();}
    }
    private static abstract class ExpBin extends Exp {
        protected Exp opnd0;
        protected Exp opnd1;
        public ExpBin(Exp opnd0, Exp opnd1) {
            super();
            this.opnd0 = opnd0;
            this.opnd1 = opnd1;
        }
        public Exp opnd0() {return opnd0;}
        public Exp opnd1() {return opnd1;}

    }
    // Nuevo
    private static abstract class ExpUna extends Exp {
        protected Exp opnd0;
        public ExpUna(Exp opnd0) {
            super();
            this.opnd0 = opnd0;
        }
        public Exp opnd0() {return opnd0;}

    }
    
    private static abstract class ExpBin2 extends Exp {
        protected Exp opnd0;
        protected String opnd1;
        public ExpBin2(Exp opnd0, String opnd1) {
            super();
            this.opnd0=opnd0;
            this.opnd1=opnd1;
        }
        public Exp opnd0() {return opnd0;}
        public String opnd1String() {return opnd1;}
        //public Exp opnd1() {return opnd1;}

    }
    //
    
    // Nuevo
    public static class Prog extends Nodo {
	   private Bloq bloq;
       public Prog(Bloq bloq) {
		   super();
		   this.bloq = bloq;
       }   
       public Bloq bloq() {return bloq;}
       
       
       public String toString() {
            //return "prog("+bloq+")";
    	   return ""+bloq;
       }
       
       public void imprime() {
			bloq.imprime();
       }
       
       @Override
       public void procesa(Procesamiento p) {
           p.procesa(this);
       }
    }    
    public static class Bloq extends Nodo { 
 	   	private DecsOp decsOp;
 	   	private InstrsOp instrsOp;
        public Bloq(DecsOp decsOp, InstrsOp instrsOp) {
 		   super();
 		   this.decsOp = decsOp;
 		   this.instrsOp = instrsOp;
        }   
        public DecsOp decsOp() {return decsOp;}
        public InstrsOp instrsOp() {return instrsOp;}
        
        public String toString() {
           //return "bloq("+decsOp+","+instrsOp+")";
        	return "{\n"+decsOp+instrsOp+"}\n";
        }
        
        public void imprime() {
			System.out.println("{");
			decsOp.imprime();
			instrsOp.imprime();
			System.out.println("}");					
		}
	   
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Si_decs extends DecsOp {
    	private Decs decs; 
        public Si_decs(Decs decs) {
 		   super();
 		   this.decs = decs;
        }   
        public Decs decs() {return decs;}
        
        public String toString() {
             //return "si_decs("+decs+")";
        	return decs+"&&\n"; // TODO
        }
    	
        public void imprime() {
			decs.imprime();
			System.out.println("&&");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }    
    public static class No_decs extends DecsOp {
        public No_decs() {
           super();
        }   
        
        public String toString() {
            //return "no_decs()";
        	return "";
        }
        
        public void imprime() {
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Si_instrs extends InstrsOp {
    	private Instrs instrs; 
        public Si_instrs(Instrs  instrs) {
 		   super();
 		   this.instrs = instrs;
        }   
        public Instrs instrs() {return instrs;}
        
        public String toString() {
             //return "si_intrs("+instrs+")";
        	return ""+instrs;
        }
        
        public void imprime() {
			instrs.imprime();
		}
		
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }    
    public static class No_instrs extends InstrsOp {
        public No_instrs() {
           super();
        }   
        
        public String toString() {
             //return "no_intrs()";
        	return "";
        }
        public void imprime() {
		
		}
		 
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    
	
    public static class Si_tipo extends Tipo { // TODO extends ?
 	   	private Tipo tipo;
 	   	
        public Si_tipo(Tipo tipo) {
 		   super();
 		   this.tipo= tipo; 		   
        }   
        public Tipo tipo() {return tipo;}
        public String toString() {
             //return "si_tipo("+tipo+")";
        	return ""+tipo;
         }
        
        public void imprime() {
			tipo.imprime();
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		 
    }
    public static class No_tipo extends Tipo { // TODO extends ?
        public No_tipo() {
           super();
        }   
        public String toString() {
             //return "no_tipo()";
        	return "";
        }
        
        public void imprime() {
			
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
	
	
    public static class Tipo_lista extends Tipo { // TODO extends Tipo?
 	   	private Tipo tipo;
 	    private String literalEntero;
        public Tipo_lista(Tipo tipo, String literalEntero) {
 		   super();
 		   this.tipo = tipo;
 		   this.literalEntero = literalEntero;
        }   
        public Tipo tipo() {return tipo;}
        public String literalEntero() {return literalEntero;}
        
        public String toString() {
             //return "tipo_lista("+tipo+","+literalEntero+")";
        	return tipo+"[\n"+literalEntero()+"\n]$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        }
		
        public void imprime() {
			/*tipo.imprime();
			System.out.print(" [" + literalEntero + "]");*/
			tipo.imprime();
			System.out.println("[");
			System.out.println(literalEntero());
			System.out.println("]" + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Tipo_circum extends Tipo { // TODO extends Tipo?
 	   	private Tipo tipo;
        public Tipo_circum(Tipo tipo) {
 		   super();
 		   this.tipo = tipo;
        }   
        public Tipo tipo() {return tipo;}
        
        public String toString() {
             //return "tipo_circum("+tipo+")";
             return "^\n"+tipo;
        }
		
        public void imprime() {
			System.out.println("^");
			tipo.imprime();
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Tipo_struct extends Tipo { // TODO extends Tipo?
 	   	private Campos campos;
        public Tipo_struct(Campos campos) {
 		   super();
 		   this.campos = campos;
        }   
        public Campos campos() {return campos;}
        
        public String toString() {
             //return "tipo_struct("+campos+")";
        	return "<struct>\n"
    			 + "{\n"
    			 + campos
    			 + "}\n";
        } 
		
        public void imprime() {
			/*System.out.print("struct {");
			System.out.println("");
			campos.imprime();
			System.out.print("}");*/
			System.out.println("<struct>");
			System.out.println("{");
			campos.imprime();
			System.out.println("}");
			
		}
 
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Tipo_iden extends Tipo { // TODO extends Tipo? 	   	
 	    private String identificador;
        public Tipo_iden(String identificador) {
 		   super();
 		   this.identificador = identificador;
        }   
        public String identificador() {return identificador;}
        
        public String toString() {
             //return "tipo_iden("+identificador+")";
        	return identificador() + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        }
        
        public void imprime() {
			//System.out.print(identificador);
			System.out.println(identificador() + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
		 
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Tipo_int extends Tipo { // TODO extends Tipo? 	   	
        public Tipo_int() {
 		   super();
        }   
        public String toString() { // TODO
             return "<int>\n";
        } 
		
        public void imprime() {
			System.out.println("<int>");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Tipo_real extends Tipo { // TODO extends Tipo?
 	   	public Tipo_real() {
 		   super();
        }   
        public String toString() { // TODO
             return "<real>\n";
        }
		
        public void imprime() {
			System.out.println("<real>");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Tipo_bool extends Tipo { // TODO extends Tipo?
 	    public Tipo_bool() {
        	super();
        }   
        public String toString() { 
             return "<bool>\n";
        } 
        
        public void imprime() {
			System.out.println("<bool>");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Tipo_string extends Tipo { // TODO extends Tipo?
 	   	public Tipo_string() {
 		   super(); 		   
        }           
        public String toString() { // TODO
             return "<string>\n";
        } 
        
        public void imprime() {
			System.out.println("<string>");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }

    public static class Muchos_campos extends Campos { // TODO extends ?
 	   	private Campos campos;
 	   	private Campo campo;
        public Muchos_campos(Campos campos, Campo campo) {
 		   super();
 		   this.campos = campos;
 		   this.campo = campo;
        }   
        public Campos campos() {return campos;}
        public Campo  campo() {return campo;}
        
        public String toString() {
             //return "muchos_campos("+campos+","+campo+")";
        	return ""+campos+",\n"+campo;
        }
        
        public void imprime() {
			/*campos.imprime();
			System.out.print(",");
			campo.imprime();*/
			
			campos().imprime();
			System.out.println(",");
			campo().imprime();
		}	
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }		 
    }
    public static class Un_campo extends Campos { // TODO extends ?
 	   	private Campo campo;
        public Un_campo(Campo campo) {
 		   super();
 		   this.campo = campo;
        }   
        public Campo campo() {return campo;}
        
        public String toString() {
             //return "un_campo("+campo+")";
        	return ""+campo;//+",\n";
        }
        
        public void imprime() {
			campo.imprime();
			//System.out.println(",");			
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }	
		 
    }
    public static class Crea_campo extends Campo { // TODO extends ?
 	   	private Tipo tipo;
    	private String identificador;
        public Crea_campo(Tipo tipo, String identificador) {
 		   super();
 		   this.tipo = tipo;
 		   this.identificador = identificador;
        }   
        public Tipo tipo() {return tipo;}
        public String identificador() {return identificador;}
        
        public String toString() {
        	//return "crea_campo("+tipo+","+identificador+")";
        	return tipo+identificador + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        }
        
        public void imprime() {
			/*tipo.imprime();
			System.out.print(identificador);*/
			tipo.imprime();
			System.out.println(identificador  + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    
    public static class Muchas_decs extends Decs {
        private Decs decs;
        private Dec dec;
        public Muchas_decs(Decs decs, Dec dec) {
           super();
           this.dec = dec;
           this.decs = decs;
        }
        public Decs decs() {return decs;}
        public Dec dec() {return dec;}
        
        public String toString() {
             //return "muchas_decs("+decs+","+dec+")";
        	return decs()
        		 + ";\n"
        		 + dec;
        }
        
        public void imprime() {
			/*decs.imprime();
			dec.imprime();*/
			decs().imprime();
			System.out.println(";");
			dec().imprime();	
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Una_dec extends Decs {
        private Dec dec;
        public Una_dec(Dec dec) {
           super();
           this.dec = dec;
        }
        public Dec dec() {return dec;}
        
        public String toString() {
             //return "una_dec("+dec+")";
        	return ""+dec;
        }
        
        public void imprime() {
			/*dec.imprime();
			System.out.println(";");*/
			dec().imprime();
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Dec_variable extends Dec { // TODO Nodo en vez de Dec?
    	private Tipo tipo;
    	private String id;
        
        public Dec_variable(Tipo tipo, String id) {
            this.tipo=tipo;
        	this.id = id;
            
        }
        public Tipo tipo() {return tipo;}
        public String iden() {return id;}        
        
        public String toString() {
            //return "dec_variable("+id+"["+leeFila()+","+leeCol()+"],"+tipo+")";
        	return tipo+id + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        } 
		
        public void imprime() {
			/*tipo.imprime();
			System.out.println(id);*/
			
			tipo.imprime();
			System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Dec_tipo extends Dec {// TODO Nodo en vez de Dec?
    	private Tipo tipo;
    	private String id;
        
        public Dec_tipo(Tipo tipo, String id) {
            this.tipo=tipo;
        	this.id = id;
            
        }
        public Tipo tipo() {return tipo;}
        public String iden() {return id;}        
        
        public String toString() {
            //return "dec_tipo("+id+"["+leeFila()+","+leeCol()+"],"+tipo+")";
        	return "<type>\n"+tipo+id  + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        } 
		
        public void imprime() {
			/*System.out.print("type");
			tipo.imprime();
			System.out.println(id +";");		*/	
			System.out.println("<type>");
			tipo.imprime();
			System.out.println(id  + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Dec_proc extends Dec { // TODO Nodo en vez de Dec?
    	
    	private String id;
    	private ParsFOp parsfop;
    	private Bloq bloq;
        
        public Dec_proc(String id, ParsFOp parsfop, Bloq bloq) {            
        	this.id = id;
        	this.parsfop=parsfop;
        	this.bloq=bloq;            
        }
        
        public String iden() {return id;}
        public ParsFOp parsfop() {return parsfop;}
        public Bloq bloq() {return bloq;}
        
        public String toString() {
            //return "dec_proc("+id+"["+leeFila()+","+leeCol()+"],"+parsfop + bloq +")";
        	return "<proc>\n"
            	  + id  + "$f:" + leeFila() + ",c:" + leeCol()+"$\n"
            	  + "(\n"
            	  + parsfop
            	  + ")\n"
            	  + bloq;
        } 
        
        public void imprime() {
			/*System.out.print("proc");
			System.out.print(id);
			System.out.print("(");
			parsfop.imprime();
			System.out.print(")");
			bloq.imprime();*/
			
			System.out.println("<proc>");
			System.out.println(id  + "$f:" + leeFila() + ",c:" + leeCol()+"$");
			System.out.println("(");
			parsfop.imprime();
			System.out.println(")");
			bloq.imprime();	
		}
		
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    
    public static class Si_parsF extends ParsFOp { // Si_decs, Si_parsRe
    	private ParsF parsf ; 
        public Si_parsF(ParsF parsf) {
 		   super();
 		   this.parsf = parsf;
        }   
        public ParsF parsf() {return parsf;}
        
        public String toString() {
             //return "si_parsF("+parsf+")";
        	return ""+parsf;
        }
        
        public void imprime() {
			parsf.imprime();
		}
		 
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }    
    public static class No_parsF extends ParsFOp { // No_decs, No_parsRe
        public No_parsF() {
           super();
        }   
        
        public String toString() {
             //return "no_parsF()";
        	return "";
        }

        public void imprime() {
			
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Muchos_parsF extends ParsF { // Muchas_decs
        private ParsF parsF;
        private ParF parF;
        public Muchos_parsF(ParsF parsF, ParF parF) {
           super();
           this.parF = parF;
           this.parsF = parsF;
        }
        public ParsF parsF() {return parsF;}
        public ParF parF() {return parF;}
        
        public String toString() {
             //return "muchos_parsF("+parsF+","+parF+")";
        	return parsF 
        		 + ",\n"
        		 + parF;
        }
        
        public void imprime() {
			/*parsF.imprime();
			System.out.print(",");
			parF.imprime();*/
			parsF.imprime();
			System.out.println(",");
			parF.imprime();
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Un_parF extends ParsF { // Una_dec, Un_parRe 
    	private ParF parF;
        public Un_parF(ParF parF) {
           super();
           this.parF = parF;
        }
        public ParF parF() {return parF;}
        
        public String toString() {
             //return "un_parF("+parF+")";
        	return ""+parF;
        }
        
        public void imprime() {
			parF.imprime();
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class ParamF extends ParF { // TODO Param?
    	private String id;
    	private Tipo tipo;
    	
        
        public ParamF(String id, Tipo tipo) {
            this.tipo=tipo;
        	this.id = id;
            
        }
        public Tipo tipo() {return tipo;}
        public String iden() {return id;}        
        
        public String toString() {
            return tipo+"&\n"+id + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        }
		
        public void imprime() {
			/*tipo.imprime();
			System.out.print(id);*/
			tipo.imprime();		
			System.out.println("&");
			System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Param extends ParF {
    	private String id;
    	private Tipo tipo;
    	        
        public Param(String id, Tipo tipo) {
            this.tipo=tipo;
        	this.id = id;
            
        }
        public Tipo tipo() {return tipo;}
        public String iden() {return id;}        
        
        public String toString() {
            //return "param("+id+"["+leeFila()+","+leeCol()+"],"+tipo+")";
        	return tipo+id + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        }
        
        
		protected void imprime() {
			/*tipo.imprime();
			System.out.print(id);*/
			
			tipo.imprime();
			System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		} 
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
	
    public static class Muchas_instrs extends Instrs { // Muchas_decs
        private Instrs instrs;
        private Instr instr;
        public Muchas_instrs(Instrs instrs, Instr instr) {
           super();
           this.instr = instr;
           this.instrs = instrs;
        }
        public Instrs instrs() {return instrs;}
        public Instr instr() {return instr;}
        public String toString() {
             //return "muchas_instrs("+instrs+","+instr+")";
        	return instrs
        		 + ";\n"
        		 + instr;
        }
        
        public void imprime() {
			/*instrs.imprime();
			System.out.println(";");
			instr.imprime();*/
			
			instrs.imprime();
			System.out.println(";");
			instr.imprime();
		}
		 
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Una_instr extends Instrs { // Una_dec
    	private Instr instr;
        public Una_instr(Instr instr) {
           super();
           this.instr = instr;
        }
        public Instr instr() {return instr;}
        public String toString() {
             //return "una_instr("+instr+")";
        	return ""+instr;
        }
        
        public void imprime() {
			instr.imprime();
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_eval extends Instr { // TODO Nodo en vez de Instr?
    	private Exp exp;
        
        public Instr_eval(Exp exp) {
            this.exp = exp;        	            
        }
        public Exp exp() {return exp;}       
        
        public String toString() {
            //return "instr_eval("+exp+"["+leeFila()+","+leeCol()+"])";
        	return "@\n" +exp;
        }
        
        public void imprime() {
			/*System.out.print("@");
			exp.imprime();*/
			System.out.println("@");
			exp.imprime();
			
		}
		
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_if extends Instr { // TODO Nodo en vez de Instr?
    	
    	private Exp exp;
    	private Bloq bloq;
        
        public Instr_if(Exp exp,Bloq bloq) {
            this.exp = exp;
            this.bloq= bloq;    
        }
        public Exp exp() {return exp;}
        public Bloq bloq() {return bloq;} 
        
        public String toString() {
            //return "instr_if("+exp+"["+leeFila()+","+leeCol()+"]" + bloq +")";
        	return "<if>\n"+exp+bloq;
        } 
        
        public void imprime() {
			/*System.out.print("if");
			exp.imprime();
			bloq.imprime();*/
			
			System.out.println("<if>");
			exp.imprime();
			bloq.imprime();
		}
		
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_ifelse extends Instr { // TODO Nodo en vez de Instr?
    	private Exp exp;
    	private Bloq bloq1, bloq2;
        
        public Instr_ifelse(Exp exp,Bloq bloq1, Bloq bloq2) {
            this.exp = exp;
            this.bloq1= bloq1;
            this.bloq2= bloq2; 
        }
        public Exp exp() {return exp;}
        public Bloq bloq1() {return bloq1;}
        public Bloq bloq2() {return bloq2;} 
        
        public String toString() {
            //return "instr_ifelse("+exp+"["+leeFila()+","+leeCol()+"]" + bloq1 + "," + bloq2 +")";
        	return "<if>\n"
        		 //+ "(\n"
        		 + exp
        		 //+ ")\n"
        		 + bloq1
        		 + "<else>\n"
        		 + bloq2;
        }
        
        public void imprime() {
			/*System.out.print("if (");
			exp.imprime();
			System.out.print(") ");
			bloq1.imprime();			
			System.out.println("else ");
			bloq2.imprime();*/
			
        	System.out.println("<if>");
			// System.out.println("(");
			exp.imprime();
			// System.out.println(")");
			bloq1.imprime();	
			System.out.println("<else>");
			bloq2.imprime();	
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_while extends Instr { // TODO Nodo en vez de Instr?
    	private Exp exp;
    	private Bloq bloq;
        
        public Instr_while(Exp exp,Bloq bloq) {
            this.exp = exp;
            this.bloq= bloq;    
        }
        public Exp exp() {return exp;}
        public Bloq bloq() {return bloq;} 
        
        public String toString() {
            //return "instr_while("+exp+"["+leeFila()+","+leeCol()+"]" + bloq +")";
        	return "<while>\n"
        		 //+ "(\n"
        		 + exp
        		 //+ ")\n"
        		 + bloq;        			
        }
        
        public void imprime() {
			/*System.out.print("while (");
			exp.imprime();
			System.out.print(") ");
			bloq.imprime();*/
			
			System.out.println("<while>");
			//System.out.println("(");
			exp.imprime();
			//System.out.println(")");
			bloq.imprime();
		}
		
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_read extends Instr { // TODO Nodo en vez de Instr?
		private Exp exp;
        
        public Instr_read(Exp exp) {
            this.exp = exp;        	            
        }
        public Exp exp() {return exp;}       
        
        public String toString() {
            //return "instr_read("+exp+"["+leeFila()+","+leeCol()+"])";
        	return "<read>\n"+exp;
        }
        
        public void imprime() {
			/*System.out.print("read ");
			exp.imprime();*/
			System.out.println("<read>");
			exp.imprime();
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_write extends Instr { // TODO Nodo en vez de Instr?
    	private Exp exp;
        
        public Instr_write(Exp exp) {
            this.exp = exp;        	            
        }
        public Exp exp() {return exp;}       
        
        public String toString() {
            //return "instr_write("+exp+"["+leeFila()+","+leeCol()+"])";
        	return "<write>\n"+exp;
        }
        
        public void imprime() {
			/*System.out.print("write ");
			exp.imprime();*/
			System.out.println("<write>");
			exp.imprime();
		}
		
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_nl extends Instr { // TODO Nodo en vez de Instr?    	    	
        
        public Instr_nl() {       	            
        }     
        
        public String toString() {
            return "<nl>\n";
        }
        
        public void imprime() {
			System.out.println("<nl>");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_new extends Instr { // TODO Nodo en vez de Instr?
		private Exp exp;
        
        public Instr_new(Exp exp) {
            this.exp = exp;        	            
        }
        public Exp exp() {return exp;}       
        
        public String toString() {
            //return "instr_new("+exp+"["+leeFila()+","+leeCol()+"])";
        	return "<new>\n"+exp;
        } 
        
        public void imprime() {
			System.out.println("<new>");
			exp.imprime();
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_del extends Instr { // TODO Nodo en vez de Instr?
    	private Exp exp;
        
        public Instr_del(Exp exp) {
            this.exp = exp;        	            
        }
        public Exp exp() {return exp;}       
        
        public String toString() {
            //return "instr_del("+exp+"["+leeFila()+","+leeCol()+"])";
        	return "<delete>\n"+exp;
        }
        
        public void imprime() {
			System.out.println("<delete>");
			exp.imprime();
		}
		
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_call extends Instr { // TODO Nodo en vez de Instr?
    	private String identificador;
    	private ParsReOp parsreop ;    	
        
        public Instr_call(String identificador, ParsReOp parsreop ) {
            this.identificador = identificador;
        	this.parsreop = parsreop ;        	            
        }
        public String id() {return identificador;}
        public ParsReOp parsreop () {return parsreop ;}
        
        public String toString() {
            //return "instr_call("+identificador +"["+leeFila()+","+leeCol()+"] ,"+parsreop+")";
        	return "<call>\n"
            	  + identificador + "$f:" + leeFila() + ",c:" + leeCol()+"$\n"
            	  + "(\n"
            	  + parsreop
            	  + ")\n";
        }
        
        public void imprime() {
			/*System.out.print("call " + identificador + "(");
			parsreop.imprime();
			System.out.print(")");*/
			System.out.println("<call>");
			System.out.println(identificador + "$f:" + leeFila() + ",c:" + leeCol()+"$");
			System.out.println("(");
			parsreop.imprime();
			System.out.println(")");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Instr_bloque extends Instr { // TODO Nodo en vez de Instr?
    	private Bloq bloq;    	
        
        public Instr_bloque(Bloq bloq) {
            this.bloq = bloq;        	            
        }
        public Bloq bloq() {return bloq;}       
        
        public String toString() {
            //return "instr_bloque("+bloq+"["+leeFila()+","+leeCol()+"])";
        	return ""+bloq;
        }
        
        public void imprime() {
			bloq.imprime();
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }		
    }
    
    public static class Si_parsRe extends ParsReOp { // Si_decs, Si_parsF
    	private ParsRe parsre;
        public Si_parsRe(ParsRe parsre) {
 		   super();
 		   this.parsre = parsre;
        }   
        public ParsRe parsre() {return parsre;}
        public String toString() {
             //return "si_parsRE("+parsre+")";
        	return "" + parsre;
        }
        
        public void imprime() {
			parsre.imprime();
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }    
    public static class No_parsRe extends ParsReOp { // No_decs, No_parsF
        public No_parsRe() {
           super();
        }   
        public String toString() {
             //return "no_parsRe()";
        	return "";
        }
        
        public void imprime() {			
			
		} 

        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Muchos_parsRe extends ParsRe { // Muchas_decs
        private ParsRe parsRe;
        private Exp exp;
        public Muchos_parsRe(ParsRe parsRe, Exp exp) {
           super();
           this.parsRe = parsRe;
           this.exp = exp;           
        }
        public ParsRe parsF() {return parsRe;}
        public Exp parF() {return exp;}
        
        public String toString() {
             //return "muchos_parsRe("+parsRe+","+exp+")";
        	return parsRe+",\n"+exp;
        }
        
        public void imprime() {
			/*parsRe.imprimir();
			System.out.print(",");
			exp.imprime();*/
			 
			parsRe.imprime();
			System.out.println(",");
			exp.imprime();
		}
		 
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }
    public static class Un_parRe extends ParsRe { // Una_dec, Un_parF
    	private Exp exp;
        public Un_parRe(Exp exp) {
           super();
           this.exp = exp;
        }
        public Exp parsre() {return exp;}
        public String toString() {
             //return "un_parRe("+exp+")";
        	return ""+exp;
         }
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
        
        public void imprime() {
			exp.imprime();
		}
    }
    
    
    
    public static abstract class Decs extends Nodo { // Instrs, ParsF
        public Decs() {
     	   super();
        }
        protected abstract void imprime();
        
        public Dec dec() {throw new UnsupportedOperationException();}
 	   public Decs ldecs() {throw new UnsupportedOperationException();}
     }
 	public static abstract class Instrs extends Nodo { // Decs, ParsF
        public Instrs() {
     	   super();
        }
        protected abstract void imprime();
 	public Instr instr() {throw new UnsupportedOperationException();}
 	   public Instrs instrs() {throw new UnsupportedOperationException();}

     }	
     public static abstract class ParsF extends Nodo { // Decs, Instrs, ParsRe
         public ParsF() {
         	super();
         }
         protected abstract void imprime();
 		public ParF parf() {throw new UnsupportedOperationException();}
  	   	public ParsF parsf() {throw new UnsupportedOperationException();}
     }
     public static abstract class ParsRe extends Nodo { // Decs, Instrs, ParsF
         public ParsRe() {
         	super();
         }
        //protected abstract void imprimir();
 		protected abstract void imprime();
 		public ParRe parf() {throw new UnsupportedOperationException();}
  	   	public ParsRe parsf() {throw new UnsupportedOperationException();}
     }

     
     // Nuevo
     public static abstract class DecsOp extends Nodo {
         public DecsOp() {
         }        
         protected abstract void imprime();
 		//public DecsOp decsop() {throw new UnsupportedOperationException();}
         public Decs decs() {throw new UnsupportedOperationException();}
     }
     public static abstract class InstrsOp extends Nodo {
         public InstrsOp() {
         }   
         protected abstract void imprime();
         public Instrs instrs() {throw new UnsupportedOperationException();}
     }
     public static abstract class ParsFOp extends Nodo {
         public ParsFOp() {
         }
         protected abstract void imprime();
         public ParsF parsf() {throw new UnsupportedOperationException();}
     }
     public static abstract class ParsReOp extends Nodo { // ParsFOp 
         public ParsReOp() {
         }
         protected abstract void imprime();
         public ParsRe parsre() {throw new UnsupportedOperationException();}
     }
     
     
     public static abstract class Tipo extends Nodo {
         public Tipo() {
         }
         protected abstract void imprime();
         // TODO
         //public LDecs ldecs() {throw new UnsupportedOperationException();}

     }
     
     public static abstract class Campos extends Nodo {
         public Campos() {
         }
         protected abstract void imprime();
         // TODO
         //public LDecs ldecs() {throw new UnsupportedOperationException();}

     }
     public static abstract class Campo extends Nodo {
         public Campo() {
         }
         protected abstract void imprime();
         // TODO
         //public LDecs ldecs() {throw new UnsupportedOperationException();}

     }
     
     public static abstract class Dec extends Nodo { // ParF
         public Dec() {
         }
         protected abstract void imprime();
         // TODO
         //public LDecs ldecs() {throw new UnsupportedOperationException();}
     }
     public static abstract class Instr extends Nodo { // Dec, ParF 
         public Instr() {
         }
         protected abstract void imprime();
         // TODO
         //public LDecs ldecs() {throw new UnsupportedOperationException();}
     }       
     public static abstract class ParF extends Nodo { // Dec, Instr, ParRe
         public ParF() {
         }
         protected abstract void imprime();
         public Tipo tipo() {throw new UnsupportedOperationException();}
         public String string() {throw new UnsupportedOperationException();}
     }           
     public static abstract class ParRe extends Nodo { // Dec, Instr, ParF
         public ParRe() {
         }
         protected abstract void imprime();
         // TODO
         //public LDecs ldecs() {throw new UnsupportedOperationException();}
     }
     
     
     public static abstract class FtypeDeclaracion extends Nodo { // Dec, Instr, ParF?
         public FtypeDeclaracion () {
         }
         protected abstract void imprime();
         // TODO
         //public LDecs ldecs() {throw new UnsupportedOperationException();}
     }
     public static abstract class TypeDec extends Nodo { // Decs, Instrs, ParsRe
         public TypeDec() {
         }
         protected abstract void imprime();
         // TODO
         //public LDecs ldecs() {throw new UnsupportedOperationException();}
     }
     
     //
     
     
     private static String imprimeOpndRecursivo(Exp opnd, int np) {    	 
    	 String ret="";
    	 
    	 if(opnd.prioridad() < np) ret+="(\n";
    	 ret+=opnd.rec();
    	 if(opnd.prioridad() < np) ret+=")\n";
    	 
    	 return ret;
     }
  	 private static String imprimeExpBinRecursivo(Exp opnd0, String op, Exp opnd1, int np0, int np1, int opFila, int opCol) {  		
  		 String ret="";
  		 
  		 ret+=imprimeOpndRecursivo(opnd0,np0);
  		 ret+=op + "$f:" + opFila + ",c:" + opCol +"$\n";
  		 ret+=imprimeOpndRecursivo(opnd1,np1);
  		 
  		 return ret;
  	 }
     
     
 	 private static void imprimeOpndInterprete(Exp opnd, int np) { 		  		
 		if(opnd.prioridad() < np) { System.out.println("("); };
 		opnd.imprime();
 		if(opnd.prioridad() < np) { System.out.println(")"); };
 	}
 	 private static void imprimeExpBinInterprete(Exp opnd0, String op, Exp opnd1, int np0, int np1, int opFila, int opCol) {
 		imprimeOpndInterprete(opnd0,np0);
 		System.out.println(op + "$f:" + opFila + ",c:" + opCol + "$");
 		imprimeOpndInterprete(opnd1,np1);
 	}
	
	 private static void imprimeOpnd(Exp opnd, int np) {
		 if(opnd.prioridad() < np) {System.out.print("(");};
		 opnd.imprime();
		 if(opnd.prioridad() < np) {System.out.print(")");};
	 }
	 private static void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1) {
		 imprimeOpnd(opnd0,np0);
		 System.out.print(" "+op+" ");
		 imprimeOpnd(opnd1,np1);
	 }
    
    // Operadores
    public static class Suma extends ExpBin {
        public Suma(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "suma("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"+",opnd1,2,3,leeFila(), leeCol());
        }
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"+",opnd1,2,3, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 2;}
    }
    public static class Resta extends ExpBin {
        public Resta(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "resta("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"-",opnd1,3,3,leeFila(), leeCol());
        }
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"-",opnd1,3,3, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 2;}
    }
    public static class Mul extends ExpBin {
        public Mul(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "mul("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"*",opnd1,4,5,leeFila(), leeCol());
        }
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"*",opnd1,4,5, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 4;}
    }
    public static class Div extends ExpBin {
        public Div(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "div("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"/",opnd1,4,5,leeFila(), leeCol());
        }
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"/",opnd1,4,5, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 4;}
    }
	public static class Mod extends ExpBin {
        public Mod(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "mod("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"%",opnd1,4,5,leeFila(), leeCol());
        }
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"%",opnd1,4,5, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 4;}
    }
    // Nuevo (operadores binarios y unarios)
    public static class Asig extends ExpBin {
        public Asig(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "asig("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"=",opnd1,1,0,leeFila(), leeCol());
        }
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"=",opnd1,1,0, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 0;}
    }
    public static class MenorI extends ExpBin {
        public MenorI(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "menorI("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"<=",opnd1,1,2,leeFila(), leeCol());
        }
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"<=",opnd1,1,2, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 1;}
    }
    public static class Menor extends ExpBin {
        public Menor(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "menor("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"<",opnd1,1,2,leeFila(), leeCol());
        }
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"<",opnd1,1,2, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 1;}
    }
    public static class MayorI extends ExpBin {
        public MayorI(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "mayorI("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,">=",opnd1,1,2,leeFila(), leeCol());
        }
        public void imprime() {
			imprimeExpBinInterprete(opnd0,">=",opnd1,1,2, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 1;}
		
    }
    public static class Mayor extends ExpBin {
        public Mayor(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "mayor("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,">",opnd1,1,2,leeFila(), leeCol());
        } 
        public void imprime() {
			imprimeExpBinInterprete(opnd0,">",opnd1,1,2, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 1;}
    }
    public static class Igual extends ExpBin {
        public Igual(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "igual("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"==",opnd1,1,2,leeFila(), leeCol());
        } 
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"==",opnd1,1,2, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 1;}
    }
    public static class Distint extends ExpBin {
        public Distint(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "distint("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"!=",opnd1,1,2,leeFila(), leeCol());
        } 
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"!=",opnd1,1,2, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 1;}
    }
    public static class And extends ExpBin {
        public And(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "and("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"<and>",opnd1,4,3,leeFila(), leeCol());
        } 
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"<and>",opnd1,4,3, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 3;}
    }
    public static class Or extends ExpBin {
        public Or(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "or("+opnd0+","+opnd1+")";
        	return imprimeExpBinRecursivo(opnd0,"<or>",opnd1,4,4,leeFila(), leeCol());
        } 
        public void imprime() {
			imprimeExpBinInterprete(opnd0,"<or>",opnd1,4,4, leeFila(), leeCol());
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 3;}
    }
    public static class Negacion extends ExpUna {
        public Negacion(Exp opnd0) {
            super(opnd0);
        }
        public String toString() {
            //return "negacion("+opnd0+")";
        	//System.out.println("<not>$f:" + leeFila() + ",c:" + leeCol()+"$");
        	return "<not>$f:" + leeFila() + ",c:" + leeCol()+"$\n" +imprimeOpndRecursivo(opnd0,5);
        } 
        public void imprime() {
        	System.out.println("<not>$f:" + leeFila() + ",c:" + leeCol()+"$");
        	imprimeOpnd(opnd0,5);
			
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 5;}
    }
    public static class MenosUnario extends ExpUna {
        public MenosUnario(Exp opnd0) {
            super(opnd0);
        }
        public String toString() {
            //return "menosUnario("+opnd0+")";
        	return "-$f:" + leeFila() + ",c:" + leeCol()+"$\n"+imprimeOpndRecursivo(opnd0,5);
        }
        public void imprime() {
        	System.out.println("-$f:" + leeFila() + ",c:" + leeCol()+"$");
			imprimeOpnd(opnd0,5);
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 5;}
    }
    public static class Indexacion extends ExpBin {
        public Indexacion(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "indexacion("+opnd0+","+opnd1+")";
        	return opnd0().rec()
        		 + "[" + "$f:" + leeFila() + ",c:" + (leeCol())+"$\n"
        		 + opnd1().rec()
        		 + "]\n";
        } 
        public void imprime() {
			opnd0().imprime();
			System.out.println("[" + "$f:" + leeFila() + ",c:" + (leeCol())+"$");
			opnd1().imprime(); 
			System.out.println("]");
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 6;}
    }
	// TODO creo que este es string
    public static class Acceso extends ExpBin2 {
        public Acceso(Exp opnd0, String opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            //return "acceso("+opnd0+","+opnd1+")";
        	return opnd0().rec()
        		 + ".\n"
        		 + opnd1String() + "$f:" + leeFila() + ",c:" + (leeCol())+"$\n";
        } 
        public void imprime() {
			opnd0().imprime();
			System.out.println(".");
			System.out.println(opnd1String() + "$f:" + leeFila() + ",c:" + (leeCol())+"$");
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 6;}
    }
    public static class Indireccion extends ExpUna {
        public Indireccion(Exp opnd0) {
            super(opnd0);
        }
        public String toString() {
            //return "indireccion("+opnd0+")";
        	return opnd0().rec()
        		 + "^" + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        } 
        public void imprime() {
			opnd0().imprime();
			System.out.println("^" + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		public int prioridad() {return 6;}
    }   
    //
    
    
    public static class Iden extends Exp {
        private String id;
        public Iden(String id) {
            super();
            this.id = id;
        }
        public String id() {return id;}
        public String toString() {
            //return "iden("+id+"["+leeFila()+","+leeCol()+"])";
        	return id + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        } 
        public void imprime() {
			System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		@Override
		public int prioridad() {
			// TODO Auto-generated method stub
			return 7;
		}
    }
    public static class Lit_ent extends Exp {
        private String num;
        public Lit_ent(String num) {
            super();
            this.num = num;
        }
        public String valor() {return num;}
        public String toString() {
            //return "lit_ent("+num+"["+leeFila()+","+leeCol()+"])";
        	return num + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        } 
        
        public void imprime() {
			System.out.println(num + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
       
		@Override
		public int prioridad() {
			// TODO Auto-generated method stub
			return 7;
		}
    }
    public static class Lit_real extends Exp {
        private String num;
        public Lit_real(String num) {
            super();
            this.num = num;
        }
        public String valor() {return num;}
        public String toString() {
            //return "lit_real("+num+"["+leeFila()+","+leeCol()+"])";
        	return num + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        } 
        public void imprime() {
			System.out.println(num + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		@Override
		public int prioridad() {
			// TODO Auto-generated method stub
			return 7;
		}
    }
    // Nuevo TODO null?
    public static class TRUE extends Exp {        
        public TRUE() {
            super();
        }
        public String toString() {
            return "<true>$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        } 
        public void imprime() {
			System.out.println("<true>$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		@Override
		public int prioridad() {
			// TODO Auto-generated method stub
			return 7;
		}
    }
    public static class FALSE extends Exp {
        public FALSE() {
            super();
        }
        public String toString() {
            return "<false>$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        }
        public void imprime() {
			System.out.println("<false>$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		@Override
		public int prioridad() {
			// TODO Auto-generated method stub
			return 7;
		}
		
    }
    public static class Lit_cadena extends Exp {
        private String id;
        public Lit_cadena(String id) {
            super();
            this.id = id;
        }
        public String valor() {return id;}
        public String id() {return id;}
        public String toString() {
            //return "lit_cadena("+id+"["+leeFila()+","+leeCol()+"])";
        	return id + "$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        } 
        public void imprime() {
			System.out.println(id + "$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		@Override
		public int prioridad() {
			// TODO Auto-generated method stub
			return 7;
		}
    }
    public static class NULL extends Exp {
        public NULL() {
            super();
        }
        public String toString() {
            return "<null>$f:" + leeFila() + ",c:" + leeCol()+"$\n";
        }
        public void imprime() {
			System.out.println("<null>$f:" + leeFila() + ",c:" + leeCol()+"$");
		}
        @Override
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
		@Override
		public int prioridad() {
			return 7;
		} 
    }
    //
    
    // -----------------------------------------------------------------------------------------
    // -- Constructoras ------------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------
    
    
    public Prog prog(Bloq bloq) { return new Prog(bloq); }
    public Bloq bloq(DecsOp decsop,InstrsOp instrsop) { return new Bloq(decsop,instrsop); }
    public Si_decs si_decs(Decs decs) { return new Si_decs(decs); }
    public No_decs no_decs() { return new No_decs(); }
    public Si_instrs si_instrs(Instrs instrs) { return new Si_instrs(instrs); }
    public No_instrs no_instrs() { return new No_instrs(); }
    
    public Si_tipo si_tipo(Tipo tipo) { return new Si_tipo(tipo); }
    public No_tipo no_tipo() { return new No_tipo(); }
    public Tipo_lista tipo_lista(Tipo tipo, String literalEntero) { 
    	return new Tipo_lista(tipo, literalEntero); 
	}
    public Tipo_circum tipo_circum(Tipo tipo) { return new Tipo_circum(tipo); }
    public Tipo_struct tipo_struct(Campos campos) { return new Tipo_struct(campos); }
    public Tipo_iden tipo_iden(String id) { return new Tipo_iden(id); }
    public Tipo_int tipo_int() { return new Tipo_int(); }
    public Tipo_real tipo_real() { return new Tipo_real(); }
    public Tipo_bool tipo_bool() { return new Tipo_bool(); }
    public Tipo_string tipo_string() { return new Tipo_string(); }
    
    
    public Muchos_campos muchos_campos(Campos campos, Campo campo) { 
    	return new Muchos_campos(campos, campo); }
    public Un_campo un_campo(Campo campo) { return new Un_campo(campo); }
    public Crea_campo crea_campo(Tipo tipo, String id) { return new Crea_campo(tipo, id); }
    
    public Muchas_decs muchas_decs(Decs decs, Dec dec) { return new Muchas_decs(decs, dec); }
    public Una_dec una_dec(Dec dec) { return new Una_dec(dec); }
    public Dec_variable dec_variable(Tipo tipo, String id) { return new Dec_variable(tipo, id); }
    public Dec_tipo dec_tipo(Tipo tipo, String id) { return new Dec_tipo(tipo, id); }
    public Dec_proc dec_proc(String id, ParsFOp parsfop, Bloq bloq) { 
    	return new Dec_proc(id,parsfop,bloq); 
	}
    
    public Si_parsF si_parsF(ParsF parsf) { return new Si_parsF(parsf); }
    public No_parsF no_parsF() { return new No_parsF(); }
    public Muchos_parsF muchos_parsF(ParsF parsf, ParF parf) { return new Muchos_parsF(parsf,parf); }
    public Un_parF un_parF(ParF parf) { return new Un_parF(parf); }
    public ParamF paramF(String id, Tipo tipo) { return new ParamF(id,tipo); }
    public Param param(String id, Tipo tipo) { return new Param(id, tipo); }
    
    public Muchas_instrs muchas_instrs(Instrs instrs, Instr instr) { 
    	return new Muchas_instrs(instrs, instr); 
	}
    public Una_instr una_instr(Instr instr) { return new Una_instr(instr); }
    public Instr_eval instr_eval(Exp exp) { return new Instr_eval(exp); }
    public Instr_if instr_if(Exp exp, Bloq bloq) { return new Instr_if(exp, bloq); }
    public Instr_ifelse instr_ifelse(Exp exp, Bloq bloq1, Bloq bloq2) { 
    	return new Instr_ifelse(exp,bloq1,bloq2); 
	}
    public Instr_while instr_while(Exp exp, Bloq bloq) { return new Instr_while(exp,bloq); }
    public Instr_read instr_read(Exp exp) { return new Instr_read(exp); }
    public Instr_write instr_write(Exp exp) { return new Instr_write(exp); }
    public Instr_nl instr_nl() { return new Instr_nl(); }
    public Instr_new instr_new(Exp exp){ return new Instr_new(exp); }
    public Instr_del instr_del(Exp exp) { return new Instr_del(exp); }
    public Instr_call instr_call(String id, ParsReOp parsreop) { return new Instr_call(id, parsreop); }
    public Instr_bloque instr_bloque(Bloq bloq) { return new Instr_bloque(bloq); }
    
    public Si_parsRe si_parsRe(ParsRe parsre) { return new Si_parsRe(parsre); }
    public No_parsRe no_parsRe() { return new No_parsRe(); }
    public Muchos_parsRe muchos_parsRe(ParsRe parsre, Exp exp) { return new Muchos_parsRe(parsre, exp); }
    public Un_parRe un_parRe(Exp exp) { return new Un_parRe(exp); }
    
    
    
    // Operadores
    public Exp suma(Exp opnd0, Exp opnd1) { return new Suma(opnd0,opnd1); }
    public Exp resta(Exp opnd0, Exp opnd1) { return new Resta(opnd0,opnd1);}
    public Exp mul(Exp opnd0, Exp opnd1) { return new Mul(opnd0,opnd1); }
    public Exp div(Exp opnd0, Exp opnd1) { return new Div(opnd0,opnd1); }
    // Nuevo
    public Exp mod(Exp opnd0, Exp opnd1) { return new Mod(opnd0,opnd1); }
    public Exp asig(Exp opnd0, Exp opnd1) { return new Asig(opnd0,opnd1); }
    public Exp menorI(Exp opnd0, Exp opnd1) { return new MenorI(opnd0,opnd1); }
    public Exp menor(Exp opnd0, Exp opnd1) { return new Menor(opnd0,opnd1); }
    public Exp mayorI(Exp opnd0, Exp opnd1) { return new MayorI(opnd0,opnd1); }
    public Exp mayor(Exp opnd0, Exp opnd1) { return new Mayor(opnd0,opnd1); }
    public Exp igual(Exp opnd0, Exp opnd1) { return new Igual(opnd0,opnd1); }
    public Exp distint(Exp opnd0, Exp opnd1) { return new Distint(opnd0,opnd1); }
    public Exp and(Exp opnd0, Exp opnd1) { return new And(opnd0,opnd1); }
    public Exp or(Exp opnd0, Exp opnd1) { return new Or(opnd0,opnd1); }
    public Exp negacion(Exp opnd0) { return new Negacion(opnd0); }
    public Exp menosUnario(Exp opnd0) { return new MenosUnario(opnd0); }
    public Exp indexacion(Exp opnd0, Exp opnd1) { return new Indexacion(opnd0,opnd1); }
    public Nodo acceso(Exp opnd0, String opnd1) { return new Acceso(opnd0,opnd1); }
    public Exp indireccion(Exp opnd0) { return new Indireccion(opnd0); }
    //
    
    
    
    // TODO Cambiar por otros nombres int, real...?
    public Exp iden(String num) { return new Iden(num); }
    public Exp lit_ent(String num) { return new Lit_ent(num); }
    public Exp lit_real(String num) { return new Lit_real(num); }
    // Nuevo
    public Exp lit_true() { return new TRUE(); }
    public Exp lit_false() { return new FALSE(); }
    public Exp lit_cadena(String num) { return new Lit_cadena(num);}
    public Exp lit_null() { return new NULL(); }
}