package fr.umlv.splendor;

import java.util.Objects;

/**
 *This object represents the Noble card
 *which will visit the player during the game.
 *
 */
public class Noble {
	private final String nom;
	private final Tokens prix;
	
	/**
	 *Constructor of Noble
	 *
	 *@param nom	the name of the Noble card
	 *@param prix	the price of the Noble card
	 */
	public Noble (String nom, Tokens prix) {
		Objects.requireNonNull(nom);
		Objects.requireNonNull(prix);
		
		this.nom = nom;
		this.prix = prix;

	}
	
	/**
	 *Give access to the name of the card.
	 *
	 *@return the name of the card
	 */
	public String nom() {
		return nom;
	}
	
	/**
	 *Give access to the price of the card.
	 *
	 *@return the price of the card
	 */
	public Tokens prix() {
		return prix;
	}
	
	@Override
	public String toString() {
		return "nom : " + nom + " - prix : " + prix ;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nom, prix);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Noble))
			return false;
		Noble other = (Noble) obj;
		return Objects.equals(nom, other.nom) && Objects.equals(prix, other.prix);
	}
	
	/**
	 *Check if the player has enough bonus to get the card.
	 *
	 *@param player	the player
	 *@return if the player has enough bonus to get the card
	 */
	public boolean checkPlayer(Player player) {
		if (prix.reduceTokens(player.bonus()).noTokens()) {
			player.addNobles(this);
			return true;
		}
		return false;
	}
	
	/**
	 *Check if the player will get the visit of one of the Noble on the board.
	 *
	 *@param board	the board
	 *@param player	the number of the player 
	 */
	public static void nobleVisit(Board board, int player) {
		for(Noble noble : board.nobles()) {
			if(noble.checkPlayer(board.getPlayer(player))) {
				board.nobles().remove(noble);
				break;
			}
		}
	}
	
}
