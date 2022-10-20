package fr.umlv.splendor;
import java.util.ArrayList;

import fr.umlv.zen5.ApplicationContext;
import fr.umlv.zen5.Event;
import fr.umlv.zen5.Event.Action;

import java.awt.geom.Rectangle2D;

/**
 * This object read the actions of the player and executes them.
 * 
 */
public class ActionReader2 {
	
	final private ArrayList<Rectangle2D> buttons;
	final private ArrayList<Rectangle2D> cards;
	final private ArrayList<Rectangle2D> tokens;
	
	/**
	 * Constructor of Card.
	 * 
	 *@param board (Board) the board
	 *@see Board
	 */
	public ActionReader2(Board board) {
		this.buttons = new ArrayList<>();
		this.cards = new ArrayList<>();
		this.tokens = new ArrayList<>();
		loadButtons(board);
		loadCards(board);
		loadTokens(board);
	}
	
	/**
	 *Load all the rectangles set on buttons positions.
	 * 
	 * @param board (Board) the board
	 * @see Board
	 */
	public void loadButtons(Board board) {
		int n = board.mode() == 1 ? 4 : 5;
		for(int i = 0; i < n; i ++) {
			var rect = new Rectangle2D.Float();
			rect.setFrame(1200, 50 + 200 * i , 250, 80);
			buttons.add(rect);
		}
	}
	
	/**
	 *Load all the rectangles set on cards positions.
	 * 
	 * @param board (Board) the board
	 * @see Board
	 */
	public void loadCards(Board board) {
		int n = board.mode() == 1 ? 1 : 3;
		for(int i = 0; i < n; i ++) {
			for(int j = 0; j < 4; j ++) {
				var rect = new Rectangle2D.Float();
				rect.setFrame(200 + 200 * j, 50 + 275 * i , 150, 225);
				cards.add(rect);
			}
		}
		for(int i = 0; i < 3; i ++) {
			int dist1 = i == 2 ? 0 : i ;
			int dist2 = i == 2 ? 395 : 120 ;
			var rect = new Rectangle2D.Float();
			rect.setFrame(1500 + 200 * dist1, dist2 , 150, 225);
			cards.add(rect);
			
		}
		
	}
	
	/**
	 *Load all the rectangles set on tokens position.
	 * 
	 * @param board (Board) the board
	 * @see Board
	 */
	public void loadTokens(Board board) {
		var rect = new Rectangle2D.Float();
		rect.setFrame(1000, 200 , 60, 60);
		tokens.add(rect);
		for( int i = board.mode() == 1 ? 1 : 2; i < 6; i ++) {
			var rect2 = new Rectangle2D.Float();
			rect2.setFrame(1000, 200 + 80 * i , 60, 60);
			tokens.add(rect2);
		}
	}
	
	/**
	 *Check if the click is contains in a list of rectangles.
	 * 
	 *@param event (Event)			the event produced by the click
	 *@param rectangles (ArrayList)	the list of rectangles
	 *@return (Integer)				the positions of the rectangle in the list 
	 *								if the click is in it, -1 otherwise.
	 */
	public int checkClic(Event event, ArrayList<Rectangle2D> rectangles) {
		if(event == null || (event.getAction() != Action.POINTER_UP && event.getAction() != Action.POINTER_DOWN)){
			return -1;
		}
		int i = 0;
		for(Rectangle2D rectangle : rectangles) {
			if(rectangle.contains(event.getLocation())) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	/**
	 * Read the click of the player and make him buy a card if
	 * he can.
	 * 
	 * @param board (Board) 	the board
	 * @param player (Integer)	number of the player
	 * @param clic (Integer)	the click of the player
	 * @see Board
	 * @return (boolean)	true if the buy has been made, false otherwise
	 */
	public boolean buyCard(Board board, int player, int clic) {
		if(clic != -1) {
			if (clic > 11 ) {
				int numero = clic - 12;
				if( board.getPlayer(player).reserved().size() > numero ) {
					if (board.playerBuyReserved(player, numero)) {
						return true;
					}
				}
			}
			else {
				if(board.mode() == 1 && clic > 3) { return false;}
				int niveau = clic / 4 + 1;
				int numero = (clic % 4) + 1;
				if (board.playerBuy(player, niveau, numero)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Read the click of the player and make him buy a card if
	 * he can.
	 * 
	 * @param context (ApplicationContext)	the context
	 * @param board (Board) 				the board
	 * @param player (Integer)				number of the player
	 * @see Board
	 * @return (boolean)	true if the buy has been made, false otherwise
	 */
	public boolean buy(ApplicationContext context, Board board, int player) {
		Event event = null;
		boolean done = false;
		Drawer.drawButtonPressed(context, board, 1200, 50, "Acheter carte");
		do {
			event = context.pollOrWaitEvent(1000);
			int clic = checkClic(event, cards);
			if(clic != -1) {
				done = buyCard(board, player, clic);
			}
			if(checkClic(event, buttons) != -1) {
				done = true;
				Drawer.drawButton(context, board, 1200, 50, "Acheter carte");
				readChoice(context, event, board, player);
			}
		}while(done == false);
		Drawer.drawButton(context, board, 1200, 50, "Acheter carte");
		return done;
	}
	
	/**
	 * Read the click of the player and make him reserve a card if
	 * he can.
	 * 
	 * @param board (Board) 	the board
	 * @param player (Integer)	number of the player
	 * @param clic (Integer)	the click of the player
	 * @see Board
	 * @return (boolean)	true if the reservation has been made, false otherwise
	 */
	public boolean reserveCard(Board board, int player, int clic) {
		if(clic != -1) {
			if(board.mode() == 1 && clic > 3) { return false;}
			int niveau = clic / 4 + 1;
			int numero = (clic % 4) + 1;
			return board.playerReservation(player, niveau, numero);
		}
		return false;
	}
	
	/**
	 * Read the click of the player and make him reserve a card if
	 * he can.
	 * 
	 * @param context (ApplicationContext)	the context
	 * @param board (Board) 				the board
	 * @param player (Integer)				number of the player
	 * @see Board
	 * @return (boolean)	true if the reservation has been made, false otherwise
	 */
	public boolean reserve(ApplicationContext context, Board board, int player) {
		boolean done = false;
		Event event = null;
		Drawer.drawButtonPressed(context, board, 1200, 250, "Réserver carte");
		do {
			event = context.pollOrWaitEvent(1000);
			int clic = checkClic(event, cards);
			if(clic != -1) {
				done = reserveCard(board, player, clic);
			}
			if(checkClic(event, buttons) != -1) {
				done = true;
				Drawer.drawButton(context, board, 1200, 250, "Réserver carte");
				readChoice(context, event, board, player);
			}
			
		}while(done == false);
		Drawer.drawButton(context, board, 1200, 250, "Réserver carte");
		return done;
	}
	
	/**
	 * Read the click of the player and make him take tokens if
	 * he can.
	 * 
	 * @param board (Board) 	the board
	 * @param player (Integer)	number of the player
	 * @param clic (Integer)	the click of the player
	 * @see Board
	 * @return (boolean)	true if the tokens have been taken, false otherwise
	 */
	public boolean takeTokens(Board board, int player, int clic) {
		String[] colors = { "Green", "White", "Blue", "Black", "Red"};
		if (board.moreThan3(colors[clic]) == false) {
			return false;
		}
		return board.playerTakeTokens(player, colors[clic], 2);
	}
	
	/**
	 * Read the click of the player and make him take tokens if
	 * he can.
	 * 
	 * @param context (ApplicationContext)	the context
	 * @param board (Board) 				the board
	 * @param player (Integer)				number of the player
	 * @see Board
	 * @return (boolean)	true if the tokens have been taken, false otherwise
	 */
	public boolean take2Tokens(ApplicationContext context, Board board, int player) {
		boolean done = false;
		Event event = null;
		Drawer.drawButtonPressed(context, board, 1200, 450, "Prendre 2 jetons");
		do {
			event = context.pollOrWaitEvent(1000);
			int clic = checkClic(event, tokens);
			if(clic != -1) {
				done = takeTokens(board, player, clic);
			}
			if(checkClic(event, buttons) != -1) {
				done = true;
				Drawer.drawButton(context, board, 1200, 450, "Prendre 2 jetons");
				readChoice(context, event, board, player);
			}
			
		}while(done == false);
		Drawer.drawButton(context, board, 1200, 450, "Prendre 2 jetons");
		return done;
	}
	
	/**
	 * Read the clicks of the player and make him take tokens if
	 * he can.
	 * 
	 * @param board (Board) 				the board
	 * @param context (ApplicationContext)	the context
	 * @param player (Integer)				number of the player
	 * @param clics (ArrayList)				the clicks of the player
	 * @see Board
	 * @return (boolean)	true if the tokens have been taken, false otherwise
	 */
	public boolean take3ColorTokens(Board board, ApplicationContext context, int player, ArrayList<Integer> clics) {
		Drawer.drawBoardTokens(board, context);
		String[] colors = { "Green", "White", "Blue", "Black", "Red"};
		for(Integer clic: clics) {
			if(board.tokens().get(colors[clic]) == 0) {
				return false;
			}
		}
		for(Integer clic: clics) {
			board.playerTakeTokens(player, colors[clic], 1);
		}
		
		return true;
	}
	
	/**
	 * Read the clicks of the player and make him take tokens if
	 * he can.
	 * 
	 * @param board (Board) 				the board
	 * @param context (ApplicationContext)	the context
	 * @param player (Integer)				number of the player
	 * @param done (boolean)				true if the action is achieved, false otherwise
	 * @param clics (ArrayList)				the clicks of the player
	 * @see Board
	 * @return (boolean)	true if the tokens have been taken, false otherwise
	 */
	public boolean take3Tokens2(ApplicationContext context, Board board, int player, boolean done, ArrayList<Integer> clics) {
		do {
			Event event = context.pollOrWaitEvent(1000);
			int clic = checkClic(event, tokens);
			if(clic != -1 && clics.contains(clic)== false) {
				clics.add(clic);
				Drawer.drawTokensPressed(board, context, 1000, 200, 60, clic);
				if(clics.size() == 3) {
					done = take3ColorTokens(board, context, player, clics);
					clics.clear();}
			}
			if(checkClic(event, buttons) != -1) {
				done = true;
				Drawer.drawBoardTokens(board, context);
				Drawer.drawButton(context, board, 1200, 650, "Prendre 3 jetons");
				readChoice(context, event, board, player);}
		}while(done == false);
		return done;
	}
	
	/**
	 * Read the clicks of the player and make him take tokens if
	 * he can.
	 * 
	 * @param context (ApplicationContext)	the context
	 * @param board (Board) 				the board
	 * @param player (Integer)				number of the player
	 * @see Board
	 * @return (boolean)	true if the tokens have been taken, false otherwise
	 */
	public boolean take3Tokens(ApplicationContext context, Board board, int player) {
		var clics = new ArrayList<Integer>();
		Drawer.drawBoardTokens(board, context);
		Drawer.drawButtonPressed(context, board, 1200, 650, "Prendre 3 jetons");
		return take3Tokens2(context, board, player, false, clics);
	}
	
	/**
	 * Read the click of the player and start the action associated to
	 * the button pressed.
	 * 
	 * @param context (ApplicationContext)	the context
	 * @param event (Event) 				the event produced by the click
	 * @param board (Board)					the board
	 * @param player (Integer)				number of the player
	 * @see Board
	 * @return (boolean)	true if the action is achieved, false otherwise
	 */
	public boolean readChoice(ApplicationContext context, Event event, Board board, int player) {
		boolean done = false;
		if(board.mode() == 1) { return readChoiceLimited(context, event, board, player);}
		if(event != null && (event.getAction() == Action.POINTER_UP || event.getAction() == Action.POINTER_DOWN)) {
			switch(checkClic(event, buttons)) {
				case 0: 
					done = buy(context, board, player);break;
				case 1:
					done = reserve(context, board, player);break;
				case 2: 
					done = take2Tokens(context, board, player);break;
				case 3:
					done = take3Tokens(context, board, player);break;
				case 4 :
					System.exit(0);
				case -1:
					break;
			}
		}
		return done;
	}
	
	/**
	 * Read the click of the player and start the action associated to
	 * the button pressed.
	 * 
	 * @param context (ApplicationContext)	the context
	 * @param event (Event) 				the event produced by the click
	 * @param board (Board)					the board
	 * @param player (Integer)				number of the player
	 * @see Board
	 * @return (boolean)	true if the action is achieved, false otherwise
	 */
	public boolean readChoiceLimited(ApplicationContext context, Event event, Board board, int player) {
		boolean done = false;
		if(event != null && (event.getAction() == Action.POINTER_UP || event.getAction() == Action.POINTER_DOWN)) {
			switch(checkClic(event, buttons)) {
				case 0: 
					done = buy(context, board, player);break;
				case 1: 
					done = take2Tokens(context, board, player);break;
				case 2:
					done = take3Tokens(context, board, player);break;
				case 3:
					System.exit(0);
				case -1:
					break;
			}
		}
		return done;
	}
	
	/**
	 * Read the click of the player and start the action associated to
	 * the button pressed.
	 * 
	 * @param board (Board)					the board
	 * @param context (ApplicationContext)	the context
	 * @param player (Integer)				number of the player
	 * @see Board
	 */
	public void playerChoice(Board board, ApplicationContext context, int player) {
		Event event = null;
		boolean done = false;
		do {
			event = context.pollOrWaitEvent(1000);
			if(board.mode() == 1) {
				done = readChoiceLimited(context, event, board, player);
			}
			else {
				done = readChoice(context, event, board, player);
			}
		}
		while(done == false);
	}
	
	
}
