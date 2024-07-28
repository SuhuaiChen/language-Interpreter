package asint;
import asint.SintaxisAbstractaTiny.*;

// this file is adapted from https://github.com/bhavinshah7/Mini-Java-Compiler/blob/master/Compiler/src/main/java/eminijava/semantics/NameError.java

public class NameError extends Tipo implements Comparable<NameError>{

	private int line;
	private int column;
	private String errorText;

	public NameError(int line, int column, String errorText) {
		this.line = line;
		this.column = column;
		this.errorText = errorText;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	@Override
	public String toString() {
		return line + ":" + column + " error: " + errorText;
	}

	public String ErrDJ(String errormessage) {
		return errormessage + " fila:" + line + " col:" + column;
	}

	@Override
	protected void imprime() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'imprime'");
	}

	@Override
	public void procesa(Procesamiento p) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa'");
	}

	@Override
	public void procesa2(Procesamiento p) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'procesa2'");
	}

	@Override
	public int compareTo(NameError o) {
		if (getLine() < o.getLine()) {
			return -1;
		} else if (getLine() > o.getLine()) {
			return 1;
		} else {
			if (getColumn() < o.getColumn()) {
				return -1;
			} else if (getColumn() > o.getColumn()) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
