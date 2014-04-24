package it.AP.Parser;
//Per poter usare questo parser basta richiamare la funzione esegui dopo aver inserito la formula
//con l'apposito metodo setFormula; per poter definire il valore di una variabile basta usare l'assegnamento
//es x = 3
//si può assegnare anche il risultato di una espressione.
//per poter creare una funzione la procedura è simile
//f(x)=x+3
//lo scope della variabile x è solo interno alla funzione e non intacca il valore di una eventuale variabile
//x definita in precedenza.
//le funzioni builtin sono: "log","sin","cos","tan","ln","arcsin","arccos","arctan","abs","rand","round"
//mentre gli operatori sono : +, -, *, /, ^, =
//la virgola serve per separare i parametri di una funzione mentre il punto viene usato per definire un valore decimale(2.3)

import java.util.ArrayList;

	//Questa estensione esiste solo perche' a pietro non piace avere un file solo
public class Esecutore extends Parser {
	private ScopeVariabili scopeVar;
	private ScopeFunzioni scopeFunz;
	public Esecutore(String formula) {
		super(formula);
		this.scopeVar = new ScopeVariabili();
		this.scopeFunz = new ScopeFunzioni();
	}
	public double getVariabile(String nome){
		return this.scopeVar.getVariabile(nome);
	}
	private Double risolutore(Nodo n, ScopeVariabili scopeLocale) throws EsecuzioneException{
		//caso base
		if(n.getTipoNodo() == TipoNodo.foglia){
			if(n.getTipoValore() == TipoValore.numero){
				return(Double.parseDouble(n.getValore()));
			} else {
				if(n.getTipoValore() != TipoValore.variabile){
					throw new StronzataException();
				}
				//se è una variabile
				Double valoreVariabile= scopeLocale.getVariabile(n.getValore());
				if(valoreVariabile == null){
					throw new VariabileNonTrovataException("Variabile "+ n.getValore() + " non trovata");
				}
				return valoreVariabile;
			}
		}
		//caso ricorsivo
		
		//calcolo argomenti
		ArrayList<Double> args = new ArrayList<Double>();
		for(Nodo arg: n.getNodi()){
			if(arg.getValore() != null){
				args.add(this.risolutore(arg, scopeLocale));
			}
		}
		
		if(n.getTipoValore() == TipoValore.operatore){
			//esecuzione operazioni di operatore
			double sinistra = args.get(0);
			double destra = args.get(1);
			String op = n.getValore();
			return Operazioni.operazione(sinistra, destra, op);
			
		} else {
			if(n.getTipoValore() != TipoValore.funzione){
				throw new StronzataException();
			}
			//Caso funzione:
			String nomeFunz = n.getValore();
			
			try {
				return Operazioni.builtin(nomeFunz, args);
			}
			catch(BuiltinNonTrovata e){
				//cerchiamo la funzione nello scope
				MappaFunzione mappa = this.scopeFunz.getFunzione(nomeFunz);
				if(mappa == null){
					throw new FunzioneNonTrovataException("Funzione \""+ nomeFunz+ "\" non trovata.");
				}
				//la funzione esiste, controlliamo numero parametri
				if(mappa.argomenti.size() != args.size()){
					throw new NumeroArgomentiException("La funzione \"" + nomeFunz + "\" richiede " + mappa.argomenti.size() + 
							" argomenti, "+ args.size() +" forniti.");
				}
				//ok possiamo partire
				ScopeVariabili scopeFunz = (ScopeVariabili)scopeLocale.clone();
				for(int i = 0; i < args.size(); i++){
					//settiamo nello scope della funzione gli argomenti passati
					scopeFunz.setVariabile(mappa.argomenti.get(i), args.get(i));
				}
				//invochiamo la funzione!
				double risultato = this.risolutore(mappa.alberoFunzione, scopeFunz);
				return risultato;
			}
		}
	}
	public CosESuccesso esegui() throws ParsingException, EsecuzioneException{
		this.parsing();
		//se siamo in caso di assegnamento
		if(this.albero.getValore().equals(Parser.uguale[0])){
			if(this.albero.getNodi().get(0).getTipoValore() == TipoValore.variabile){
				double valoreVariabile= this.risolutore(this.albero.getNodi().get(1), this.scopeVar);
				//salvare in scope
				String nome= this.albero.getNodi().get(0).getValore();
				this.scopeVar.setVariabile(nome, valoreVariabile);
			}
			if (this.albero.getNodi().get(0).getTipoValore() == TipoValore.funzione){
				Nodo funz = this.albero.getNodi().get(0);
				MappaFunzione mappa = new MappaFunzione(this.albero.getNodi().get(1));
				
				for(Nodo arg: funz.getNodi()){
					if(arg.getTipoValore() == null)
						break;
					//cicliamo sugli argomenti della funzione controllando che siano variabili
					if(arg.getTipoValore() == TipoValore.variabile){
						//li aggiungiamo alla lista di stringhe degli argomenti
						String nomeArgomento = arg.getValore();
						mappa.argomenti.add(nomeArgomento);
					} else {
						throw new ArgomentoErratoException("Argomento errato: "+ arg.getValore());
					}
				}
				//aggiungiamo allo scope
				this.scopeFunz.setFunzione(funz.getValore(), mappa);
			}
			return CosESuccesso.assegnamento;
		} else {	//se invece è un'espressione e basta
			double valoreRestituito = this.risolutore(this.albero, this.scopeVar);
			this.scopeVar.setVariabile("ans", valoreRestituito);
			
			return CosESuccesso.espressione;
		}
	}
}
