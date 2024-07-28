package asint;
import asint.SintaxisAbstractaTiny.*;
import maquinap.MaquinaP;
import java.util.Stack;

public class Generacion implements Procesamiento{

    private MaquinaP m;
    private Stack<Dec_proc> sub_pendientes;

    public Generacion(MaquinaP m){
        this.m = m;
        sub_pendientes = new Stack<Dec_proc>();
    }

    @Override
    public void procesa(Prog prog) {
        prog.bloq().decsOp().procesa(this);
		prog.bloq().instrsOp().procesa(this);
        m.emit(m.stop());
        while(!sub_pendientes.empty()){
            Dec_proc sub = sub_pendientes.pop();
            m.emit(m.desapilad(sub.nivel));
            sub.bloq().decsOp().procesa(this);
            sub.bloq().instrsOp().procesa(this);
            m.emit(m.desactiva(sub.nivel, sub.tam));
            m.emit(m.ir_ind());
        }
    }

    @Override
    public void procesa(Bloq bloq) {
        bloq.decsOp().procesa(this);
		bloq.instrsOp().procesa(this);
    }

    @Override
    public void procesa(Si_instrs si_instrs) {
        si_instrs.instrs().procesa(this);
    }

    @Override
    public void procesa(No_instrs no_instrs) {}

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
		m.emit(m.pop());
	}

	@Override
	public void procesa(Instr_if instr_if) {
		instr_if.exp().procesa(this);
        gen_acc_val(instr_if.exp());
        m.emit(m.ir_f(instr_if.sig));
		instr_if.bloq().procesa(this);
	}

	@Override
	public void procesa(Instr_ifelse instr_ifelse) {
		instr_ifelse.exp().procesa(this);
        gen_acc_val(instr_ifelse.exp());
        m.emit(m.ir_f(instr_ifelse.bloq2().prim));
		instr_ifelse.bloq1().procesa(this);
        m.emit(m.ir_a(instr_ifelse.bloq2().sig));
		instr_ifelse.bloq2().procesa(this);
	}

	@Override
	public void procesa(Instr_while instr_while) {
		instr_while.exp().procesa(this);
        gen_acc_val(instr_while.exp());
        m.emit(m.ir_f(instr_while.sig));
		instr_while.bloq().procesa(this);
        m.emit(m.ir_a(instr_while.prim));
	}

	@Override
	public void procesa(Instr_read instr_read) {
		instr_read.exp().procesa(this);
		m.emit(m.read(instr_read.exp().t));
        m.emit(m.store());
	}

	@Override
	public void procesa(Instr_write instr_write) {
		instr_write.exp().procesa(this);
		gen_acc_val(instr_write.exp());
        m.emit(m.write());
	}

	@Override
	public void procesa(Instr_nl instr_nl) {
        m.emit(m.nl());
    }

	@Override
	public void procesa(Instr_new instr_new) {
		instr_new.exp().procesa(this);
		m.emit(m.alloc(((Tipo_circum)TipoUtil.REF(instr_new.exp().t)).tipo().tam));
        m.emit(m.store());
	}

	@Override
	public void procesa(Instr_del instr_del) {
		instr_del.exp().procesa(this);
		m.emit(m.dup());
        m.emit(m.apila_int(-1));
        m.emit(m.distint());
        m.emit(m.ir_f(instr_del.sig-1));
        m.emit(m.dealloc(((Tipo_circum)instr_del.exp().t).tam));
        m.emit(m.ir_a(instr_del.sig));
        m.emit(m.error());
	}

	@Override
	public void procesa(Instr_call instr_call) {
        Dec_proc dec_proc = (Dec_proc)instr_call.vinculo;
		ParsFOp parsFOp = dec_proc.parsfop();
        ParsReOp parsReOp = instr_call.parsreop();
		m.emit(m.activa(dec_proc.nivel, dec_proc.tam, instr_call.sig));
		
		if(parsFOp instanceof Si_parsF){
            Si_parsF si_parsF = (Si_parsF) parsFOp;
			Si_parsRe si_parsRe = (Si_parsRe) parsReOp;
            gen_paso_params(si_parsF.parsf(), si_parsRe.parsre());
        }
        m.emit(m.ir_a(dec_proc.prim));
	}

	@Override
	public void procesa(Instr_bloque instr_bloque) {
		instr_bloque.bloq().procesa(this);
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
		asig.opnd0().procesa(this);
        m.emit(m.dup());
        asig.opnd1().procesa(this);
		if((TipoUtil.REF(asig.opnd0().t) instanceof Tipo_real)&&
        (TipoUtil.REF(asig.opnd1().t) instanceof Tipo_int))
        {
            gen_acc_val(asig.opnd1());
            m.emit(m.int2real());
            m.emit(m.store());
        }
        else{
            if(TipoUtil.esDesignador(asig.opnd1)){
                m.emit(m.copy(asig.opnd0().t.tam));
            }
            else{
                m.emit(m.store());
            }
        }
		m.emit(m.fetch());
		//asig.opnd0().procesa(this);
		//gen_acc_val(asig.opnd0());
	}

	@Override
	public void procesa(Suma suma) {		
		gen_cod_opnds(suma);
        m.emit(m.suma());		
	}
	@Override
	public void procesa(Resta resta) {
		gen_cod_opnds(resta);
        m.emit(m.resta());	
	}
	@Override
	public void procesa(Mul mul) {
		gen_cod_opnds(mul);
        m.emit(m.multiplicacion());
	}
	@Override
	public void procesa(Div div) {
		gen_cod_opnds(div);
        m.emit(m.division());
	}
	@Override
	public void procesa(Mod mod) {
		gen_cod_opnds(mod);
        m.emit(m.modulo());
	}
	
	@Override
	public void procesa(MenorI menorI) {
		gen_cod_opnds(menorI);
        m.emit(m.menorI());
	}
	@Override
	public void procesa(Menor menor) {
		gen_cod_opnds(menor);
        m.emit(m.menor());
	}
	@Override
	public void procesa(MayorI mayorI) {
		gen_cod_opnds(mayorI);
        m.emit(m.mayorI());
	}
	@Override
	public void procesa(Mayor mayor) {
		gen_cod_opnds(mayor);
        m.emit(m.mayor());
	}
	@Override
	public void procesa(Igual igual) {
		gen_cod_opnds(igual);
        m.emit(m.igual());
	}
	@Override
	public void procesa(Distint distint) {
		gen_cod_opnds(distint);
        m.emit(m.distint());
	}
	@Override
	public void procesa(And and) {
		gen_cod_opnds(and);
        m.emit(m.and());
	}
	@Override
	public void procesa(Or or) {
		gen_cod_opnds(or);
        m.emit(m.or());
	}


    // prefixes
	@Override
	public void procesa(Negacion negacion) {
		negacion.opnd0().procesa(this);
		gen_acc_val(negacion.opnd0());
        m.emit(m.negacion());

	}
	@Override
	public void procesa(MenosUnario menosUnario) {
		menosUnario.opnd0().procesa(this);
		gen_acc_val(menosUnario.opnd0());
        m.emit(m.menosUnario());
	}

    // postfixes (designadores)
	@Override
	public void procesa(Indexacion indexacion) {
		indexacion.opnd0().procesa(this);	
		indexacion.opnd1().procesa(this);
		gen_acc_val(indexacion.opnd1());
        // opnd0.dir + opnd0.tipo().tam * opnd1.val
        m.emit(m.indx(((Tipo_lista)TipoUtil.REF(indexacion.opnd0().t)).tipo().tam));
	}

	@Override
	public void procesa(Acceso acceso) {
		acceso.opnd0().procesa(this);
        Tipo_struct T = (Tipo_struct)TipoUtil.REF(acceso.opnd0().t);
        int desp = T.nm.get(acceso.opnd1String()).desp;
		m.emit(m.acc(desp));
	}

	@Override
	public void procesa(Indireccion indireccion) {
		indireccion.opnd0().procesa(this);
		m.emit(m.dup());
        m.emit(m.apila_int(-1));
        m.emit(m.distint());
        m.emit(m.ir_f(indireccion.sig-1));
        m.emit(m.fetch());
        m.emit(m.ir_a(indireccion.sig));
        m.emit(m.error());
	}

    // iden and literals
	@Override
	public void procesa(Iden iden) {
        if(iden.vinculo instanceof Dec_variable){
            gen_acc_id((Dec_variable)iden.vinculo);
        }
        else if(iden.vinculo instanceof Param){
            gen_acc_id((Param)iden.vinculo);
        }
        else{
            gen_acc_id((ParamF)iden.vinculo);
        }
	}

	@Override
	public void procesa(Lit_ent lit_ent) {m.emit(m.apila_int(Integer.parseInt(lit_ent.valor())));}
	@Override
	public void procesa(Lit_real lit_real) {m.emit(m.apila_real(Float.parseFloat(lit_real.valor())));}
	@Override
	public void procesa(TRUE true1) {m.emit(m.apila_bool(true));}
	@Override
	public void procesa(FALSE false1) {m.emit(m.apila_bool(false));}
	@Override
	public void procesa(Lit_cadena lit_cadena) {
		String myString = lit_cadena.valor().substring(1, lit_cadena.valor().length()-1);
		m.emit(m.apila_cadena(myString));
	}
	@Override
	public void procesa(NULL null1) {m.emit(m.apila_null());}


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

    // gen-cod-opnds
    public void gen_cod_opnds(ExpBin E){
        E.opnd0().procesa(this);
        gen_acc_val(E.opnd0);
        E.opnd1().procesa(this);
        gen_acc_val(E.opnd1);
    }

    // gen-cod-opnds-aritmetico
    public void gen_cod_opnds_aritmetico(ExpBin E){
        E.opnd0().procesa(this);
        gen_acc_val(E.opnd0);
        if((TipoUtil.REF(E.t) instanceof Tipo_real)&&(TipoUtil.REF(E.opnd0.t) instanceof Tipo_int)){
            m.emit(m.int2real());
        }
        E.opnd1().procesa(this);
        gen_acc_val(E.opnd1);
        if((TipoUtil.REF(E.t) instanceof Tipo_real)&&(TipoUtil.REF(E.opnd1.t) instanceof Tipo_int)){
            m.emit(m.int2real());
        }
    }

    // gen-acc-val
    public void gen_acc_val(Exp E){
        if(TipoUtil.esDesignador(E)){
            m.emit(m.fetch());
        }
    }

    // gen-paso-params
    public void gen_paso_params(ParsF PF, ParsRe PR){
        if(PF instanceof Muchos_parsF){
            gen_paso_param(PF.parF(), PR.parF());
            gen_paso_params(PF.parsF(), PR.parsF());
        }
        else{
            gen_paso_param(PF.parF(), PR.parF());
        }
    }

    // gen-paso-param
    public void gen_paso_param(ParF parf, Exp E){
        m.emit(m.dup());
        m.emit(m.apila_int(parf.dir));
		m.emit(m.suma());
        E.procesa(this);
        if(parf instanceof Param){
            if((TipoUtil.REF(parf.tipo()) instanceof Tipo_real)&&(TipoUtil.REF(E.t) instanceof Tipo_int)){
                gen_acc_val(E);
                m.emit(m.int2real());
                m.emit(m.store());
            }
            else if(TipoUtil.esDesignador(E)){
                m.emit(m.copy(parf.tipo().tam));
            }
            else{
                m.emit(m.store());
            }
        }
        else{
            m.emit(m.store());
        }
    }


    // gen-acc-id
    public void gen_acc_id(Dec_variable dec){
        if(dec.nivel == 0){
            m.emit(m.apila_int(dec.dir));
        }
        else{
            gen_acc_var(dec.nivel, dec.dir);
        }
    }
    public void gen_acc_id(Param param){
        gen_acc_var(param.nivel,param.dir);
    }
    public void gen_acc_id(ParamF paramf){
        gen_acc_var(paramf.nivel,paramf.dir);
        m.emit(m.fetch());
    }

    public void gen_acc_var(int nivel, int dir){
        m.emit(m.apilad(nivel));
        m.emit(m.apila_int(dir));
        m.emit(m.suma());
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
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_circum tipo_circum) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_struct tipo_struct) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_iden tipo_iden) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_int tipo_int) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_real tipo_real) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_bool tipo_bool) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Tipo_string tipo_string) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Muchos_campos muchos_campos) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Un_campo un_campo) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Crea_campo crea_campo) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Muchas_decs muchas_decs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Una_dec una_dec) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Dec_variable dec_variable) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Dec_tipo dec_tipo) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Dec_proc dec_proc) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Si_parsF si_parsF) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(No_parsF no_parsF) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Muchos_parsF muchos_parsF) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Un_parF un_parF) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(ParamF paramF) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public void procesa2(Param param) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}
}
