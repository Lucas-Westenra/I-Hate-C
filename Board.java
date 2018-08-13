
import java.io.*;
import java.util.*;

/**
 * Repsents the game board
 * @author Cameron Rose & Lucas Westenra
 * @version 1.0
 */
public class Board{

	//Fields
	private Tile[][] tiles = new Tile[24][25];
	private List<Player> players;
	private List<Weapon> weapons;
	private List<Card> murderCards;
	private List<Door> doors;
	private List<Card> allCards;
	private List<Player> playing;
	private Player playersTurn;

	//Variables
	public enum dir{NORTH, EAST, SOUTH, WEST}
	static boolean gameOver = false;
	private List<Tile> moveTiles = new ArrayList<Tile>();

	//Constructor
	public Board(Tile[][] tiles, List<Player> players, List<Weapon> weapons, List<Card> murderCards, List<Door> doors, List<Card> allCards, List<Player> playing)
	{
		this.allCards = allCards;
		this.playersTurn = players.get((int)Math.random()*players.size());
		this.murderCards = murderCards;
		
		//Creates a new tile for each position on the board
		for(int x=0; x<24; x++) {
			for(int y=0; y<25; y++) {
				this.tiles[x][y] = tiles[x][y];
			}
		}
		this.doors = doors;
		this.weapons = weapons;
		this.players = players;
		this.playing = playing;
	}
	
	public List<Player> getPlayers(){
		return this.players;
	}
	
	public List<Weapon> getWeapons(){
		return this.weapons;
	}
	
	public List<Card> getMurderCards(){
		return this.murderCards;
	}

	/**
	 * used for debugging, prints the board's characters out as a string
	 * @return string
	 */
	public String getBoardState(){
		String state = "";
		for(int y=0;y<25;y++) {
			for(int x=0;x<24;x++) {
				if(tiles[x][y].player != null) state += tiles[x][y].player.getPiece();
				else if(tiles[x][y].weapon != null) state += tiles[x][y].weapon.getPiece();
				else if(tiles[x][y].isDoor) state += 'o';
				else if(tiles[x][y].getName().equals("Walkway")) state += ' ';
				else if(tiles[x][y].getName().equals("Inaccessible")) state += 'X';
				else state += '*';
			}
			state += "\n";
		}
		return state;
	}

	/**
	 * takes a message and displays it, awaiting user input as the answer of the String
	 * user input must be an integer otherwise method asks again until an int is returned
	 * @param msg
	 * @return int
	 */
	private static int inputNumber(String msg) {
		System.out.print(msg + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				String v = input.readLine();
				return Integer.parseInt(v);
			} catch (NumberFormatException | IOException e) { //Catches if invalid input
				System.out.println("Please enter a number!");
			}
		}
	}

	/**
	 * takes a message and displays it, awaiting user input as the answer of the String
	 * user input must be a String otherwise method asks again until a String is returned
	 * @param msg
	 * @return String
	 */
	private static String inputString(String msg) {
		System.out.print(msg + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in)); 
			try {
				String v = input.readLine().toLowerCase();
				return v;
			} catch (NumberFormatException | IOException e) { //Catches if invalid input
				System.out.println("Please enter a character!");
			}
		}
	}

	/**
	 * moves param player in dir of param dir.
	 * Checks if player can move in direction dir before moving
	 * sets player position and tile positions as new location if successful
	 * returns false if player cannot move in direction dir
	 * @param player
	 * @param dir
	 * @return boolean
	 */
	public boolean movePlayer(Player player, dir dir) {
		Tile current = player.getPosition();

		int x = current.getXPos();
		int y = current.getYPos();
		Tile next=null;
		switch(dir){
		case NORTH:
			if(y-1<0)return false; //Null check
			next = tiles[x][y-1];
			for(Tile t: moveTiles) {
				if(t.equals(next)) {
					System.out.println("You have already been in this tile");
					return false;
				}
			}
			break;  
		case SOUTH:
			if(y+1>24)return false; //Null check
			next = tiles[x][y+1];
			for(Tile t: moveTiles) {
				if(t.equals(next)) {
					System.out.println("You have already been in this tile");
					return false;
				}
			}
			break;
		case EAST:
			if(x+1>23)return false; //Null check
			next = tiles[x+1][y];
			for(Tile t: moveTiles) {
				if(t.equals(next)) {
					System.out.println("You have already been in this tile");
					return false;
				}
			}
			break;
		case WEST:
			if(x-1<0)return false; //Null check
			next = tiles[x-1][y];
			for(Tile t: moveTiles) {
				if(t.equals(next)) {
					System.out.println("You have already been in this tile");
					return false;
				}
			}
			break;
		}
		
		//Checks if the player can move between two tiles and the moves the player if it can
		if(canMove(current, next)) {
			next.setPlayer(player);
			current.setPlayer(null);
			player.setPosition(next);
			moveTiles.add(current);
			return true;
		}
		return false;
	}

	/**
	 * This method works out whether the player can move 
	 * between two different tiles next to each other on the
	 * board
	 * @param current This is the current tile the player is on
	 * @param next This is the tile that the player intends on moving to 
	 * @return boolean
	 */
	public boolean canMove(Tile current, Tile next) {
		if(next.getName().equals("Inaccessible"))return false; //Player tries to move into inaccessible tile
		if(next.player != null) {
			return false; //Player tries to move into a tile occupied by another player
		}
		for(Tile t: moveTiles) {
			if(t.equals(next)) {
				return false; //Player tries to move to a tile that they have already been on in the move
			}
		}
		if(current.getName().equals(next.getName())) { 
			return true; //Player moves tiles which are in the same room
		}

		if(current.isDoor && next.isDoor) {
			return true; //Player moves through a tile that is occupied by a door
		}

		return false;
	}

	/**
	 * returns a random number from 2-12 inclusive
	 * @return int
	 */
	public static int rollDice() {
		return (int)(Math.random()*10+2);
	}

	/**
	 * returns an inhabitable tile of the same name as current tile
	 * determines inhabitable time based on proximity and then direction from current clockwise
	 * @param current
	 * @return
	 */
	public Tile checkDir(Tile current) {
		int x = current.getXPos();
		int y = current.getYPos();
		String name = current.getName();
		if(y>0)
			if(tiles[x][y-1].getName().equals(name) &&
					tiles[x][y-1].player == null) return tiles[x][y-1];
		if(x<23)
			if(tiles[x+1][y].getName().equals(name) &&
					tiles[x+1][y].player == null) return tiles[x+1][y];
		if(y<24)
			if(tiles[x][y+1].getName().equals(name) &&
					tiles[x][y+1].player == null) return tiles[x][y+1];
		if(x>0)
			if(tiles[x-1][y].getName().equals(name) &&
					tiles[x-1][y].player == null) return tiles[x-1][y];
		if(y>1)
			if(tiles[x][y-2].getName().equals(name) &&
					tiles[x][y-2].player == null) return tiles[x][y-2];
		if(x<22)
			if(tiles[x+2][y].getName().equals(name) &&
					tiles[x+2][y].player == null) return tiles[x+2][y];
		if(y<23)
			if(tiles[x][y+2].getName().equals(name) &&
					tiles[x][y+2].player == null) return tiles[x][y+2];
		if(x>1)
			if(tiles[x-2][y].getName().equals(name) &&
					tiles[x-2][y].player == null) return tiles[x-2][y];
		return null;
	}

	/**
	 * loops for number returned by rollDice()
	 * provides information for player such as moves remaining
	 * prompts player to perform an action 
	 * handles the action
	 */
	private void takeTurn(Player player) {
		int moves = rollDice();
		String tileName = "Walkway";
		System.out.println("It's " + player.getName() + "'s turn!");
		System.out.println(player.getName() +" rolled a "+moves+"!");

		for(int i=moves; i>0; i--) {
			boolean noMove = true;
			boolean deadEnd = false;
			while(noMove) {
				int playerX = player.getPosition().getXPos();
				int playerY = player.getPosition().getYPos();
				
				//Checks if player has moved into a dead end and ends their turn if they have
				if(playerX+1 > 23 || !canMove(player.getPosition(), tiles[playerX+1][playerY])) {
					if(playerX-1 < 0 || !canMove(player.getPosition(), tiles[playerX-1][playerY])) {
						if(playerY+1 >24 || !canMove(player.getPosition(), tiles[playerX][playerY+1])) {
							if(playerY-1 < 0 || !canMove(player.getPosition(), tiles[playerX][playerY-1])) {
								i=0;
								noMove=false;
								deadEnd = true;
								break;
							}
						}
					}
				}

				System.out.println(player.getName() + " has " + i + " moves left.");
				String inp;

				if(!player.getPosition().getName().equals("Walkway")) {
					inp = inputString("Enter a direction (W/A/S/D/Hand/End): ");
				}
				else {
					inp = inputString("Enter a direction (W/A/S/D/Hand): ");
				}

				if(inp.equalsIgnoreCase("w")) {
					if(movePlayer(player, dir.NORTH)) {
						noMove = false;
					}
				}
				else if(inp.equalsIgnoreCase("a")) {
					if(movePlayer(player, dir.WEST)) {
						noMove = false;
					}
				}
				else if(inp.equalsIgnoreCase("s")) {
					if(movePlayer(player, dir.SOUTH)) {
						noMove = false;
					}
				}
				else if(inp.equalsIgnoreCase("d")) {
					if(movePlayer(player, dir.EAST)) {
						noMove = false;
					}
				}
				else if(inp.equalsIgnoreCase("hand")){
					System.out.println("Your hand is: ");
					for(Card card: player.getHand())
						System.out.println("\t"+card.getType()+": "+card.getName());
				}
				else if(!player.getPosition().getName().equals("Walkway") && inp.equalsIgnoreCase("end")){
					i=0;
					noMove = false;

				}	
				else {
					System.out.println("Enter W/A/S/D/Hand");
				}

			}
			if(deadEnd) {
				System.out.print("You have moved into a dead end, you turn is over!\n");
			}
			else {
				drawTiles();
			}
			tileName = player.getPosition().getName();

		}
		moveTiles.clear();
		String choice;
		if(player.getPlaying())
			choice = getEndTurnChoice(player, tileName);
		else { 
			System.out.println("Accusation has already been made.");
			choice = "skip";
		}

		if(choice.equals("accu")) {
			if(player.isPlaying()) doAccusation(player);
			else System.out.println(player.getName()+" has already make their accusation!\n"
					+ "Only one accusation may be made per game.");
		}
		if(choice.equals("suggest")) { 
			doSuggestion(player, tileName);	
		}
		if(!gameOver) {
			drawTiles();
		}

	}


	/**
	 * helper method called at end of takeTurn(Player p)
	 * prompts the player to select an action after turn is over
	 * options available are dependent on location of player
	 * returns the user's choice as a string
	 * @param player
	 * @param tileName
	 * @return String
	 */
	public String getEndTurnChoice(Player player, String tileName) {
		int ans = 0;
		System.out.print("Would you like to...\n");
		if(tileName.equals("Walkway")) {
			while(ans != 1 && ans != 2) 
				ans = inputNumber("1. Make an accusation. (1 per game)\n"
						+ "2. End turn.\n" + "Enter choice (1/2): ");
			if(ans == 1) return "accu";
		}
		else {
			while(ans != 1 && ans != 2 && ans != 3)
				ans = inputNumber("1. Make a suggestion. (Using room '" +tileName+"')\n"
						+ "2. Make an accusation. (1 per game)\n"
						+ "3. End turn\n" + "Enter choice (1/2/3): ");
			if(ans == 1) return "suggest";
			if(ans == 2) return "accu";

		} 
		return "end";
	}

	/**
	 * prompts player to choose cards to suggest
	 * players suggested are moved to player
	 * weapons suggested are moved to player
	 * displays which cards are held if a player only holds 1 suggested card
	 * prompts other player to select a card to refute with if a player holds more than 1 suggested card
	 * @param player
	 * @param tileName
	 */
	private void doSuggestion(Player player, String tileName) {
		List<Card> roomCards = new ArrayList<Card>();
		List<Card> playerCards = new ArrayList<Card>();
		List<Card> weaponCards = new ArrayList<Card>();
		System.out.println("Your hand is: ");
		for(Card handCard: player.getHand()) { 
			System.out.println("\t"+handCard.getType()+": "+handCard.getName());
		}
		for(Card c: allCards) {
			if(c.getType().equals("Room")) roomCards.add(c);
			else if(c.getType().equals("Player")) playerCards.add(c);
			else if(c.getType().equals("Weapon")) weaponCards.add(c);
		}
		List<Card> guessedCards = new ArrayList<Card>();
		for(Card room: roomCards) 
			if(room.getName().equals(tileName)) 
				guessedCards.add(room);
		guessedCards.add(getGuess(playerCards, "character"));
		guessedCards.add(getGuess(weaponCards, "weapon"));
		for(Player p: players) {
			if(p.getName().equals(guessedCards.get(1).getName()) && !guessedCards.get(1).getName().equals(player.getName())) {
				p.getPosition().setPlayer(null);
				p.setPosition(checkDir(player.getPosition()));
				p.getPosition().setPlayer(p);
			}
		}
		for(Weapon w: weapons) {
			if(w.getName().equals(guessedCards.get(2).getName())) {
				w.getPosition().setWeapon(null);
				w.setPosition(checkDir(player.getPosition()));
				w.getPosition().setWeapon(w);
			}
		}

		List<Card> foundCards = new ArrayList<Card>();
		for(Player p: playing) {
			int count = 0;
			List<Card> cardList = new ArrayList<Card>();
			for(int cc=0; cc<3; cc++) {
				if(p != player)
					if(p.getHand().contains(guessedCards.get(cc))) {
						count++;
						cardList.add(guessedCards.get(cc));
						foundCards.add(guessedCards.get(cc));
					}
			}
			if(count > 1) {
				System.out.println(p.getName()+" Which card would you like to show?\n");
				for(int i=0;i<count;i++) System.out.println(i+1+": "+cardList.get(i).getName());
				int inp = (inputNumber("You may only show one: "));
				while(inp<1 || inp>count) inp = inputNumber("Please select a valid number.");
				System.out.println("Player "+p.getName()+" has the "+(cardList.get(inp-1).getName())+" card!");
				for(int i=0;i<count;i++) {
					if(i != inp-1) System.out.println("The "+(cardList.get(i).getName())+"card was not found!");
				}
			} 
			else if(count == 1)
				System.out.println("Player "+p.getName()+" has the "+cardList.get(0).getName()+" card!");
		}
		for(Card c: guessedCards) {
			if(!foundCards.contains(c))
				System.out.println("The "+c.getName()+" card was not found!");
		}
	}

	/**
	 * prompts player to choose a room card, player card and weapon card to accuse with
	 * if any of these cards are not the actaul murder cards then the player is eliminated
	 * if all 3 accusation cards match the 3 murder cards the player wins and the game ends
	 * @param player
	 */
	private void doAccusation(Player player) {
		List<Card> roomCards = new ArrayList<Card>();
		List<Card> playerCards = new ArrayList<Card>();
		List<Card> weaponCards = new ArrayList<Card>();
		System.out.println("Your hand is: ");
		for(Card handCard: player.getHand()) { 
			System.out.println("\t"+handCard.getType()+": "+handCard.getName());
		}
		for(Card c: allCards) {
			if(c.getType().equals("Room")) roomCards.add(c);
			else if(c.getType().equals("Player")) playerCards.add(c);
			else if(c.getType().equals("Weapon")) weaponCards.add(c);
		}
		List<Card> guessedCards = new ArrayList<Card>();
		guessedCards.add(getGuess(roomCards, "room"));
		guessedCards.add(getGuess(playerCards, "character"));
		guessedCards.add(getGuess(weaponCards, "weapon"));
		if(guessedCards.get(0) == murderCards.get(0) &&
				guessedCards.get(1) == murderCards.get(1) &&
				guessedCards.get(2) == murderCards.get(2)) {
			System.out.println(player.getName()+"'s accusation was CORRECT and has won the game!\n");
			gameOver = true;
		}
		else {
			System.out.println(player.getName()+"'s accusation was INCORRECT!\n"
					+player.getName()+" cannot make any more accusations!\n");

			player.setPlaying(false);
		}

	}

	/**
	 * helper method for doAccusation and doSuggestion
	 * prompts user to choose a card from a list
	 * returns chosen card
	 * @param cards
	 * @param type
	 * @return Card
	 */
	private Card getGuess(List<Card> cards, String type) {
		int count = 1;
		System.out.println();
		for(Card c: cards) {
			System.out.println(count++ +"\t"+c.getName());
		}
		int ans = inputNumber("Choose a "+type+" card: ");
		while(ans < 1 || ans > cards.size()) ans = inputNumber("Choose a valid "+type+" card: ");

		System.out.println();

		return cards.get(ans-1);
	}

	/**
	 * prints the key for the text-based user interface
	 * the key indicates which char is used to represent which object
	 * @param line
	 */
	public void printKey(int line) {
		if(line == -1)System.out.print("\t\t\tKey:");
		if(line == 0)System.out.print("\t\t\tS\t-\tMiss Scarlet");
		if(line == 1)System.out.print("\t\t\tM\t-\tColonel Mustard");
		if(line == 2)System.out.print("\t\t\tW\t-\tMrs. White");
		if(line == 3)System.out.print("\t\tG\t-\tMr. Green");
		if(line == 4)System.out.print("\t\t\tP\t-\tMrs. Peacock");
		if(line == 5)System.out.print("\t\t\tL\t-\tProfessor Plum");

		if(line == 7)System.out.print("\t\t\tc\t-\tCandlestick");
		if(line == 8)System.out.print("\t\t\td\t-\tDagger");
		if(line == 9)System.out.print("\t\t\tl\t-\tLead Pipe");
		if(line == 10)System.out.print("\t\tv\t-\tRevolver");
		if(line == 11)System.out.print("\t\tr\t-\tRope");
		if(line == 12)System.out.print("\t\t\ts\t-\tSpanner");

		if(line == 14)System.out.print("\t\t\t\u2588\t-\tInacessable Position");
		if(line == 15)System.out.print("\t\t.\t-\tRoom");
		if(line == 16)System.out.print("\t\t\t-\t-\tVertical Door Entrance");
		if(line == 17)System.out.print("\t\t\t|\t-\tHorizontal Door Entrance");
		if(line == 18)System.out.print("\t\t\to\t-\tPlayer has already moved to this position during turn");
		if(line == 19)System.out.print("\t\t\tSpace\t-\tWalkway");
	}

	/**
	 * initialize board state fully, create a new board object and call looping function to play game
	 * @param args
	 */
	public static void main(String[] args) {
		//initialize arrays needed to create new board object
		Tile[][] tiles = new Tile[24][25];
		List<Player> players = new ArrayList<Player>();
		List<Weapon> weapons = new ArrayList<Weapon>();
		List<Card> allCards = new ArrayList<Card>();
		List<Card> cards = new ArrayList<Card>();
		List<Card> murderCards = new ArrayList<Card>();
		List<Door> doors = new ArrayList<Door>();

		//initialize tiles 
		for(int x=0; x<24; x++) {
			for(int y=0; y<25; y++) {
				if((x<9 && y==0)	||
						(x>9 && x<14 && y==0)	||
						(x>14 && y==0)	||
						(x==6 && y==1)	||
						(x==17 && y==1)	||
						((x==0) && (y==6 || y==8))	||
						((x == 23) && (y==5 || y==7))	||
						(x>9 && x<15 && y>9 && y<17)	||
						(x==23 && y>12 && y<15)	||
						(x==0 && (y==16 || y==18))	||
						(x==23 && (y==18 || y==20))	||
						((x==6 || x==8 || x==15 || x==17) && y==24))
					tiles[x][y] = new Tile("Inaccessible",x,y);
				else if(x<6 && y<7)
					tiles[x][y] = new Tile("Kitchen",x,y);
				else if((x>=8 && x<16 && y>=2 && y<8) || (x>=10 && x<14 && y==1))
					tiles[x][y] = new Tile("Ball Room",x,y);
				else if((x>=18 && y<5) || (x>=19 && y==5)) 
					tiles[x][y] = new Tile("Conservatory",x,y);
				else if((x<5 && y==9) || (x<8 && y>9 && y<16))
					tiles[x][y] = new Tile("Dining Room",x,y);
				else if(x>9 && x<15 && y>9 && y<17)
					tiles[x][y] = new Tile("Cellar",x,y);
				else if(x>17 && y>7 && y<13)
					tiles[x][y] = new Tile("Billiard Room",x,y);
				else if((x>17 && y>13 && y<19) || (x==17 && y>14 && y<18))
					tiles[x][y] = new Tile("Library",x,y);
				else if(x<7 && y>18) 
					tiles[x][y] = new Tile("Lounge",x,y);
				else if(x>8 && x<15 && y>17)
					tiles[x][y] = new Tile("Hall",x,y);
				else if(x>16 && y>20)
					tiles[x][y] = new Tile("Study",x,y);
				else 
					tiles[x][y] = new Tile("Walkway",x,y);
			}
		}

		//players
		players.add(new Player("Miss Scarlett", tiles[7][24], 'S', true));
		players.add(new Player("Colonel Mustard", tiles[0][17], 'M', true));
		players.add(new Player("Mrs. White", tiles[9][0], 'W', true));
		players.add(new Player("Mr. Green", tiles[14][0], 'G', true));
		players.add(new Player("Mrs. Peacock", tiles[23][6], 'P', true));
		players.add(new Player("Professor Plum", tiles[23][19], 'L', true));

		//weapons
		weapons.add(new Weapon("Candlestick", tiles[18][22], 'c'));
		weapons.add(new Weapon("Dagger", tiles[3][3], 'd'));
		weapons.add(new Weapon("Lead Pipe", tiles[22][2], 'l'));
		weapons.add(new Weapon("Revolver", tiles[4][11], 'v'));
		weapons.add(new Weapon("Rope", tiles[20][8], 'r'));
		weapons.add(new Weapon("Spanner", tiles[1][22], 's'));

		//cards
		cards.add(new Card("Kitchen", "Room"));
		cards.add(new Card("Ball Room", "Room"));
		cards.add(new Card("Conservatory", "Room"));
		cards.add(new Card("Billiard Room", "Room"));
		cards.add(new Card("Library", "Room"));
		cards.add(new Card("Study", "Room"));
		cards.add(new Card("Dining Room", "Room"));
		cards.add(new Card("Hall", "Room"));
		cards.add(new Card("Lounge", "Room"));
		for(Player p: players) cards.add(new Card(p.getName(), "Player"));
		for(Weapon w: weapons) cards.add(new Card(w.getName(), "Weapon"));
		for(Card c: cards) allCards.add(c);

		//murderCards
		//rooms are .get(0..8), players are .get(9..14), weapons are .get(15..20)
		int roomNum = (int)(Math.random()*9);
		int personNum = (int)(Math.random()*6) + 9;
		int weaponNum = (int)(Math.random()*6) + 15;
		murderCards.add(cards.get(roomNum));
		murderCards.add(cards.get(personNum));
		murderCards.add(cards.get(weaponNum));
		cards.remove(weaponNum);
		cards.remove(personNum);
		cards.remove(roomNum);

		//Doors
		doors.add(new Door(new Tile("Walkway", 4, 5), new Tile("Kitchen", 4, 6)));
		doors.add(new Door(new Tile("Walkway", 7, 5), new Tile("Ball Room", 8, 5)));
		doors.add(new Door(new Tile("Walkway", 9, 8), new Tile("Ball Room", 9, 7)));
		doors.add(new Door(new Tile("Walkway", 14, 8), new Tile("Ball Room", 14, 7)));
		doors.add(new Door(new Tile("Walkway", 16, 5), new Tile("Ball Room", 15, 5)));
		doors.add(new Door(new Tile("Walkway", 18, 5), new Tile("Conservatory", 18, 4)));
		doors.add(new Door(new Tile("Walkway", 17, 9), new Tile("Billiard Room", 18, 9)));
		doors.add(new Door(new Tile("Walkway", 22, 13), new Tile("Billiard Room", 22, 12)));
		doors.add(new Door(new Tile("Walkway", 20, 13), new Tile("Library", 20, 14)));
		doors.add(new Door(new Tile("Walkway", 16, 16), new Tile("Library", 17, 16)));
		doors.add(new Door(new Tile("Walkway", 17, 20), new Tile("Study", 17, 21)));
		doors.add(new Door(new Tile("Walkway", 15, 20), new Tile("Hall", 14, 20)));
		doors.add(new Door(new Tile("Walkway", 12, 17), new Tile("Hall", 12, 18)));
		doors.add(new Door(new Tile("Walkway", 11, 17), new Tile("Hall", 11, 18)));
		doors.add(new Door(new Tile("Walkway", 6, 18), new Tile("Lounge", 6, 19)));
		doors.add(new Door(new Tile("Walkway", 6, 16), new Tile("Dining Room", 6, 15)));
		doors.add(new Door(new Tile("Walkway", 8, 12), new Tile("Dining Room", 7, 12)));

		//set field to true in tiles to hold a door
		for(Door door: doors) {
			int x1 = door.t1.getXPos(); int y1 = door.t1.getYPos();
			int x2 = door.t2.getXPos(); int y2 = door.t2.getYPos();
			tiles[x1][y1].setDoor();
			tiles[x2][y2].setDoor();
		}

		//set field of players and weapons in tiles to hold the respective objects
		for(Player player: players) {
			tiles[player.getPosition().getXPos()][player.getPosition().getYPos()].setPlayer(player);
		}
		for(Weapon weapon: weapons) {
			tiles[weapon.getPosition().getXPos()][weapon.getPosition().getYPos()].setWeapon(weapon);
		}

		List<Player> playing = new ArrayList<Player>();

		//startup options, choose characters and number of players
		System.out.println("\t\tCLUEDO");
		int nplayers = inputNumber("Enter the number of players that are playing: ");

		while(nplayers < 3 || nplayers > 6) {  
			System.out.println("The amount of players must be between 3-6");
			nplayers = inputNumber("Enter the number of players that are playing: "); 
		}
		String text = "Which character would you like?\n"
				+ "1:\t Miss Scarlett.\n"
				+ "2:\t Col. Mustard.\n"
				+ "3:\t Mrs. White.\n"
				+ "4:\t Mr. Green.\n"
				+ "5:\t Mrs. Peacock.\n"
				+ "6:\t Prof. Plum.\n";
		System.out.println(text);
		for(int i=0; i<nplayers; i++) {
			System.out.printf("Player '"+(i+1)+"' choose your character: ");
			int selection = inputNumber("");
			while(selection < 1 || selection > 6) {
				System.out.println("You must choose a number between 1-6!");
				System.out.printf("Player '"+(i+1)+"' is choosing: ");
				selection = inputNumber("");
			}
			while(players.get(selection-1).isPlaying()) {
				System.out.println("Character is already selected!");
				System.out.printf("Player '"+(i+1)+"' choose your character: ");
				selection = inputNumber("");
			}
			players.get(selection-1).setPlayer(i);
			playing.add(players.get(selection-1));
		} 

		System.out.println();

		//add a random card to a looping player, looping until all remaining cards are given out
		int nextPlayer = (int)Math.random()*nplayers+1;
		while(!cards.isEmpty()) {
			if(nextPlayer > nplayers) nextPlayer = 1;
			int randomCard = (int)(Math.random()*(cards.size()-1));
			playing.get(nextPlayer-1).addToHand(cards.get(randomCard));
			cards.get(randomCard).setPlayer(playing.get(nextPlayer-1));
			cards.remove(randomCard);
			nextPlayer++;
		}

		Board board = new Board(tiles, players, weapons, murderCards, doors, allCards, playing);
		board.drawTiles();

		board.runGame();
	}

	/**
	 * Returns boolean for debugging 
	 * returns true if game won by player
	 * returns false if no player won the game
	 * contains the main gameplay loop, breaking if gameOver
	 * @return boolean
	 */
	public boolean runGame() {
		while(!gameOver) {
			int count = 0;
			for(Player pl: playing) if(!pl.getPlaying()) count++;
			if(count == playing.size()) { 
				System.out.println("Every Player has gotten their accusation wrong!\n"
						+ "Game Over!");
				return false;
			}
			for(Player p: playing) {
				if(gameOver) {
					return true;
				}
				takeTurn(p);
			}
		}
		return true;
	}

	/**
	 * Draws the whole board to the console
	 */
	private void drawTiles() {
		System.out.print("\t\t\t   BALL\n");
		System.out.print("\t\t\t   ROOM\n");
		System.out.print("\t\t");
		for(int i = 0; i<=25; i++) {
			System.out.print('\u2588');
		}
		printKey(-1);

		for(int y=0; y<25; y++) {
			System.out.println();
			if(y==3) System.out.print("\tKITCHEN "+'\u2588');
			else if(y==13) System.out.print("    DINING ROOM "+'\u2588');
			else if(y==22) System.out.print("\t LOUNGE "+'\u2588');
			else System.out.print("\t\t"+ '\u2588');
			for(int x=0; x<24; x++) {
				String roomName = tiles[x][y].getName();

				if(tiles[x][y].player != null) System.out.printf("%s", tiles[x][y].player.getPiece());
				else if(moveTiles.contains(tiles[x][y])) System.out.print("o");
				else if(tiles[x][y].isDoor) {
					for(Door d: doors) {
						if(d.t2.getXPos()==x && d.t2.getYPos()==y) {
							if(d.t1.getXPos() == d.t2.getXPos()-1 || d.t1.getXPos() == d.t2.getXPos()+1) {
								System.out.print("|");
							}
							else {
								System.out.print("-");  
							}
						}
						if(d.t1.getXPos()==x && d.t1.getYPos()==y) {
							System.out.print(" ");
						}
					}
				}
				else if(tiles[x][y].weapon != null) System.out.printf("%s", tiles[x][y].weapon.getPiece());
				else if(roomName.equals("Inaccessible"))
					System.out.printf("%s", '\u2588');
				else if(!roomName.equals("Walkway"))
					System.out.printf("%s", '.');
				else if(!moveTiles.contains(tiles[x][y]))
					System.out.printf("%s", ' ');
			}
			System.out.print('\u2588');
			if(y==3) System.out.print(" CONSERVATORY");
			if(y==10) System.out.print(" BILLIARD");
			if(y==11) System.out.print("   ROOM");
			if(y==15) System.out.print(" LIBRARY");
			if(y==23) System.out.print(" STUDY");

			printKey(y);
		}
		System.out.printf("%s", "\n\t\t");
		for(int i = 0; i<=25; i++) {
			System.out.print('\u2588');
		}

		System.out.print("\n\t\t\t   HALL\n");
	}
	
	/**
	 * This method is used to test the doSuggestion method for the JUnit test suite
	 * @param player
	 * @param pl
	 * @param wep
	 */
	public void debugSuggestion(Player player, Player pl, Weapon wep) {
		List<Card> roomCards = new ArrayList<Card>();
		List<Card> playerCards = new ArrayList<Card>();
		List<Card> weaponCards = new ArrayList<Card>();
		for(Card c: allCards) {
			if(c.getType().equals("Room")) roomCards.add(c);
			else if(c.getType().equals("Player")) playerCards.add(c);
			else if(c.getType().equals("Weapon")) weaponCards.add(c);
		}

		for(Player p: players) {
			if(p.getName().equals(pl.getName()) && !pl.getName().equals(player.getName())) {
				p.getPosition().setPlayer(null);
				p.setPosition(checkDir(player.getPosition()));
				p.getPosition().setPlayer(p);
			}
		}
		for(Weapon w: weapons) {
			if(w.getName().equals(wep.getName())) {
				w.getPosition().setWeapon(null);
				w.setPosition(checkDir(player.getPosition()));
				w.getPosition().setWeapon(w);
			}
		}
	}

	/**
	 * This method is used to test the doAccusation method for the JUnit test suite
	 * @param player
	 * @param murderC
	 * @return
	 */
	public boolean debugAccusation(Player player, List<Card> murderC) {
		List<Card> roomCards = new ArrayList<Card>();
		List<Card> playerCards = new ArrayList<Card>();
		List<Card> weaponCards = new ArrayList<Card>();
		for(Card c: allCards) {
			if(c.getType().equals("Room")) roomCards.add(c);
			else if(c.getType().equals("Player")) playerCards.add(c);
			else if(c.getType().equals("Weapon")) weaponCards.add(c);
		}
		List<Card> guessedCards = new ArrayList<Card>();
		guessedCards.add(murderC.get(0));
		guessedCards.add(murderC.get(1));
		guessedCards.add(murderC.get(2));
		if(guessedCards.get(0) == murderCards.get(0) &&
				guessedCards.get(1) == murderCards.get(1) &&
				guessedCards.get(2) == murderCards.get(2)) {
			gameOver = true;
			return true;
		}
		else {
			player.setPlaying(false);
		}
		return false;
	}

}
