package org.game.gui;

import org.game.models.Bullet;
import org.game.models.Enemy;
import org.game.models.Player;
import org.game.services.InputHandler;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private final JLabel scoreLabel;
    private final JLabel livesLabel;
    private final JLabel endGameLabel;
    private final int gameWidth;
    private final int gameHeight;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;


    public GamePanel(InputHandler inputHandler, int gameWidth, int gameHeight){
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        setPreferredSize(new Dimension(gameWidth, gameHeight));
        addKeyListener(inputHandler);
        setFocusable(true);
        requestFocusInWindow();
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        scoreLabel = new JLabel("Score: 0", SwingConstants.LEFT);
        livesLabel = new JLabel("Lives: 3", SwingConstants.RIGHT);

        scoreLabel.setOpaque(true);
        scoreLabel.setBackground(Color.BLACK);
        scoreLabel.setForeground(Color.WHITE);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20));
        livesLabel.setOpaque(true);
        livesLabel.setBackground(Color.BLACK);
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 20));

        add(scoreLabel, BorderLayout.SOUTH);
        add(livesLabel, BorderLayout.NORTH);

        endGameLabel = new JLabel("Gratulacje! " +
                "Zwiększ poziom trudności i spróbuj jeszcze raz!"
                + SwingConstants.CENTER);
        endGameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        endGameLabel.setForeground(Color.RED);
        endGameLabel.setVisible(false);
        add(endGameLabel);
        }

    public int getGameWidth() {
        return gameWidth;
    }
    public int getGameHeight() {
        return gameHeight;
    }

    public void displayGameOver() {
        JOptionPane.showMessageDialog(this, "Game Over!", "Koniec Gry", JOptionPane.ERROR_MESSAGE);
    }

    public void displayEndGameLabel(){
        endGameLabel.setVisible(true);
    }

    public void hideEndGameLabel(){
        endGameLabel.setVisible(false);
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score);
    }

    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives);
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
            if (player.getShipIcon() != null){
                player.getShipIcon().paintIcon(this, g, player.getXPosition(), player.getYPosition());
            }
            else
            {
                g.setColor(Color.BLUE);
                g.fillRect(player.getXPosition(), player.getYPosition(), player.getWidth(), player.getHeight());
        }
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
