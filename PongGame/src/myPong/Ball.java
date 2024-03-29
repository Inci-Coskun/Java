package myPong;

import java.awt.*;
import java.util.*;
public class Ball extends Rectangle {

	
	int angle = 0;
    int xVelocity;
    int yVelocity;
    int initialSpeed = 6;
    int radius;

    Ball(int x, int y, int width, int height) {
        super(x, y, width, height);
        setRandomVelocity();
        this.radius = width / 2;
    }

    public void setRandomVelocity() {
        
    	do {
    	    angle = (int)(Math.random() * 360);
    	} while ((angle >= 85 && angle <= 95) ||  
    	         (angle >= 175 && angle <= 185) || 
    	         (angle >= 265 && angle <= 275) ||
    	         (angle >= 355 || angle <= 5));

        xVelocity = (int) (Math.cos(angle) * initialSpeed);
        yVelocity = (int) (Math.sin(angle) * initialSpeed);
    }

    public void setXDirection(int randomXDirection) {
        xVelocity = randomXDirection;
    }

    public void setYDirection(int randomYDirection) {
        yVelocity = randomYDirection;
    }

    public void move() {
        this.x += xVelocity;
        this.y += yVelocity;
    }

    public void draw(Graphics g) {
        g.setColor(Color.white);
        g.fillOval(x, y, height, width);
    }

    public int getXVelocity() {
        return xVelocity;
    }

    public int getYVelocity() {
        return yVelocity;
    }

    public int getRadius() {
        return this.radius;
    }
    
    public void setReady(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        setRandomVelocity(); 
    }
}