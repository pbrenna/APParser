package it.AP.Parser;
public class Test{
	public static void main(String[] args) throws ParsingException, EsecuzioneException{
		String s = "f()=2";
		String s1 = "y*3";
		/*Parser p = new Parser(s);
		p.parsing();
		p.getAlbero().printa(0);*/
		//813,4
		Esecutore e = new Esecutore(s);
		e.esegui();
		e.setFormula("f()");
		e.esegui();
		System.out.println("risposta: "+ e.getVariabile("ans"));
	}
}

