package asint;
import asint.SintaxisAbstractaTiny.*;

public class AsigEspacio implements Procesamiento{
    private int dir;
	private int max_dir; // so far this max_dir still doesn't make sense to me as it is not stored anywhere
    private int nivel;
    public AsigEspacio(){
        dir = 0;
        nivel = 0;
    }

	// helper function
	private void inc_dir(int inc){
		dir += inc;
		if (dir > max_dir){
			max_dir = dir;
		}
	}
	

    @Override
    public void procesa(Prog prog) {
        prog.bloq().procesa(this);
    }

    @Override
    public void procesa(Bloq bloq) {
        int dir_ant = dir;
		bloq.decsOp().procesa(this);
		bloq.instrsOp().procesa(this);
        dir = dir_ant;
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

	@Override
	public void procesa(Tipo_lista tipo_lista) {
		tipo_lista.tipo().procesa(this);
        tipo_lista.tam = tipo_lista.tipo().tam * Integer.parseInt(tipo_lista.literalEntero());
	}

	@Override
	public void procesa(Tipo_circum tipo_circum) {
        Tipo tipo = tipo_circum.tipo();
        if(!(tipo instanceof Tipo_iden)){
            tipo.procesa(this);
        }
        tipo_circum.tam = 1;
	}

	@Override
	public void procesa(Tipo_struct tipo_struct) {
        int dir_ant = dir;
		dir = 0;
		tipo_struct.campos().procesa(this);
        tipo_struct.tam = dir;
        dir = dir_ant;
	}

	@Override
	public void procesa(Tipo_iden tipo_iden) {
        Dec_tipo dec = (Dec_tipo) tipo_iden.vinculo;
        tipo_iden.tam = dec.tipo().tam;
	}

	@Override
	public void procesa(Tipo_int tipo_int) {tipo_int.tam = 1;}
	@Override
	public void procesa(Tipo_real tipo_real) {tipo_real.tam = 1;}
	@Override
	public void procesa(Tipo_bool tipo_bool) {tipo_bool.tam = 1;}
	@Override
	public void procesa(Tipo_string tipo_string) {tipo_string.tam=1;}

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
		crea_campo.desp = dir;
		crea_campo.tipo().procesa(this);
		dir += crea_campo.tipo().tam;
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
		dec_variable.dir = dir;
		dec_variable.nivel = nivel;
        inc_dir(dec_variable.tipo().tam);
	}

	@Override
	public void procesa(Dec_tipo dec_tipo) {
		dec_tipo.tipo().procesa(this);
	}

	@Override
	public void procesa(Dec_proc dec_proc) {
        int dir_ant = dir;
		int max_dir_ant = max_dir;
		nivel ++;
		dec_proc.nivel = nivel;
		dir = 0;
		max_dir = 0;
		dec_proc.parsfop().procesa(this);
        dec_proc.parsfop().procesa2(this);
		dec_proc.bloq().decsOp().procesa(this);
		dec_proc.bloq().decsOp().procesa2(this);
		dec_proc.bloq().instrsOp().procesa(this);
		dec_proc.tam = max_dir;
		dir = dir_ant;
		max_dir = max_dir_ant;
		nivel --;
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
        paramF.dir = dir;
		paramF.nivel = nivel;
		inc_dir(1);
	}
	
	@Override
	public void procesa(Param param) {
		param.tipo().procesa(this);
        param.dir = dir;
		param.nivel = nivel;
		inc_dir(param.tipo().tam);
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
		//instr_ifelse.exp().procesa(this);
		instr_ifelse.bloq1().procesa(this);
		instr_ifelse.bloq2().procesa(this);
	}

	@Override
	public void procesa(Instr_while instr_while) {
		//instr_while.exp().procesa(this);
		instr_while.bloq().procesa(this);
	}

	@Override
	public void procesa(Instr_read instr_read) {
		//instr_read.exp().procesa(this);
	}

	@Override
	public void procesa(Instr_write instr_write) {
		//instr_write.exp().procesa(this);
	}

	@Override
	public void procesa(Instr_nl instr_nl) {}

	@Override
	public void procesa(Instr_new instr_new) {
		//instr_new.exp().procesa(this);
	}

	@Override
	public void procesa(Instr_del instr_del) {
		//instr_del.exp().procesa(this);
	}

	@Override
	public void procesa(Instr_call instr_call) {
	}

	@Override
	public void procesa(Instr_bloque instr_bloque) {
		instr_bloque.bloq().procesa(this);
	}

	@Override
	public void procesa(Si_parsRe si_parsRe) {
		// si_parsRe.parsre().procesa(this);
	}

	@Override
	public void procesa(No_parsRe no_parsRe) {}

	@Override
	public void procesa(Muchos_parsRe muchos_parsRe) {
		// muchos_parsRe.parsF().procesa(this);
		// muchos_parsRe.parF().procesa(this);
	}

	@Override
	public void procesa(Un_parRe un_parRe) {
		// un_parRe.parF().procesa(this);
	}

    // infixes (binary operations)
    // private void vinculaBin(ExpBin exp){
        // exp.opnd0().procesa(this);
        // exp.opnd1().procesa(this);
    // }
	@Override
	public void procesa(Suma suma) {		
		// vinculaBin(suma);			
	}
	@Override
	public void procesa(Resta resta) {
		// vinculaBin(resta);
	}
	@Override
	public void procesa(Mul mul) {
		// vinculaBin(mul);
	}
	@Override
	public void procesa(Div div) {
		//vinculaBin(div);
	}
	@Override
	public void procesa(Mod mod) {
		//vinculaBin(mod);
	}
	@Override
	public void procesa(Asig asig) {
		//vinculaBin(asig);
	}
	@Override
	public void procesa(MenorI menorI) {
		//vinculaBin(menorI);
	}
	@Override
	public void procesa(Menor menor) {
		//vinculaBin(menor);
	}

	@Override
	public void procesa(MayorI mayorI) {
		//vinculaBin(mayorI);
	}
	@Override
	public void procesa(Mayor mayor) {
		//vinculaBin(mayor);
	}
	@Override
	public void procesa(Igual igual) {
		//vinculaBin(igual);
	}
	@Override
	public void procesa(Distint distint) {
		//vinculaBin(distint);
	}
	@Override
	public void procesa(And and) {
		//vinculaBin(and);
	}
	@Override
	public void procesa(Or or) {
		//vinculaBin(or);
	}


    // prefixes
	@Override
	public void procesa(Negacion negacion) {
		//negacion.opnd0().procesa(this);
	}
	@Override
	public void procesa(MenosUnario menosUnario) {
		//menosUnario.opnd0().procesa(this);
	}

    // postfixes (designadores)
	@Override
	public void procesa(Indexacion indexacion) {
		//indexacion.opnd0().procesa(this);	
		//indexacion.opnd1().procesa(this);
	}

	@Override
	public void procesa(Acceso acceso) {
		//acceso.opnd0().procesa(this);
	}

	@Override
	public void procesa(Indireccion indireccion) {
		//indireccion.opnd0().procesa(this);		
	}

    // iden and literals
	@Override
	public void procesa(Iden iden) {
		// iden.vinculo = ts.valorDe(iden.id());
        // if (iden.vinculo == null){
        //     SemanticErrors.addError(iden.leeFila(), iden.leeCol(), "cannot find the link of: "+ iden.id());
        // }
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
            Dec_tipo dec = (Dec_tipo) tipo.vinculo;
            tipo.tam = dec.tipo().tam;
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