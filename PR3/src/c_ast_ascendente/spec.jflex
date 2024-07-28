package c_ast_ascendente;

%%
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode
%public
%cup

%{
  private ALexOperations ops;
  private GestionErroresTiny errores;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
  public void fijaGestionErrores(GestionErroresTiny errores) {
   this.errores = errores;
  }
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}



true = [Tt][Rr][Uu][Ee]  
false = [Ff][Aa][Ll][Ss][Ee]
and = [Aa][Nn][Dd]
or = [Oo][Rr]
not = [Nn][Oo][Tt]
bool = [Bb][Oo][Oo][Ll]
real =  [Rr][Ee][Aa][Ll]
int = [Ii][Nn][Tt] 
string = [Ss][Tt][Rr][Ii][Nn][Gg]
null = [Nn][Uu][Ll][Ll]
proc = [Pp][Rr][Oo][Cc]
if = [Ii][Ff]
else = [Ee][Ll][Ss][Ee]
while = [Ww][Hh][Ii][Ll][Ee]
struct = [Ss][Tt][Rr][Uu][Cc][Tt]
new = [Nn][Ee][Ww]
delete = [Dd][Ee][Ll][Ee][Tt][Ee]
read = [Rr][Ee][Aa][Dd]
write = [Ww][Rr][Ii][Tt][Ee]
nl = [Nn][Ll]
type = [Tt][Yy][Pp][Ee]
call = [Cc][Aa][Ll][Ll]

letra  = ([A-Z]|[a-z]|_)
digitoPositivo = [1-9]
digito = ({digitoPositivo}|0)
parteEntera = (({digitoPositivo}{digito}*) | 0)
parteDecimal = (({digito}* {digitoPositivo})|0)
parteExponencial = [Ee][\+\-]?{parteEntera}

separador = [ \t\r\b\n]
comentario = ##[^\n]* 
identificador = {letra}({letra}|{digito})*
literalEntero = [\+\-]?{parteEntera}


literalReal =  {literalEntero}({parteExponencial}|(\.{parteDecimal})|(\.{parteDecimal}{parteExponencial}))
literalCadena = \"[^\"]*\"


operadorSuma = \+
operadorResta = \-
operadorMultiplicacion = \*
operadorDivision = \/

operadorMayor = >
operadorMayorIgual = >=
operadorMenor = < 
operadorMenorIgual = <=
operadorIgual = ==
operadorDistinto = \!=


parentesisApertura = \(
parentesisCierre = \)
asignar = \=
coma  = \,

puntoycoma = ; 
llaveApertura = \{
llaveCierre = \}
eval = @
modulo = % 
corcheteApertura = \[
corcheteCierre = \] 
punto = \. 
circunflejo = \^
ampersand = &
amp = &&

%%

{int}                  {return ops.unidadEnt();}
{real}                  {return ops.unidadReal();}
{true}                  {return ops.unidadTrue();}
{false}                  {return ops.unidadFalse();}
{and}                  {return ops.unidadAnd();}
{or}                  {return ops.unidadOr();}
{not}                  {return ops.unidadNot();}
{bool}                  {return ops.unidadBool();}
{string}                  {return ops.unidadString();}
{null}                  {return ops.unidadNull();}
{proc}                  {return ops.unidadProc();}
{if}                  {return ops.unidadIf();}
{else}                  {return ops.unidadElse();}
{while}                  {return ops.unidadWhile();}
{struct}                  {return ops.unidadStruct();}
{new}                  {return ops.unidadNew();}
{delete}                  {return ops.unidadDelete();}
{read}                  {return ops.unidadRead();}
{write}                  {return ops.unidadWrite();}
{nl}                  {return ops.unidadNl();}
{type}                  {return ops.unidadType();}
{call}                  {return ops.unidadCall();}

{separador}               {}
{comentario}              {}
{literalEntero}            {return ops.unidadLiteralEnt();}
{literalReal}              {return ops.unidadLiteralReal();}
{identificador}           {return ops.unidadId();}
{literalCadena}              {return ops.unidadLiteralCadena();}



{operadorSuma}            {return ops.unidadMas();}
{operadorResta}           {return ops.unidadMenos();}
{operadorMultiplicacion}  {return ops.unidadPor();}
{operadorDivision}        {return ops.unidadDiv();}

{operadorMayor}        {return ops.unidadGT();}
{operadorMayorIgual}        {return ops.unidadGE();}
{operadorMenor}        {return ops.unidadLT();}
{operadorMenorIgual}        {return ops.unidadLE();}
{operadorIgual}        {return ops.unidadEQ();}
{operadorDistinto}        {return ops.unidadNE();}


{puntoycoma}        {return ops.unidadPYC();}
{llaveApertura}        {return ops.unidadLLAP();}
{llaveCierre}        {return ops.unidadLLCIERRE();}
{eval}        {return ops.unidadEVAL();}
{modulo}        {return ops.unidadModulo();}
{corcheteApertura}        {return ops.unidadCAp();}
{corcheteCierre}        {return ops.unidadCCierre();}
{punto}        {return ops.unidadPunto();}
{circunflejo}        {return ops.unidadCircunflejo();}
{ampersand}        {return ops.unidadAmpersand();}
{amp}        {return ops.unidadSEP();}


{parentesisApertura}      {return ops.unidadPAp();}
{parentesisCierre}        {return ops.unidadPCierre();} 
{asignar}                   {return ops.unidadASIG();} 
{coma}                    {return ops.unidadComa();}
[^]                       {ops.error();}  