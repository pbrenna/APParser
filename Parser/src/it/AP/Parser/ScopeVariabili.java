package it.AP.Parser;

import java.util.HashMap;

public class ScopeVariabili {
	private HashMap<String,Double> vars;
	
	public ScopeVariabili(){
		this.vars = new HashMap<String, Double>();
		this.setVariabile("pi", Math.PI);
		this.setVariabile("e", Math.E);
		this.setVariabile("ans", 0);
	}
	private ScopeVariabili(HashMap<String, Double> vars){
		this.vars = vars;
	}
	public void setVariabile(String nome, double valore){
		this.vars.put(nome, valore);
	}
	public Double getVariabile(String nome){
		return this.vars.get(nome);
	}
	public Object clone() {
		return new ScopeVariabili((HashMap<String, Double>)this.vars.clone());
	}
}
