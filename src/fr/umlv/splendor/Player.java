package fr.umlv.splendor;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This object represent a Player.
 * 
 * @see Card
 * @see Tokens
 * @see Noble
 */
public class Player {

	final private ArrayList<Card>  possessed;
	final private ArrayList<Card> reserved;
	private Tokens tokens;
	final private Tokens bonus;
	final private ArrayList<Noble> nobles;
	private int prestige;
	
	/**
	 * Constructor of Player.
	 *
	 */
	public Player() {
		
		this.possessed = new ArrayList<>();
		this.reserved = new ArrayList<>();
		this.tokens = new Tokens();
		this.bonus = new Tokens();
		this.nobles = new ArrayList<>();
		this.prestige = 0;
		
	}
	
	/**
	 *Give access to the card possessed by the player.
	 *
	 *@see Card
	 *@return (ArrayList) the card possessed by the player
	 */
	public ArrayList<Card> possessed() {
		return possessed;
	}
	
	/**
	 *Give access to the card reserved by the player.
	 *
	 *@see Card
	 *@return (ArrayList) the card reserved by the player
	 */
	public ArrayList<Card> reserved() {
		return reserved;
	}
	
	/**
	 *Give access to the tokens possessed by the player.
	 *
	 *@see Tokens
	 *@return (Tokens) the tokens possessed by the player
	 */
	public Tokens tokens() {
		return tokens;
	}
	
	/**
	 *Give access to the bonus possessed by the player.
	 *
	 *@see Tokens
	 *@return (Tokens) the bonus possessed by the player
	 */
	public Tokens bonus() {
		return bonus;
	}
	
	/**
	 *Give access to the noble possessed by the player.
	 *
	 *@see Noble
	 *@return (ArrayList) the noble possessed by the player
	 */
	public ArrayList<Noble> nobles() {
		return nobles;
	}
	
	/**
	 *Give access to the prestige possessed by the player.
	 *
	 *@return (Integer) the prestige possessed by the player
	 */
	public int prestige() {
		return prestige;
	}
	
	/**
	 *Make the player do a reservation if he can.
	 *
	 *@param deck (Deck)		the card on the plate
	 *@param draw (Deck)		the card in the reserve of the plate
	 *@param niveau	(Integer) 	the level of the card
	 *@param n	(Integer)		the position of the card on the plate
	 *
	 *@see Deck
	 *@return (boolean) 		true if the reservation has been done, false otherwise
	 */
	public boolean reservation(Deck deck, Deck draw, int niveau, int n) {
		Objects.requireNonNull(deck);
		if(niveau < 0 || niveau > 3) {
			throw new IllegalArgumentException("niveau incorrect");
		}
		if(reserved.size() < 3) {
			Card card = deck.take(niveau, n);
			reserved.add(card);
			deck.add(draw.take(niveau), niveau, n);
			return true;
		}
		return false;
	}
	
	/**
	 *Make the player buy a card if he can.
	 *	
	 *@param deck (Deck)		the card on the plate
	 *@param draw (Deck)		the card in the reserve of the plate
	 *@param niveau	(Integer) 	the level of the card
	 *@param n	(Integer)		the position of the card on the plate
	 *
	 *@see Deck
	 *@return (boolean) 		true if the buy has been done, false otherwise
	 */
	public boolean buyCard(Deck deck, Deck draw, int niveau, int n) {
		Objects.requireNonNull(deck);
		if(niveau < 0 || niveau > 3) {
			throw new IllegalArgumentException("niveau incorrect");
		}
		Card card = deck.get(niveau, n);
		if(card.available(tokens, bonus)) {
			deck.take(niveau, card);
			tokens = tokens.reduceTokens(card.price().reduceTokens(bonus));
			possessed.add(card);
			deck.add(draw.take(niveau), niveau, n);
			bonus.add(card.bonus(), 1);
			prestige += card.prestige();
			return true;
		}
		return false;
	}
	
	/**
	 *Make the player buy a card  he has already reserved if he can.
	 *
	 *@param n	(Integer)	the position of the card in his reserved list
	 *@return (boolean)		true if the buy has been done, false otherwise
	 */
	public boolean buyReserved(int n) {
		if(n < 0 || n > 4) {
			throw new IllegalArgumentException("Numéro de carte incorrect");
		}
		Card card = reserved.get(n);
		if(card.available(tokens, bonus)) {
			reserved.remove(n);
			tokens = tokens.reduceTokens(card.price().reduceTokens(bonus));
			possessed.add(card);
			bonus.add(card.bonus(), 1);
			prestige += card.prestige();
			return true;
		}
		return false;
	}
	
	/**
	 *Add n tokens of the given color
	 *
	 *@param color (String)	the color of the tokens
	 *@param n	(Integer)	the number of tokens 
	 */
	public void addTokens(String color, int n) {
		tokens.add(color, n);
	}
	
	/**
	 *Add a the given noble to the noble list
	 *
	 *@param noble (Noble)	The noble
	 *@see Noble
	 */
	public void addNobles(Noble noble) {
		nobles.add(noble);
		prestige += 3;
	}
	
}
