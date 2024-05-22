package org.game.models;

import org.game.interfaces.ILiveObject;

import javax.swing.*;

public class Player implements ILiveObject {
    private static final int SHOT_INTERVAL = 500; // interwał między strzałami w ms
    private final JLabel shipLabel; // etykieta graficzna reprezentująca statek gracza
    private final int yPosition; // stała pozycja Y statku
    private final int speed = 5; // prędkość przemieszczania się statku
    private int xPosition; // pozycja X statku, może się zmieniać
    private int width; // szerokość statku
    private int height; // wysokość statku
    private int health; // zdrowie statku
    private boolean alive; // flaga życia, true jeśli statek jest 'żywy'
    private long lastShotTime = 0; // czas ostatniego strzału

    public Player(int startX, int startY, int initialHealth) {
        this.shipLabel = new JLabel(); // inicjalizacja etykiety statku
        this.xPosition = startX; // ustawienie początkowej pozycji X
        this.yPosition = startY; // ustawienie początkowej pozycji Y
        this.health = initialHealth; // ustawienie początkowego zdrowia
        this.alive = true; // gracz na początku jest żywy
        this.width = 20; // domyślna szerokość
        this.height = 20; // domyślna wysokość
    }

    public void setShipIcon(Icon icon) {
        shipLabel.setIcon(icon); // ustawienie ikony statku
    }

    public Icon getShipIcon() {
        if (shipLabel.getIcon() != null) {
            return shipLabel.getIcon(); // zwróć ikonę jeśli istnieje
        }
        return null; // zwróć null jeśli ikona nie istnieje
    }

    @Override
    public void moveLeft() {
        this.xPosition -= this.speed; // przesuń statek w lewo
    }

    @Override
    public void moveRight() {
        this.xPosition += this.speed; // przesuń statek w prawo
    }

    @Override
    public boolean isAlive() {
        return this.alive; // zwróć status życia
    }

    @Override
    public void hit() {
        this.health -= 1; // zmniejsz zdrowie o 1
        if (this.health <= 0) {
            this.alive = false; // ustaw flagę życia na false, jeśli zdrowie <= 0
        }
    }

    @Override
    public Bullet shoot() {
        int bulletSpeed = 10; // prędkość pocisku
        long currentTime = System.currentTimeMillis(); // bieżący czas w ms
        Bullet bullet = new Bullet(this.xPosition,
                this.yPosition - 30, -bulletSpeed); // utwórz nowy pocisk
        if (!isAlive()){
            bullet.hit(); // automatycznie traf pocisk, jeśli gracz nie żyje
        }
        if (currentTime - lastShotTime >= SHOT_INTERVAL) {
            lastShotTime = currentTime; // uaktualnij czas ostatniego strzału
            return bullet; // zwróć pocisk
        } else {
            long timeToWait = SHOT_INTERVAL - (currentTime - lastShotTime);
            System.out.println("Za szybko, do strzału zostało jeszcze " + timeToWait);
            bullet.hit(); // traf pocisk, jeśli próbowano strzelać za szybko
            return bullet;
        }
    }

    // Gettery
    public int getXPosition() {
        return xPosition; // zwróć pozycję X
    }

    public int getYPosition() {
        return yPosition; // zwróć pozycję Y
    }

    public int getWidth() {
        return width; // zwróć szerokość
    }

    public int getHealth(){
        return health; // zwróć zdrowie
    }

    public int getHeight() {
        return height; // zwróć wysokość
    }
}
