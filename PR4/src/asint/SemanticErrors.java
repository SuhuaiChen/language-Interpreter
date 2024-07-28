package asint;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import asint.SintaxisAbstractaTiny.*;

// this file is adapted from https://github.com/bhavinshah7/Mini-Java-Compiler/blob/master/Compiler/src/main/java/eminijava/semantics/SemanticErrors.java

public class SemanticErrors {

	public static List<NameError> errorList = new ArrayList<>();

    public static boolean noHayError(){
        return errorList.isEmpty();
    }

	public static void addError(int line, int col, String errorText) {
		NameError error = new NameError(line, col, errorText);
		errorList.add(error);
	}

	public static void addError(Nodo n, String errorText) {
		NameError error = new NameError(n.leeFila(), n.leeCol(), errorText);
		errorList.add(error);
		n.t = error;
	}

	public static void avisoError(Nodo n, Tipo T0, Tipo T1, Tipo T2, String errorText){
        if (T0 instanceof NameError){
            n.t = T0;
        }
        else if (T1 instanceof NameError){
            n.t = T1;
        }
		else if (T2 instanceof NameError){
            n.t = T2;
        }
        else{
            addError(n, errorText);;
        }
    }

	public static void avisoError(Nodo n, Tipo T0, Tipo T1, String errorText){
        if (T0 instanceof NameError){
            n.t = T0;
        }
        else if (T1 instanceof NameError){
            n.t = T1;
        }
        else{
            addError(n, errorText);;
        }
    }

    public static void avisoError( Nodo n, Tipo T, String errorText){
        if (T instanceof NameError){
            n.t = T;
        }
        else{
            addError(n, errorText);
        } 
    }

    public static void sort() {
		Collections.sort(errorList);
	}

    public static void printErrors(){
        SemanticErrors.sort();
		for (NameError e : SemanticErrors.errorList) {
						System.err.println(e);
		}
    }

    public static void printErrorsDJ(String errormessage){
        SemanticErrors.sort();
		for (NameError e : SemanticErrors.errorList) {
						System.out.println(e.ErrDJ(errormessage));
		}
    }


}
