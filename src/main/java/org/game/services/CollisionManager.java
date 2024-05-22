package org.game.services;

import org.game.models.Bullet;
import org.game.models.Enemy;
import org.game.models.Player;

import java.awt.*;
import java.util.List;

public class CollisionManager {
    private final List<Enemy> enemies; // Lista przeciwników w grze
    private final List<Bullet> bullets; // Lista pocisków wystrzelonych w grze
    private final Player player; // Referencja do gracza

    public CollisionManager(List<Enemy> enemies, List<Bullet> bullets, Player player) {
        this.enemies = enemies;
        this.bullets = bullets;
        this.player = player;
    }

    public void checkCollisions() {
        checkBulletCollisions(); // Sprawdź kolizje dla pocisków
        checkPlayerCollisions(); // Sprawdź kolizje dla gracza
    }

    private void checkBulletCollisions() {
        for (Bullet bullet : bullets) {
            if (!bullet.isAlive()) {
                continue; // Pomija nieaktywne pociski
            }
            for (Enemy enemy : enemies) {
                if (enemy.isAlive() && bulletCollidesWith(enemy, bullet)) {
                    if(bullet.getSpeed() < 0){ // Dodatkowy warunek, by upewnić się, że pocisk porusza się w odpowiednim kierunku
                        enemy.hit(); // Zadaj obrażenia przeciwnikowi
                        bullet.hit(); // Zadaj obrażenia pociskowi
                    }
                    if (!enemy.isAlive()) {
                        // Opcjonalnie: działania po śmierci przeciwnika
                    }
                    break; // Przerwij pętlę po wykryciu kolizji
                }
            }
        }
    }

    private void checkPlayerCollisions() {
        for (Bullet bullet : bullets) {
            if (bullet.isAlive() && bulletCollidesWith(player, bullet)) {
                player.hit(); // Zadaj obrażenia graczowi
                bullet.hit(); // Zadaj obrażenia pociskowi
                break; // Przerwij pętlę po wykryciu kolizji
            }
        }
    }

    private boolean bulletCollidesWith(Object obj, Bullet bullet) {
        Rectangle bulletRect = new Rectangle(bullet.getXPosition(), bullet.getYPosition(), Bullet.getWidth(), Bullet.getHeight()); // Stworzenie prostokąta dla pocisku
        int objX = 0, objY = 0, objWidth = 0, objHeight = 0;

        if (obj instanceof Player player) {
            objX = player.getXPosition();
            objY = player.getYPosition();
            objWidth = player.getWidth();
            objHeight = player.getHeight();
        } else if (obj instanceof Enemy enemy) {
            objX = enemy.getXPosition();
            objY = enemy.getYPosition();
            objWidth = enemy.getWidth();
            objHeight = enemy.getHeight();
        }

        Rectangle objRect = new Rectangle(objX, objY, objWidth, objHeight); // Stworzenie prostokąta dla obiektu
        return bulletRect.intersects(objRect); // Sprawdzenie czy prostokąty się przecinają, co oznacza kolizję
    }

}
