package org.game.models;

import org.game.interfaces.IHittable;
import org.game.interfaces.IMovableVertically;

public class Bullet implements IMovableVertically, IHittable {
    private static int width = 5; // szerokość pocisku
    private static int height = 10; // wysokość pocisku
    private int xPosition; // położenie pocisku na osi X
    private int yPosition; // położenie pocisku na osi Y
    private int speed;  // prędkość pocisku: dodatnia -> porusza się do góry; ujemna -> porusza się do dołu
    private boolean isAlive; // status pocisku: true jeśli jest aktywny

    public Bullet(int startX, int startY, int speed) {
        this.xPosition = startX; // początkowa pozycja X
        this.yPosition = startY; // początkowa pozycja Y
        this.speed = speed; // prędkość pocisku
        this.isAlive = true; // pocisk jest aktywny przy tworzeniu
    }

    public static int getWidth() {
        return width; // zwraca szerokość pocisku
    }

    public static int getHeight() {
        return height; // zwraca wysokość pocisku
    }


    @Override
    public boolean isAlive() {
        return this.isAlive; // zwraca czy pocisk jest aktywny
    }

    @Override
    public void hit() {
        this.isAlive = false; // deaktywuje pocisk po trafieniu
    }

    @Override
    public void moveVertically() {
        this.yPosition += this.speed; // aktualizuje pozycję Y pocisku na podstawie prędkości
    }


    // getters
    public boolean isOffScreen(int screenHeight) {
        return this.yPosition < 0 || this.yPosition > screenHeight; // sprawdza, czy pocisk znajduje się poza ekranem
    }

    public int getXPosition() {
        return this.xPosition; // zwraca pozycję X
    }

    public int getYPosition() {
        return this.yPosition; // zwraca pozycję Y
    }

    public int getSpeed(){
        return this.speed; // zwraca prędkość pocisku
    }
}
