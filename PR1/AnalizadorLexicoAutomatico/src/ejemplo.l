%%
%line
%column
%class AnalizadorLexicoTiny
%type  UnidadLexica
%unicode

%{
  private ALexOperations ops;
  public String lexema() {return yytext();}
  public int fila() {return yyline+1;}
  public int columna() {return yycolumn+1;}
%}

%eofval{
  return ops.unidadEof();
%eofval}

%init{
  ops = new ALexOperations(this);
%init}







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


{e}                  {return ops.unidadE();}



{operadorEval}        {return ops.unidadEval();}
{operadorEOF}        {return ops.unidadEOF();}


{PYQ}        {return ops.unidadPYQ();}
{llaveApertura}        {return ops.unidadLLAP();}
{llaveCierre}        {return ops.unidadLLCIERRE();}
{amp}        {return ops.unidadSEP();}
[^]                       {ops.error();}  