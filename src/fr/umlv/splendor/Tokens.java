package fr.umlv.splendor;

import java.util.LinkedHashMap;
import java.util.Objects;

/**
 * This object represent the tokens and how many there are.
 * 
 *
 */
public class Tokens {
	/**
	 * Ordered Map of the tokens.
	 *
	 */
	private final LinkedHashMap<Token, Integer> tokens;
	
	/**
	 * Constructor of Tokens.
	 * 
	 */
	public Tokens () {
		 this.tokens = new LinkedHashMap<>();
		 tokens.put(new Token("Green"), 0);
		 tokens.put(new Token("Yellow"), 0);
		 tokens.put(new Token("White"), 0);
		 tokens.put(new Token("Blue"), 0);
		 tokens.put(new Token("Black"), 0);
		 tokens.put(new Token("Red"), 0);
	}
	
	/**
	 * Set the number of tokens associated to the color
	 * in the Map.
	 * 
	 * @param color	(String)	the color
	 * @param n	(String)		the number of tokens
	 */
	public void set(String color, int n) {
		Objects.requireNonNull(color);
		if(n < 0) {
			throw new IllegalArgumentException("Nombre de jetons invalide !");
		}
		tokens.put(new Token(color), n);
	}
	
	/**
	 * Return the number of tokens associated to the color
	 * in the Map.
	 * 
	 * @param color	(String)	the color
	 * @return (Integer)		the number of tokens associated to the color.
	 */
	public int get(String color) {
		Objects.requireNonNull(color);
		if (tokens.get(new Token(color)) == null) {
			return 0;
		}
		return tokens.get(new Token(color)); 
	}
	
	/**
	 * Increase the number of tokens associated to the color
	 * in the Map by the number give in argument.
	 * 
	 * @param color (String)	the color
	 * @param n	(String)		the number of new tokens
	 */
	public void add(String color, int n) {
		Objects.requireNonNull(color);
		if(tokens.containsKey(new Token(color))) {
			set(color, n + get(color));
		}
	}
	
	/**
	 * Take n tokens of the given color and return if it has 
	 * been taken or not.
	 * 
	 * @param color (String)	the color
	 * @param n	(Integer)		the number of taken tokens
	 * @return (boolean)		if the tokens have been taken or not.
	 */
	public boolean take(String color, int n) {
		Objects.requireNonNull(color);
		if(tokens.containsKey(new Token(color))) {
			if(get(color) >= n) {
				set(color, get(color) - n);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Set the number of each tokens in the Map.
	 * 
	 */
	public void plateTokens() {
		tokens.forEach((token, n) -> {
			if( token.color().equals("Yellow")) {
				tokens.replace(token, 5);
			}
			else {
				tokens.replace(token, 7);
			}
		});
	}
	
	/**
	 * Set the number of each tokens in the Map
	 * excluding the Yellow ones. Used for 2
	 * players only.
	 * 
	 */
	public void plateTokensLimited() {
		tokens.forEach((token, n) -> {
			if( token.color().equals("Yellow") == false) {
				tokens.replace(token, 7);
			}
		});
	}
	
	/**
	 * Deduce a number of tokens for each color
	 * based on an other Map of tokens.
	 * 
	 * @param reduce (Tokens)	the other Tokens object
	 * 							use for the reduction.
	 * 
	 * @see Tokens
	 * @return (Tokens)			the Tokens object obtained by this
	 * 							reduction.
	 */
	public Tokens reduceTokens(Tokens reduce) {
		var res = new Tokens();
		tokens.forEach((token, n) -> {
			if(n < reduce.get(token.color())) {
				res.add(token.color(), 0);
			}
			else {
				res.add(token.color(), n - reduce.get(token.color()));
			} 
		});
		
		return res;
	}
	
	/**
	 * Check if there is any token in the Map.
	 * 
	 * @return (boolean) true if there is no token, false if there is at least one.
	 */
	public boolean noTokens() {
		String[] colors = { "Green", "Yellow", "White", "Blue", "Black", "Red"};
		for(String color : colors) {
			if(get(color) != 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Calculate the total of tokens.
	 * 
	 * @return (Integer) the total of tokens.
	 */
	public int sumTokens() {
		int sum = 0;
		for(var entry : tokens.entrySet()) {
			sum += entry.getValue();
		}
		return sum;
	}
	
	/**
	 * Check if the number of tokens in the Map is bigger than
	 * those in an other for each color of them.
	 * 
	 * @param other (Tokens)	the other tokens
	 * @return (boolean) 		return true if it's the case, false otherwise.
	 */
	public boolean checkPrice(Tokens other) {
		int leftover = other.reduceTokens(this).sumTokens();
		if(leftover > 0 && leftover > get("Yellow")) {
			return false;
		}
		this.take("Yellow", leftover);
		return true;
	}
	
	@Override
	public String toString() {
		var builder = new StringBuilder("[ ");
		tokens.forEach((token, n) -> {
			String color = token.color();
			if(n > 0) {
			builder.append(color.charAt(0)).append(color.charAt(1)).append(color.charAt(2))
				   .append(" : ")
				   .append(n)
			       .append(" - ");
			}
		});
		if(builder.length() > 2) {
			builder.deleteCharAt(builder.length() - 1);
			builder.deleteCharAt(builder.length() - 1);
		}
		builder.append("]");
		return builder.toString();
	}
	
}