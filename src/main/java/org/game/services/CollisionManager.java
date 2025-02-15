package org.game.services;

import org.game.models.Bullet;
import org.game.models.Enemy;
import org.game.models.Player;

import java.awt.*;
import java.util.List;

public class CollisionManager {
    private final List<Enemy> enemies;
    private final List<Bullet> bullets;
    private final Player player;

    public CollisionManager(List<Enemy> enemies, List<Bullet> bullets, Player player) {
        this.enemies = enemies;
        this.bullets = bullets;
        this.player = player;
    }


    public void checkCollisions() {
        checkBulletCollisions();
        checkPlayerCollisions();
    }


    private void checkBulletCollisions() {
        for (Bullet bullet : bullets) {
            if (!bullet.isAlive()) {
                continue;
            }
            for (Enemy enemy : enemies) {
                if (enemy.isAlive() && bulletCollidesWith(enemy, bullet)) {
                    if(bullet.getSpeed() < 0){
                        enemy.hit();
                        bullet.hit();
                    }
                    if (!enemy.isAlive()) {
                    }
                    break;
                }
            }
        }
    }

    private void checkPlayerCollisions() {
        for (Bullet bullet : bullets) {
            if (bullet.isAlive() && bulletCollidesWith(player, bullet)) {
                player.hit();
                bullet.hit();
                break;
            }
        }
    }

    private boolean bulletCollidesWith(Object obj, Bullet bullet) {
        Rectangle bulletRect = new Rectangle(bullet.getXPosition(), bullet.getYPosition(), Bullet.getWidth(), Bullet.getHeight());
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

        Rectangle objRect = new Rectangle(objX, objY, objWidth, objHeight);
        return bulletRect.intersects(objRect);
    }

}
