package org.game.services;

import org.game.models.Player;
import org.game.models.Enemy;
import org.game.models.Bullet;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class RenderService {
    private JPanel gamePanel;

    public RenderService(JPanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void renderGame(List<Enemy> enemies, List<Bullet> bullets, Player player) {
        gamePanel.repaint();

        Graphics g = gamePanel.getGraphics();
        if (g != null) {
            drawPlayer(g, player);
            drawEnemies(g, enemies);
            drawBullets(g, bullets);
        }
    }

    public void drawPlayer(Graphics g, Player player) {
        if (player.isAlive()) {
            g.setColor(Color.BLUE);
            g.fillRect(player.getXPosition(), player.getYPosition(), 20, 20);
        }
    }

    public void drawEnemies(Graphics g, List<Enemy> enemies) {
        g.setColor(Color.RED);
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                g.fillRect(enemy.getXPosition(), enemy.getYPosition(), 20, 20);
            }
        }
    }

    public void drawBullets(Graphics g, List<Bullet> bullets) {
        g.setColor(Color.YELLOW);
        for (Bullet bullet : bullets) {
            if (bullet.isAlive()) {
                g.fillRect(bullet.getXPosition(), bullet.getYPosition(), 5, 10);
            }
        }
    }
}
