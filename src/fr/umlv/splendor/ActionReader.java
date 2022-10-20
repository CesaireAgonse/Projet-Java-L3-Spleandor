package fr.umlv.splendor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

import fr.umlv.zen5.Application;

/**
 * This object read the actions of the player and executes them.
 * 
 */
public class ActionReader {
	
	/**
	 * Read the input of the player and make him buy a card if
	 * he can.
	 * 
	 * @param board (Board) 	the board
	 * @param player (Integer)	number of the player
	 * @see Board
	 * @return (boolean)		true if the buy has been made, false otherwise
	 */
	public static boolean buyPlate(Board board, int player) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Quel carte souhaiter vous acheter? (Niveau numéro)");
		int niveau = 0;
		int numero = 0;
		do {
			niveau = sc.nextInt();
			numero = sc.nextInt();
			if(niveau < 1 || niveau > 3 || numero < 1 || numero > 4 ) {
				System.out.println("Niveau ou numéro incorrect !");
			}
		}while(niveau < 1 || niveau > 3 || numero < 1 || numero > 4 );
		if (board.playerBuy(player, niveau, numero)) {
			return true;
		}
		System.out.println("Carte non achetable avec les ressources du joueur !");
		return false;
	}
	
	/**
	 * Read the input of the player and make him buy a reserved card if
	 * he can.
	 * 
	 * @param board (Board) the board
	 * @param player (Integer)	number of the player
	 * @see Board
	 * @return (boolean)	true if the buy has been made, false otherwise
	 */
	public static boolean buyReserved(Board board, int player) {
		if(board.getPlayer(player).reserved().size() < 1) {
			System.out.println("Pas de cartes réservées pour ce joueur!");
			return false;
		}
		Printer.printReserved(board, player);
		Scanner sc = new Scanner(System.in);
		System.out.println("Quel carte souhaiter vous acheter? (Numéro)");
		int numero = 0;
		do {
			numero = sc.nextInt();
			if(numero < 1 || numero > board.getPlayer(player).reserved().size()) {
				System.out.println("Numéro incorrect !");
			}
		}while(numero < 1 || numero > board.getPlayer(player).reserved().size() );
		if (board.playerBuyReserved(player, numero)) {
			return true;
		}
		System.out.println("Carte non achetable avec les ressources du joueur !");
		return false;
	}
	
	/**
	 * Read the input of the player and make him buy a card if
	 * he can.
	 * 
	 * @param board (Board) the board
	 * @param player (Integer)	number of the player
	 * @see Board
	 * @return (boolean)	true if the buy has been made, false otherwise
	 */
	public static boolean buy(Board board, int player) {
		if(board.mode == 1) {
			return buyPlate(board, player);
		}
		Scanner sc = new Scanner(System.in);
		System.out.println("1 : Acheter carte du plateau - 2 : Acheter carte réservée");
		String choice = sc.nextLine();
		while(choice.equals("1") == false && choice.equals("2") == false) {
			System.out.println("Réponse invalide, entrez \"1\" ou \"2\" pour valider votre choix!");
			choice = sc.nextLine();
		}
		if(choice.equals("1")) {
			return buyPlate(board, player);
		}
		return buyReserved(board, player);
	}

	/**
	 * Read the input of the player and make him reserved a card if
	 * he can.
	 * 
	 * @param board (Board) the board
	 * @param player (Integer)	number of the player
	 * @see Board
	 * @return (boolean)	true if the reservation has been made, false otherwise
	 */
	public static boolean reservation(Board board, int player) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Quel carte souhaiter vous réserver ? (Niveau numéro)");
		int niveau = 0;
		int numero = 0;
		do {
			String choice = sc.nextLine();
			while(choice.split(" ").length < 2) {
				System.out.println("Nombre d'arguments insuffisant !  ");
				choice = sc.nextLine();
			}
			niveau = Integer.parseInt(choice.split(" ")[0]);
			numero = Integer.parseInt(choice.split(" ")[1]);
			if(niveau < 1 || niveau > 3 || numero < 1 || numero > 4 ) {
				System.out.println("Niveau ou numéro incorrect !");
			}
		}while(niveau < 1 || niveau > 3 || numero < 1 || numero > 4 );
		if (board.playerReservation(player, niveau, numero)) {
			board.playerTakeTokens(player, "Yellow", 1);
			return true;
		}
		System.out.println("Plus de 2 cartes réservés : réservation impossible !");
		return false;
	}
	
	/**
	 * Read the input of the player and make him take 2
	 * tokens of the same color if he can.
	 * 
	 * @param board (Board)  	the board
	 * @param player (Integer)	number of the player
	 * @see Board
	 * @return (boolean)		true if the tokens have been taken, false otherwise
	 */
	public static boolean take2Tokens(Board board, int player) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Quelle couleur ?");
		String choice = sc.nextLine();
		while(board.moreThan3(choice) == false) {
			System.out.println("Moins de 4 jetons disponibles ! Changer de couleur ou quitter en entrant \"quit\"");
			choice = sc.nextLine();
			if( choice.equals("quit")) { return false;}
		}
		while(choice.equals("Yellow") || board.playerTakeTokens(player, choice, 2) == false) {
			System.out.println("Pas de jetons disponibles ! Changer de couleur ou quitter en entrant \"quit\"");
			choice = sc.nextLine();
			if( choice.equals("quit")) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check if the choice is composed by 3 English colors
	 * 
	 * @param choice (String[]) the choice
	 * @return (boolean)		return if the choice is composed by 3 English colors
	 */
	public static boolean check3Colors(String[] choice) {
		var colors = new ArrayList<String>(
				Arrays.asList("blue", "red", "green", "white", "black"));
		for(int i = 0; i < 3; i++) {
			if(!(colors.contains(choice[i].toLowerCase()))){
				System.out.println("Entrée incorrect ! Entrez 3 couleurs en anglais !");
				
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Check if the choice is composed by 3 different colors
	 * 
	 * @param board (Board) 		the board
	 * @param player (Integer)		number of the player
	 * @param choice (String[]) 	the choice
	 * @see Board
	 * @return (boolean)			return if the choice is composed by 3 different colors
	 */
	public static boolean differentColors(Board board, int player, String[] choice) {
		String color1 = choice[0];
		String color2 = choice[1];
		String color3 = choice[2];
		if(color1.equals(color2) || color1.equals(color3)|| color2.equals(color3)){
			System.out.println("Couleurs identiques !Changer de couleur ou quitter en entrant \"quit\"");
			return false;
		}
		return true;
	}
	
	/**
	 * Read the input of the player and check
	 * if it's a correct input to take 3 tokens.
	 * 
	 * @param board (Board) 	the board
	 * @param player (Integer)	number of the player
	 * @param choice (String)	the choice
	 * @see Board
	 * @return (boolean) 		return if it's a correct input
	 */
	public static boolean check3Tokens(Board board, int player, String choice) {
		if(choice.split(" ").length < 3) {
			System.out.println("Nombre de couleurs insuffisant ! ");
			return false;
		}
		if(check3Colors(choice.split(" ")) == false ||
				differentColors(board, player, choice.split(" ")) == false){
			return false;
		}
		for(int i = 0; i < 3; i++) {
			String color = choice.split(" ")[i];
			if(board.tokens().get(color) == 0 ) {
				System.out.println("Nombre de jetons disponibles insuffisant ! "
						+ "Veuillez entrer d'autres couleurs ou \"quit\" pour quitter.");
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Read the input of the player and make him take 3
	 * tokens of different colors if he can.
	 * 
	 * @param board (Board) 	the board
	 * @param player (Integer)	number of the player
	 * @see Board
	 * @return (boolean) 		true if the tokens have been taken, false otherwise
	 */
	public static boolean take3Tokens(Board board, int player) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Quelles couleurs ?");
		String choice = sc.nextLine();
		while(!(check3Tokens(board, player, choice))){
			choice = sc.nextLine();
			if(choice.equals("quit")) {
				return false;
			}
		}
		for(int i = 0; i < 3; i++) {
			board.playerTakeTokens(player, choice.split(" ")[i], 1);
		}
		return true;
	}
	
	/**
	 * Read the input of the player and make him take tokens
	 *	based on his choice if it's possible.
	 * 
	 * @param board (Board)  	the board
	 * @param player (Integer)	number of the player
	 * @see Board
	 * @return (boolean) 		true if the tokens have been taken, false otherwise
	 */
	public static boolean takeTokens(Board board, int player) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Souhaiter vous prendre : - 1 : 2 jetons (même couleur) - 2 : 3 jetons(couleurs différentes) ?");
		String choice;
		do {
			choice = sc.nextLine();
			if(!(choice.equals("1")) && !(choice.equals("2")) ) {
				System.out.println("Nombre de jetons incorrect !");
			}
		}while(!(choice.equals("1")) && !(choice.equals("2")) );
		switch(Integer.parseInt(choice) + 1) {
			case 2 :
				return take2Tokens(board, player);
			case 3 :
				return take3Tokens(board, player);
		}
		return false;
	}
	
	/**
	 * Read the input of the player and make him execute actions
	 * based on his choice if it's possible. 
	 * 
	 * @param board (Board)  	the board
	 * @param player (Integer)	number of the player
	 * @param choice (String)	the input of the player
	 * @see Board
	 * @return	(boolean) 		If the action (except for print actions) is done, 
	 * 							return true.Else, return false. 
	 */
	public static boolean readChoice(Board board,int player, String choice) {
		boolean done = false;
		switch(choice) {
			case "1": 
				done = buy(board, player);break;
			case "2":
				done = reservation(board, player);break;
			case "3": 
				done = takeTokens(board,player);break;
			case "4": 
				Printer.printTokens(board, player);return false;
			case "5": 
				Printer.printHand(board, player);return false;
			case "6": 
				Printer.printPlate(board);return false;
			case "7": System.exit(0);break;
			default: return false;
		}
		return done;
	}
	
	/**
	 * Read the input of the player and make him execute actions
	 * based on his choice if it's possible. 
	 * 
	 * @param board (Board) 	the board
	 * @param player (Integer)	number of the player
	 * @param choice (String)	the input of the player
	 * @see Board
	 * @return (boolean)		If the action (except for print actions) is done, return 
	 * 							true. Else, return false. 
	 */
	public static boolean readChoiceLimited(Board board,int player, String choice) {
		boolean done = false;
		switch(choice) {
			case "1": 
				done = buy(board, player);break;
			case "2": 
				done = takeTokens(board,player);break;
			case "3": 
				Printer.printTokens(board, player);return false;
			case "4": 
				Printer.printHand(board, player);return false;
			case "5": 
				Printer.printPlate(board);return false;
			case "6": System.exit(0);break;
			default: return false;
		}
		return done;
	}
	
	/**
	 * Read the input of the player and make him execute actions
	 * based on his choice if it's possible. 
	 * 
	 * @param board (Board)  	the board
	 * @see Board
	 * @param player (Integer)	number of the player
	 */
	public static void playerChoice(Board board, int player) {
	    
		Scanner sc = new Scanner(System.in);
		String choice = "";
		do {
			System.out.println("Que souhaiter vous faire ?");
			System.out.println("1 : Acheter carte - 2 : Réserver carte \n3 : Prendre jetons "
					+ "- 4 : Voir Jetons \n5 : Voir Main - 6 : Voir Plateau\n7 : Quitter  ");
			choice = sc.nextLine();
		}while(readChoice(board, player, choice) == false);
		Noble.nobleVisit(board, player);
	
	}
	
	/**
	 * Read the input of the player and make him execute actions
	 * based on his choice if it's possible. 
	 * 
	 * @param board (Board)  the board
	 * @see Board
	 * @param player (Integer)	number of the player
	 */
	public static void playerChoiceLimited(Board board, int player) {
	    
		Scanner sc = new Scanner(System.in);
		String choice = "";
		do {
			System.out.println("Que souhaiter vous faire ?");
			System.out.println("1 : Acheter carte - 2 : Prendre jetons "
					+ "\n3 : Voir Jetons 4 : Voir Main \n5 : Voir Plateau \n6 : Quitter");
			choice = sc.nextLine();
		}while(readChoiceLimited(board, player, choice) == false);
	}
	
	/**
	 * Proposes choice to the placer based on the number of players. 
	 * 
	 * @param board (Board) the board
	 * @see Board
	 * @param player (Integer)	number of the player
	 */
	public static void playerChoiceDiff(Board board, int player) {
		if(board.mode == 1) {
			playerChoiceLimited(board, player);
		}
		else {
			playerChoice(board, player);
		}
	}

	/**
	 * Asks to specify the number of players in the game.
	 * 
	 * @return (Integer) the number of player.
	 */
	public static int nbPlayer() {
	    
		Scanner sc = new Scanner(System.in);
		String choice = "";
		System.out.println("A combien de joueurs souhaitez vous jouer?");
		System.out.println("1 : 2 Joueurs - 2 : 3 Joueurs - 3 : 4 Joueurs ");
		choice = sc.nextLine();
		while(choice.equals("1") == false && choice.equals("2") == false && choice.equals("3") == false) {
			System.out.println("Réponse invalide, entrez \"1\", \"2\" ou \"3\" pour valider votre choix!");
			choice = sc.nextLine();
		}
		switch(choice) {
			case "1": return 2;
			case "2": return 3;
			case "3": return 4;
			default: return 2;
		}
	
	}
	
	/**
	 * Asks to specify the mode of the game.
	 * 
	 * @return (Integer) the mode of the game ( 1 for simple, 2 for advanced ).
	 */
	public static int mode() {
		Scanner sc = new Scanner(System.in);
		String choice = "";
		System.out.println("Dans quel mode souhaitez vous jouer ?");
		System.out.println("1 : Simple - 2 : Avancé");
		choice = sc.nextLine();
		while(choice.equals("1") == false && choice.equals("2") == false) {
			System.out.println("Réponse invalide, entrez \"1\" ou \"2\" pour valider votre choix!");
			choice = sc.nextLine();
		}
		switch(choice) {
			case "1": return 1;
			case "2": return 2;
			default: return 2;
		}
	}
	
	/**
	 * Switch between players.
	 * 
	 * @param player (Integer)		number of the player
	 * @param nbPlayer (Integer)	number of players
	 * 
	 * @return (Integer) 			the number of the next player
	 */
	public static int switchPlayer(int player, int nbPlayer) {
		
		switch(player) {
			case 1 :
				return 2;
			case 2 :
				if(nbPlayer > 2) { return 3;}
				return 1;
			case 3 :
				if(nbPlayer > 3) { return 4;}
				return 1;
			case 4 :
				return 1;
			default :
				return 1;
		}
	}
	
	/**
	 * Load the game loop using the graphic Interface.
	 * 
	 * @param nbPlayers (Integer) the number of players
	 * @param mode (Integer) the mode of the game
	 */
	public static void graphicInterface(int nbPlayers, int mode) {
		Application.run(Color.GRAY, context -> {
			int player = nbPlayers;
			var board = new Board(nbPlayers, mode);
			var actionReader2 = new ActionReader2(board);
			if(mode == 2) { board.loadNobles("Splendor_Nobles_list.txt");}
			board.loadDeck("Splendor_Card_list.txt");
			board.fillFaceCards();
			player = ActionReader.switchPlayer(player, nbPlayers);
			for(int i = 0; i < 5; i++) {Drawer.drawAll(board, context, player);}
			while(board.checkVictory(player) == false) {
				actionReader2.playerChoice(board, context, player);
				Noble.nobleVisit(board, player);
				if (board.checkVictory(player)) {break;} 
				player = ActionReader.switchPlayer(player, board.nbPlayers());
				for(int i = 0; i < 5; i++) { Drawer.drawAll(board, context, player);}
			}
	        context.exit(0);
	    });
	}
	
	/**
	 * Load the game loop using the terminal Interface.
	 * 
	 * @param nbPlayers (Integer) the number of players
	 * @param mode (Integer) the mode of the game
	 */
	public static void terminalInterface(int nbPlayers, int mode) {
		int player = nbPlayers;
		var board = new Board(nbPlayers, mode);
		if(mode == 2) { 
			board.loadNobles("Splendor_Nobles_list.txt");
		}
		board.loadDeck("Splendor_Card_list.txt");
		board.fillFaceCards();
		Printer.printPlate(board);
		while(board.checkVictory(player) == false) {
			player = ActionReader.switchPlayer(player, nbPlayers);
			System.out.println("----------- Tour du Joueur " + player + " ------------------");
			ActionReader.playerChoiceDiff(board, player);
		}
		System.out.println("Le vainqueur est le joueur " + player + " ! Félicitations !");
	}
	
	/**
	 * Asks to specify the type of interface.
	 * 
	 * @return (Integer) the type of interface ( 1 for graphical, 2 for terminal).
	 */
	public static int interfaceChoice() {
	    
		Scanner sc = new Scanner(System.in);
		String choice = "";
		System.out.println("Quel affichage souhaitez vous ?");
		System.out.println("1 : Graphique - 2 : Textuel ");
		choice = sc.nextLine();
		while(choice.equals("1") == false && choice.equals("2") == false) {
			System.out.println("Réponse invalide, entrez \"1\" ou \"2\" pour valider votre choix!");
			choice = sc.nextLine();
		}
		if(choice.equals("1")) {return 1;};
		return 2;
	
	}
	
	/**
	 * Starting the asking for game options.
	 * 
	 */
	public static void startingChoices() {
		int mode = mode();
		int nbPlayers = nbPlayer();
		int inter = interfaceChoice();
		if(inter == 1) {
			graphicInterface(nbPlayers, mode);
		}
		else terminalInterface(nbPlayers, mode);
	}
	
}
