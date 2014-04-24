package it.AP.Parser;

public class EsecuzioneException extends Exception {
	public EsecuzioneException(){
		super("Errore nell'esecuzione");
	}
	public EsecuzioneException(String s){
		super(s);
	}
}


class VariabileNonTrovataException extends EsecuzioneException{
	public VariabileNonTrovataException() {
		super("Variabile non trovata");
	}
	
	public VariabileNonTrovataException(String s) {
		super(s);
	}
}

class ArgomentoErratoException extends EsecuzioneException{
	public ArgomentoErratoException() {
		super("Sintassi degli argomenti errata");
	}
	
	public ArgomentoErratoException(String s) {
		super(s);
	}
}

class StronzataException extends EsecuzioneException{
	public StronzataException() {
		super("È accaduta una stronzata.");
	}
	
	public StronzataException(String s) {
		super(s);
	}
}

class FunzioneNonTrovataException extends EsecuzioneException{
	public FunzioneNonTrovataException() {
		super("Funzione non trovata.");
	}
	
	public FunzioneNonTrovataException(String s) {
		super(s);
	}
}

class NumeroArgomentiException extends EsecuzioneException{
	public NumeroArgomentiException() {
		super("Numero argomenti incongruente");
	}
	
	public NumeroArgomentiException(String s) {
		super(s);
	}
}
class BuiltinNonTrovata extends EsecuzioneException{
	public BuiltinNonTrovata() {
		super("Builtin non trovata");
	}
}