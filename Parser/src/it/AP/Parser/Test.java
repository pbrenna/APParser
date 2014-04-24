package it.AP.Parser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Test{
	public static void main(String[] args) throws ParsingException, EsecuzioneException, IOException{
		Esecutore e = new Esecutore("");
		
		BufferedReader br = new BufferedReader(new FileReader("./src/it/AP/Parser/formule.txt"));
		String line;
		while ((line = br.readLine()) != null) {
		   e.setFormula(line.trim());
		   e.esegui();
		}
		System.out.println("risposta: "+ e.getVariabile("ans"));
	}
}

