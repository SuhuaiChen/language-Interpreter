package asint;
import asint.SintaxisAbstractaTiny.*;

import java.util.Arrays;

import java.util.List; 
import java.util.HashSet;

public class TipoUtil {

    private static HashSet<List<Tipo>> equations = new HashSet<List<Tipo>>();

    public static Tipo ambosOK(Tipo T0, Tipo T1){
        if (T0 instanceof NameError){
            return T0;
        }
        else if (T1 instanceof NameError){
            return T1;
        }
        else{
            return null;
        }
    }

    public static Tipo OK(Tipo T0){
        if (T0 instanceof NameError){
            return T0;
        }
        else{
            return null;
        } 
    }

    public static Tipo ref(Tipo T){
        // get most recent linked type
        if (T instanceof NameError){
            return T;
        }

        Dec_tipo dec = (Dec_tipo)T.vinculo;
        return dec.tipo();
    }

    public static Tipo REF(Tipo T){
        // get the original linked type
        if (T instanceof Tipo_iden){
            Dec_tipo dec = (Dec_tipo)T.vinculo;
            return REF(dec.tipo());
        }
        return T;
    }

    public static boolean esDesignador(Exp E){
        return (E instanceof Iden) || (E instanceof Indexacion) || (E instanceof Acceso) || (E instanceof Indireccion);
    }


    // check compatible
    public static boolean compatible(Tipo T1, Tipo T2){
        boolean result = unificable(T1,T2);
        equations.clear();
        return result;
    }

    public static boolean unificable(Tipo T1, Tipo T2){
        //System.out.println("T1: " + T1);
        //System.out.println("T2: " + T2);
        T1 = REF(T1);
        T2 = REF(T2);

        if((T1 instanceof Tipo_lista)&&(T2 instanceof Tipo_lista)){
            Tipo_lista Tlista1 = (Tipo_lista) T1;
            Tipo_lista Tlista2 = (Tipo_lista) T2;
            if(!(Tlista1.literalEntero().equals(Tlista2.literalEntero()))){
                return false;
            }
            else{
                return sonUnificable(Tlista1.tipo(), Tlista2.tipo()) ;
            }
        }

        else if (T1.getClass().equals(T2.getClass()) && !(T1 instanceof NameError)){
            return true;
        } 

        else if((T1 instanceof Tipo_real)&&(T2 instanceof Tipo_int)){
            return true;
        }

        else if((T1 instanceof Tipo_circum)&&(T2 instanceof Tipo_null)){
            return true;
        }

        else if((T1 instanceof Tipo_struct)&&(T2 instanceof Tipo_struct)){
            Tipo_struct Tstruct1 = (Tipo_struct) T1;
            Tipo_struct Tstruct2 = (Tipo_struct) T2;
            return comprobarCampos(Tstruct1.campos(), Tstruct2.campos());
        }
        else if((T1 instanceof Tipo_circum)&&(T2 instanceof Tipo_circum)){
            Tipo_circum Tcircum1 = (Tipo_circum) T1;
            Tipo_circum Tcircum2 = (Tipo_circum) T2;
            return sonUnificable(Tcircum1.tipo(), Tcircum2.tipo());
        }
        else{
            return false;
        }
    }

    public static boolean sonUnificable(Tipo T1, Tipo T2){
        List<Tipo> equation = Arrays.asList(T1, T2);
        if (equations.contains(equation)){
            return true;
        }
        else{
            equations.add(equation);
            return unificable(T1, T2);
        }
    }

    public static boolean comprobarCampos(Campos C1, Campos C2){
        // recursively campare each field of a struct
        if((C1 instanceof Muchos_campos)&&(C2 instanceof Muchos_campos)){
            return sonUnificable(C1.campo().tipo(), C2.campo().tipo()) && comprobarCampos(C1.campos(), C2.campos());
        }
        else if((C1 instanceof Un_campo)&&(C2 instanceof Un_campo)){
            return sonUnificable(C1.campo().tipo(), C2.campo().tipo());
        }
        else{
            return false;
        }
    }

    public static boolean comprobarParametros(ParsF PF, ParsRe PR){
        // recursively compare each param(F) with un_parRe
        if((PF instanceof Muchos_parsF)&&(PR instanceof Muchos_parsRe)){
            return comprobarParametro(PF.parF(), PR.parF()) && comprobarParametros(PF.parsF(), PR.parsF());
        }
        else if((PF instanceof Un_parF)&&(PR instanceof Un_parRe)){
            return comprobarParametro(PF.parF(), PR.parF());
        }
        else{
            return false;
        }
    }

    public static boolean comprobarParametro(ParF PF, Exp E){
        
        if(PF instanceof Param){
            Param param = (Param) PF;
            boolean result = compatible(param.tipo(), E.t);
            if(!result){
                SemanticErrors.avisoError(E, E.t, "param not matching");
            }
            return result;
        }
        else{
            ParamF paramF = (ParamF) PF;
            if (!esDesignador(E)){
                SemanticErrors.avisoError(E, E.t, "paramre not a designador");
                return false;
            }
            if (paramF.tipo() instanceof Tipo_real){
                boolean result = REF(E.t) instanceof Tipo_real;
                if(!result){
                    SemanticErrors.avisoError(E, E.t, "paramF real not matching");
                }
                return result;
            }
            else{
                boolean result = compatible(paramF.tipo(), E.t);
                if(!result){
                    SemanticErrors.avisoError(E, E.t, "paramF not matching");
                }
                return result;
            }
        }
    }

    public static void tipadoBinAritmetico(Exp E){
        Tipo T0 = REF(E.opnd0().t);
        Tipo T1 = REF(E.opnd1().t);
        if((T0 instanceof Tipo_int) && (T1 instanceof Tipo_int)){
            E.t = new Tipo_int();
        }
        else if(((T0 instanceof Tipo_int) || (T0 instanceof Tipo_real))&& ((T1 instanceof Tipo_int) || (T1 instanceof Tipo_real))){
            E.t = new Tipo_real();
        }
        else{
            SemanticErrors.avisoError(E, T0, T1, "incompatible values for binary arithmetic expressions");
        }
    }

    public static void tipadoBinLogico(Exp E){
        Tipo T0 = REF(E.opnd0().t);
        Tipo T1 = REF(E.opnd1().t);
        if((T0 instanceof Tipo_bool) && (T1 instanceof Tipo_bool)){
            E.t = new Tipo_bool();
        }
        else{
            SemanticErrors.avisoError(E, T0, T1, "incompatible values for binary logical expressions");
        }
    }

    public static void tipadoBinRelacional(Exp E){
        Tipo T0 = REF(E.opnd0().t);
        Tipo T1 = REF(E.opnd1().t);
        if(((T0 instanceof Tipo_int) || (T0 instanceof Tipo_real))&& ((T1 instanceof Tipo_int) || (T1 instanceof Tipo_real))){
            E.t = new Tipo_bool();
        }
        else if((T0 instanceof Tipo_bool) && (T1 instanceof Tipo_bool)){
            E.t = new Tipo_bool();
        }
        else if((T0 instanceof Tipo_string) && (T1 instanceof Tipo_string)){
            E.t = new Tipo_bool();
        }
        else{
            SemanticErrors.avisoError(E, T0, T1, "incompatible values for binary relational expressions");
        }
    }

    public static void tipadoBinRelacionalEspecial(Exp E){
        Tipo T0 = REF(E.opnd0().t);
        Tipo T1 = REF(E.opnd1().t);
        if(((T0 instanceof Tipo_circum) || (T0 instanceof Tipo_null))&& ((T1 instanceof Tipo_circum) || (T1 instanceof Tipo_null))){
            E.t = new Tipo_bool();
        }
        else if(((T0 instanceof Tipo_int) || (T0 instanceof Tipo_real))&& ((T1 instanceof Tipo_int) || (T1 instanceof Tipo_real))){
            E.t = new Tipo_bool();
        }
        else if((T0 instanceof Tipo_bool) && (T1 instanceof Tipo_bool)){
            E.t = new Tipo_bool();
        }
        else if((T0 instanceof Tipo_string) && (T1 instanceof Tipo_string)){
            E.t = new Tipo_bool();
        }
        else{
            SemanticErrors.avisoError(E, T0, T1, "incompatible values for binary relational expressions");
        }
    }
}
