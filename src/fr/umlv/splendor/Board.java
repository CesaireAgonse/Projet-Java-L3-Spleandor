package fr.umlv.splendor;

import java.util.ArrayList;

/**
 * This object represents the board and all its elements.
 * 
 */
public class Board {

	final private ArrayList<Player> players;
	final private Deck draw;
	final private Deck faceCards;
	final private Tokens tokens;
	final private ArrayList<Noble> nobles;
	final private int nbPlayers;
	final int mode;
	
	/**
	 * Constructor of Board.
	 * 
	 * @param nbPlayers (Integer)	the number of players
	 * @param mode (Integer)		mode of the game ( 1 for simple, 2 for advanced)
	 * @see Tokens
	 * @see Player
	 * @see Deck
	 */
	public Board(int nbPlayers, int mode) {
		this.players = new ArrayList<>();
		this.draw = new Deck();
		this.faceCards = new Deck();
		this.tokens = new Tokens();
		this.nobles = new ArrayList<>();
		this.nbPlayers = nbPlayers;
		for(int i = 0; i < nbPlayers; i++) {
			players.add(new Player());
		}
		if(mode == 2) {
			tokens.plateTokens();
		}
		else {
			tokens.plateTokensLimited();
		}
		this.mode = mode;
	}
	
	/**
	 * Give access to the face down deck
	 * @see Deck
	 * @return (Deck) the face down deck
	 */
	public Deck draw() {
		return draw;
	}
	
	/**
	 * Return the face up deck
	 * @see Deck
	 * @return (Deck) the face up deck
	 */
	public Deck faceCards() {
		return faceCards;
	}
	
	/**
	 * Give access to the tokens of the plate
	 * @see Tokens
	 * @return (Tokens) the tokens
	 */
	public Tokens tokens() {
		return tokens;
	}
	
	/**
	 * Give access to the list of nobles 
	 * @see Tokens
	 * @return (ArrayList) the list of nobles
	 */
	public ArrayList<Noble> nobles() {
		return nobles;
	}
	
	/**
	 * Give access to the number of players
	 * @see Deck
	 * @return (Integer) the number of players
	 */
	public int nbPlayers() {
		return nbPlayers;
	}
	
	/**
	 * Give access to the mode of the game
	 * @see Deck
	 * @return (Integer) the mode of the game ( 1 for easy, 2 for advanced)
	 */
	public int mode() {
		return mode;
	}
	
	/**
	 * Load a preset deck from a file and shuffle this deck
	 * @see FileLoader
	 * @param file (String) the name of the file
	 */
	public void loadDeck(String file) {
		if(mode == 1) {
			String[] colors = { "Green", "White", "Blue", "Black", "Red"};
			for(String color : colors) {
				var Tokens = new Tokens();
				Tokens.add(color, 3);
				for(int i = 0; i < 8; i++) {
					Card card = new Card(1, color, Tokens, i, color);
					draw.add(card , 1);
				}
			}
		}
		else {
			FileLoader.loadDeck(draw, file);
		}
		draw.shuffleDeck();
	}
	
	/**
	 * Load a preset list of nobles from a file and shuffle this list
	 * @see FileLoader
	 * @param file (String) the name of the file
	 */
	public void loadNobles(String file) {
		FileLoader.loadNobles(nobles, file, nbPlayers);
	}
	
	/**
	 * Fill face up deck
	 * @see Deck
	 */
	public void fillFaceCards() {
		if(mode == 1) {
			while (faceCards.size(1) < 4) {
				Card card = draw.take(1);
				faceCards.add(card, 1);
			}
		}
		else {
			for(int i = 1; i <= 3; i++) {
				while (faceCards.size(i) < 4) {
					Card card = draw.take(i);
					faceCards.add(card, i);
				}
			}
		}
	}
	
	/**
	 * Return the player based of the given number of it 
	 * 
	 * @param player (Integer)	number of the player
	 * @see Player
	 * @return (Player) 		the player
	 */
	public Player getPlayer(int player) {
		if( player < 1 || player > nbPlayers) {
			throw new IllegalArgumentException("Numéro de joueur incorrect!");
		}
		return players.get(player - 1);
	}
	
	/**
	 * Make a player do a reservation.
	 * 
	 * @param player (Integer) 	number of the player
	 * @param niveau (Integer) 	level of the card
	 * @param n (Integer) 	   	position of the card in the level
	 * @see Player
	 * @return (boolean)   		Return if the reservation is done.
	 */
	public boolean playerReservation(int player, int niveau, int n) {
		Player P = getPlayer(player);
		if (P.reservation(faceCards, draw, niveau, n)) {
			playerTakeTokens(player, "Yellow", 1);
			return true;
		}
		return false;
	}
	
	/**
	 * Make a player buy a card.
	 * 
	 * @param player (Integer) 	number of the player
	 * @param niveau (Integer) 	level of the card
	 * @param n (Integer) 	   	position of the card in the level
	 * @see Player
	 * @return (boolean)   		Return if the buy is done.
	 */
	public boolean playerBuy(int player, int niveau, int n) {
		Player P = getPlayer(player);
		if(faceCards.get(niveau, n).available(P.tokens(), P.bonus())) {
			P.buyCard(faceCards, draw, niveau, n);
			return true;
		}
		return false;
	}
	
	/**
	 * Make a player buy a card he has already reserved.
	 * 
	 * @param player (Integer) 	number of the player
	 * @param n (Integer) 	   	position of the card in the reserved list
	 * @see Player
	 * @return (boolean)   		Return if the buy is done.
	 */
	public boolean playerBuyReserved(int player, int n) {
		Player P = getPlayer(player);
		return P.buyReserved(n);
	}
	
	/**
	 * Make a player take tokens from the board.
	 * 
	 * @param player (Integer) 	number of the player
	 * @param color (String)	the color of the tokens
	 * @param number (Integer) 	the number of tokens
	 * @see Player
	 * @return (boolean)   		Return if the tokens are taken.
	 */
	public boolean playerTakeTokens(int player, String color, int number) {
		if(color.toLowerCase().equals("yellow")) {return false;}
		Player P = getPlayer(player);
		if(tokens.take(color, number)) {
			P.addTokens(color, number);
			return true;
		}
		return false;
	}
	
	/**
	 * Check if there is more than 3 tokens of the given color.
	 * 
	 * @param color (String) the color
	 * @return (boolean)   Return if there is more than 3 tokens or not.
	 */
	public boolean moreThan3(String color) {
		if(tokens.get(color) > 3) {
			return true;
		}
		return false;
	}
	
	/**
	 * Check if the current player has more than 15 prestige points.
	 * 
	 * @param player (Integer) number of the player
	 * @return (boolean) Return if the player has more than 15 prestige points or not.
	 */
	public boolean checkVictory(int player) {
		Player P = getPlayer(player);
		return P.prestige() >= 15;
	}
}
