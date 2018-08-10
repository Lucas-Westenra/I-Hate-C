import java.io.*;
import java.util.*;

public class Board{

  //Board Attributes
  private Player playersTurn;
  private List<Card> murderCards;
  //private List<Card> cards;

  //Board Associations
  private Tile[][] tiles = new Tile[24][25];
  private List<Weapon> weapons;
  private List<Player> players;
  private List<Player> playing;
  private List<Door> doors;
  private List<Card> allCards;
  
  public enum dir{NORTH, EAST, SOUTH, WEST}
  
  static boolean gameOver = false;
  
  private List<Tile> moveTiles = new ArrayList<Tile>();


  public Board(Tile[][] tiles, List<Player> players, List<Weapon> weapons,
		  List<Card> murderCards, List<Door> doors, List<Card> allCards, List<Player> playing)
  {
	this.allCards = allCards;
    this.playersTurn = players.get((int)Math.random()*players.size());
    this.murderCards = murderCards;
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

  public boolean setPlayersTurn(Player aPlayersTurn)
  {
    boolean wasSet = false;
    playersTurn = aPlayersTurn;
    wasSet = true;
    return wasSet;
  }

  public boolean setMurderCards(List aMurderCards)
  {
    boolean wasSet = false;
    murderCards = aMurderCards;
    wasSet = true;
    return wasSet;
  }

  public Player getPlayersTurn()
  {
    return playersTurn;
  }
	
  public Weapon getWeapon(int index){
    Weapon aWeapon = weapons.get(index);
    return aWeapon;
  }

  private static int inputNumber(String msg) {
		System.out.print(msg + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				String v = input.readLine();
				return Integer.parseInt(v);
			} catch (NumberFormatException | IOException e) {
				System.out.println("Please enter a number!");
			}
		}
	}
  
  private static String inputString(String msg) {
	  System.out.print(msg + " ");
		while (true) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in)); 
			try {
				String v = input.readLine().toLowerCase();
				return v;
			} catch (NumberFormatException | IOException e) {
				System.out.println("Please enter a character!");
			}
		}
  }
  
  	private boolean movePlayer(Player player, dir dir) {
	  Tile current = player.getPosition();
	  moveTiles.add(current);
	  int x = current.getXPos();
	  int y = current.getYPos();
	  Tile next;
	  switch(dir){
		  case NORTH:
			  if(y-1<0)return false;
			  next = tiles[x][y-1];
			  for(Tile t: moveTiles) {
				  if(t.equals(next)) {
					  System.out.println("You have already been in this tile");
					  return false;
				  }
			  }
			  
			  if(canMove(current, next)) {
				  moveTiles.add(next);
				  next.setPlayer(player);
				  current.setPlayer(null);
				  player.setPosition(next);
				  return true;
			  }
			  break;
		  case SOUTH:
			  if(y+1>24)return false;
			  next = tiles[x][y+1];
			  for(Tile t: moveTiles) {
				  if(t.equals(next)) {
					  System.out.println("You have already been in this tile");
					  return false;
				  }
			  }
			  
			  if(canMove(current, next)) {
				  moveTiles.add(next);
				  next.setPlayer(player);
				  current.setPlayer(null);
				  player.setPosition(next);
				  return true;
			  }
			  break;
		  case EAST:
			  if(x+1>23)return false;
			  next = tiles[x+1][y];
			  for(Tile t: moveTiles) {
				  if(t.equals(next)) {
					  System.out.println("You have already been in this tile");
					  return false;
				  }
			  }
			  
			  if(canMove(current, next)) {
				  moveTiles.add(next);
				  next.setPlayer(player);
				  current.setPlayer(null);
				  player.setPosition(next);
				  return true;
			  }
			  break;
		  case WEST:
			  if(x-1<0)return false;
			  next = tiles[x-1][y];
			  for(Tile t: moveTiles) {
				  if(t.equals(next)) {
					  System.out.println("You have already been in this tile");
					  return false;
				  }
			  }
			  
			  if(canMove(current, next)) {
				  moveTiles.add(next);
				  next.setPlayer(player);
				  current.setPlayer(null);
				  player.setPosition(next);
				  return true;
			  }
			  break;
	  }
	  return false;
	}
	
  	public boolean canMove(Tile current, Tile next) {
  		if(next.getName().equals("Inaccessible"))return false;
  		if(next.player != null) {
  			return false;
  		}
  		if(current.getName().equals(next.getName())) { //Allows movement within the same room
  			return true;
  		}
  		
  		if(current.isDoor && next.isDoor) {
				return true;
		}
  		return false;
  	}
   
  	public static int rollDice() {
  		return 40;
  		//return (int)(Math.random()*10+2);
  	}
  	
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
  	
  	private void takeTurn(Player player) {
  		int moves = rollDice();
  		String tileName = "Walkway";
  		System.out.println(player.getName() +" rolled a "+moves+"!");

  		for(int i=moves; i>0; i--) {
			boolean noMove = true;
			while(noMove) {
				System.out.println(player.getName() + " has " + i + " moves left.");
  				String inp = inputString("Enter a direction (W/A/S/D/Hand): ");
				
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
  				else {
  					System.out.println("Enter W/A/S/D/Hand");
  				}
  				
  			}
  				
  			drawTiles();	
  			tileName = player.getPosition().getName();
  			if(!tileName.equals("Walkway")) {
  				String ans = "";
				while(!ans.equalsIgnoreCase("y") && !ans.equalsIgnoreCase("n"))
					ans = inputString("You are in the "+tileName+". Do you want to end your turn? (Y/N)");
				if(ans.equalsIgnoreCase("y")) i=0;
				else if(!ans.equalsIgnoreCase("n")) {System.out.println("Invalid response. Expected 'Y' or 'N'.");}
			}
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
  		drawTiles();
  		
  	}
  	
  	private String getEndTurnChoice(Player player, String tileName) {
  		int ans = 0;
		if(tileName.equals("Walkway")) {
			while(ans != 1 && ans != 2) 
				ans = inputNumber("1. Make an accusation. (1 per game):\n"
					+ "2. End turn:\n");
			if(ans == 1) return "accu";
		}
		else {
			while(ans != 1 && ans != 2 && ans != 3)
				ans = inputNumber("1. Make an accusation. (1 per game):\n"
						+ "2. Make a suggestion. (Using room '"+tileName+"'):\n"
								+ "3. End turn:\n");
			if(ans == 1) return "accu";
			if(ans == 2) return "suggest";
		} 
		return "end";
  	}
  	
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
  		for(Card c: guessedCards) {
  			boolean isFound = false;
  			for(Player p: playing) {
  				if(p != player)
	  				if(p.getHand().contains(c)) {
	  					System.out.println("Player "+p.getName()+" has the card "+c.getName()+"!");
	  					isFound = true;
	  				}
  			}
  			if(!isFound) System.out.println("the "+c.getName()+" was not found card!");
  		}
  	}
  	
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
  			System.out.println(player.getName()+" has won!\n"
  					+ "Game Over!");
  			gameOver = true;
  		}
  		else {
  			System.out.println(player.getName()+"'s guess was INCORRECT!\n"
  					+player.getName()+" cannot make any more accusations!");
  			player.setPlaying(false);
  		}
  		
  	}
  	
  	private Card getGuess(List<Card> cards, String type) {
  		int count = 1;
  		System.out.println();
  		for(Card c: cards) {
  			System.out.println(count++ +"\t"+c.getName());
  		}
  		int ans = inputNumber("Choose a "+type+" card: ");
  		while(ans < 1 || ans > cards.size()) ans = inputNumber("Choose a valid "+type+" card: ");
  		return cards.get(ans-1);
  	}
  
  public static void main(String[] args) {
	  Tile[][] tiles = new Tile[24][25];
	  List<Player> players = new ArrayList<Player>();
	  List<Weapon> weapons = new ArrayList<Weapon>();
	  List<Card> allCards = new ArrayList<Card>();
	  List<Card> cards = new ArrayList<Card>();
	  List<Card> murderCards = new ArrayList<Card>();
	  List<Door> doors = new ArrayList<Door>();
	  
	  for(int x=0; x<24; x++) {
		  for(int y=0; y<25; y++) {
			  if((x<9 && y==0)	||
					  (x>9 && x<14 && y==0)	||
					  (x>14 && y==0)	||
					  (x==6 && y==1)	||
					  (x==17 && y==1)	||
					  ((x==0 || x==23) && (y==5 || y==7))	||
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
	  int roomNum = (int)Math.random()*9;
	  int personNum = (int)Math.random()*6 + 9;
	  int weaponNum = (int)Math.random()*6 + 15;
	  murderCards.add(cards.get(roomNum));
	  murderCards.add(cards.get(personNum));
	  murderCards.add(cards.get(weaponNum));
	  cards.remove(weaponNum);
	  cards.remove(personNum);
	  cards.remove(roomNum);
	  
	  //Doors
	  doors.add(new Door(new Tile("Walkway", 4, 6), new Tile("Kitchen", 4, 7)));
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
	  
	  for(Door door: doors) {
		  int x1 = door.t1.getXPos(); int y1 = door.t1.getYPos();
		  int x2 = door.t2.getXPos(); int y2 = door.t2.getYPos();
		  tiles[x1][y1].setDoor();
		  tiles[x2][y2].setDoor();
	  }
	  for(Player player: players) {
		  tiles[player.getPosition().getXPos()][player.getPosition().getYPos()].setPlayer(player);
	  }
	  for(Weapon weapon: weapons) {
		  tiles[weapon.getPosition().getXPos()][weapon.getPosition().getYPos()].setWeapon(weapon);
	  }
	  
	  List<Player> playing = new ArrayList<Player>();
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
	  
	  int nextPlayer = (int)Math.random()*nplayers+1;
	  while(!cards.isEmpty()) {
		  if(nextPlayer > nplayers) nextPlayer = 1;
		  int randomCard = (int)(Math.random()*(cards.size()-1));
		  playing.get(nextPlayer-1).addToHand(cards.get(randomCard));
		  cards.get(randomCard).setPlayer(playing.get(nextPlayer-1));
		  cards.remove(randomCard);
		  nextPlayer++;
	  }
	  for(Player p: players) {
		  for(Card c: p.getHand()) {
			  System.out.println(p.getName()+" has the card "+c.getName());
		  }
	  }
	  
	  Board board = new Board(tiles, players, weapons, murderCards, doors, allCards, playing);
	  board.drawTiles();
	  
	  while(!gameOver) {
		  for(Player p: playing) {
			  board.takeTurn(p);
		  }
	  }
	  
	  
  }
  
  private void drawTiles() {
	  System.out.print("\t\t\t   BALL\n");
	  System.out.print("\t\t\t   ROOM\n");
	  System.out.print("\t\t");
	  for(int i = 0; i<=25; i++) {
		  System.out.print('\u2588');
	  }

	  
	  for(int y=0; y<25; y++) {
		  System.out.println();
		  if(y==3) System.out.print("KITCHEN\t\t"+'\u2588');
		  else if(y==13) System.out.print("DINING ROOM\t"+'\u2588');
		  else if(y==22) System.out.print("LOUNGE\t\t"+'\u2588');
		  else System.out.print("\t\t"+ '\u2588');
		  for(int x=0; x<24; x++) {
			  String roomName = tiles[x][y].getName();
			  if(tiles[x][y].player != null) System.out.printf("%s", tiles[x][y].player.getPiece());
			  else if(tiles[x][y].isDoor) {
				  System.out.printf("%s", "|");
			  }
			  else if(tiles[x][y].weapon != null) System.out.printf("%s", tiles[x][y].weapon.getPiece());
			  else if(roomName.equals("Inaccessible"))
				  System.out.printf("%s", '\u2588');
			  else if(!roomName.equals("Walkway"))
				  System.out.printf("%s", '.');
			  else
				  System.out.printf("%s", ' ');
			  
		  }
		  System.out.print('\u2588');
		  if(y==3) System.out.print(" CONSERVATORY");
		  if(y==10) System.out.print(" BILLIARD");
		  if(y==11) System.out.print("   ROOM");
		  if(y==15) System.out.print(" LIBRARY");
		  if(y==23) System.out.print(" STUDY");
	  }
	  System.out.printf("%s", "\n\t\t");
	  for(int i = 0; i<=25; i++) {
		  System.out.print('\u2588');
	  }
	  
	  System.out.print("\n\t\t\t   HALL\n");
  }
}
