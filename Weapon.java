/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4144.2043e3153 modeling language!*/



// line 13 "model.ump"
// line 55 "model.ump"
public class Weapon
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Weapon Attributes
  private Tile position;
  private String name;

  //Weapon Associations
  private Board board;
  private Tile tile;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Weapon(Tile aPosition, String aName, Board aBoard, Tile aTile)
  {
    position = aPosition;
    name = aName;
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create weapon due to board");
    }
    boolean didAddTile = setTile(aTile);
    if (!didAddTile)
    {
      throw new RuntimeException("Unable to create weapon due to tile");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPosition(Tile aPosition)
  {
    boolean wasSet = false;
    position = aPosition;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public Tile getPosition()
  {
    return position;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetOne */
  public Tile getTile()
  {
    return tile;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    //Must provide board to weapon
    if (aBoard == null)
    {
      return wasSet;
    }

    //board already at maximum (6)
    if (aBoard.numberOfWeapons() >= Board.maximumNumberOfWeapons())
    {
      return wasSet;
    }
    
    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      boolean didRemove = existingBoard.removeWeapon(this);
      if (!didRemove)
      {
        board = existingBoard;
        return wasSet;
      }
    }
    board.addWeapon(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setTile(Tile aNewTile)
  {
    boolean wasSet = false;
    if (aNewTile == null)
    {
      //Unable to setTile to null, as weapon must always be associated to a tile
      return wasSet;
    }
    
    Weapon existingWeapon = aNewTile.getWeapon();
    if (existingWeapon != null && !equals(existingWeapon))
    {
      //Unable to setTile, the current tile already has a weapon, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Tile anOldTile = tile;
    tile = aNewTile;
    tile.setWeapon(this);

    if (anOldTile != null)
    {
      anOldTile.setWeapon(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removeWeapon(this);
    }
    Tile existingTile = tile;
    tile = null;
    if (existingTile != null)
    {
      existingTile.setWeapon(null);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "position" + "=" + (getPosition() != null ? !getPosition().equals(this)  ? getPosition().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "tile = "+(getTile()!=null?Integer.toHexString(System.identityHashCode(getTile())):"null");
  }
}