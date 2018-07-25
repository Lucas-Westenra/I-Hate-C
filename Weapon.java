
public class Weapon
{
  private String name;
  private Tile position;
  
  public Weapon(String name, Tile position){
    this.name=name;
    this.position=position;
  }

  public void setName(String name){
    this.name = name;
  }
  
  public void setPosition(Tile position){
	this.position=position;
  }
  
  public String getName(){
	    return name;
  }
  
  public Tile getPosition(){
    return position;
  }

  public String toString()
  {
    return "[" + "name" + ":" + getName()+ "," + "position:" + position.toString() + "]"; 
  }
}