package asint;
import java.util.Stack;

import asint.SintaxisAbstractaTiny.*;

public class Etiquetado implements Procesamiento{

    private int etq;
    private Stack<Dec_proc> sub_pendientes;
    public Etiquetado(){
        etq = 0;
        sub_pendientes = new Stack<Dec_proc>();
    }


    @Override
    public void procesa(Prog prog) {
        prog.prim = etq;
        // recolecta_procs
		prog.bloq().decsOp().procesa(this);
		prog.bloq().instrsOp().procesa(this);
        etq++;
        while(!sub_pendientes.empty()){
            Dec_proc sub = sub_pendientes.pop();
			sub.prim = etq;
            etq++;
            sub.bloq().decsOp().procesa(this);
            sub.bloq().instrsOp().procesa(this);
            etq+=2;
			sub.sig = etq;
        }
        prog.sig = etq;
    }

    @Override
    public void procesa(Bloq bloq) {
        bloq.prim = etq;
        bloq.decsOp().procesa(this);
		bloq.instrsOp().procesa(this);
        bloq.sig = etq;
    }

    @Override
    public void procesa(Si_instrs si_instrs) {
        si_instrs.prim = etq;
        si_instrs.instrs().procesa(this);
        si_instrs.sig = etq;
    }

    @Override
    public void procesa(No_instrs no_instrs) {}

	@Override
	public void procesa(Muchas_instrs muchas_instrs) {
        muchas_instrs.prim = etq;
		muchas_instrs.instrs().procesa(this);
		muchas_instrs.instr().procesa(this);
        muchas_instrs.sig = etq;
	}

	@Override
	public void procesa(Una_instr una_instr) {
        una_instr.prim = etq;
		una_instr.instr().procesa(this);
        una_instr.sig = etq;
	}

	@Override
	public void procesa(Instr_eval instr_eval) {
        instr_eval.prim = etq;
		instr_eval.exp().procesa(this);
		etq++;
        instr_eval.sig = etq;
	}

	@Override
	public void procesa(Instr_if instr_if) {
        instr_if.prim = etq;
		instr_if.exp().procesa(this);
        etiquetado_acc_val(instr_if.exp());
        etq++;
		instr_if.bloq().procesa(this);
        instr_if.sig = etq;
	}

	@Override
	public void procesa(Instr_ifelse instr_ifelse) {
        instr_ifelse.prim = etq;
		instr_ifelse.exp().procesa(this);
        etiquetado_acc_val(instr_ifelse.exp());
        etq++;
		instr_ifelse.bloq1().procesa(this);
        etq++;
		instr_ifelse.bloq2().procesa(this);
        instr_ifelse.sig = etq;
	}

	@Override
	public void procesa(Instr_while instr_while) {
        instr_while.prim = etq;
		instr_while.exp().procesa(this);
        etiquetado_acc_val(instr_while.exp());
        etq++;
		instr_while.bloq().procesa(this);
        etq++;
        instr_while.sig = etq;
	}

	@Override
	public void procesa(Instr_read instr_read) {
        instr_read.prim = etq;
		instr_read.exp().procesa(this);
		etq+=2;
        instr_read.sig = etq;
	}

	@Override
	public void procesa(Instr_write instr_write) {
        instr_write.prim = etq;
		instr_write.exp().procesa(this);
		etiquetado_acc_val(instr_write.exp());
        etq++;
        instr_write.sig = etq;
	}

	@Override
	public void procesa(Instr_nl instr_nl) {
        instr_nl.prim = etq;
        etq++;
        instr_nl.sig = etq;
    }

	@Override
	public void procesa(Instr_new instr_new) {
        instr_new.prim = etq;
		instr_new.exp().procesa(this);
		etq += 2;
        instr_new.sig = etq;
	}

	@Override
	public void procesa(Instr_del instr_del) {
        instr_del.prim = etq;
		instr_del.exp().procesa(this);
		etq+=7;
        instr_del.sig = etq;
	}

	@Override
	public void procesa(Instr_call instr_call) {
        instr_call.prim = etq;
        Dec_proc dec_proc = (Dec_proc)instr_call.vinculo;
		ParsFOp parsFOp = dec_proc.parsfop();
        ParsReOp parsReOp = instr_call.parsreop();
		etq++;
		
		if(parsFOp instanceof Si_parsF){
            Si_parsF si_parsF = (Si_parsF) parsFOp;
			Si_parsRe si_parsRe = (Si_parsRe) parsReOp;
            etiquetado_paso_params(si_parsF.parsf(), si_parsRe.parsre());
        }
        etq++;
        instr_call.sig = etq;
	}

	@Override
	public void procesa(Instr_bloque instr_bloque) {
        instr_bloque.prim = etq;
		instr_bloque.bloq().procesa(this);
        instr_bloque.sig = etq;
	}

	@Override
	public void procesa(Si_parsRe si_parsRe) {}

	@Override
	public void procesa(No_parsRe no_parsRe) {}

	@Override
	public void procesa(Muchos_parsRe muchos_parsRe) {}

	@Override
	public void procesa(Un_parRe un_parRe) {}

    // infixes (binary operations)

    @Override
	public void procesa(Asig asig) {
        asig.prim = etq;
		asig.opnd0().procesa(this);
        etq++;
        asig.opnd1().procesa(this);
		if((TipoUtil.REF(asig.opnd0().t) instanceof Tipo_real)&&
        (TipoUtil.REF(asig.opnd1().t) instanceof Tipo_int))
        {
            etiquetado_acc_val(asig.opnd1());
            etq+=2;
        }
        else{
            if(TipoUtil.esDesignador(asig.opnd1)){
                etq++;
            }
            else{
                etq++;
            }
        }
		//asig.opnd0().procesa(this);
		//etiquetado_acc_val(asig.opnd0());
		etq++;
        asig.sig = etq;
	}

	@Override
	public void procesa(Suma suma) {
        suma.prim = etq;		
		etiquetado_opnds(suma);
        etq++;		
        suma.sig = etq;
	}
	@Override
	public void procesa(Resta resta) {
        resta.prim = etq;
		etiquetado_opnds(resta);
        etq++;
        resta.sig = etq;
	}
	@Override
	public void procesa(Mul mul) {
        mul.prim = etq;
		etiquetado_opnds(mul);
        etq++;
        mul.sig = etq;
	}
	@Override
	public void procesa(Div div) {
        div.prim = etq;
		etiquetado_opnds(div);
        etq++;
        div.sig = etq;
	}
	@Override
	public void procesa(Mod mod) {
        mod.prim = etq;
		etiquetado_opnds(mod);
        etq++;
        mod.sig = etq;
	}
	
	@Override
	public void procesa(MenorI menorI) {
        menorI.prim = etq;
		etiquetado_opnds(menorI);
        etq++;
        menorI.sig = etq;
	}
	@Override
	public void procesa(Menor menor) {
        menor.prim = etq;
		etiquetado_opnds(menor);
        etq++;
        menor.sig = etq;
	}
	@Override
	public void procesa(MayorI mayorI) {
        mayorI.prim = etq;
		etiquetado_opnds(mayorI);
        etq++;
        mayorI.sig = etq;
	}
	@Override
	public void procesa(Mayor mayor) {
        mayor.prim = etq;
		etiquetado_opnds(mayor);
        etq++;
        mayor.sig = etq;
	}
	@Override
	public void procesa(Igual igual) {
        igual.prim = etq;
		etiquetado_opnds(igual);
        etq++;
        igual.sig = etq;
	}
	@Override
	public void procesa(Distint distint) {
        distint.prim = etq;
		etiquetado_opnds(distint);
        etq++;
        distint.sig = etq;
	}
	@Override
	public void procesa(And and) {
        and.prim = etq;
		etiquetado_opnds(and);
        etq++;
        and.sig = etq;
	}
	@Override
	public void procesa(Or or) {
        or.prim = etq;
		etiquetado_opnds(or);
        etq++;
        or.sig = etq;
	}


    // prefixes
	@Override
	public void procesa(Negacion negacion) {
        negacion.prim = etq;
		negacion.opnd0().procesa(this);
		etiquetado_acc_val(negacion.opnd0());
        etq++;
        negacion.sig = etq;
	}

	@Override
	public void procesa(MenosUnario menosUnario) {
        menosUnario.prim = etq;
		menosUnario.opnd0().procesa(this);
		etiquetado_acc_val(menosUnario.opnd0());
        etq++;
        menosUnario.sig = etq;
	}

    // postfixes (designadores)
	@Override
	public void procesa(Indexacion indexacion) {
        indexacion.prim = etq;
		indexacion.opnd0().procesa(this);	
		indexacion.opnd1().procesa(this);
		etiquetado_acc_val(indexacion.opnd1());
        etq++;
        indexacion.sig = etq;
	}

	@Override
	public void procesa(Acceso acceso) {
        acceso.prim = etq;
		acceso.opnd0().procesa(this);
		etq++;
        acceso.sig = etq;
	}

	@Override
	public void procesa(Indireccion indireccion) {
        indireccion.prim = etq;
		indireccion.opnd0().procesa(this);
		etq+=7;
        indireccion.sig = etq;
	}

    // iden and literals
	@Override
	public void procesa(Iden iden) {
        iden.prim = etq;
        if(iden.vinculo instanceof Dec_variable){
            etiquetado_acc_id((Dec_variable)iden.vinculo);
        }
        else if(iden.vinculo instanceof Param){
            etiquetado_acc_id((Param)iden.vinculo);
        }
        else{
            etiquetado_acc_id((ParamF)iden.vinculo);
        }
        iden.sig = etq;
	}

	@Override
	public void procesa(Lit_ent lit_ent) {etq++;;}
	@Override
	public void procesa(Lit_real lit_real) {etq++;}
	@Override
	public void procesa(TRUE true1) {etq++;}
	@Override
	public void procesa(FALSE false1) {etq++;}
	@Override
	public void procesa(Lit_cadena lit_cadena) {etq++;}
	@Override
	public void procesa(NULL null1) {etq++;}


    /* 
    extra functions
    */

    // recolecta procs
    @Override
    public void procesa(Si_decs si_decs) {
        si_decs.decs().procesa(this);
    }
    @Override
    public void procesa(No_decs no_decs) {}
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
	public void procesa(Dec_proc dec_proc) {
        sub_pendientes.push(dec_proc);
    }
    @Override
	public void procesa(Dec_variable dec_variable) {}
    @Override
	public void procesa(Dec_tipo dec_tipo) {}

    // etiquetado-cod-opnds
    public void etiquetado_opnds(ExpBin E){
        E.opnd0().procesa(this);
        etiquetado_acc_val(E.opnd0);
        E.opnd1().procesa(this);
        etiquetado_acc_val(E.opnd1);
    }

    // etiquetado-cod-opnds-aritmetico
    public void etiquetado_opnds_aritmetico(ExpBin E){
        E.opnd0().procesa(this);
        etiquetado_acc_val(E.opnd0);
        if((TipoUtil.REF(E.t) instanceof Tipo_real)&&(TipoUtil.REF(E.opnd0.t) instanceof Tipo_int)){
            etq++;
        }
        E.opnd1().procesa(this);
        etiquetado_acc_val(E.opnd1);
        if((TipoUtil.REF(E.t) instanceof Tipo_real)&&(TipoUtil.REF(E.opnd1.t) instanceof Tipo_int)){
            etq++;
        }
    }

    // etiquetado-acc-val
    public void etiquetado_acc_val(Exp E){
        if(TipoUtil.esDesignador(E)){
            etq++;
        }
    }

    // etiquetado-paso-params
    public void etiquetado_paso_params(ParsF PF, ParsRe PR){
        if(PF instanceof Muchos_parsF){
            etiquetado_paso_param(PF.parF(), PR.parF());
            etiquetado_paso_params(PF.parsF(), PR.parsF());
        }
        else{
            etiquetado_paso_param(PF.parF(), PR.parF());
        }
    }

    // etiquetado-paso-param
    public void etiquetado_paso_param(ParF parf, Exp E){
        etq+=3;
        E.procesa(this);
        if(parf instanceof Param){
            if((TipoUtil.REF(parf.tipo()) instanceof Tipo_real)&&(TipoUtil.REF(E.t) instanceof Tipo_int)){
                etiquetado_acc_val(E);
                etq+=2;
            }
            else if(TipoUtil.esDesignador(E)){
                etq++;
            }
            else{
                etq++;
            }
        }
        else{
            etq++;
        }
    }


    // etiquetado-acc-id
    public void etiquetado_acc_id(Dec_variable dec){
        if(dec.nivel == 0){
            etq++;
        }
        else{
            etiquetado_acc_var(dec.nivel, dec.dir);
        }
    }
    public void etiquetado_acc_id(Param param){
        etiquetado_acc_var(param.nivel,param.dir);
    }
    public void etiquetado_acc_id(ParamF paramf){
        etiquetado_acc_var(paramf.nivel,paramf.dir);
        etq++;
    }

    public void etiquetado_acc_var(int nivel, int dir){
        etq+=3;
    }



    /*
    unused
    */ 
    @Override
	public void procesa(Tipo_lista tipo_lista) {}
	@Override
	public void procesa(Tipo_circum tipo_circum) {}
	@Override
	public void procesa(Tipo_struct tipo_struct) {}
    @Override
	public void procesa(Tipo_iden tipo_iden) {}
	@Override
	public void procesa(Tipo_int tipo_int) {}
	@Override
	public void procesa(Tipo_real tipo_real) {}
	@Override
	public void procesa(Tipo_bool tipo_bool) {}
	@Override
	public void procesa(Tipo_string tipo_string) {}
	@Override
	public void procesa(Muchos_campos muchos_campos) {}
	@Override
	public void procesa(Un_campo un_campo) {}
	@Override
	public void procesa(Crea_campo crea_campo) {}
	@Override
	public void procesa(Si_parsF si_parsF) {}
	@Override
	public void procesa(No_parsF no_parsF) {}
	@Override
	public void procesa(Muchos_parsF muchos_parsF) {}
	@Override
	public void procesa(Un_parF un_parF) {}
	@Override
	public void procesa(ParamF paramF) {}
	@Override
	public void procesa(Param param) {}



	@Override
	public void procesa2(Tipo_lista tipo_lista) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_circum tipo_circum) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_struct tipo_struct) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_iden tipo_iden) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_int tipo_int) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_real tipo_real) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_bool tipo_bool) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_string tipo_string) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Muchos_campos muchos_campos) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Un_campo un_campo) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Crea_campo crea_campo) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Muchas_decs muchas_decs) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Una_dec una_dec) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Dec_variable dec_variable) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Dec_tipo dec_tipo) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Dec_proc dec_proc) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Si_parsF si_parsF) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(No_parsF no_parsF) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Muchos_parsF muchos_parsF) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Un_parF un_parF) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(ParamF paramF) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Param param) {
		// TODO Auto-etiquetadoerated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}
    
}
