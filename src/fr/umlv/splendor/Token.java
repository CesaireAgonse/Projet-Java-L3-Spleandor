package fr.umlv.splendor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

/**
 * This object represent a token.
 * 
 */
public class Token {

	private String color;
	
	/**
	 * Constructor of Card.
	 * 
	 *@param color (String) the color of the token
	 */
	public Token(String color){
		ArrayList<String> colors = new ArrayList<String>(
				Arrays.asList("blue", "red", "green", "yellow", "white", "black"));
		Objects.requireNonNull(color);
		if(colors.contains(color.toLowerCase(Locale.getDefault())) == false) {
			throw new IllegalArgumentException("Invalid color");
		}
		this.color = color.substring(0, 1).toUpperCase(Locale.getDefault()) + color.substring(1).toLowerCase();
	}
	
	/**
	 *Give access to the color of the token
	 * 
	 * @return (String) the color of the token
	 */
	public String color() {
		return color;
	}
	
	@Override
	public String toString() {
		return color;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Token))
			return false;
		Token other = (Token) obj;
		return Objects.equals(color, other.color());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(color);
	}

}