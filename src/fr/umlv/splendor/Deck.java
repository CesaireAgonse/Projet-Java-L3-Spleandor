package fr.umlv.splendor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * This object represents a 3 levels deck of card .
 * 
 */
public class Deck {

	private final LinkedHashMap<Card,Integer> niveau1;
	private final LinkedHashMap<Card,Integer> niveau2;
	private final LinkedHashMap<Card,Integer> niveau3;
	
	/**
	 * Constructor of Deck.
	 * 
	 */
	public Deck() {
		this.niveau1 = new LinkedHashMap<Card,Integer>();
		this.niveau2 = new LinkedHashMap<Card,Integer>();
		this.niveau3 = new LinkedHashMap<Card,Integer>();
	}
	
	/**
	 * Return a card in a deck
	 * 
	 * @param niveau (Integer)	level of the card
	 * @param n (Integer) 		the number of the card in the deck
	 * @see Card
	 * @return a card (Card)
	 */
	public Card get(int niveau, int n) {	
		if (niveau < 1 || niveau > 3) {
			throw new IllegalArgumentException("Niveau incorrect !");
		}
		LinkedHashMap<Card,Integer> level =null;
		switch(niveau) {
			case 1:
				level = niveau1;break;
			case 2:
				level = niveau2;break;
			case 3:
				level = niveau3;break;
		}
		var iter = level.entrySet().iterator();
		Card card = null;
		for (int i = 0; i < n; i++) {
			card = iter.next().getKey();
		}
		return card;
	}
	
	/**
	 * Add a card in a deck
	 * 	
	 * @param card (Card) 		the card who must be add to the deck
	 * @param niveau (Integer) 	the level of the deck where the card should 
	 * 							be inserted
	 * @see Card
	 */
	public void add(Card card, int niveau) {
		Objects.requireNonNull(card);
		if (niveau < 1) {
			throw new IllegalArgumentException("niveau < 1");
		}else if (niveau > 3) {
			throw new IllegalArgumentException("niveau > 3");
		}
		switch(niveau) {
			case 1 : niveau1.put(card, 1); break;
			case 2 : niveau2.put(card, 1); break;
			case 3 : niveau3.put(card, 1); break;
			default : throw new IllegalArgumentException("il y a une erreur quelque part");
		}
	}
	
	/**
	 * Add a card in a deck at a precise position
	 * 	
	 * @param card (Card) 		the card who must be add to the deck
	 * @param niveau (Integer) 	the level of the deck where the card should 
	 * 							be inserted
	 * @param numero (Integer)	the position where it should be inserted
	 * @see Card
	 */
	public void add(Card card, int niveau, int numero) {
		LinkedHashMap<Card, Integer> level = null;
		switch(niveau) {
		case 1 : level = niveau1; break;
		case 2 : level = niveau2; break;
		case 3 : level = niveau3; break;
		}
		if (level != null) { 
			for(int i = 0; i < numero - 1; i++) {
				level.put(take(niveau), 1);
			}
			level.put(card, 1);
			for(int i = numero ; i < 4; i++) {
				level.put(take(niveau), 1);
			}
		}
	}
	
	/**
	 * Shuffle a level of the deck
	 * 
	 * @param level (LinkedHashMap) the level of the deck
	 * @see Card
	 */
	public void shuffleLevel(LinkedHashMap<Card,Integer> level) {
		
		List<Card> keys = new ArrayList<Card>(level.keySet());
		Collections.shuffle(keys);
		level.clear();
		for(Card card : keys) {
			level.put(card, 1);
		}
	}
	
	/**
	 * Shuffle the level 1,2 and three of the deck
	 */
	public void shuffleDeck(){
		shuffleLevel(niveau1);
		shuffleLevel(niveau2);
		shuffleLevel(niveau3);
	}
	
	/**
	 * Take a card from a deck
	 * 
	 * @param niveau (Integer) level of the deck where the card is
	 * @see Card
	 * @return (Card) the card which has been retrieve from the deck
	 */
	public Card take(int niveau) {		
		if (niveau < 1 || niveau > 3) {
			throw new IllegalArgumentException("Niveau incorrect !");
		}
		LinkedHashMap<Card,Integer> level =null;
		switch(niveau) {
			case 1:
				level = niveau1;break;
			case 2:
				level = niveau2;break;
			case 3:
				level = niveau3;break;
		}
		var iter = level.entrySet().iterator();
		Card card = iter.next().getKey();
		level.remove(card);
		return card;
	}
	
	/**
	 * Take a card from a deck at precise position
	 * 
	 * @param niveau (Integer) 	level of the deck where the card is
	 * @param n (Integer)		position of the card in this level
	 * @see Card
	 * @return (Card) the card which has been retrieve from the deck
	 */
	public Card take(int niveau, int n) {		
		if (niveau < 1 || niveau > 3) {
			throw new IllegalArgumentException("Niveau incorrect !");
		}
		LinkedHashMap<Card,Integer> level =null;
		switch(niveau) {
			case 1:
				level = niveau1;break;
			case 2:
				level = niveau2;break;
			case 3:
				level = niveau3;break;
		}
		var iter = level.entrySet().iterator();
		Card card = null;
		for (int i = 0; i < n; i++) {
			card = iter.next().getKey();
		}
		level.remove(card);
		return card;
	}
	
	/**
	 * Remove a card from a deck
	 * 
	 * @param level (Integer)	the level of the card
	 * @param card (Card)		the card
	 * @see Card
	 */
	public void take(int level, Card card) {		
		if (level < 1 || level > 3) {
			throw new IllegalArgumentException("Niveau incorrect !");
		}
		switch(level) {
			case 1:
				niveau1.remove(card);break;
			case 2:
				niveau2.remove(card);break;
			case 3:
				niveau3.remove(card);break;
		}
	}
	
	/**
	 * Return the number of card in a level of the deck
	 * 
	 * @param niveau (Integer)	the level
	 * @return (Integer) 		the size of the level
	 */
	public int size(int niveau) {
		if (niveau < 1 || niveau > 3) {
			throw new IllegalArgumentException("Niveau incorrect !");
		}
		int size = 0;
		switch(niveau) {
			case 1:
				 size = niveau1.size();break;
			case 2:
				size = niveau2.size();break;
			case 3:
				size = niveau3.size();break;
		}
		return size;
	}

	
	/**
	 * Return the sum of number of prestige point in deck of level 1, 2 and 3
	 * 
	 * @return (Integer) the sum of number of prestige points 
	 */
	public int prestige(){
		int prestige = 0;
		for(var entry : niveau1.entrySet()) {
			prestige += entry.getKey().prestige();
		}
		for(var entry : niveau2.entrySet()) {
			prestige += entry.getKey().prestige();
		}
		for(var entry : niveau3.entrySet()) {
			prestige += entry.getKey().prestige();
		}
		return prestige;
	}
	

	@Override
	public String toString() {
		return "Deck \nCartes de niveau1: " + niveau1 + "\n\nCartes de niveau2: " + niveau2 + "\n\nCartes de niveau3: " + niveau3 ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(niveau1, niveau2, niveau3);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Deck))
			return false;
		Deck other = (Deck) obj;
		return Objects.equals(niveau1, other.niveau1) && Objects.equals(niveau2, other.niveau2)
				&& Objects.equals(niveau3, other.niveau3);
	}
	
	
}