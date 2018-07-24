/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.28.0.4144.2043e3153 modeling language!*/


import java.util.*;

// line 27 "model.ump"
// line 66 "model.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String name;
  private Tile position;
  private Card hand;
  private char piece;
  private boolean playing;

  //Player Associations
  private Tile tile;
  private Board board;
  private List<Card> cards;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, Tile aPosition, Card aHand, char aPiece, Tile aTile, Board aBoard, Card... allCards)
  {
    name = aName;
    position = aPosition;
    hand = aHand;
    piece = aPiece;
    playing = true;
    boolean didAddTile = setTile(aTile);
    if (!didAddTile)
    {
      throw new RuntimeException("Unable to create player due to tile");
    }
    boolean didAddBoard = setBoard(aBoard);
    if (!didAddBoard)
    {
      throw new RuntimeException("Unable to create player due to board");
    }
    cards = new ArrayList<Card>();
    boolean didAddCards = setCards(allCards);
    if (!didAddCards)
    {
      throw new RuntimeException("Unable to create Player, must have 3 to 6 cards");
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

  public boolean setPosition(Tile aPosition)
  {
    boolean wasSet = false;
    position = aPosition;
    wasSet = true;
    return wasSet;
  }

  public boolean setHand(Card aHand)
  {
    boolean wasSet = false;
    hand = aHand;
    wasSet = true;
    return wasSet;
  }

  public boolean setPiece(char aPiece)
  {
    boolean wasSet = false;
    piece = aPiece;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlaying(boolean aPlaying)
  {
    boolean wasSet = false;
    playing = aPlaying;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public Tile getPosition()
  {
    return position;
  }

  public Card getHand()
  {
    return hand;
  }

  public char getPiece()
  {
    return piece;
  }

  public boolean getPlaying()
  {
    return playing;
  }
  /* Code from template association_GetOne */
  public Tile getTile()
  {
    return tile;
  }
  /* Code from template association_GetOne */
  public Board getBoard()
  {
    return board;
  }
  /* Code from template association_GetMany */
  public Card getCard(int index)
  {
    Card aCard = cards.get(index);
    return aCard;
  }

  public List<Card> getCards()
  {
    List<Card> newCards = Collections.unmodifiableList(cards);
    return newCards;
  }

  public int numberOfCards()
  {
    int number = cards.size();
    return number;
  }

  public boolean hasCards()
  {
    boolean has = cards.size() > 0;
    return has;
  }

  public int indexOfCard(Card aCard)
  {
    int index = cards.indexOf(aCard);
    return index;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setTile(Tile aNewTile)
  {
    boolean wasSet = false;
    if (aNewTile == null)
    {
      //Unable to setTile to null, as player must always be associated to a tile
      return wasSet;
    }
    
    Player existingPlayer = aNewTile.getPlayer();
    if (existingPlayer != null && !equals(existingPlayer))
    {
      //Unable to setTile, the current tile already has a player, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Tile anOldTile = tile;
    tile = aNewTile;
    tile.setPlayer(this);

    if (anOldTile != null)
    {
      anOldTile.setPlayer(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToAtMostN */
  public boolean setBoard(Board aBoard)
  {
    boolean wasSet = false;
    //Must provide board to player
    if (aBoard == null)
    {
      return wasSet;
    }

    //board already at maximum (6)
    if (aBoard.numberOfPlayers() >= Board.maximumNumberOfPlayers())
    {
      return wasSet;
    }
    
    Board existingBoard = board;
    board = aBoard;
    if (existingBoard != null && !existingBoard.equals(aBoard))
    {
      boolean didRemove = existingBoard.removePlayer(this);
      if (!didRemove)
      {
        board = existingBoard;
        return wasSet;
      }
    }
    board.addPlayer(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfCards()
  {
    return 3;
  }
  /* Code from template association_MaximumNumberOfMethod */
  public static int maximumNumberOfCards()
  {
    return 6;
  }
  /* Code from template association_AddMNToOptionalOne */
  public boolean addCard(Card aCard)
  {
    boolean wasAdded = false;
    if (cards.contains(aCard)) { return false; }
    if (numberOfCards() >= maximumNumberOfCards())
    {
      return wasAdded;
    }
    Player existingPlayer = aCard.getPlayer();
    if (existingPlayer != null && existingPlayer.numberOfCards() <= minimumNumberOfCards())
    {
      return wasAdded;
    }
    else if (existingPlayer != null)
    {
      existingPlayer.cards.remove(aCard);
    }
    cards.add(aCard);
    setPlayer(aCard,this);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeCard(Card aCard)
  {
    boolean wasRemoved = false;
    if (cards.contains(aCard) && numberOfCards() > minimumNumberOfCards())
    {
      cards.remove(aCard);
      setPlayer(aCard,null);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_SetMNToOptionalOne */
  public boolean setCards(Card... newCards)
  {
    boolean wasSet = false;
    if (newCards.length < minimumNumberOfCards() || newCards.length > maximumNumberOfCards())
    {
      return wasSet;
    }

    ArrayList<Card> checkNewCards = new ArrayList<Card>();
    HashMap<Player,Integer> playerToNewCards = new HashMap<Player,Integer>();
    for (Card aCard : newCards)
    {
      if (checkNewCards.contains(aCard))
      {
        return wasSet;
      }
      else if (aCard.getPlayer() != null && !this.equals(aCard.getPlayer()))
      {
        Player existingPlayer = aCard.getPlayer();
        if (!playerToNewCards.containsKey(existingPlayer))
        {
          playerToNewCards.put(existingPlayer, new Integer(existingPlayer.numberOfCards()));
        }
        Integer currentCount = playerToNewCards.get(existingPlayer);
        int nextCount = currentCount - 1;
        if (nextCount < 3)
        {
          return wasSet;
        }
        playerToNewCards.put(existingPlayer, new Integer(nextCount));
      }
      checkNewCards.add(aCard);
    }

    cards.removeAll(checkNewCards);

    for (Card orphan : cards)
    {
      setPlayer(orphan, null);
    }
    cards.clear();
    for (Card aCard : newCards)
    {
      if (aCard.getPlayer() != null)
      {
        aCard.getPlayer().cards.remove(aCard);
      }
      setPlayer(aCard, this);
      cards.add(aCard);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_GetPrivate */
  private void setPlayer(Card aCard, Player aPlayer)
  {
    try
    {
      java.lang.reflect.Field mentorField = aCard.getClass().getDeclaredField("player");
      mentorField.setAccessible(true);
      mentorField.set(aCard, aPlayer);
    }
    catch (Exception e)
    {
      throw new RuntimeException("Issue internally setting aPlayer to aCard", e);
    }
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addCardAt(Card aCard, int index)
  {  
    boolean wasAdded = false;
    if(addCard(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveCardAt(Card aCard, int index)
  {
    boolean wasAdded = false;
    if(cards.contains(aCard))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfCards()) { index = numberOfCards() - 1; }
      cards.remove(aCard);
      cards.add(index, aCard);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addCardAt(aCard, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Tile existingTile = tile;
    tile = null;
    if (existingTile != null)
    {
      existingTile.setPlayer(null);
    }
    Board placeholderBoard = board;
    this.board = null;
    if(placeholderBoard != null)
    {
      placeholderBoard.removePlayer(this);
    }
    for(Card aCard : cards)
    {
      setPlayer(aCard,null);
    }
    cards.clear();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "piece" + ":" + getPiece()+ "," +
            "playing" + ":" + getPlaying()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "position" + "=" + (getPosition() != null ? !getPosition().equals(this)  ? getPosition().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "hand" + "=" + (getHand() != null ? !getHand().equals(this)  ? getHand().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "tile = "+(getTile()!=null?Integer.toHexString(System.identityHashCode(getTile())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "board = "+(getBoard()!=null?Integer.toHexString(System.identityHashCode(getBoard())):"null");
  }
}