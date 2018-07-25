import java.util.*;

public class Player
{

  //Player Attributes
  private String name;
  private Tile position;
  private final List<Card> hand;
  private char piece;
  private boolean playing;

  public Player(String name, Tile position, List<Card> hand, char piece, boolean playing){
    this.name=name;
    this.position=position;
    this.hand=hand;
    this.piece=piece;
    this.playing=playing;
  }

  public void setName(String name){
    this.name = name;
  }

  public void setPosition(Tile position){
    this.position = position;
  }

  public void setPiece(char piece){
    this.piece = piece;
  }

  public void setPlaying(boolean playing){
    this.playing = playing;

  }

  public String getName(){
    return name;
  }

  public Tile getPosition(){
    return position;
  }

  public List<Card> getHand()
  {
	List<Card> newHand = Collections.unmodifiableList(hand);
    return newHand;
  }

  public char getPiece()
  {
    return piece;
  }

  public boolean getPlaying()
  {
    return playing;
  }


  public int numberOfCards()
  {
    return hand.size();
  }

  public boolean hasCards()
  {
    boolean has = hand.size() > 0;
    return has;
  }

  public String toString()
  {
    return "["+ "name" + ":" + getName()+ "," +
    			"position:" + position.toString()+","+
            "piece" + ":" + getPiece()+ "," +
            "playing" + ":" + getPlaying()+ "]";
  }
}