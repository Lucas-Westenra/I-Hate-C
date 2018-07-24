/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4144.2043e3153 modeling language!*/



// line 37 "model.ump"
// line 72 "model.ump"
public class Card
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Card Attributes
  private String name;
  private String type;

  //Card Associations
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Card(String aName, String aType)
  {
    name = aName;
    type = aType;
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

  public boolean setType(String aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getType()
  {
    return type;
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

  public void delete()
  {
    if (player != null)
    {
      if (player.numberOfCards() <= 3)
      {
        player.delete();
      }
      else
      {
        Player placeholderPlayer = player;
        this.player = null;
        placeholderPlayer.removeCard(this);
      }
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "type" + ":" + getType()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null");
  }
}