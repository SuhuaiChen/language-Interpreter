package asint;

import asint.SintaxisAbstractaTiny.*;

public class Impresion implements Procesamiento {
    
	public void procesa(Prog prog) {
		prog.bloq().procesa(this);
	}

	
	public void procesa(Bloq bloq) {
		System.out.println("{");
		bloq.decsOp().procesa(this);
		bloq.instrsOp().procesa(this);
		System.out.println("}");
	}

	@Override
	public void procesa(Si_decs si_decs) {
		si_decs.decs().procesa(this);
		System.out.println("&&");
	}

	@Override
	public void procesa(No_decs no_decs) {
		
	}

	@Override
	public void procesa(Si_instrs si_instrs) {
		si_instrs.instrs().procesa(this);
		
	}

	@Override
	public void procesa(No_instrs no_instrs) {
		
	}

	// @Override
	// public void procesa(Si_tipo si_tipo) {
	// 	// TODO Auto-generated method stub
	// 	si_tipo.tipo().procesa(this);
	// }

	// @Override
	// public void procesa(No_tipo no_tipo) {
	// 	// TODO Auto-generated method stub
		
	// }

	@Override
	public void procesa(Tipo_lista tipo_lista) {
		tipo_lista.tipo().procesa(this);
		System.out.println("[");
		System.out.println(tipo_lista.literalEntero());		
		System.out.println("]" + "$f:" + tipo_lista.leeFila() + ",c:" + tipo_lista.leeCol()+"$");
		
	}

	@Override
	public void procesa(Tipo_circum tipo_circum) {
		System.out.println("^");
		tipo_circum.tipo().procesa(this);
		
	}

	@Override
	public void procesa(Tipo_struct tipo_struct) {
		System.out.println("<struct>");
		System.out.println("{");
		tipo_struct.campos().procesa(this);
		System.out.println("}");
		
	}

	@Override
	public void procesa(Tipo_iden tipo_iden) {
		System.out.println(tipo_iden.identificador() + "$f:" + tipo_iden.leeFila() + ",c:" + tipo_iden.leeCol()+"$");		
	}

	@Override
	public void procesa(Tipo_int tipo_int) {
		System.out.println("<int>");		
	}

	@Override
	public void procesa(Tipo_real tipo_real) {
		System.out.println("<real>");
		
	}

	@Override
	public void procesa(Tipo_bool tipo_bool) {
		System.out.println("<bool>");
		
	}

	@Override
	public void procesa(Tipo_string tipo_string) {
		System.out.println("<string>");
		
	}

	@Override
	public void procesa(Muchos_campos muchos_campos) {
		muchos_campos.campos().procesa(this);
		System.out.println(",");
		muchos_campos.campo().procesa(this);
		
	}

	@Override
	public void procesa(Un_campo un_campo) {
		un_campo.campo().procesa(this);
		
	}

	@Override
	public void procesa(Crea_campo crea_campo) {
		crea_campo.tipo().procesa(this);
		System.out.println(crea_campo.identificador()  + "$f:" + crea_campo.leeFila() + ",c:" + crea_campo.leeCol()+"$");		
	}

	@Override
	public void procesa(Muchas_decs muchas_decs) {
		muchas_decs.decs().procesa(this);
		System.out.println(";");
		muchas_decs.dec().procesa(this);		
	}

	@Override
	public void procesa(Una_dec una_dec) {
		una_dec.dec().procesa(this);		
	}

	@Override
	public void procesa(Dec_variable dec_variable) {
		dec_variable.tipo().procesa(this);
		System.out.println(dec_variable.iden()  + "$f:" + dec_variable.leeFila() + ",c:" + dec_variable.leeCol()+"$");		
	}

	@Override
	public void procesa(Dec_tipo dec_tipo) {
		System.out.println("<type>");
		dec_tipo.tipo().procesa(this);
		System.out.println(dec_tipo.iden()  + "$f:" + dec_tipo.leeFila() + ",c:" + dec_tipo.leeCol()+"$");
	}

	@Override
	public void procesa(Dec_proc dec_proc) {
		System.out.println("<proc>");
		System.out.println(dec_proc.iden()  + "$f:" + dec_proc.leeFila() + ",c:" + dec_proc.leeCol()+"$");
		System.out.println("(");
		dec_proc.parsfop().procesa(this);
		System.out.println(")");
		dec_proc.bloq().procesa(this);	
        	
	}

	@Override
	public void procesa(Si_parsF si_parsF) {
		si_parsF.parsf().procesa(this);
		
	}

	@Override
	public void procesa(No_parsF no_parsF) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Muchos_parsF muchos_parsF) {
		muchos_parsF.parsF().procesa(this);
		System.out.println(",");
		muchos_parsF.parF().procesa(this);
		
	}

	@Override
	public void procesa(Un_parF un_parF) {
		un_parF.parF().procesa(this);
		
	}

	@Override
	public void procesa(ParamF paramF) {
		paramF.tipo().procesa(this);
		System.out.println("&");
		System.out.println(paramF.iden() + "$f:" + paramF.leeFila() + ",c:" + paramF.leeCol()+"$");
		
	}
	
	@Override
	public void procesa(Param param) {
		param.tipo().procesa(this);
		System.out.println(param.iden() + "$f:" + param.leeFila() + ",c:" + param.leeCol()+"$");
		
	}

	@Override
	public void procesa(Muchas_instrs muchas_instrs) {
		muchas_instrs.instrs().procesa(this);
		System.out.println(";");
		muchas_instrs.instr().procesa(this);
		
	}

	@Override
	public void procesa(Una_instr una_instr) {
		una_instr.instr().procesa(this);
		
	}

	@Override
	public void procesa(Instr_eval instr_eval) {
		System.out.println("@");
		instr_eval.exp().procesa(this);
	}

	@Override
	public void procesa(Instr_if instr_if) {
		System.out.println("<if>");
		instr_if.exp().procesa(this);
		instr_if.bloq().procesa(this);	
		
	}

	@Override
	public void procesa(Instr_ifelse instr_ifelse) {
		System.out.println("<if>");
		//System.out.println("(");
		instr_ifelse.exp().procesa(this);
		//System.out.println(")");
		instr_ifelse.bloq1().procesa(this);
		System.out.println("<else>");
		instr_ifelse.bloq2().procesa(this);
		
	}

	@Override
	public void procesa(Instr_while instr_while) {
		System.out.println("<while>");
		//System.out.println("(");
		instr_while.exp().procesa(this);
		//System.out.println(")");
		instr_while.bloq().procesa(this);
		
	}

	@Override
	public void procesa(Instr_read instr_read) {
		System.out.println("<read>");
		instr_read.exp().procesa(this);
		
	}

	@Override
	public void procesa(Instr_write instr_write) {
		System.out.println("<write>");
		instr_write.exp().procesa(this);
		
	}

	@Override
	public void procesa(Instr_nl instr_nl) {
		System.out.println("<nl>");
		
	}

	@Override
	public void procesa(Instr_new instr_new) {
		System.out.println("<new>");
		instr_new.exp().procesa(this);
		
	}

	@Override
	public void procesa(Instr_del instr_del) {
		System.out.println("<delete>");
		instr_del.exp().procesa(this);
		
	}

	@Override
	public void procesa(Instr_call instr_call) {
		System.out.println("<call>");
		System.out.println(instr_call.id() + "$f:" + instr_call.leeFila() + ",c:" + instr_call.leeCol()+"$");
		System.out.println("(");
		instr_call.parsreop().procesa(this);
		System.out.println(")");
		
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
	public void procesa(No_parsRe no_parsRe) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void procesa(Muchos_parsRe muchos_parsRe) {
		muchos_parsRe.parsF().procesa(this);
		System.out.println(",");
		muchos_parsRe.parF().procesa(this);
		
	}

	@Override
	public void procesa(Un_parRe un_parRe) {
		un_parRe.parF().procesa(this);
	}
	
	private void imprimeOpnd(Exp opnd, int np) {
		if(opnd.prioridad() < np) { System.out.println("("); };
		opnd.procesa(this);
		if(opnd.prioridad() < np) { System.out.println(")"); };
	}
	
	private void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1, int opFila, int opCol) {
		imprimeOpnd(opnd0,np0);
		System.out.println(op + "$f:" + opFila + ",c:" + opCol + "$");
		imprimeOpnd(opnd1,np1);
	}

	@Override
	public void procesa(Suma suma) {		
		imprimeExpBin(suma.opnd0(),"+",suma.opnd1(),2,3,suma.leeFila(), suma.leeCol());				
	}

	@Override
	public void procesa(Resta resta) {
		imprimeExpBin(resta.opnd0(),"-",resta.opnd1(),3,3,resta.leeFila(), resta.leeCol());
		
	}

	@Override
	public void procesa(Mul mul) {
		imprimeExpBin(mul.opnd0(),"*",mul.opnd1(),4,5,mul.leeFila(), mul.leeCol());
		
	}

	@Override
	public void procesa(Div div) {
		imprimeExpBin(div.opnd0(),"/",div.opnd1(),4,5,div.leeFila(), div.leeCol());
		
	}
	
	@Override
	public void procesa(Mod mod) {
		imprimeExpBin(mod.opnd0(),"%",mod.opnd1(),4,5,mod.leeFila(), mod.leeCol());
		
	}

	@Override
	public void procesa(Asig asig) {
		imprimeExpBin(asig.opnd0(),"=",asig.opnd1(),1,0,asig.leeFila(), asig.leeCol());
		
	}

	@Override
	public void procesa(MenorI menorI) {
		imprimeExpBin(menorI.opnd0(),"<=",menorI.opnd1(),1,2,menorI.leeFila(), menorI.leeCol());
		
	}

	@Override
	public void procesa(Menor menor) {
		imprimeExpBin(menor.opnd0(),"<",menor.opnd1(),1,2,menor.leeFila(), menor.leeCol());
		
	}

	@Override
	public void procesa(MayorI mayorI) {
		imprimeExpBin(mayorI.opnd0(),">=",mayorI.opnd1(),1,2,mayorI.leeFila(), mayorI.leeCol());
		
	}

	@Override
	public void procesa(Mayor mayor) {
		imprimeExpBin(mayor.opnd0(),">",mayor.opnd1(),1,2,mayor.leeFila(), mayor.leeCol());
		
	}

	@Override
	public void procesa(Igual igual) {
		imprimeExpBin(igual.opnd0(),"==",igual.opnd1(),1,2,igual.leeFila(), igual.leeCol());
		
	}

	@Override
	public void procesa(Distint distint) {
		imprimeExpBin(distint.opnd0(),"!=",distint.opnd1(),1,2,distint.leeFila(), distint.leeCol());
		
	}

	@Override
	public void procesa(And and) {
		imprimeExpBin(and.opnd0(),"<and>",and.opnd1(),4,3,and.leeFila(), and.leeCol());
		
	}

	@Override
	public void procesa(Or or) {
		imprimeExpBin(or.opnd0(),"<or>",or.opnd1(),4,4,or.leeFila(), or.leeCol());
		
	}

	@Override
	public void procesa(Negacion negacion) {
		System.out.println("<not>$f:" + negacion.leeFila() + ",c:" + negacion.leeCol()+"$");
		imprimeOpnd(negacion.opnd0(),5);
		
	}

	@Override
	public void procesa(MenosUnario menosUnario) {
		System.out.println("-$f:" + menosUnario.leeFila() + ",c:" + menosUnario.leeCol()+"$");
		imprimeOpnd(menosUnario.opnd0(),5);
		
	}

	@Override
	public void procesa(Indexacion indexacion) {
		indexacion.opnd0().procesa(this);
		System.out.println("[" + "$f:" + indexacion.leeFila() + ",c:" + (indexacion.leeCol())+"$");		
		indexacion.opnd1().procesa(this);
		System.out.println("]");
	}
	
	/*opnd0().imprime();
			System.out.println(".");
			System.out.println(opnd1String() + "$f:" + opnd0().leeFila() + ",c:" + (opnd0().leeCol()+2)+"$");*/

	@Override
	public void procesa(Acceso acceso) {
		acceso.opnd0().procesa(this);
		System.out.println(".");
		//System.out.println(acceso.opnd1String() + "$f:" + acceso.opnd0().leeFila() + ",c:" + (acceso.opnd0().leeCol()+2)+"$");		
		System.out.println(acceso.opnd1String() + "$f:" + acceso.leeFila() + ",c:" + (acceso.leeCol())+"$");
	}

	@Override
	public void procesa(Indireccion indireccion) {
		indireccion.opnd0().procesa(this);
		System.out.println("^" + "$f:" + indireccion.leeFila() + ",c:" + indireccion.leeCol()+"$");		
	}

	@Override
	public void procesa(Iden iden) {
		System.out.println(iden.id() + "$f:" + iden.leeFila() + ",c:" + iden.leeCol()+"$");
		
	}

	@Override
	public void procesa(Lit_ent lit_ent) {
		System.out.println(lit_ent.valor() + "$f:" + lit_ent.leeFila() + ",c:" + lit_ent.leeCol()+"$");
		
	}

	@Override
	public void procesa(Lit_real lit_real) {
		System.out.println(lit_real.valor() + "$f:" + lit_real.leeFila() + ",c:" + lit_real.leeCol()+"$");
		
	}

	@Override
	public void procesa(TRUE true1) {
		System.out.println("<true>" + "$f:" + true1.leeFila() + ",c:" + true1.leeCol()+"$");
		
	}

	@Override
	public void procesa(FALSE false1) {
		System.out.println("<false>" + "$f:" + false1.leeFila() + ",c:" + false1.leeCol()+"$");
		
	}

	@Override
	public void procesa(Lit_cadena lit_cadena) {
		System.out.println(lit_cadena.valor() + "$f:" + lit_cadena.leeFila() + ",c:" + lit_cadena.leeCol()+"$");
		
	}

	@Override
	public void procesa(NULL null1) {
		System.out.print(null1);		
	}


	

	// @Override
	// public void procesa2(Si_tipo si_tipo) {
	// 	// TODO Auto-generated method stub
	// 	throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	// }


	// @Override
	// public void procesa2(No_tipo no_tipo) {
	// 	// TODO Auto-generated method stub
	// 	throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	// }


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
