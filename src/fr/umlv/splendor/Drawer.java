package fr.umlv.splendor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import fr.umlv.zen5.ApplicationContext;

/**
 * This object draw the board and all its elements.
 * 
 */
public class Drawer {
	
	/**
	 *Draw a token.
	 *
	 *@param color (String) 	the color of the token 
	 *@param size (Integer) 	the size of the token
	 *@param context (ApplicationContext) the context
	 *@param x (Integer)		x position of the token
	 *@param y (Integer)		y position of the token
	 */
	public static void drawToken(String color, int size, ApplicationContext context, int x, int y) {
	    Image token= Toolkit.getDefaultToolkit().getImage("./src/ressources/tokens/"+color+"-token.png");
	    
        context.renderFrame(graphics -> {
          graphics.drawImage(token, x, y , size, size, null);
        });
	}
	
	/**
	 *Draw a pile of tokens.
	 *
	 *@param color (String) 	the color of the tokens 
	 *@param size (Integer) 	the size of the tokens
	 *@param context (ApplicationContext) the context
	 *@param x (Integer)		x position of the tokens
	 *@param y (Integer)		y position of the tokens
	 */
	public static void drawTokens(String color, int size, ApplicationContext context, int x, int y) {
	    Image token= Toolkit.getDefaultToolkit().getImage("./src/ressources/tokens/"+color+"-tokens.png");
        context.renderFrame(graphics -> {
          graphics.drawImage(token, x, y , size, size, null);
        });
	}
	
	/**
	 *Draw a pressed version of the tokens.
	 *
	 *@param board (Board) 
	 *@param context (ApplicationContext) the context
	 *@param x (Integer)		x position of the tokens
	 *@param y (Integer)		y position of the tokens
	 *@param size (Integer) 	the size of the tokens
	 *@param clic (Integer)		the click which indicates the tokens pressed
	 */
	public static void drawTokensPressed(Board board,ApplicationContext context, int x, int y, int size, int clic) {
		String[] colors = { "Green", "White", "Blue", "Black", "Red"};
		String color = colors[clic];
		int n = (board.mode() == 2 && clic > 0) ? clic + 1 : clic;
	    Image token= Toolkit.getDefaultToolkit().getImage("./src/ressources/tokens/"+color+"-tokens-pressed.png");
        context.renderFrame(graphics -> {
        	graphics.drawImage(token, x, y + 80 * n , size, size, null);
        });
	}
	
	/**
	 *Draw the quantity of token.
	 *
	 *@param context (ApplicationContext) the context
	 *@param x (Integer)		x position of the quantity
	 *@param y (Integer)		y position of the quantity
	 *@param quantity (Integer) quantity of the tokens
	 */
	public static void drawTokenQuantity(ApplicationContext context, int x, int y, int quantity) {
        context.renderFrame(graphics -> {
          graphics.setColor(Color.BLACK);
          graphics.fillRect(x + 35, y + 10 , 20, 20);
          graphics.setFont(new Font ("Symbol", Font.BOLD, 15));
          graphics.setColor(Color.WHITE);
          graphics.drawString(String.valueOf(quantity), x + 40, y + 25);
        });
	}	
	
	/**
	 *Draw the quantity of tokens on the board.
	 *
	 *@param context (ApplicationContext) the context
	 *@param x (Integer)		x position of the quantity
	 *@param y (Integer)		y position of the quantity
	 *@param quantity (Integer) quantity of the tokens
	 */
	public static void drawTokenQuantityPlate(ApplicationContext context, int x, int y, int quantity) {
        context.renderFrame(graphics -> {
          graphics.setColor(Color.BLACK);
          graphics.fillRect(x + 60, y + 20 , 35, 35);
          graphics.setFont(new Font ("Symbol", Font.BOLD, 30));
          graphics.setColor(Color.WHITE);
          graphics.drawString(String.valueOf(quantity), x + 65, y + 50);
        });
	}
	
	/**
	 *Draw the prestige of a card.
	 *
	 *@param card (Card)		the card 
	 *@param context (ApplicationContext) the context
	 *@param x (Integer)		x position of the prestige
	 *@param y (Integer)		y position of the prestige
	 *@see Card
	 */
	public static void drawCardPrestige(Card card, ApplicationContext context, int x, int y) {
        context.renderFrame(graphics -> {
            graphics.setColor(Color.GRAY);
            graphics.setFont(new Font ("Symbol", Font.BOLD, 36));
            graphics.drawString(String.valueOf(card.prestige()), x + 10, y + 30);
            graphics.setColor(Color.GREEN);
            graphics.setFont(new Font ("Symbol", Font.BOLD, 30));
            graphics.drawString(String.valueOf(card.prestige()), x + 13, y + 31);
          });
	}
	
	/**
	 *Draw the price of a card.
	 *
	 *@param card (Card)		the card 
	 *@param context (ApplicationContext) the context
	 *@param x (Integer)		x position of the price
	 *@param y (Integer)		y position of the price
	 *@see Card
	 */
	public static void drawCardTokens(Card card, ApplicationContext context, int x, int y) {
		int i = 0;
		String[] colors = { "Green", "Yellow", "White", "Blue", "Black", "Red"};
		for(String color : colors) {
			int dist = 45 * i;
			if(card.price().get(color) != 0) {
		        drawToken(color, 35, context, x + 10, y + 185 - dist);
		        drawTokenQuantity(context, x + 10, y + 185 - dist, card.price().get(color));
		        i++;
			}
		}
	}
	
	/**
	 *Draw a card.
	 *
	 *@param card (Card)		the card 
	 *@param context (ApplicationContext) the context
	 *@param x (Integer)		x position of the prestige
	 *@param y (Integer)		y position of the prestige
	 *@param size (Integer)		size of the card
	 *@see Card
	 */
	public static void drawCard(Card card, ApplicationContext context, int x, int y, int size) {
	    Image cardimg = Toolkit.getDefaultToolkit().getImage("./src/ressources/cards/" + card.name() +".png");
        context.renderFrame(graphics -> {
          graphics.drawImage(cardimg, x, y, 50 * size , 75 * size, null);
          drawCardTokens(card, context, x, y);
          drawToken(card.bonus(), 48, context, x + 100, y );
          drawCardPrestige(card, context, x, y);
        });
	}
	
	/**
	 *Draw a level of a deck.
	 *
	 *@param deck (Deck)	the deck
	 *@param level (Integer) the level 
	 *@param context (ApplicationContext) the context
	 *@see Deck
	 */
	public static void drawLevel(Deck deck, int level, ApplicationContext context) {
		for(int i = 1 ; i <= 4; i++) {
			drawCard(deck.get(level, i), context, 200 + 200 * (i - 1), 50 + 275 * (level - 1), 3 );
		}
	}
	
	/**
	 *Draw all the cards shown on the board.
	 *
	 *@param board (Board)	the board
	 *@param context (ApplicationContext) the context
	 *@see Board
	 */
	public static void drawFaceCards(Board board, ApplicationContext context) {
		if(board.mode() == 1) {
			drawLevel(board.faceCards(), 1, context );
		} 
		else {
			for(int i = 1 ; i <= 3; i++) {
				drawLevel(board.faceCards(), i, context );
			}
		}
	}

	/**
	 *Draw all the  tokens shown on the board.
	 *
	 *@param board (Board)	the board
	 *@param context (ApplicationContext) the context
	 *@see Board
	 */
	public static void drawBoardTokens(Board board, ApplicationContext context) {
		String[] colors = { "Green", "Yellow", "White", "Blue", "Black", "Red"};
		int i = 0;
		for(String color : colors) {
			if(board.mode() == 1 && color.equals("Yellow")){
				continue;
			}
			int dist = 80 * i;
		    drawTokens(color, 60, context, 1000, 200 + dist);
		    drawTokenQuantityPlate(context, 1000, 200 + dist, board.tokens().get(color));
		     i++;
		}
	}
	
	/**
	 *Draw a noble.
	 *
	 *@param noble (Noble) 	the noble  
	 *@param context (ApplicationContext) the context
	 *@param x (Integer)		x position of the noble
	 *@param y (Integer)		y position of the noble
	 *@see Noble
	 */
	public static void drawNoble(Noble noble, ApplicationContext context, int x, int y) {
	    Image nobleimg = Toolkit.getDefaultToolkit().getImage("./src/ressources/nobles/"+ noble.nom()+".png");
        context.renderFrame(graphics -> {
          graphics.drawImage(nobleimg, x, y, 135, 135, null);
        });
	}
	
	/**
	 *Draw all the nobles shown on the board.
	 *
	 *@param board (Board)	the board
	 *@param context (ApplicationContext) the context
	 *@see Board
	 */
	public static void drawNobles(Board board, ApplicationContext context) {
		for(int i = 0; i < board.nobles().size(); i++) {
			int dist = 160 * i;
			drawNoble(board.nobles().get(i), context, 10, 50 + dist );
		}
	}
	
	/**
	 *Draw the board.
	 *
	 *@param board (Board)	the board
	 *@param context (ApplicationContext) the context
	 *@see Board
	 */
	public static void drawBoard(Board board, ApplicationContext context) {
	    Image nobleimg = Toolkit.getDefaultToolkit().getImage("./src/ressources/Board.png");
        context.renderFrame(graphics -> {
          graphics.drawImage(nobleimg, 0, 0, 1920, 1080, null);
        });
	    drawFaceCards(board, context);
        for(int i = 0; i < 5; i ++) {
			drawTokensPressed(board, context, 1000, 200, 60, i);
        }
	    drawBoardTokens( board, context);
	    if(board.mode == 2) {
	    	drawNobles(board, context);
	    } 
	}
	
	/**
	 *Draw a button.
	 *
	 *@param context (ApplicationContext) the context
	 *@param board	(Board)		the board
	 *@param x (Integer)		x position of the token
	 *@param y (Integer)		y position of the token
	 *@param content (String)	the text in the button
	 *@see Board
	 */
	public static void drawButton(ApplicationContext context,Board board, int x, int y, String content) {
		
        context.renderFrame(graphics -> {
        	Image token= Toolkit.getDefaultToolkit().getImage("./src/ressources/button.png");
    		int n = board.mode() == 1 && y >= 250 ? 200 : 0;
        	graphics.drawImage(token, x, y - n , 250, 80, null);
            graphics.setFont(new Font ("Symbol", Font.BOLD, 17));
            graphics.setColor(Color.WHITE);
            graphics.drawString(content, x + 45, y + 50 - n);
        });
	}
	
	/**
	 *Draw a pressed version of the button.
	 *
	 *@param context (ApplicationContext) the context
	 *@param board	(Board)		the board
	 *@param x (Integer)		x position of the token
	 *@param y (Integer)		y position of the token
	 *@param content (String)	the text in the button
	 *@see Board
	 */
	public static void drawButtonPressed(ApplicationContext context,Board board, int x, int y, String content) {
		
        context.renderFrame(graphics -> {
        	Image token= Toolkit.getDefaultToolkit().getImage("./src/ressources/button-pressed.png");
    		int n = board.mode() == 1 && y >= 250 ? 200 : 0;
        	graphics.drawImage(token, x, y - n , 250, 80, null);
            graphics.setFont(new Font ("Symbol", Font.BOLD, 17));
            graphics.setColor(Color.WHITE);
            graphics.drawString(content, x + 45, y + 50 - n );
        });
	}
	
	/**
	 *Draw all the buttons.
	 *
	 *@param context (ApplicationContext) 	the context
	 *@param board	(Board)					the board
	 *@see Board
	 */
	public static void drawButtons(ApplicationContext context,Board board) {
		drawButton(context, board, 1200, 50, "Acheter carte");
		if(board.mode() == 2) {
			drawButton(context, board, 1200, 250, "Réserver carte");
		}
		drawButton(context, board, 1200, 450, "Prendre 2 Jetons");
		drawButton(context, board, 1200, 650, "Prendre 3 Jetons");
		drawButton(context, board, 1200, 850, "Quitter");
	}
	
	/**
	 *Draw the bonus tokens the player have.
	 *
	 *@param context (ApplicationContext) the context
	 *@param x (Integer)		x position of the tokens
	 *@param y (Integer)		y position of the tokens
	 *@param color (String)		color of the tokens
	 *@param player (Player)	the player
	 *@see Player
	 */
	public static void drawBonusQuantity(ApplicationContext context, int x, int y, String color, Player player) {
        context.renderFrame(graphics -> {
          graphics.setColor(Color.BLACK);
          graphics.fillRect(x + 60, y  , 20, 20);
          graphics.setFont(new Font ("Symbol", Font.BOLD, 15));
          graphics.setColor(Color.YELLOW);
          graphics.drawString(String.valueOf(player.bonus().get(color)), x + 65, y + 15);
        });
	}
	
	/**
	 *Draw the tokens the player have.
	 *
	 *@param board	(Board)					the board
	 *@param player (Player)				the player
	 *@param context (ApplicationContext)	the context
	 *@see Board
	 *@see Player
	 */
	public static void drawPlayerTokens(Board board, Player player, ApplicationContext context) {
		String[] colors = { "Green", "Yellow", "White", "Blue", "Black", "Red"};
		int i = 0;
		for(String color : colors) {
			if(board.mode() == 1){
				if(color.equals("Yellow")) {
					continue;
				}
			}
			int dist = i > 2 ? 115 *(i - 3) : 115 * i;
			int dist2 = i > 2 ? 800 : 900;
		    drawTokens(color, 60, context, 1500 + dist, dist2);
		    drawTokenQuantityPlate(context, 1500  + dist, dist2, player.tokens().get(color));
		    drawBonusQuantity(context, 1500 + dist, dist2, color, player );
		    i ++;
		}
	}
	
	/**
	 *Draw the card reserved by the player.
	 *
	 *@param player (Player)				the player
	 *@param context (ApplicationContext)	the context
	 *@see Player
	 */
	public static void drawPlayerReserved(Player player, ApplicationContext context) {
		for(int i = 0 ; i < player.reserved().size(); i++) {
			int dist1 = i == 2 ? 0 : i ;
			int dist2 = i == 2 ? 395 : 120 ;
			drawCard(player.reserved().get(i), context, 1500 + 200 * dist1, dist2, 3 );
		}
	}
	
	/**
	 *Draw the prestige points of the player.
	 *
	 *@param player (Player)				the player
	 *@param context (ApplicationContext)	the context
	 *@see Player
	 */
	public static void drawPlayerPrestige(Player player, ApplicationContext context) {
        context.renderFrame(graphics -> {
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font ("Symbol", Font.BOLD, 20));
            graphics.drawString("Prestige = " + String.valueOf(player.prestige()), 1500, 700 );
          });
	}
	
	/**
	 *Draw the hand of the player, meaning his cards, tokens and prestige.
	 *
	 *@param board	(Board)					the board
	 *@param player (Player)				the player
	 *@param context (ApplicationContext)	the context
	 *@see Board
	 *@see Player
	 */
	public static void drawPlayerHand (Board board, int player, ApplicationContext context) {
        context.renderFrame(graphics -> {
            graphics.setColor(Color.BLACK);
            graphics.fillRect(1525, 10  , 300, 80);
            graphics.setFont(new Font ("Symbol", Font.BOLD, 20));
            graphics.setColor(Color.WHITE);
            graphics.drawString("Tour du Joueur " + player , 1580, 50);
          });
	    drawPlayerTokens(board, board.getPlayer(player), context);
	    drawPlayerReserved(board.getPlayer(player), context);
	    drawPlayerPrestige(board.getPlayer(player), context);
	}
	
	/**
	 *Draw the board, buttons and player hand.
	 *
	 *@param board	(Board)					the board
	 *@param context (ApplicationContext)	the context
	 *@param player (Player)				the player
	 *@see Board
	 *@see Player
	 */
	public static void drawAll(Board board, ApplicationContext context, int player) {
	    drawBoard(board, context);
	    drawButtons(context, board);
	    drawPlayerHand(board, player, context);
	}
}
