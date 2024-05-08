package org.game.models;

import org.game.interfaces.ILiveObject;

public class Player implements ILiveObject {
    private int xPosition;
    private int yPosition;
    private int health;
    private boolean alive;
    private final int speed = 5; // Prędkość przesunięcia w lewo/prawo
    private final int bulletSpeed = 10; // Prędkość pocisku

    public Player(int startX, int startY, int initialHealth) {
        this.xPosition = startX;
        this.yPosition = startY;
        this.health = initialHealth;
        this.alive = true;
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
    public void moveLeft() {
        this.xPosition -= this.speed;
    }

    @Override
    public void moveRight() {
        this.xPosition += this.speed;
    }

    @Override
    public void shoot() {
    }
}