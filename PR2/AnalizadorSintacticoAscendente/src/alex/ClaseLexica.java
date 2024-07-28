/*package alex;

public enum ClaseLexica {
 IDEN, 
 ENT, 
 REAL, 
 PAP("("), 
 PCIERRE(")"), 
 IGUAL("="), 
 COMA(","), 
 MAS("+"), 
 MENOS("-"), 
 POR("*"), 
 DIV("/"), 
 EVALUA("<evalua>"), 
 DONDE("<donde>"), 
 EOF("EOF");
private String image;
public String getImage() {
     return image;
 }
 private ClaseLexica() {
     image = toString();
 }
 private ClaseLexica(String image) {
    this.image = image;  
 }

}*/


package alex;

public class ClaseLexica {
	
	
	/*IDEN, LITERALCADENA, LITERALENTERO, LITERALREAL, 
	
	
	MAS("+"), MENOS("-"), POR("*"), DIV("/"), 
	GT(">"), GE(">="), LT("<"), LE("<="), EQ("=="), NE("!="), 
	ASIG("="),PYC(";"), PAP("("), PCIERRE(")"),LLAP("{"), LLCIERRE("}"),
	
	EVAL("@"), MOD("%"), CAP("["), CCIERRE("]"), PUNTO("."),COMA(","), 
	CIRCUNFLEJO("^"), AMP("&"), SEP("&&"), 
	
	

	  
	
	
	EOF("<EOF>"),
	TRUE("<true>"), FALSE("<false>"), AND("<and>"), OR("<or>"), NOT("<not>"), BOOL("<bool>"), ENT("<int>"), REAL("<real>"),
	STRING("<string>"), NULL("<null>"), PROC("<proc>"), IF("<if>"), ELSE("<else>"), WHILE("<while>"), STRUCT("<struct>"), NEW("<new>"),
	DELETE("<delete>"), READ("<read>"), WRITE("<write>"), NL("<nl>"), TYPE("<type>"), CALL("<call>");*/
	
	private String image;	
	
	public String getImage() {
		return image;
	}
	private ClaseLexica() {
		image = toString();
	}
	private ClaseLexica(String image) {
		this.image = image;  
	}
	
}

