package org.game.services;

import org.game.models.Bullet;
import org.game.models.Enemy;
import org.game.models.Player;
import org.game.services.InputHandler;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private final static int gameWidth = 800;
    private final static int gameHeight = 600;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private JLabel scoreLabel;

    public GamePanel(InputHandler inputHandler) {
        setPreferredSize(new Dimension(gameWidth, gameHeight));
        addKeyListener(inputHandler);
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.WHITE);

        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        this.setLayout(new BorderLayout());
        this.add(scoreLabel, BorderLayout.NORTH);
        }

    public static int getGameWidth() {
        return gameWidth;
    }
    public static int getGameHeight() {
        return gameHeight;
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score); // Metoda do aktualizacji wyniku na etykiecie
    }

    public void setGameObjects(List<Enemy> enemies, List<Bullet> bullets, Player player) {
        this.enemies = enemies;
        this.bullets = bullets;
        this.player = player;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlayer(g, player);
        drawEnemies(g, enemies);
        drawBullets(g, bullets);
    }

    private void drawPlayer(Graphics g, Player player) {
        if (player != null && player.isAlive()) {
            g.setColor(Color.BLUE);
            g.fillRect(player.getXPosition(), player.getYPosition(), player.getWidth(), player.getHeight());
        }
    }

    private void drawEnemies(Graphics g, List<Enemy> enemies) {
        g.setColor(Color.RED);
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                g.fillRect(enemy.getXPosition(), enemy.getYPosition(), enemy.getWidth(), enemy.getHeight());
            }
        }
    }

    private void drawBullets(Graphics g, List<Bullet> bullets) {
        g.setColor(Color.YELLOW);
        for (Bullet bullet : bullets) {
            if (bullet.isAlive()) {
                g.fillRect(bullet.getXPosition(), bullet.getYPosition(), Bullet.getWidth(), Bullet.getHeight());
            }
        }
    }
}
