package it.AP.Parser;

public class ParsingException extends Exception {
	public ParsingException(){
		super("Errore nel parsing");
	}
	public ParsingException(String s){
		super(s);
	}
}

class IdentificatoreException extends ParsingException{
	public IdentificatoreException() {
		super("Errore identificatore");
	}
	
	public IdentificatoreException(String s) {
		super(s);
	}
}