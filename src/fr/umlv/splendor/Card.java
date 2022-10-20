package fr.umlv.splendor;

import java.util.Objects;

/**
 * This object represent a Card.
 * 
 */
public class Card {
	private final int prestige;
	private String bonus;
	private final Tokens price;
	private final int id;
	private String name;
	
	/**
	 * Constructor of Card.
	 * 
	 *@param prestige (Integer)	the prestige of the card
	 *@param bonus	(String)	the bonus of the card
	 *@param price (Tokens)		the price of the card
	 *@param id	(Integer)		the id of the card
	 *@param name (String)		the name of the card
	 *
	 *@see Tokens
	 */
	public Card (int prestige, String bonus, Tokens price, int id, String name) {
		Objects.requireNonNull(bonus);
		Objects.requireNonNull(price);
		if (prestige < 0) {
			throw new IllegalArgumentException("prestige < 0");
		}
		this.bonus = bonus;
		this.price = price;
		this.prestige = prestige;
		this.id = id;
		this.name = name;
	}

	/**
	 *Give access to the prestige of the card.
	 * 
	 * @return (Integer) the prestige of the card
	 */
	public int prestige() {
		return prestige;
	}
	
	/**
	 *Give access to the bonus of the card.
	 * 
	 * @return (String) the bonus of the card
	 */
	public String bonus() {
		return bonus;
	}
	
	/**
	 *Give access to the price of the card.
	 * 
	 * @see Tokens
	 * @return (Tokens) the price of the card
	 */
	public Tokens price() {
		return price;
	}
	
	/**
	 *Give access to the name of the card.
	 * 
	 * @return (String) the name of the card
	 */
	public String name() {
		return name;
	}
	
	/**
	 *Check if the card can be buy by the player.
	 *
	 *@param tokens (Tokens)	the tokens of the player
	 *@param bonus (Tokens) 	the bonus of the player
	 *@see Tokens
	 *@return (boolean) 		if the player can buy this card
	 */
	public boolean available(Tokens tokens, Tokens bonus){
		return tokens.checkPrice(price.reduceTokens(bonus));
	}

	@Override
	public String toString() {
		return "\nprestige: " + prestige + " - bonus: " + bonus + "\nprice:" + price ;
	}
	@Override
	public int hashCode() {
		return Objects.hash(bonus, prestige, price, id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Card))
			return false;
		Card other = (Card) obj;
		return Objects.equals(bonus, other.bonus) && prestige == other.prestige
				&& Objects.equals(price, other.price) && id == other.id;
	}
	
}