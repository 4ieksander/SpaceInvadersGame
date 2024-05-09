package org.game.services;

import org.game.models.Bullet;
import org.game.models.Enemy;
import org.game.models.Player;
import java.util.List;

public class CollisionManager {
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;

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
                    enemy.hit();
                    bullet.hit();
                    if (!enemy.isAlive()) {
                        //TODO SCORE
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
        // TODO
        return false;
    }
}
