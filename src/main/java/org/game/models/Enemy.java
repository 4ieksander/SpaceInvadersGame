package org.game.models;

import org.game.interfaces.ILiveObject;
import org.game.interfaces.IMovableVertically;

public class Enemy implements ILiveObject, IMovableVertically {
    private static boolean movingRight = true;
    private final int width;
    private final int height;
    private final int speed = 2;
    private int xPosition;
    private int yPosition;
    private int health;
    private boolean alive;

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
        Enemy.movingRight = !Enemy.movingRight;
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
        return new Bullet(this.xPosition, this.yPosition + 11, speed);
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

    public int getHeight() {
        return height;
    }
}