package fr.umlv.splendor;

/**
 * This object print the board and all its elements.
 * 
 */
public class Printer {
	
	/**
	 * Displays one level of cards face up available on the board
	 * 
	 * @param deck (Deck)  the deck who is printed
	 * @param level (Integer) the level of the deck 
	 * @see Deck
	 */
	public static void printLevel(Deck deck, int level) {
		var builder = new StringBuilder(); 
		for(int i = 1 ; i <= 4; i++) {
			builder.append("Carte ").append(i).append(" :")
				   .append(deck.get(level, i)).append("\n");
		}
		System.out.println(builder);
	}
	
	/**
	 * Displays the different level 1, 2 and 3 cards face up available on the board
	 *
	 * @param board (Board) the board
	 * @see Board
	 */
	public static void printFaceCards(Board board) {
		System.out.println("------------- Cartes : -------------");
		if(board.mode() == 1) {
			printLevel(board.faceCards(), 1);
		}
		else {
			for(int i = 1 ; i <= 3; i++) {
				System.out.println("Niveau " + i + " : ");
				printLevel(board.faceCards(),i);
			}
		}
	}
	
	/**
	 * Displays the Noble cards on the board
	 * 
	 * @param board (Board)  the board
	 * @see Board
	 */
	public static void printNobles(Board board) {
		System.out.println("------------- Nobles : -------------");
		int i  = 1;
		for(Noble noble : board.nobles()) {
			System.out.println("Noble " + i + " : \n" + noble);
			i++;
		}
	}
	
	/**
	 * Displays the board
	 * 
	 * @param board (Board)  the board
	 * @see Board
	 */
	public static void printPlate(Board board) {
		System.out.println("------------- Plateau : -------------");
		if(board.mode() == 2) {printNobles(board);}
		printFaceCards(board);
	}
	
	/**
	 * Displays the available tokens on the board and the player's 
	 * tokens that the player owns
	 * 
	 * @param board (Board) 	the board
	 * @param player (Integer)	number of the player
	 * @see Board
	 */
	public static void printTokens(Board board, int player) {
		System.out.println("------------- Jetons disponibles sur le plateau : -------------");
		System.out.println(board.tokens());
		System.out.println(" ------------- Jetons du joueur "+ player + " :  -------------");
		System.out.println(board.getPlayer(player).tokens());
	}
	
	/**
	 * Displays the cards of the player who play
	 * 
	 * @param board (Board) 	the board
	 * @param player (Integer)	number of the player
	 */
	public static void printHand(Board board, int player) {
		int i  = 1;
		System.out.println("-------------Main du joueur " + player + " : -------------");
		for(Card card : board.getPlayer(player).possessed() ) {
			System.out.println("Carte " + i + " : " + card);
		}
		System.out.println("------------- Nobles du joueur: -------------");
		i  = 1;
		for(Noble noble : board.getPlayer(player).nobles()) {
			System.out.println("Noble " + i + " : \n" + noble);
			i++;
		}
	}
	
	/**
	 * Displays the cards reserved by the player
	 * 
	 * @param board (Board)  the board
	 * @param player (Integer)	number of the player
	 * @see Board
	 */
	public static void printReserved(Board board, int player) {
		int i  = 1;
		System.out.println("-------------Cartes réservées par le joueur " + player + " : -------------");
		for(Card card : board.getPlayer(player).reserved() ) {
			System.out.println("Carte " + i + " : " + card);
		}
	}
}

