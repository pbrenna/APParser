package it.AP.Parser;

public class Parser {
	public static final String[] piuMeno = {"+", "-"};
	public static final String[] perDiviso = {"*", "/"};
	public static final String[] potenza = {"^"};
	public static final String[] virgola = {","};
	public static final String[] uguale = {"="};
	protected Nodo albero;
	private String formula;
	
	public Parser(String formula){
		this.formula = formula;
	}
	
	public void parsing() throws ParsingException{	
		this.albero = new Nodo();	
		//avvio del metodo statico ricorsivo
		if(this.formula.indexOf('=') != this.formula.lastIndexOf('='))
			throw new ParsingException("troppi operatori \"=\" trovati");
		Parser.parser(this.albero, this.formula.replaceAll(" ", ""));
		if(this.formula.indexOf('=') != -1 &&	//controllla se c'e' l'uguale
				(!this.albero.getValore().equals(Parser.uguale[0]) ||		//controlla se l'operatore uguale è in prima posizione ossia se è la radice dell'albero
				!(this.albero.getNodi().get(0).getTipoValore() == TipoValore.variabile ||  //cotrolla se l'oggetto a cui si sta assegnando sia una variabile
				  this.albero.getNodi().get(0).getTipoValore() == TipoValore.funzione)))   //o una funzione
			throw new ParsingException("Errore nel parsing dell'assegnamento");
	}
	
	private static void parser(Nodo n, String s) throws ParsingException{
		int indiceSeparatore;
		if (s.length() == 0)
			return;
		String[] tocchiFunzione = trovaFunzione(s);
		//System.out.println("Opero su "+ s);
		//si cerca l'ultimo simbolo di + o -, poi di * o /, poi ^
		if((indiceSeparatore = Parser.trovaCarattere(s, Parser.uguale)) != -1 ||
			(indiceSeparatore = Parser.trovaCarattere(s, Parser.piuMeno)) != -1 ||
		   (indiceSeparatore = Parser.trovaCarattere(s, Parser.perDiviso)) != -1 ||
		   (indiceSeparatore = Parser.trovaCarattere(s, Parser.potenza)) != -1){
		   	//si creano i due nodi che popoleremo
			Nodo n1 = new Nodo();
			Nodo n2 = new Nodo();
			//prendiamo l'operatore incriminato ed etichettiamo con esso il nodo
			n.setValore(s.charAt(indiceSeparatore) + "");
			//settiamo il tipo di nodo a nodo
			n.setTipoNodo(TipoNodo.nodo);
			//l'etichetta è un operatore
			n.setTipoValore(TipoValore.operatore);
			//dividiamo in 2 blocchi
			if(indiceSeparatore == 0){
			    //probabilmente è un meno negativo
			    s = "0"+s;
			    indiceSeparatore++;
			}
			String p1 = s.substring(0, indiceSeparatore);
			String p2 = s.substring(indiceSeparatore + 1);
			
			Parser.parser(n1, p1);	//ricorsione 1° blocco
			Parser.parser(n2, p2);	//ricorsione 2° blocco
			
			n.getNodi().add(n1);	//aggiunta alla lista nodi
			n.getNodi().add(n2);
			
		}
		else if(s.charAt(0)=='(' && s.charAt(s.length()-1)==')'){
		    //se la stringa è circondata da parentesi, basta estrarre il contenutoParentesi 
		    //e ri-invocare il metodo sullo stesso nodo.
		    String contenutoParentesi= s.substring(1,s.length()-1);
		    Parser.parser(n, contenutoParentesi);
		}
		else if(tocchiFunzione[0]!=null){
		    n.setValore(tocchiFunzione[0]);
			n.setTipoNodo(TipoNodo.nodo);
			n.setTipoValore(TipoValore.funzione);
			
			int tmpIndice = 0;	//inizio argomento
			int tmpIndice2 = tocchiFunzione[1].length();	//fine argomento
			boolean flag = true;
			//leggiamo l'indice della virgola
			while( (tmpIndice = Parser.trovaCarattere(tocchiFunzione[1].substring(0,tmpIndice2), Parser.virgola) ) != -1 || flag){
				if(tmpIndice == -1){
					flag = false;
				}
				Nodo tmp = new Nodo();
				//parsiamo l'argomento
				String arg = tocchiFunzione[1].substring(tmpIndice+1, tmpIndice2);
				Parser.parser(tmp, arg);
				n.getNodi().add(0,tmp);
				tmpIndice2 = tmpIndice>0?tmpIndice:0;
			}
			
		}
		else{
			//nodo foglia
			//per ora solo valore
			n.setValore(s);
			n.setTipoNodo(TipoNodo.foglia);
			try{
				Double.parseDouble(s);
				n.setTipoValore(TipoValore.numero);				
			}catch(Exception e){
				for(char c:s.toCharArray()){
					if(!Parser.isLettera(c)){
						throw new IdentificatoreException("Errore nel parsare " + s);
					}
				}
				n.setTipoValore(TipoValore.variabile);
			}
		}
	}
	
	private static int trovaCarattere(String s, String[] caratteri){
	    //partendo dalla fine, trova la prima occorrenza di uno qualunque
	    //dei caratteri passati, ignorando il contenuto delle parentesi.
		int parentesi = 0;
		int indice = s.length();
		while(indice > 0){
			indice--;
			String charI = s.charAt(indice) + "";
			if(charI.equals(")"))
				parentesi++;
			else if(charI.equals("("))
				parentesi--;
			else if(parentesi == 0){
				for(String c:caratteri){
					if(charI.equals(c))
						return indice;
				}
			}
		}
		return -1;
	}
	private static String[] trovaFunzione(String s){
	    //trova la prima parentesi, cammina a gambero controllando che gli altri caratteri siano validi
		String[] ret = new String[2];
		int primaParentesi;
		if( (primaParentesi = s.indexOf('(')) != -1){
		    int index= primaParentesi-1;
		    while(index >=0 && Parser.isLettera(s.charAt(index))){
		        index--;
		    }
		    if(index == -1){
		        //il nome è valido
		        ret[0] = s.substring(0,primaParentesi);                 //nome della funzione
		        ret[1] = s.substring(primaParentesi+1,s.length()-1);    //stringa argomenti
		    }
		}
		return ret;
	}
	private static boolean isLettera(char c){
	    return (c>='A' && c<='Z' || c>='a' && c<='z' || c == '_');
	}
	
	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Nodo getAlbero(){
	    return this.albero;
	}
}
