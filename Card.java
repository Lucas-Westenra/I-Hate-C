
public class Card
{
  private String name;
  private String type;

  public Card(String name, String type)
  {
    this.name = name;
    this.type = type;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getName()
  {
    return name;
  }

  public String getType()
  {
    return type;
  }

  public String toString()
  {
    return  "["+ "name" + ":" + getName()+ "," +
            "type" + ":" + getType()+ "]";
  }
}