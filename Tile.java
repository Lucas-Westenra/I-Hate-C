/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4144.2043e3153 modeling language!*/



// line 19 "model.ump"
// line 60 "model.ump"
public class Tile
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tile Attributes
  private String name;
  private int xPos;
  private int yPos;

  //Tile Associations
  private Weapon weapon;
  private Board board;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tile(String aName, int aXPos, int aYPos, Board aBoard)
  {
    name = aName;
    xPos = aXPos;
    yPos = aYPos;
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create tile due to board");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setXPos(int aXPos)
  {
    boolean wasSet = false;
    xPos = aXPos;
    wasSet = true;
    return wasSet;
  }

  public boolean setYPos(int aYPos)
  {
    boolean wasSet = false;
    yPos = aYPos;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getXPos()
  {
    return xPos;
  }

  public int getYPos()
  {
    return yPos;
  }
  /* Code from template association_GetOne */
  public Weapon getWeapon()
  {
    return weapon;
  }

  public boolean hasWeapon()
  {
    boolean has = weapon != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setWeapon(Weapon aNewWeapon)
  {
    boolean wasSet = false;
    if (weapon != null && !weapon.equals(aNewWeapon) && equals(weapon.getTile()))
    {
      //Unable to setWeapon, as existing weapon would become an orphan
      return wasSet;
    }

    weapon = aNewWeapon;
    Tile anOldTile = aNewWeapon != null ? aNewWeapon.getTile() : null;

    if (!this.equals(anOldTile))
    {
      if (anOldTile != null)
      {
        anOldTile.weapon = null;
      }
      if (weapon != null)
      {
        weapon.setTile(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    //Must provide board to tile
    if (aBoard == null)
    {
      return wasSet;
    }

    //board already at maximum (600)
    if (aBoard.numberOfTiles() >= Board.maximumNumberOfTiles())
    {
      return wasSet;
    }
    
    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      boolean didRemove = existingBoard.removeTile(this);
      if (!didRemove)
      {
        board = existingBoard;
        return wasSet;
      }
    }
    board.addTile(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    if (player != null && !player.equals(aNewPlayer) && equals(player.getTile()))
    {
      //Unable to setPlayer, as existing player would become an orphan
      return wasSet;
    }

    player = aNewPlayer;
    Tile anOldTile = aNewPlayer != null ? aNewPlayer.getTile() : null;

    if (!this.equals(anOldTile))
    {
      if (anOldTile != null)
      {
        anOldTile.player = null;
      }
      if (player != null)
      {
        player.setTile(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Weapon existingWeapon = weapon;
    weapon = null;
    if (existingWeapon != null)
    {
      existingWeapon.delete();
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removeTile(this);
    }
    Player existingPlayer = player;
    player = null;
    if (existingPlayer != null)
    {
      existingPlayer.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "xPos" + ":" + getXPos()+ "," +
            "yPos" + ":" + getYPos()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "weapon = "+(getWeapon()!=null?Integer.toHexString(System.identityHashCode(getWeapon())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}