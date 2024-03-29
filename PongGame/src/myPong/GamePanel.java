package myPong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GamePanel extends Canvas implements KeyListener {
    private final int screenWidth = 1000;
    private final int screenHeight = 700;
    private final Dimension SCREEN_SIZE = new Dimension(screenWidth, screenHeight);
    private final int ballDiameter = 15;
    private final int racketWidth = 20;
    private final int racketHeight = 100;
    private final int d = 5;

    private Paddle leftRacket;
    private Paddle rightRacket;
    private Ball ball;
    private Random random = new Random();
    private Image buffer;
    private boolean showReady = false;

    public GamePanel() {
        ball = new Ball((screenWidth / 2) - (ballDiameter / 2), (screenHeight / 2) - (ballDiameter / 2), ballDiameter, ballDiameter);
        leftRacket = new Paddle(55, (screenHeight / 2) - (racketHeight / 2), racketWidth, racketHeight, screenHeight, Color.red);
        rightRacket = new Paddle(screenWidth - racketWidth - 55, (screenHeight / 2) - (racketHeight / 2), racketWidth, racketHeight, screenHeight, Color.blue);
        this.setFocusable(true);
        this.setPreferredSize(SCREEN_SIZE);
        this.addKeyListener(this);
        this.setBackground(Color.BLACK);

        Thread gameThread = new Thread(() -> {
            while (true) {
                move();
                checkCollision();
                repaint();
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {}
            }
        });
        gameThread.start();
    }

    @Override
    public void update(Graphics g) {
        if (buffer == null) {
            buffer = createImage(getWidth(), getHeight());
        }
        Graphics bufferGraphics = buffer.getGraphics();
        draw(bufferGraphics);
        g.drawImage(buffer, 0, 0, null);
    }

    public void paint(Graphics g) {
        update(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, screenWidth, screenHeight);

        g.setColor(leftRacket.getColor());
        g.fillRect((int) leftRacket.getX(), (int) leftRacket.getY(), (int) leftRacket.getWidth(), (int) leftRacket.getHeight());

        g.setColor(rightRacket.getColor());
        g.fillRect((int) rightRacket.getX(), (int) rightRacket.getY(), (int) rightRacket.getWidth(), (int) rightRacket.getHeight());

        g.setColor(Color.white);
        g.fillOval((int) ball.getX(), (int) ball.getY(), (int) ball.getWidth(), (int) ball.getHeight());

        g.setColor(Color.red);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        g.drawString(Integer.toString(leftRacket.getScore()), 20, 40);

        g.setColor(Color.blue);
        g.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        g.drawString(Integer.toString(rightRacket.getScore()), 965, 40);

        g.setColor(Color.WHITE);
        g.drawLine(50, 0, 50, screenHeight);
        g.drawLine(screenWidth - 50, 0, screenWidth - 50, screenHeight);

        if (showReady) {
            g.setFont(new Font("Times New Roman", Font.PLAIN, 40));
            g.drawString("Ready?", screenWidth / 2 - 75, screenHeight / 2);
        }
    }

    public void move() {
        leftRacket.move();
        rightRacket.move();
        ball.move();
    }

    public void checkCollision() {
        if (ball.getY() <= 0 || ball.getY() >= screenHeight - ball.getRadius() * 2) {
            ball.setYDirection(-ball.getYVelocity());
        }

        if (ball.getX() >= screenWidth - 50 - ball.getRadius() * 2) {
            leftRacket.incrementScore();
            showReadyMessage();
            rightRacket.setReady(screenWidth - (int) rightRacket.getWidth() - 55, (screenHeight / 2) - (int) (rightRacket.getHeight() / 2), (int) rightRacket.getWidth(), (int) rightRacket.getHeight());
            leftRacket.setReady(55, (screenHeight / 2) - (int) (leftRacket.getHeight() / 2), (int) leftRacket.getWidth(), (int) leftRacket.getHeight());
            ball.setReady((screenWidth / 2) - (ballDiameter / 2), (screenHeight / 2) - (ballDiameter / 2), ballDiameter, ballDiameter);
        }

        if (ball.getX() <= 50) {
            rightRacket.incrementScore();
            showReadyMessage();
            rightRacket.setReady(screenWidth - (int) rightRacket.getWidth() - 55, (screenHeight / 2) - (int) (rightRacket.getHeight() / 2), (int) rightRacket.getWidth(), (int) rightRacket.getHeight());
            leftRacket.setReady(55, (screenHeight / 2) - (int) (leftRacket.getHeight() / 2), (int) leftRacket.getWidth(), (int) leftRacket.getHeight());
            ball.setReady((screenWidth / 2) - (ballDiameter / 2), (screenHeight / 2) - (ballDiameter / 2), ballDiameter, ballDiameter);
        }

        if (ball.intersects(leftRacket)) {
            ball.setXDirection(Math.abs(ball.getXVelocity()));
        }

        if (ball.intersects(rightRacket)) {
            ball.setXDirection(-Math.abs(ball.getXVelocity()));
        }

        if (leftRacket.getY() <= 0) {
            leftRacket.setY(0);
        }

        if (leftRacket.getY() >= (screenHeight - leftRacket.getHeight())) {
            leftRacket.setY(screenHeight - (int) leftRacket.getHeight());
        }

        if (rightRacket.getY() <= 0) {
            rightRacket.setY(0);
        }
        if (rightRacket.getY() >= (screenHeight - rightRacket.getHeight())) {
            rightRacket.setY(screenHeight - (int) rightRacket.getHeight());
        }
    }

    public void showReadyMessage() {
        showReady = true;
        repaint();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}
        showReady = false;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A) {
            leftRacket.setYDirection(-d);
        }
        if (e.getKeyCode() == KeyEvent.VK_Z) {
            leftRacket.setYDirection(d);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            rightRacket.setYDirection(-d);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            rightRacket.setYDirection(d);
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_Z) {
            leftRacket.setYDirection(0);
        }
        if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
            rightRacket.setYDirection(0);
        }
    }

}

