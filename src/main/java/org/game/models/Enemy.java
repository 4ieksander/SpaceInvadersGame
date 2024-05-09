package org.game.models;

import org.game.interfaces.ILiveObject;
import org.game.interfaces.IMovableVertically;

public class Enemy implements ILiveObject, IMovableVertically {
    private static boolean movingRight = true;
    private int xPosition;
    private int yPosition;
    private int width;
    private int height;
    private int health;
    private boolean alive;
    private final int speed = 2;

    public Enemy(int startX, int startY, int initialHealth) {
        this.xPosition = startX;
        this.yPosition = startY;
        this.health = initialHealth;
        this.alive = true;
        this.width = 20;
        this.height = 20;
    }

    public Enemy(int startX, int startY, int initialHealth, int width, int height) {
        this.xPosition = startX;
        this.yPosition = startY;
        this.health = initialHealth;
        this.alive = true;
        this.width = width;
        this.height = height;
    }

    public static boolean isMovingRight() {
        return movingRight;
    }

    public static void reverseDirection() {
        if (Enemy.movingRight){
            Enemy.movingRight = false;
        }
        else{
            Enemy.movingRight = true;
        }
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
    public void moveVertically() {
        this.yPosition += this.speed;
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
    public Bullet shoot() {
        return new Bullet(this.xPosition, this.yPosition + 1, -speed);
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

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}