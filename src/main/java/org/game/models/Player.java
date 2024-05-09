package org.game.models;

import org.game.interfaces.ILiveObject;

public class Player implements ILiveObject {
    private int score;
    private int xPosition;
    private int yPosition;
    private int width;
    private int height;
    private int health;
    private boolean alive;
    private final int speed = 5; //Player speed
    private final int bulletSpeed = 10;


    public Player(int startX, int startY, int initialHealth) {
        this.xPosition = startX;
        this.yPosition = startY;
        this.health = initialHealth;
        this.alive = true;
        score = 0;
        this.width = 20;
        this.height = 20;
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

    public void addPoint(){
        score += 1;
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
        return new Bullet(this.xPosition, this.yPosition -30, - this.bulletSpeed);
    }

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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}