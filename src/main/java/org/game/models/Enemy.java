package org.game.models;

import org.game.interfaces.ILiveObject;
import org.game.interfaces.IMovableVertically;

public class Enemy implements ILiveObject, IMovableVertically {
    private static boolean movingRight = true; // określa, czy wrogowie poruszają się w prawo
    private final int width; // szerokość wroga
    private final int height; // wysokość wroga
    private final int speed = 2; // prędkość wroga
    private int xPosition; // położenie X wroga
    private int yPosition; // położenie Y wroga
    private int health; // zdrowie wroga
    private boolean alive; // status życia wroga

    public Enemy(int startX, int startY, int initialHealth) {
        this.xPosition = startX; // początkowa pozycja X
        this.yPosition = startY; // początkowa pozycja Y
        this.health = initialHealth; // początkowe zdrowie
        this.alive = true; // wrog żyje przy tworzeniu
        this.width = 20; // domyślna szerokość
        this.height = 20; // domyślna wysokość
    }

    public Enemy(int startX, int startY, int initialHealth, int width, int height) {
        this.xPosition = startX;
        this.yPosition = startY;
        this.health = initialHealth;
        this.alive = true;
        this.width = width; // szerokość wroga
        this.height = height; // wysokość wroga
    }


    public static boolean isMovingRight() {
        return movingRight; // zwraca informację czy wrogowie poruszają się w prawo
    }

    public static void reverseDirection() {
        Enemy.movingRight = !Enemy.movingRight; // zmienia kierunek ruchu wrogów
    }


    @Override
    public boolean isAlive() {
        return this.alive; // zwraca status życia wroga
    }

    @Override
    public void hit() {
        this.health -= 1; // zmniejsza zdrowie wroga
        if (this.health <= 0) {
            this.alive = false; // wrog umiera gdy zdrowie spadnie do 0 lub mniej
        }
    }

    @Override
    public void moveVertically() {
        this.yPosition += this.speed; // przemieszcza wroga w pionie
        // jesli speed jest na minusie to w przeciwnym kierunku
    }

    @Override
    public void moveLeft() {
        this.xPosition -= this.speed; // przemieszcza wroga w lewo
    }

    @Override
    public void moveRight() {
        this.xPosition += this.speed; // przemieszcza wroga w prawo
    }

    @Override
    public Bullet shoot() {
        return new Bullet(this.xPosition, this.yPosition + 11, speed); // wrog strzela pociskiem
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
