import java.util.*;

public class Player
{

  //Player Attributes
  private String name;
  private Tile position;
  private List<Card> hand;
  private char piece;
  private boolean playing;
  private int isPlayer = -1;

  public Player(String name, Tile position, char piece, boolean playing){
    this.name=name;
    this.position=position;
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
  
  public void setPlayer(int player) {
	  this.isPlayer = player;
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
  
  public void addToHand(Card card){
	  this.hand.add(card);
  }

  public char getPiece()
  {
    return piece;
  }

  public boolean getPlaying()
  {
    return playing;
  }
  
  public boolean isPlaying() {
	  return isPlayer!=-1;
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
