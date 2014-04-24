package it.AP.Parser;

import java.util.ArrayList;

public class Operazioni {
	public static double operazione(double sinistra, double destra, String op){
		if(op.equals(Parser.piuMeno[0])){
			return sinistra + destra;
		} else if(op.equals(Parser.piuMeno[1])){
			return sinistra - destra;
		} else if(op.equals(Parser.perDiviso[0])){
			return sinistra * destra;
		} else if(op.equals(Parser.perDiviso[1])){
			return sinistra / destra;
		} else if(op.equals(Parser.potenza[0])){
			return Math.pow(sinistra, destra);
		}
		return 0.0;
	}
	public static final String[] funzioniBuiltin= {"log","sin","cos","tan","ln","arcsin","arccos","arctan","abs","rand","round"};
	public static double builtin(String nome, ArrayList<Double> parametri) throws EsecuzioneException{
		if(nome.equals(funzioniBuiltin[0])){
			if(parametri.size()!=2){
				throw new NumeroArgomentiException(funzioniBuiltin[0] + " richiede 2 parametri: base, argomento.");
			}
			return Math.log(parametri.get(1)) / Math.log(parametri.get(0));
		}
		if(nome.equals(funzioniBuiltin[1])){
			if(parametri.size()!=1){
				throw new NumeroArgomentiException(funzioniBuiltin[1] + " richiede 1 parametro: argomento.");
			}
			return Math.sin(parametri.get(0));
		}
		if(nome.equals(funzioniBuiltin[2])){
			if(parametri.size()!=1){
				throw new NumeroArgomentiException(funzioniBuiltin[2] + " richiede 1 parametro: argomento.");
			}
			return Math.cos(parametri.get(0));
		}
		if(nome.equals(funzioniBuiltin[3])){
			if(parametri.size()!=1){
				throw new NumeroArgomentiException(funzioniBuiltin[3] + " richiede 1 parametro: argomento.");
			}
			return Math.tan(parametri.get(0));
		}
		if(nome.equals(funzioniBuiltin[4])){
			if(parametri.size()!=1){
				throw new NumeroArgomentiException(funzioniBuiltin[4] + " richiede 1 parametro: argomento.");
			}
			return Math.log(parametri.get(0));
		}
		if(nome.equals(funzioniBuiltin[5])){
			if(parametri.size()!=1){
				throw new NumeroArgomentiException(funzioniBuiltin[5] + " richiede 1 parametro: argomento.");
			}
			return Math.asin(parametri.get(0));
		}
		if(nome.equals(funzioniBuiltin[6])){
			if(parametri.size()!=1){
				throw new NumeroArgomentiException(funzioniBuiltin[6] + " richiede 1 parametro: argomento.");
			}
			return Math.acos(parametri.get(0));
		}
		if(nome.equals(funzioniBuiltin[7])){
			if(parametri.size()!=1){
				throw new NumeroArgomentiException(funzioniBuiltin[7] + " richiede 1 parametro: argomento.");
			}
			return Math.atan(parametri.get(0));
		}
		if(nome.equals(funzioniBuiltin[8])){
			if(parametri.size()!=1){
				throw new NumeroArgomentiException(funzioniBuiltin[8] + " richiede 1 parametro: argomento.");
			}
			return Math.abs(parametri.get(0));
		}
		if(nome.equals(funzioniBuiltin[9])){
			if(parametri.size()!=0){
				throw new NumeroArgomentiException(funzioniBuiltin[9] + " richiede 0 parametri");
			}
			return Math.random();
		}
		if(nome.equals(funzioniBuiltin[10])){
			if(parametri.size()!=2){
				throw new NumeroArgomentiException(funzioniBuiltin[10] + " richiede 2 parametri: argomento, cifre decimali");
			}
			return Math.round(parametri.get(0)*Math.pow(10, parametri.get(1)))/Math.pow(10, parametri.get(1));
		}
		throw new BuiltinNonTrovata();
	}
}
