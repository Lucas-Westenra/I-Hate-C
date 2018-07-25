import java.util.*;

public class Board{


  //Board Attributes
  private Player playersTurn;
  private List<Card> murderCards;

  //Board Associations
  private List<Tile> tiles;
  private List<Weapon> weapons;
  private List<Player> players;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Player aPlayersTurn, List<Card> aMurderCards)
  {
    playersTurn = aPlayersTurn;
    murderCards = aMurderCards;
    tiles = new ArrayList<Tile>();
    weapons = new ArrayList<Weapon>();
    players = new ArrayList<Player>();
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
    int number = tiles.size();
    return number;
  }

  public boolean hasTiles()
  {
    boolean has = tiles.size() > 0;
    return has;
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
}