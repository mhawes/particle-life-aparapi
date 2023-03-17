import java.awt.Color;

public class Atom {
  private double x, y;
  private double velocityX, velocityY;
  private Color color;
  private int type;

  public Atom(Color color, int x, int y, int type) {
    this.color = color;
    this.x = x;
    this.y = y;
    this.type = type;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public int getType() {
    return type;
  }

  public void setPosition(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public double getVelocityX() {
    return velocityX;
  }

  public double getVelocityY() {
    return velocityY;
  }

  public void setVelocity(double velocityX, double velocityY) {
    this.velocityX = velocityX;
    this.velocityY = velocityY;
  }

  public Color getColor() {
    return color;
  }

  public void setX(double x) {
    this.x = x;
  }

  public void setY(double y) {
    this.y = y;
  }
}
