package myPong;
import java.awt.*;
import java.awt.event.*;

public class Paddle extends Rectangle {

    private int yVelocity;
    private int speed = 10;
    private int gameHeight;
	private boolean keys[] = new boolean[256];
	private Color color;
	private int score = 0;


    public Paddle(int x, int y, int width, int height, int gameHeight, Color color) {
        super(x, y, width, height);
        this.gameHeight = gameHeight;
        this.color = color;
    }

    public void setYDirection(int yDirection) {
        yVelocity = yDirection;
    }
 
    public void move() {
        if (y + yVelocity >= 0 && y + yVelocity <= gameHeight - height) {
            y = y + yVelocity;
        }
    }

    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(x, y, width, height);
    }
    
    public int getScore() {
    	return this.score;
    }
    
    public void incrementScore() {
    	this.score++;
    }
    
    public void setReady(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }


    public int getYVelocity() {
        return yVelocity;
    }

    public void setYVelocity(int yVelocity) {
        this.yVelocity = yVelocity;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public double getHeight() {
    	return this.height;
    }
    
    public Color getColor() {
        return this.color;
    }
    
    public void setY(int y) {
    	this.y = y;
    }
  
}
