package it.AP.Parser;

import java.util.ArrayList;
import java.util.HashMap;

public class ScopeFunzioni {
	private HashMap<String, MappaFunzione> funcs;
	public ScopeFunzioni(){
		this.funcs = new HashMap<String, MappaFunzione>();
	}
	public void setFunzione (String nome, MappaFunzione mappa){
		this.funcs.put(nome, mappa);
	}
	public MappaFunzione getFunzione(String nome){
		return this.funcs.get(nome);
	}
}
class MappaFunzione{
	ArrayList<String> argomenti;
	Nodo alberoFunzione;
	public MappaFunzione( Nodo alberoFunzione) {
		this.argomenti = new ArrayList<String>();
		this.alberoFunzione = alberoFunzione;
	}
}