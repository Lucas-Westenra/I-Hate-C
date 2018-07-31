import java.io.*;
import java.util.*;

public class Board{


  //Board Attributes
  private Player playersTurn;
  private List<Card> murderCards;
  //private List<Card> cards;

  //Board Associations
  private Tile[][] tiles;;
  private List<Weapon> weapons;
  private List<Player> players;
  private List<Door> doors;
  private List<Player> turnOrder = new ArrayList<Player>();
	

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Tile[][] tiles, List<Player> players, List<Weapon> weapons,
		  List<Card> murderCards, List<Door> doors)
  {
    this.playersTurn = players.get((int)Math.random()*players.size());
    this.murderCards = murderCards;
    this.tiles = tiles;
    this.weapons = weapons;
    this.players = players;
    this.doors = doors;	  
  }

  //------------------------
  // INTERFACE
  //------------------------

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

  public List getMurderCards()
  {
    return murderCards;
  }
  /* Code from template association_GetMany */
  public int numberOfTiles()
  {
    return 24*25;
  }

  public boolean hasTiles()
  {
    return tiles[0][0] == null;
  }

  public Weapon getWeapon(int index){
    Weapon aWeapon = weapons.get(index);
    return aWeapon;
  }

  public List<Weapon> getWeapons(){
    List<Weapon> newWeapons = Collections.unmodifiableList(weapons);
    return newWeapons;
  }

  public Player getPlayer(int index){
    Player aPlayer = players.get(index);
    return aPlayer;
  }

  public List<Player> getPlayers()
  {
    List<Player> newPlayers = Collections.unmodifiableList(players);
    return newPlayers;
  }

  public int numberOfPlayers()
  {
    int number = players.size();
    return number;
  }

  public boolean hasPlayers()
  {
    boolean has = players.size() > 0;
    return has;
  }

  public int indexOfPlayer(Player aPlayer)
  {
    int index = players.indexOf(aPlayer);
    return index;
  }

  public boolean isNumberOfTilesValid()
  {
    boolean isValid = numberOfTiles() >= minimumNumberOfTiles() && numberOfTiles() <= maximumNumberOfTiles();
    return isValid;
  }

  public static int requiredNumberOfTiles()
  {
    return 600;
  }

  public static int minimumNumberOfTiles(){
    return 600;
  }

  public static int maximumNumberOfTiles(){
    return 600;
  }

  public Tile addTile(String aName, int aXPos, int aYPos){
    if (numberOfTiles() >= maximumNumberOfTiles())
    {
      return null;
    }
    else
    {
      return new Tile(aName, aXPos, aYPos);
    }
  }

  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayersValid(){
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }
  
  public static int minimumNumberOfPlayers(){
    return 3;
  }

  public static int maximumNumberOfPlayers(){
    return 6;
  }

  public String toString(){
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playersTurn" + "=" + (getPlayersTurn() != null ? !getPlayersTurn().equals(this)  ? getPlayersTurn().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "murderCards" + "=" + (getMurderCards() != null ? !getMurderCards().equals(this)  ? getMurderCards().toString().replaceAll("  ","    ") : "this" : "null");
  }
  
  private static int inputNumber(String msg) {
		System.out.print(msg + " ");
		while (1 == 1) {
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
	
  public boolean canMove(Tile current, Tile next) {
	  Door enterRoom = new Door(current, next);
	  Door exitRoom = new Door(next, current);
	 
	  for(Door d: doors){ //Checks if the player is trying to move from one room to another via a door
		  if(enterRoom.equals(d)) {
			  return true;
		  }
		  if(exitRoom.equals(d)) {
			  return true;
		  }
	  }
	  
	  if(!(current.getName().equals(next.getName()))) { //Doesn't allow movement from one location to another
		  return false;
	  }
	  return true; //True if player is moving between two tiles that are in the same room
  }
  
  public static void main(String[] args) {
	  Tile[][] tiles = new Tile[24][25];
	  List<Player> players = new ArrayList<Player>();
	  List<Weapon> weapons = new ArrayList<Weapon>();
	  List<Card> cards = new ArrayList<Card>();
	  List<Card> murderCards = new ArrayList<Card>();
	  List<Door> doors = new ArrayList<Door>();
	  
	  for(int x=0; x<24; x++) {
		  for(int y=0; y<25; y++) {
			  if(x<6 && y<7)
				  tiles[x][y] = new Tile("Kitchen",x,y);
			  else if((x>=8 && x<16 && y>=2 && y<8) || (x>=10 && x<14 && y==1))
				  tiles[x][y] = new Tile("Ball Room",x,y);
			  else if((x>=18 && y<4) || (x>=19 && y==4)) 
				  tiles[x][y] = new Tile("Conservatory",x,y);
			  else if((x<5 && y==9) || (x<8 && y>9 && y<16))
				  tiles[x][y] = new Tile("Dining Room",x,y);
			  else if(x>9 && x<15 && y>9 && y<17)
				  tiles[x][y] = new Tile("Cellar",x,y);
			  else if(x>17 && y>7 && y<13)
				  tiles[x][y] = new Tile("Billiard Room",x,y);
			  else if((x>17 && y>13 && y<19) || (x==16 && y>14 && y<18))
				  tiles[x][y] = new Tile("Library",x,y);
			  else if(x<7 && y>18) 
				  tiles[x][y] = new Tile("Lounge",x,y);
			  else if(x>8 && x<15 && y>17)
				  tiles[x][y] = new Tile("Hall",x,y);
			  else if(x>16 && y>20)
				  tiles[x][y] = new Tile("Study",x,y);
			  else if((x<9 && y==0)	||
					  (x>9 && x<14 && y==0)	||
					  (x>14 && y==0)	||
					  (x==6 && y==1)	||
					  (x==17 && y==1)	||
					  ((x==0 || x==23) && (y==6 || y==8))	||
					  (x>9 && x<15 && y>9 && y<17)	||
					  (x==23 && y>12 && y<15)	||
					  (x==0 && (y==16 || y==18))	||
					  (x==23 && (y==18 || y==20))	||
					  ((x==6 || x==8 || x==15 || x==17) && y==24))
				  tiles[x][y] = new Tile("Inaccessible",x,y);
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
	  
	  
	  int nplayers = inputNumber("How many players?");
	  while(nplayers < 3 || nplayers > 6)  nplayers = inputNumber("How many players? Must be 3-6."); 
	  String text = "Which character would you like?\n"
		  + "1:\t Miss Scarlett.\n"
		  + "2:\t Col. Mustard.\n"
		  + "3:\t Mrs. White.\n"
		  + "4:\t Mr. Green.\n"
		  + "5:\t Mrs. Peacock.\n"
		  + "6:\t Prof. Plum.\n";
	  for(int i=0; i<nplayers; i++) {
		  System.out.printf("Player '"+(i+1)+"' is choosing.\n");
		  int selection = inputNumber(text);
		  while(selection < 1 || selection > 6 || players.get(selection).isPlaying()) {
			  System.out.printf("You must choose a number between 1-6!\n");
			  System.out.printf("Player '"+(i+1)+"' is choosing.\n");
			  selection = inputNumber(text);
		  }
		  players.get(selection-1).setPlayer(i);
		  System.out.println("Player "+(i+1)+" chose "+selection+"!");
	  } 
	  
	  int nextPlayer = (int)Math.random()*(nplayers)-1;
	  for(int i=cards.size(); i<=0; i--) {
		  if(nextPlayer >= nplayers-1) nextPlayer = 0;
		  int randomCard = (int)Math.random()*i-1;
		  players.get(nextPlayer).addToHand(cards.get(randomCard));
		  cards.remove(randomCard);
	  }
	  
	  new Board(tiles, players, weapons, murderCards, doors);
  }
}
