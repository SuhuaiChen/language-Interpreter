package asint;
import asint.SintaxisAbstractaTiny.*;

public class Vinculacion implements Procesamiento{

    private SymbolTable ts;

    public Vinculacion(){
        this.ts = null;
    }

    @Override
    public void procesa(Prog prog) {
        prog.bloq().procesa(this);
    }

    @Override
    public void procesa(Bloq bloq) {
        // abre ambito
        ts = new SymbolTable(ts);
		bloq.decsOp().procesa(this);
		bloq.instrsOp().procesa(this);
        // cierre ambito
        ts = ts.parent;
    }

    @Override
    public void procesa(Si_decs si_decs) {
        si_decs.decs().procesa(this);
        si_decs.decs().procesa2(this);
    }

    @Override
    public void procesa(No_decs no_decs) {}

    @Override
    public void procesa(Si_instrs si_instrs) {
        si_instrs.instrs().procesa(this);
    }

    @Override
    public void procesa(No_instrs no_instrs) {}

/*
 * Primera Pasada
 */


    // @Override
    // public void procesa(Si_tipo si_tipo) {
    //     si_tipo.tipo().procesa(this);
    // }
    // @Override
	// public void procesa(No_tipo no_tipo) {}

	@Override
	public void procesa(Tipo_lista tipo_lista) {
		tipo_lista.tipo().procesa(this);
	}

	@Override
	public void procesa(Tipo_circum tipo_circum) {
        Tipo tipo = tipo_circum.tipo();
        if(!(tipo instanceof Tipo_iden)){
            tipo.procesa(this);
        }
	}

	@Override
	public void procesa(Tipo_struct tipo_struct) {
		tipo_struct.campos().procesa(this);
	}

	@Override
	public void procesa(Tipo_iden tipo_iden) {
		tipo_iden.vinculo = ts.valorDe(tipo_iden.identificador());
        if (tipo_iden.vinculo == null){
            SemanticErrors.addError(tipo_iden.leeFila(), tipo_iden.leeCol(), "cannot find the link of: "+ tipo_iden.identificador());
        }
	}

	@Override
	public void procesa(Tipo_int tipo_int) {}
	@Override
	public void procesa(Tipo_real tipo_real) {}
	@Override
	public void procesa(Tipo_bool tipo_bool) {}
	@Override
	public void procesa(Tipo_string tipo_string) {}

	@Override
	public void procesa(Muchos_campos muchos_campos) {
		muchos_campos.campos().procesa(this);
		muchos_campos.campo().procesa(this);
	}

	@Override
	public void procesa(Un_campo un_campo) {
		un_campo.campo().procesa(this);
	}

	@Override
	public void procesa(Crea_campo crea_campo) {
		crea_campo.tipo().procesa(this);	
	}

	@Override
	public void procesa(Muchas_decs muchas_decs) {
		muchas_decs.decs().procesa(this);
		muchas_decs.dec().procesa(this);		
	}

	@Override
	public void procesa(Una_dec una_dec) {
		una_dec.dec().procesa(this);		
	}

	@Override
	public void procesa(Dec_variable dec_variable) {
		dec_variable.tipo().procesa(this);
        String id = dec_variable.iden();
        if (ts.contiene(id)){
            SemanticErrors.addError(dec_variable.leeFila(), dec_variable.leeCol(), "id already linked: "+ id);
        }
        else{
            ts.inserta(id, dec_variable);
        }
	}

	@Override
	public void procesa(Dec_tipo dec_tipo) {
		dec_tipo.tipo().procesa(this);
        String id = dec_tipo.iden();
        if (ts.contiene(id)){
            SemanticErrors.addError(dec_tipo.leeFila(), dec_tipo.leeCol(), "id already linked: "+ id);
        }
        else{
            ts.inserta(id, dec_tipo);
        }
	}

	@Override
	public void procesa(Dec_proc dec_proc) {
        String id = dec_proc.iden();
        if (ts.contiene(id)){
            SemanticErrors.addError(dec_proc.leeFila(), dec_proc.leeCol(), "id already linked: "+ id);
        }
        else{
            ts.inserta(id, dec_proc);
        }
        
        ts = new SymbolTable(ts);
		dec_proc.parsfop().procesa(this);
        dec_proc.parsfop().procesa2(this);
		dec_proc.bloq().procesa(this);	
        ts = ts.parent;
	}

	@Override
	public void procesa(Si_parsF si_parsF) {
		si_parsF.parsf().procesa(this);
	}

	@Override
	public void procesa(No_parsF no_parsF) {}

	@Override
	public void procesa(Muchos_parsF muchos_parsF) {
		muchos_parsF.parsF().procesa(this);
		muchos_parsF.parF().procesa(this);
	}

	@Override
	public void procesa(Un_parF un_parF) {
		un_parF.parF().procesa(this);
	}

	@Override
	public void procesa(ParamF paramF) {
		paramF.tipo().procesa(this);
        String id = paramF.iden();
        if (ts.contiene(id)){
            SemanticErrors.addError(paramF.leeFila(), paramF.leeCol(), "id already linked: "+ id);
        }
        else{
            ts.inserta(id, paramF);
        }
	}
	
	@Override
	public void procesa(Param param) {
		param.tipo().procesa(this);
        String id = param.iden();
        if (ts.contiene(id)){
            SemanticErrors.addError(param.leeFila(), param.leeCol(), "id already linked: "+ id);
        }
        else{
            ts.inserta(id, param);
        }
	}

	@Override
	public void procesa(Muchas_instrs muchas_instrs) {
		muchas_instrs.instrs().procesa(this);
		muchas_instrs.instr().procesa(this);
	}

	@Override
	public void procesa(Una_instr una_instr) {
		una_instr.instr().procesa(this);
	}

	@Override
	public void procesa(Instr_eval instr_eval) {
		instr_eval.exp().procesa(this);
	}

	@Override
	public void procesa(Instr_if instr_if) {
		instr_if.exp().procesa(this);
        instr_if.bloq().procesa(this);
	}

	@Override
	public void procesa(Instr_ifelse instr_ifelse) {
		instr_ifelse.exp().procesa(this);
		instr_ifelse.bloq1().procesa(this);
		instr_ifelse.bloq2().procesa(this);
	}

	@Override
	public void procesa(Instr_while instr_while) {
		instr_while.exp().procesa(this);
		instr_while.bloq().procesa(this);
	}

	@Override
	public void procesa(Instr_read instr_read) {
		instr_read.exp().procesa(this);
	}

	@Override
	public void procesa(Instr_write instr_write) {
		instr_write.exp().procesa(this);
	}

	@Override
	public void procesa(Instr_nl instr_nl) {}

	@Override
	public void procesa(Instr_new instr_new) {
		instr_new.exp().procesa(this);
	}

	@Override
	public void procesa(Instr_del instr_del) {
		instr_del.exp().procesa(this);
	}

	@Override
	public void procesa(Instr_call instr_call) {
		instr_call.vinculo = ts.valorDe(instr_call.id());
		if (instr_call.vinculo == null){
            SemanticErrors.addError(instr_call.leeFila(), instr_call.leeCol(), "cannot find the link of: "+ instr_call.id());
        }
		instr_call.parsreop().procesa(this);
	}

	@Override
	public void procesa(Instr_bloque instr_bloque) {
		instr_bloque.bloq().procesa(this);
	}

	@Override
	public void procesa(Si_parsRe si_parsRe) {
		si_parsRe.parsre().procesa(this);
	}

	@Override
	public void procesa(No_parsRe no_parsRe) {}

	@Override
	public void procesa(Muchos_parsRe muchos_parsRe) {
		muchos_parsRe.parsF().procesa(this);
		muchos_parsRe.parF().procesa(this);
	}

	@Override
	public void procesa(Un_parRe un_parRe) {
		un_parRe.parF().procesa(this);
	}

    // infixes (binary operations)
    private void vinculaBin(ExpBin exp){
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }
	@Override
	public void procesa(Suma suma) {		
		vinculaBin(suma);			
	}
	@Override
	public void procesa(Resta resta) {
		vinculaBin(resta);
	}
	@Override
	public void procesa(Mul mul) {
		vinculaBin(mul);
	}
	@Override
	public void procesa(Div div) {
		vinculaBin(div);
	}
	@Override
	public void procesa(Mod mod) {
		vinculaBin(mod);
	}
	@Override
	public void procesa(Asig asig) {
		vinculaBin(asig);
	}
	@Override
	public void procesa(MenorI menorI) {
		vinculaBin(menorI);
	}
	@Override
	public void procesa(Menor menor) {
		vinculaBin(menor);
	}

	@Override
	public void procesa(MayorI mayorI) {
		vinculaBin(mayorI);
	}
	@Override
	public void procesa(Mayor mayor) {
		vinculaBin(mayor);
	}
	@Override
	public void procesa(Igual igual) {
		vinculaBin(igual);
	}
	@Override
	public void procesa(Distint distint) {
		vinculaBin(distint);
	}
	@Override
	public void procesa(And and) {
		vinculaBin(and);
	}
	@Override
	public void procesa(Or or) {
		vinculaBin(or);
	}


    // prefixes
	@Override
	public void procesa(Negacion negacion) {
		negacion.opnd0().procesa(this);
	}
	@Override
	public void procesa(MenosUnario menosUnario) {
		menosUnario.opnd0().procesa(this);
	}

    // postfixes (designadores)
	@Override
	public void procesa(Indexacion indexacion) {
		indexacion.opnd0().procesa(this);	
		indexacion.opnd1().procesa(this);
	}

	@Override
	public void procesa(Acceso acceso) {
		acceso.opnd0().procesa(this);
	}

	@Override
	public void procesa(Indireccion indireccion) {
		indireccion.opnd0().procesa(this);		
	}

    // iden and literals
	@Override
	public void procesa(Iden iden) {
		iden.vinculo = ts.valorDe(iden.id());
        if (iden.vinculo == null){
            SemanticErrors.addError(iden.leeFila(), iden.leeCol(), "cannot find the link of: "+ iden.id());
        }
	}
	@Override
	public void procesa(Lit_ent lit_ent) {}
	@Override
	public void procesa(Lit_real lit_real) {}
	@Override
	public void procesa(TRUE true1) {}
	@Override
	public void procesa(FALSE false1) {}
	@Override
	public void procesa(Lit_cadena lit_cadena) {}
	@Override
	public void procesa(NULL null1) {}


/*
 * Segunda pasada
 */

    // @Override
    // public void procesa2(Si_tipo si_tipo) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
    // }

    // @Override
    // public void procesa2(No_tipo no_tipo) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
    // }

    @Override
    public void procesa2(Tipo_lista tipo_lista) {
        tipo_lista.tipo().procesa2(this);
    }

    @Override
    public void procesa2(Tipo_circum tipo_circum) {
        if (tipo_circum.tipo() instanceof Tipo_iden){
            Tipo_iden tipo = (Tipo_iden)(tipo_circum.tipo());
            tipo.vinculo = ts.valorDe(tipo.identificador());
            if(tipo.vinculo == null){
                SemanticErrors.addError(tipo.leeFila(), tipo.leeCol(), "cannot find the link of: "+ tipo.identificador());
            }
			else{
				// System.out.println(tipo + " " + tipo.leeFila() + ":" + tipo.leeCol() + " linked to " + tipo.vinculo.leeFila()+":"+tipo.vinculo.leeCol());
			}  
        }
        else{
            tipo_circum.tipo().procesa2(this); 
        }
    }

    @Override
    public void procesa2(Tipo_struct tipo_struct) {
        tipo_struct.campos().procesa2(this);
    }

    @Override
    public void procesa2(Tipo_iden tipo_iden) {}
    @Override
    public void procesa2(Tipo_int tipo_int) {}
    @Override
    public void procesa2(Tipo_real tipo_real) {}
    @Override
    public void procesa2(Tipo_bool tipo_bool) {}
    @Override
    public void procesa2(Tipo_string tipo_string) {}
    @Override

    public void procesa2(Muchos_campos muchos_campos) {
        muchos_campos.campos().procesa2(this);
		muchos_campos.campo().procesa2(this);
    }

    @Override
    public void procesa2(Un_campo un_campo) {
        un_campo.campo().procesa2(this);
    }

    @Override
    public void procesa2(Crea_campo crea_campo) {
        crea_campo.tipo().procesa2(this);
    }

    @Override
    public void procesa2(Muchas_decs muchas_decs) {
        muchas_decs.decs().procesa2(this);
		muchas_decs.dec().procesa2(this);
    }

    @Override
    public void procesa2(Una_dec una_dec) {
        una_dec.dec().procesa2(this);
    }

    @Override
    public void procesa2(Dec_variable dec_variable) {
        dec_variable.tipo().procesa2(this);
    }

    @Override
    public void procesa2(Dec_tipo dec_tipo) {
        dec_tipo.tipo().procesa2(this);
    }

    @Override
    public void procesa2(Dec_proc dec_proc) {}

    @Override
    public void procesa2(Si_parsF si_parsF) {
        si_parsF.parsf().procesa2(this);
    }

    @Override
    public void procesa2(No_parsF no_parsF) {}

    @Override
    public void procesa2(Muchos_parsF muchos_parsF) {
        muchos_parsF.parsF().procesa2(this);
		muchos_parsF.parF().procesa2(this);
    }

    @Override
    public void procesa2(Un_parF un_parF) {
        un_parF.parF().procesa2(this);
    }

    @Override
    public void procesa2(ParamF paramF) {
        paramF.tipo().procesa2(this);
    }

    @Override
    public void procesa2(Param param) {
        param.tipo().procesa2(this);
    }
}
