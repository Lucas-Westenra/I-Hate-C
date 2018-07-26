
public class Tile{
  private String name;
  private int xPos;
  private int yPos;

  public Tile(String name, int xPos, int yPos){
    this.name = name;
    this.xPos = xPos;
    this.yPos = yPos;
  }

  public void setName(String name){
    this.name = name;
  }

  public void setXPos(int xPos){
    this.xPos = xPos;
  }

  public void setYPos(int yPos){
	  this.yPos=yPos;
  }

  public String getName(){
    return name;
  }

  public int getXPos(){
    return xPos;
  }

  public int getYPos(){
    return yPos;
  }
  
  public boolean canMove(Tile current, Tile next) {
	  
	  
	  if(!(current.name.equals(next.name))) { //Doesn't allow movement from one location to another
		  return false;
	  }
	  return true;
  }
  
  public String toString(){
    return "[" + "name" + ":" + getName()+ "," +
            "xPos" + ":" + getXPos()+ "," +
            "yPos" + ":" + getYPos()+ "]";
  }
}