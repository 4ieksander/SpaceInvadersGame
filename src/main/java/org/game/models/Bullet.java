package org.game.models;

import org.game.interfaces.IHittable;
import org.game.interfaces.IMovableVertically;

public class Bullet implements IMovableVertically, IHittable {
    private static int width = 5;
    private static int height = 10;
    private int xPosition;
    private int yPosition;;
    private int speed;  // speed positive -> go up; speed negative -> go down
    private boolean isAlive;

    public Bullet(int startX, int startY, int speed) {
        this.xPosition = startX;
        this.yPosition = startY;
        this.speed = speed;
        this.isAlive = true;
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }


    @Override
    public boolean isAlive() {
        return this.isAlive;
    }

    @Override
    public void hit() {
        this.isAlive = false;
    }

    @Override
    public void moveVertically() {
        this.yPosition += this.speed;
    }

    public boolean isOffScreen(int screenHeight) {
        return this.yPosition < 0 || this.yPosition > screenHeight;
    }


    public int getXPosition() {
        return this.xPosition;
    }

    public int getYPosition() {
        return this.yPosition;
    }
}