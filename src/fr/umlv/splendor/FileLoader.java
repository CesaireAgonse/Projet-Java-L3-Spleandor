package fr.umlv.splendor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;


/**
 * This object load files and extract board informations from it.
 * 
 */
public class FileLoader {

	/**
	 * Convert a line into the price of a Noble
	 * 
	 * @param line (String)	the line which represents the price
	 * @see Tokens
	 * @return (Tokens) the price
	 */
	public static Tokens importNoblePrice(String line) {
	    String[] result = line.split(" : ");
	    var tokens = new Tokens();
	    tokens.set("White", Integer.parseInt(result[1]));
	    tokens.set("Blue", Integer.parseInt(result[2]));
	    tokens.set("Green", Integer.parseInt(result[3]));
	    tokens.set("Red", Integer.parseInt(result[4]));
	    tokens.set("Black", Integer.parseInt(result[5]));
	    return tokens;
	  }
	
	/**
	 * Convert a line into a Noble and add it to a list of nobles
	 * 
	 * @param nobles (ArrayList) 	the list of nobles
	 * @param line (String)				the line which represents the noble
	 * @see Noble
	 */
	public static void importNobles(ArrayList<Noble> nobles, String line) {
		  var prix = importNoblePrice(line);
		  String[] result = line.split(" : ");
		  nobles.add(new Noble(result[0], prix));
	 }
	
	/**
	 * Convert a line into the price of a card
	 * 
	 * @param line (String)	the line which represents the price
	 * @see Tokens
	 * @return the price
	 */
	public static Tokens importTokens(String line) {
	    String[] result = line.split(" : ");
	    var tokens = new Tokens();
	    tokens.set("White", Integer.parseInt(result[3]));
	    tokens.set("Blue", Integer.parseInt(result[4]));
	    tokens.set("Green", Integer.parseInt(result[5]));
	    tokens.set("Red", Integer.parseInt(result[6]));
	    tokens.set("Black", Integer.parseInt(result[7]));
	    return tokens;
	}
	
	/**
	 * Convert a line into a Card and add it to a deck
	 * 
	 * @param deck (Deck)	the deck
	 * @param line (String)	the line which represents the card
	 * @see Deck
	 */
	public static void importCard(Deck deck,String line) {
		  
	  var prix = importTokens(line);
	  String[] result = line.split(" : ");
	  var card = new Card(Integer.parseInt(result[1]), result[2], prix, 1, result [8]);
	  switch(result[0]) {
	  	case "niveau_1":
		deck.add(card, 1);break;
		case "niveau_2":
			deck.add(card, 2);break;
		case "niveau_3":
		  		deck.add(card, 3);break;
	  }
	}
	
	/**
	 * Import a deck from a file by converting each lines of it
	 * 
	 * @param deck (Deck)	the deck
	 * @param file (String)	the file from which the deck will be extracted
	 * @see Deck
	 */
	public static void loadDeck (Deck deck,String file) {
		try {
			Files.lines(Paths.get(file)).forEach(elem -> importCard(deck, elem));
		} 
		catch (IOException e) {
		  e.printStackTrace();
		}
	}
	
	/**
	 * Import a list of nobles from a file by converting each lines of it
	 * 
	 * @param nobles (ArrayList)	the list of nobles
	 * @param file (String)			the file from which the list will be extracted
	 * @param nbPlayers (Integer)	the number of players
	 * @see Noble
	 */
	public static void loadNobles (ArrayList<Noble> nobles,String file, int nbPlayers) {
		try {
			var tmp = new ArrayList<Noble>();
			Files.lines(Paths.get(file)).forEach(elem -> importNobles(tmp, elem));
			Collections.shuffle(tmp);
			for(Noble noble :tmp.subList(0, nbPlayers + 1)){
				nobles.add(noble);
			}
		} 
		catch (IOException e) {
		  e.printStackTrace();
		}
	}
}
