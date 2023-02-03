package edu.brown.cs.student.userstory03;

public class Star1Entity {

  private int starID;
  private String properName;
  private double x;
  private double y;
  private double z;

  public Star1Entity() {}

  public int getStarID() {
    return starID;
  }

  public void setStarID(int starID) {
    this.starID = starID;
  }

  public String getProperName() {
    return properName;
  }

  public void setProperName(String properName) {
    this.properName = properName;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public double getZ() {
    return z;
  }

  public void setZ(double z) {
    this.z = z;
  }

  @Override
  public String toString() {
    return "Star1Entity{"
        + "starID="
        + starID
        + ", properName='"
        + properName
        + '\''
        + ", x="
        + x
        + ", y="
        + y
        + ", z="
        + z
        + '}';
  }
}
