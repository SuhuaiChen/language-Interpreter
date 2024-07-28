package asint;
import asint.SintaxisAbstractaTiny.*;

public class Tipado implements Procesamiento{

    @Override
    public void procesa(Prog prog) {
        prog.bloq().procesa(this);
		prog.t = prog.bloq().t;
    }

    @Override
    public void procesa(Bloq bloq) {
		bloq.decsOp().procesa(this);
		bloq.instrsOp().procesa(this);
        bloq.t = TipoUtil.ambosOK(bloq.decsOp().t, bloq.instrsOp().t);
    }

    @Override
    public void procesa(Si_decs si_decs) {
        si_decs.decs().procesa(this);
		si_decs.t = si_decs.decs().t;
    }

    @Override
    public void procesa(No_decs no_decs) {}

    @Override
    public void procesa(Si_instrs si_instrs) {
        si_instrs.instrs().procesa(this);
		si_instrs.t = si_instrs.instrs().t;
    }

    @Override
    public void procesa(No_instrs no_instrs) {}

/*
 * Primera Pasada
 */

	@Override
	public void procesa(Tipo_lista tipo_lista) {
		int num;
		tipo_lista.tipo().procesa(this);
		try{
			num = Integer.parseInt(tipo_lista.literalEntero());
		}
		catch(NumberFormatException e){
			num = -1;
		}
		if(num < 0){
			SemanticErrors.avisoError(tipo_lista,tipo_lista.tipo().t,"invalid list length");
		}
	}

	@Override
	public void procesa(Tipo_circum tipo_circum) {
        tipo_circum.tipo().procesa(this);
		tipo_circum.t = tipo_circum.tipo().t;
	}

	@Override
	public void procesa(Tipo_struct tipo_struct) {
		tipo_struct.campos().procesa(this);
		tipo_struct.t = tipo_struct.campos().t;
	}

	// it is fine to leave the t as empty because in runtime null is not an instance of error, which indicates null is ok
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
	public void procesa(Muchos_campos muchos_campos) {
		muchos_campos.campos().procesa(this);
		muchos_campos.campo().procesa(this);
		muchos_campos.t = TipoUtil.ambosOK(muchos_campos.campos().t, muchos_campos.campo().t);
	}

	@Override
	public void procesa(Un_campo un_campo) {
		un_campo.campo().procesa(this);
		un_campo.t = un_campo.campo().t;
	}

	@Override
	public void procesa(Crea_campo crea_campo) {
		crea_campo.tipo().procesa(this);
	}

	@Override
	public void procesa(Muchas_decs muchas_decs) {
		muchas_decs.decs().procesa(this);
		muchas_decs.dec().procesa(this);
		muchas_decs.t = TipoUtil.ambosOK(muchas_decs.decs().t, muchas_decs.dec().t);
	}

	@Override
	public void procesa(Una_dec una_dec) {
		una_dec.dec().procesa(this);
		una_dec.t = una_dec.dec().t;
	}

	@Override
	public void procesa(Dec_variable dec_variable) {
		dec_variable.tipo().procesa(this);

        dec_variable.t = dec_variable.tipo().t;
	}

	@Override
	public void procesa(Dec_tipo dec_tipo) {
		dec_tipo.tipo().procesa(this);
        dec_tipo.t = dec_tipo.tipo().t;
	}

	@Override
	public void procesa(Dec_proc dec_proc) {
		dec_proc.parsfop().procesa(this);
		dec_proc.bloq().procesa(this);	
        dec_proc.t = TipoUtil.ambosOK(dec_proc.parsfop().t, dec_proc.bloq().t);
	}

	@Override
	public void procesa(Si_parsF si_parsF) {
		si_parsF.parsf().procesa(this);
		si_parsF.t = si_parsF.parsf().t;
	}

	@Override
	public void procesa(No_parsF no_parsF) {}

	@Override
	public void procesa(Muchos_parsF muchos_parsF) {
		muchos_parsF.parsF().procesa(this);
		muchos_parsF.parF().procesa(this);
		muchos_parsF.t = TipoUtil.ambosOK(muchos_parsF.parsF().t, muchos_parsF.parF().t);
	}

	@Override
	public void procesa(Un_parF un_parF) {
		un_parF.parF().procesa(this);
		un_parF.t = un_parF.parF().t;
	}

	@Override
	public void procesa(ParamF paramF) {
		paramF.tipo().procesa(this);
        paramF.t = paramF.tipo().t;
	}
	
	@Override
	public void procesa(Param param) {
		param.tipo().procesa(this);
        param.t = param.tipo().t;
	}

	@Override
	public void procesa(Muchas_instrs muchas_instrs) {
		muchas_instrs.instrs().procesa(this);
		muchas_instrs.instr().procesa(this);
		muchas_instrs.t = TipoUtil.ambosOK(muchas_instrs.instrs().t, muchas_instrs.instr().t);
	}

	@Override
	public void procesa(Una_instr una_instr) {
		una_instr.instr().procesa(this);
		una_instr.t = una_instr.instr().t;
	}

	@Override
	public void procesa(Instr_eval instr_eval) {
		instr_eval.exp().procesa(this);
		instr_eval.t = TipoUtil.OK(instr_eval.exp().t);
	}

	@Override
	public void procesa(Instr_if instr_if) {
		instr_if.exp().procesa(this);
		instr_if.bloq().procesa(this);
		if( !(TipoUtil.REF(instr_if.exp().t) instanceof Tipo_bool) || (instr_if.bloq().t instanceof NameError)){
			SemanticErrors.avisoError(instr_if.exp(), instr_if.exp().t, instr_if.bloq().t, "the condition in if statement should be bool");
		}
	}

	@Override
	public void procesa(Instr_ifelse instr_ifelse) {
		instr_ifelse.exp().procesa(this);
		instr_ifelse.bloq1().procesa(this);
		instr_ifelse.bloq2().procesa(this);
		if( !(TipoUtil.REF(instr_ifelse.exp().t) instanceof Tipo_bool) || (instr_ifelse.bloq1().t instanceof NameError) || (instr_ifelse.bloq2().t instanceof NameError)){
		SemanticErrors.avisoError(instr_ifelse.exp(), instr_ifelse.exp().t, instr_ifelse.bloq1().t, instr_ifelse.bloq2().t, "the condition in if statement should be bool");
		}
	}

	@Override
	public void procesa(Instr_while instr_while) {
		instr_while.exp().procesa(this);
		instr_while.bloq().procesa(this);
		if(!(TipoUtil.REF(instr_while.exp().t) instanceof Tipo_bool) || (instr_while.bloq().t instanceof NameError)){
			SemanticErrors.avisoError(instr_while.exp(), instr_while.exp().t, instr_while.bloq().t, "the condition in while statement should be bool");
		}
	}

	@Override
	public void procesa(Instr_read instr_read) {
		instr_read.exp().procesa(this);
		Tipo tipoExp = TipoUtil.REF(instr_read.exp().t);
		if(!(((tipoExp instanceof Tipo_int)||(tipoExp instanceof Tipo_real)||(tipoExp instanceof Tipo_string)) && TipoUtil.esDesignador(instr_read.exp()))){
			SemanticErrors.avisoError(instr_read.exp(), tipoExp, "cannot read invalid value type");
		}
	}

	@Override
	public void procesa(Instr_write instr_write) {
		instr_write.exp().procesa(this);
		Tipo tipoExp = TipoUtil.REF(instr_write.exp().t);
		if(!((tipoExp instanceof Tipo_int)||(tipoExp instanceof Tipo_real)||
		(tipoExp instanceof Tipo_bool) || (tipoExp instanceof Tipo_string))){
			SemanticErrors.avisoError(instr_write.exp(), tipoExp, "cannot write invalid value type: " + tipoExp);
		}
	}

	@Override
	public void procesa(Instr_nl instr_nl) {}

	@Override
	public void procesa(Instr_new instr_new) {
		instr_new.exp().procesa(this);
		Tipo tipoExp = TipoUtil.REF(instr_new.exp().t);
		if(!(tipoExp instanceof Tipo_circum)){
			SemanticErrors.avisoError(instr_new.exp(), tipoExp, "the new exp type should be a pointer");
		}
	}

	@Override
	public void procesa(Instr_del instr_del) {
		instr_del.exp().procesa(this);
		Tipo tipoExp = TipoUtil.REF(instr_del.exp().t);
		if(!(tipoExp instanceof Tipo_circum)){
			SemanticErrors.avisoError(instr_del.exp(), tipoExp, "the del exp type should be a pointer");
		}
	}

	@Override
	public void procesa(Instr_call instr_call) {
		//System.out.println(instr_call.leeFila() + ":" + instr_call.leeCol());
		ParsReOp parsReOp = instr_call.parsreop();
		parsReOp.procesa(this);
		
		if(!(instr_call.vinculo instanceof Dec_proc)){
			SemanticErrors.avisoError(instr_call, null, "the identifier of call isn't linked to a proc");
			return;
		}
		Dec_proc dec_proc = (Dec_proc)instr_call.vinculo;
		ParsFOp parsFOp = dec_proc.parsfop();

		if((parsFOp instanceof No_parsF)&&(parsReOp instanceof No_parsRe)){}
		else if((parsFOp instanceof Si_parsF)&&(parsReOp instanceof Si_parsRe)){
			Si_parsF si_parsF = (Si_parsF) parsFOp;
			Si_parsRe si_parsRe = (Si_parsRe) parsReOp;
			if(si_parsF.length()!= si_parsRe.length()){
				SemanticErrors.avisoError(instr_call, si_parsF.t, si_parsRe.t, "number of params does not match");
			}
			else{TipoUtil.comprobarParametros(si_parsF.parsf(), si_parsRe.parsre());}
		}
	}

	@Override
	public void procesa(Instr_bloque instr_bloque) {
		instr_bloque.bloq().procesa(this);
		instr_bloque.t = instr_bloque.bloq().t;
	}

	@Override
	public void procesa(Si_parsRe si_parsRe) {
		si_parsRe.parsre().procesa(this);
		si_parsRe.t = si_parsRe.parsre().t;
	}

	@Override
	public void procesa(No_parsRe no_parsRe) {}

	@Override
	public void procesa(Muchos_parsRe muchos_parsRe) {
		muchos_parsRe.parsF().procesa(this);
		muchos_parsRe.parF().procesa(this);
		muchos_parsRe.t = TipoUtil.ambosOK(muchos_parsRe.parsF().t, muchos_parsRe.parF().t);
	}

	@Override
	public void procesa(Un_parRe un_parRe) {
		un_parRe.parF().procesa(this);
		un_parRe.t = TipoUtil.OK(un_parRe.parF().t);
	}

    // infixes (binary operations)
    private void procesaBin(ExpBin exp){
        exp.opnd0().procesa(this);
        exp.opnd1().procesa(this);
    }
	@Override
	public void procesa(Suma suma) {		
		procesaBin(suma);
		TipoUtil.tipadoBinAritmetico(suma);			
	}
	@Override
	public void procesa(Resta resta) {
		procesaBin(resta);
		TipoUtil.tipadoBinAritmetico(resta);	
	}
	@Override
	public void procesa(Mul mul) {
		procesaBin(mul);
		TipoUtil.tipadoBinAritmetico(mul);	
	}
	@Override
	public void procesa(Div div) {
		procesaBin(div);
		TipoUtil.tipadoBinAritmetico(div);	
	}
	@Override
	public void procesa(Mod mod) {
		procesaBin(mod);
		if((mod.opnd0().t instanceof Tipo_int) && (mod.opnd1().t instanceof Tipo_int)){
            mod.t = new Tipo_int();
        }
        else{
            SemanticErrors.avisoError(mod, mod.opnd0().t, mod.opnd1().t, "incompatible values for mod");
        }
	}
	@Override
	public void procesa(Asig asig) {
		procesaBin(asig);
		if (TipoUtil.esDesignador(asig.opnd0) && TipoUtil.compatible(asig.opnd0().t, asig.opnd1().t)){
			asig.t = asig.opnd0().t;
		}
		else{
			// System.out.println("opnd0 " + (asig.opnd0().vinculo.leeFila()));
			// System.out.println("opnd1 " + (asig.opnd1()));
			SemanticErrors.avisoError(asig, asig.opnd0().t, asig.opnd1().t, "invalid values for asig. " + asig.opnd0().t + " is not compatible with " + asig.opnd1().t);
		}
	}
	@Override
	public void procesa(MenorI menorI) {
		procesaBin(menorI);
		TipoUtil.tipadoBinRelacional(menorI);
	}
	@Override
	public void procesa(Menor menor) {
		procesaBin(menor);
		TipoUtil.tipadoBinRelacional(menor);
	}
	@Override
	public void procesa(MayorI mayorI) {
		procesaBin(mayorI);
		TipoUtil.tipadoBinRelacional(mayorI);
	}
	@Override
	public void procesa(Mayor mayor) {
		procesaBin(mayor);
		TipoUtil.tipadoBinRelacional(mayor);
	}
	@Override
	public void procesa(Igual igual) {
		procesaBin(igual);
		TipoUtil.tipadoBinRelacionalEspecial(igual);
	}
	@Override
	public void procesa(Distint distint) {
		procesaBin(distint);
		TipoUtil.tipadoBinRelacionalEspecial(distint);
	}
	@Override
	public void procesa(And and) {
		procesaBin(and);
		TipoUtil.tipadoBinLogico(and);
	}
	@Override
	public void procesa(Or or) {
		procesaBin(or);
		TipoUtil.tipadoBinLogico(or);
	}


    // prefixes
	@Override
	public void procesa(Negacion negacion) {
		negacion.opnd0().procesa(this);
		if(TipoUtil.REF(negacion.opnd0().t) instanceof Tipo_bool){
			negacion.t = new Tipo_bool();
		}
		else{
			SemanticErrors.avisoError(negacion, negacion.opnd0().t, "negation exp should be bool");
		}

	}
	@Override
	public void procesa(MenosUnario menosUnario) {
		menosUnario.opnd0().procesa(this);
		if(TipoUtil.REF(menosUnario.opnd0().t) instanceof Tipo_int){
			menosUnario.t = new Tipo_int();
		}
		else if(TipoUtil.REF(menosUnario.opnd0().t) instanceof Tipo_real){
			menosUnario.t = new Tipo_real();
		}
		else{
			SemanticErrors.avisoError(menosUnario, menosUnario.opnd0().t, "negative exp should be int or real");
		}
	}

    // postfixes (designadores)
	@Override
	public void procesa(Indexacion indexacion) {
		indexacion.opnd0().procesa(this);	
		indexacion.opnd1().procesa(this);
		if((TipoUtil.REF(indexacion.opnd0().t) instanceof Tipo_lista) && (TipoUtil.REF(indexacion.opnd1().t) instanceof Tipo_int)){
			Tipo_lista TL = (Tipo_lista) TipoUtil.REF(indexacion.opnd0().t);
			indexacion.t = TL.tipo();
		}
		else{
			SemanticErrors.avisoError(indexacion, indexacion.opnd0().t, indexacion.opnd1().t, "invalid indexation value types ");
		}
	}

	@Override
	public void procesa(Acceso acceso) {
		acceso.opnd0().procesa(this);
		Tipo T = TipoUtil.REF(acceso.opnd0().t);
		if(T instanceof Tipo_struct){
			Tipo_struct TS = (Tipo_struct) T;
			if (TS.nm.containsKey(acceso.opnd1String())){
				acceso.t = TS.nm.get(acceso.opnd1String()).tipo();
			}
			else{
				SemanticErrors.avisoError(acceso, null, "invalid field name");
			}
		}
		else{
			SemanticErrors.avisoError(acceso, T, "the accesed value type is not a struct");
		}
	}

	@Override
	public void procesa(Indireccion indireccion) {
		indireccion.opnd0().procesa(this);
		Tipo T = TipoUtil.REF(indireccion.opnd0().t);
		if(T instanceof Tipo_circum){
			Tipo_circum TC = (Tipo_circum) T;
			indireccion.t = TC.tipo();
			// System.out.println("indireccion tipo:" + indireccion.t);
		}
		else{
			SemanticErrors.avisoError(indireccion, T, "the indirected value type is not a pointer");
		}
	}

    // iden and literals
	@Override
	public void procesa(Iden iden) {
		if (iden.vinculo instanceof Dec_variable){
			Dec_variable dec = (Dec_variable) iden.vinculo;
			iden.t = dec.tipo();
			//System.out.println(iden.id() + " " + iden.leeFila() + ":" + iden.leeCol()  + " linked to " + dec.leeFila() + ":" + dec.leeCol());
		}
		else if (iden.vinculo instanceof Param){
			Param dec = (Param) iden.vinculo;
			iden.t = dec.tipo();
			//System.out.println(iden.id() + " " + iden.leeFila() + ":" + iden.leeCol()  + " linked to " + dec.leeFila() + ":" + dec.leeCol());
		}
		else if(iden.vinculo instanceof ParamF){
			ParamF dec = (ParamF) iden.vinculo;
			iden.t = dec.tipo();
			//System.out.println(iden.id() + " " + iden.leeFila() + ":" + iden.leeCol()  + " linked to " + dec.leeFila() + ":" + dec.leeCol());
		}
		else{
			SemanticErrors.avisoError(iden, null, "failed to link iden " + iden.id() + " to a declared variable");
			//System.out.println("what's the type??? " + iden.id() + " " + iden.leeFila() + ":" + iden.leeCol());
		}
	}
	@Override
	public void procesa(Lit_ent lit_ent) {lit_ent.t = new Tipo_int();}
	@Override
	public void procesa(Lit_real lit_real) {lit_real.t = new Tipo_real();}
	@Override
	public void procesa(TRUE true1) {true1.t = new Tipo_bool();}
	@Override
	public void procesa(FALSE false1) {false1.t = new Tipo_bool();}
	@Override
	public void procesa(Lit_cadena lit_cadena) {lit_cadena.t = new Tipo_string();}
	@Override
	public void procesa(NULL null1) {null1.t = new Tipo_null();}


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

