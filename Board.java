/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4144.2043e3153 modeling language!*/


import java.util.*;

// line 2 "model.ump"
// line 46 "model.ump"
public class Board
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Board Attributes
  private Player playersTurn;
  private List murderCards;

  //Board Associations
  private List<Tile> tiles;
  private List<Weapon> weapons;
  private List<Player> players;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Board(Player aPlayersTurn, List aMurderCards)
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
  /* Code from template association_GetMany */
  public Weapon getWeapon(int index)
  {
    Weapon aWeapon = weapons.get(index);
    return aWeapon;
  }

  public List<Weapon> getWeapons()
  {
    List<Weapon> newWeapons = Collections.unmodifiableList(weapons);
    return newWeapons;
  }

  public int numberOfWeapons()
  {
    int number = weapons.size();
    return number;
  }

  public boolean hasWeapons()
  {
    boolean has = weapons.size() > 0;
    return has;
  }

  public int indexOfWeapon(Weapon aWeapon)
  {
    int index = weapons.indexOf(aWeapon);
    return index;
  }
  /* Code from template association_GetMany */
  public Player getPlayer(int index)
  {
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
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfTilesValid()
  {
    boolean isValid = numberOfTiles() >= minimumNumberOfTiles() && numberOfTiles() <= maximumNumberOfTiles();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfTiles()
  {
    return 600;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTiles()
  {
    return 600;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfTiles()
  {
    return 600;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Tile addTile(String aName, int aXPos, int aYPos)
  {
    if (numberOfTiles() >= maximumNumberOfTiles())
    {
      return null;
    }
    else
    {
      return new Tile(aName, aXPos, aYPos, this);
    }
  }

  public boolean addTile(Tile aTile)
  {
    boolean wasAdded = false;
    if (tiles.contains(aTile)) { return false; }
    if (numberOfTiles() >= maximumNumberOfTiles())
    {
      return wasAdded;
    }

    Board existingBoard = aTile.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);

    if (isNewBoard && existingBoard.numberOfTiles() <= minimumNumberOfTiles())
    {
      return wasAdded;
    }

    if (isNewBoard)
    {
      aTile.setBoard(this);
    }
    else
    {
      tiles.add(aTile);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTile(Tile aTile)
  {
    boolean wasRemoved = false;
    //Unable to remove aTile, as it must always have a board
    if (this.equals(aTile.getBoard()))
    {
      return wasRemoved;
    }

    //board already at minimum (600)
    if (numberOfTiles() <= minimumNumberOfTiles())
    {
      return wasRemoved;
    }
    tiles.remove(aTile);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfWeaponsValid()
  {
    boolean isValid = numberOfWeapons() >= minimumNumberOfWeapons() && numberOfWeapons() <= maximumNumberOfWeapons();
    return isValid;
  }
  /* Code from template association_RequiredNumberOfMethod */
  public static int requiredNumberOfWeapons()
  {
    return 6;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfWeapons()
  {
    return 6;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfWeapons()
  {
    return 6;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Weapon addWeapon(Tile aPosition, String aName, Tile aTile)
  {
    if (numberOfWeapons() >= maximumNumberOfWeapons())
    {
      return null;
    }
    else
    {
      return new Weapon(aPosition, aName, this, aTile);
    }
  }

  public boolean addWeapon(Weapon aWeapon)
  {
    boolean wasAdded = false;
    if (weapons.contains(aWeapon)) { return false; }
    if (numberOfWeapons() >= maximumNumberOfWeapons())
    {
      return wasAdded;
    }

    Board existingBoard = aWeapon.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);

    if (isNewBoard && existingBoard.numberOfWeapons() <= minimumNumberOfWeapons())
    {
      return wasAdded;
    }

    if (isNewBoard)
    {
      aWeapon.setBoard(this);
    }
    else
    {
      weapons.add(aWeapon);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeWeapon(Weapon aWeapon)
  {
    boolean wasRemoved = false;
    //Unable to remove aWeapon, as it must always have a board
    if (this.equals(aWeapon.getBoard()))
    {
      return wasRemoved;
    }

    //board already at minimum (6)
    if (numberOfWeapons() <= minimumNumberOfWeapons())
    {
      return wasRemoved;
    }
    weapons.remove(aWeapon);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_IsNumberOfValidMethod */
  public boolean isNumberOfPlayersValid()
  {
    boolean isValid = numberOfPlayers() >= minimumNumberOfPlayers() && numberOfPlayers() <= maximumNumberOfPlayers();
    return isValid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayers()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfPlayers()
  {
    return 6;
  }
  /* Code from template association_AddMNToOnlyOne */
  public Player addPlayer(String aName, Tile aPosition, Card aHand, char aPiece, Tile aTile, Card... allCards)
  {
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return null;
    }
    else
    {
      return new Player(aName, aPosition, aHand, aPiece, aTile, this, allCards);
    }
  }

  public boolean addPlayer(Player aPlayer)
  {
    boolean wasAdded = false;
    if (players.contains(aPlayer)) { return false; }
    if (numberOfPlayers() >= maximumNumberOfPlayers())
    {
      return wasAdded;
    }

    Board existingBoard = aPlayer.getBoard();
    boolean isNewBoard = existingBoard != null && !this.equals(existingBoard);

    if (isNewBoard && existingBoard.numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasAdded;
    }

    if (isNewBoard)
    {
      aPlayer.setBoard(this);
    }
    else
    {
      players.add(aPlayer);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayer(Player aPlayer)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayer, as it must always have a board
    if (this.equals(aPlayer.getBoard()))
    {
      return wasRemoved;
    }

    //board already at minimum (3)
    if (numberOfPlayers() <= minimumNumberOfPlayers())
    {
      return wasRemoved;
    }
    players.remove(aPlayer);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayerAt(Player aPlayer, int index)
  {  
    boolean wasAdded = false;
    if(addPlayer(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayerAt(Player aPlayer, int index)
  {
    boolean wasAdded = false;
    if(players.contains(aPlayer))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayers()) { index = numberOfPlayers() - 1; }
      players.remove(aPlayer);
      players.add(index, aPlayer);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayerAt(aPlayer, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=tiles.size(); i > 0; i--)
    {
      Tile aTile = tiles.get(i - 1);
      aTile.delete();
    }
    for(int i=weapons.size(); i > 0; i--)
    {
      Weapon aWeapon = weapons.get(i - 1);
      aWeapon.delete();
    }
    for(int i=players.size(); i > 0; i--)
    {
      Player aPlayer = players.get(i - 1);
      aPlayer.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playersTurn" + "=" + (getPlayersTurn() != null ? !getPlayersTurn().equals(this)  ? getPlayersTurn().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "murderCards" + "=" + (getMurderCards() != null ? !getMurderCards().equals(this)  ? getMurderCards().toString().replaceAll("  ","    ") : "this" : "null");
  }
}