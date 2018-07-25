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

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Tile[][] tiles, List<Player> players, List<Weapon> weapons,
		  List<Card> murderCards)
  {
    this.playersTurn = players.get((int)Math.random()*players.size());
    this.murderCards = murderCards;
    this.tiles = tiles;
    this.weapons = weapons;
    this.players = players;
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
  public Tile getTile(int index)
  {
    Tile aTile = tiles.get(index);
    return aTile;
  }

  public List<Tile> getTiles()
  {
    List<Tile> newTiles = Collections.unmodifiableList(tiles);
    return newTiles;
  }

  public int numberOfTiles()
  {
    return 24*25;
  }

  public boolean hasTiles()
  {
    return tiles[0][0] == null;
  }

  public int indexOfTile(Tile aTile)
  {
    int index = tiles.indexOf(aTile);
    return index;
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
  
  private static String inputString(String msg) {
		System.out.print(msg + " ");
		while (1 == 1) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				return input.readLine();
			} catch (IOException e) {
				System.out.println("I/O Error ... please try again!");
			}
		}
	}
  
  private static int inputNumber(String msg) {
		System.out.print(msg + " ");
		while (1 == 1) {
			BufferedReader input = new BufferedReader(new InputStreamReader(
					System.in));
			try {
				String v = input.readLine();
				return Integer.parseInt(v);
			} catch (IOException e) {
				System.out.println("Please enter a number!");
			}
		}
	}
  
  void main() {
	  Tile[][] tiles = new Tile[24][25];
	  List<Player> players = new ArrayList<Player>();
	  List<Weapon> weapons = new ArrayList<Weapon>();
	  List<Card> cards = new ArrayList<Card>();
	  List<Card> murderCards = new ArrayList<Card>();
	  
	  for(int x=0; x<24; x++) {
		  for(int y=0; y<25; y++) {
			  if(x<6 && y<7)
				  tiles[x][y] = new Tile("Kitchen",x,y);
			  if((x>=8 && x<16 && y>=2 && y<8) || (x>=10 && x<14 && y==1))
				  tiles[x][y] = new Tile("Ball Room",x,y);
			  if((x>=18 && y<4) || (x>=19 && y==4)) 
				  tiles[x][y] = new Tile("Conservatory",x,y);
			  if((x<5 && y==9) || (x<8 && y>9 && y<16))
				  tiles[x][y] = new Tile("Dining Room",x,y);
			  if(x>9 && x<15 && y>9 && y<17)
				  tiles[x][y] = new Tile("Cellar",x,y);
			  if(x>17 && y>7 && y<13)
				  tiles[x][y] = new Tile("Billiard Room",x,y);
			  if((x>17 && y>13 && y<19) || (x==16 && y>14 && y<18))
				  tiles[x][y] = new Tile("Library",x,y);
			  if(x<7 && y>18) 
				  tiles[x][y] = new Tile("Lounge",x,y);
			  if(x>8 && x<15 && y>17)
				  tiles[x][y] = new Tile("Hall",x,y);
			  if(x>16 && y>20)
				  tiles[x][y] = new Tile("Study",x,y);
			  else 
				  tiles[x][y] = new Tile("Walkway",x,y);
			  /*
			   * Tiles to make inaccessible 
			   * x(0..8),y(0)
			   * x(10..13),y(0)
			   * x(15..),y(0)
			   * x(6),y(1)
			   * x(17),y(1)
			   * x(0),y(6,8)
			   * x(23),y(6,8)
			   * x(10..14),y(10..16)
			   * x(23),y(13,14)
			   * x(0),y(16,18)
			   * x(23),y(18,20)
			   * x(6,8,15,17),y(24)
			   */
		  }
	  }
	  
	  //players
	  players.add(new Player("Miss Scarlett", tiles[7][24], 'S', 1));
	  players.add(new Player("Colonel Mustard", tiles[0][17], 'M', 1));
	  players.add(new Player("Mrs. White", tiles[9][0], 'W', 1));
	  players.add(new Player("Mr. Green", tiles[14][0], 'G', 1));
	  players.add(new Player("Mrs. Peacock", tiles[23][6], 'P', 1));
	  players.add(new Player("Professor Plum", tiles[23][19], 'L', 1));
	  
	  //weapons
	  weapons.add(new Weapon("Candlestick", tiles[18][22]));
	  weapons.add(new Weapon("Dagger", tiles[3][3]));
	  weapons.add(new Weapon("Lead Pipe", tiles[22][2]));
	  weapons.add(new Weapon("Revolver", tiles[4][11]));
	  weapons.add(new Weapon("Rope", tiles[20][8]));
	  weapons.add(new Weapon("Spanner", tiles[1][22]));
	  
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
	  
	  new Board(tiles, players, weapons, murderCards);
//	  int nplayers = inputNumber("How many players?");
//	  while(nplayers < 3 || nplayers > 6)  nplayers = inputNumber("How many players? Must be 3-6."); 
//	  String[] characters = new String[nplayers];
//	  for(int i=0; i<nplayers; i++) {
//		  characters[i] = inputString("Which character would you like to choose?");
//	  }
  }
}
