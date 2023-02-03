package edu.brown.cs.student.userstory03;

public class Star2Entity {

  private String starID;
  private String properName;
  private String position;

  public Star2Entity() {}

  public String getStarID() {
    return starID;
  }

  public void setStarID(String starID) {
    this.starID = starID;
  }

  public String getProperName() {
    return properName;
  }

  public void setProperName(String properName) {
    this.properName = properName;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  @Override
  public String toString() {
    return "Star2Entity{"
        + "starID='"
        + starID
        + '\''
        + ", properName='"
        + properName
        + '\''
        + ", position='"
        + position
        + '\''
        + '}';
  }
}
