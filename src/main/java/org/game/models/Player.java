package org.game.models;

import org.game.interfaces.ILiveObject;

import javax.swing.*;

public class Player implements ILiveObject {
    private static final int SHOT_INTERVAL = 500;
    private final JLabel shipLabel;
    private final int yPosition;
    private final int speed = 5; //Player speed
    private int xPosition;
    private int width;
    private int height;
    private int health;
    private boolean alive;
    private long lastShotTime = 0;


    public Player(int startX, int startY, int initialHealth) {
        this.shipLabel = new JLabel();
        this.xPosition = startX;
        this.yPosition = startY;
        this.health = initialHealth;
        this.alive = true;
        this.width = 20;
        this.height = 20;
    }

    public void setShipIcon(Icon icon) {
        shipLabel.setIcon(icon);
    }

    public Icon getShipIcon() {
        if (shipLabel.getIcon() != null) {
            return shipLabel.getIcon();
        }
        return null;
    }


    @Override
    public void moveLeft() {
        this.xPosition -= this.speed;
    }

    @Override
    public void moveRight() {
        this.xPosition += this.speed;
    }

    @Override
    public boolean isAlive() {
        return this.alive;
    }

    @Override
    public void hit() {
        this.health -= 1;
        if (this.health <= 0) {
            this.alive = false;
        }
    }

    @Override
    public Bullet shoot() {
        int bulletSpeed = 10;
        long currentTime = System.currentTimeMillis();
        Bullet bullet = new Bullet(this.xPosition,
                this.yPosition - 30, -bulletSpeed);
        if (!isAlive()){
            bullet.hit();
        }
        if (currentTime - lastShotTime >= SHOT_INTERVAL) {
            lastShotTime = currentTime;
            return bullet;
        } else {
            long timeToWait = SHOT_INTERVAL - (currentTime - lastShotTime);
            System.out.println("Za szybko, do strzału zostało jeszcze " + timeToWait);
            bullet.hit();
            return bullet;
        }
    }


    // Getters
    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getWidth() {
        return width;
    }

    public int getHealth(){
        return health;
    }

    public int getHeight() {
        return height;
    }
}