package it.AP.Parser;

import java.util.ArrayList;
public class Nodo {
	private ArrayList<Nodo> figli;
	private TipoNodo tipoNodo;
	private TipoValore tipoValore;
	private String valore;
	
	public Nodo(){
		this.figli = new ArrayList<Nodo>();
	}
	
	public void setTipoNodo(TipoNodo tipoNodo) {
		this.tipoNodo = tipoNodo;
	}

	public void setTipoValore(TipoValore tipoValore) {
		this.tipoValore = tipoValore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public ArrayList<Nodo> getNodi() {
		return figli;
	}
	public TipoNodo getTipoNodo() {
		return tipoNodo;
	}
	public TipoValore getTipoValore() {
		return tipoValore;
	}
	public String getValore() {
		return valore;
	}
	public void printa(int indent){
		System.out.println();
		for(int i=0;i<indent-1;i++){
			System.out.print("| ");
		}
		if(indent != 0)
		    System.out.print("|_");
		System.out.print("(" +this.valore+")..........."+this.tipoNodo+"->"+this.tipoValore);
		if(this.figli!=null){
			for(Nodo n:this.figli){
				n.printa(indent+1);
			}
		}
		if(indent ==0)
		    System.out.println();
	}
	
}
enum TipoNodo{nodo, foglia};
enum TipoValore{numero, variabile, operatore, funzione};
