package org.game.services;

import org.game.models.GameSettings;
import org.game.models.Player;
import org.game.models.Enemy;
import org.game.models.Bullet;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private boolean isRunning;
    private Thread gameThread;
    private GamePanel gamePanel;
    private InputHandler inputHandler;
    private GameSettings settings;



    public GameEngine(Player player, List<Enemy> enemies, List<Bullet> bullets) {
        this.player = player;
        this.enemies = enemies;
        this.bullets = bullets;
        this.isRunning = false;
    }

    public GameEngine(GameSettings gameSettings) {
        this.settings = gameSettings;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.isRunning = false;
    }

    public void initializeGame() {
        enemies.clear();
        bullets.clear();
        setupEnemies();
        player = new Player(350, 450, 3);
        this.gamePanel.setGameObjects(enemies, bullets, player);

    }


    public void startGame() {
        System.out.println(enemies);
        if (gameThread == null || !gameThread.isAlive()) {
            isRunning = true;
            gameThread = new Thread(this::gameLoop);
            gameThread.start();
        }
    }

    public void stopGame() {
        isRunning = false;
        try {
            if (gameThread != null) {
                gameThread.join();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void gameLoop() {
        while (isRunning) {
            updateGame();
            SwingUtilities.invokeLater(() -> gamePanel.repaint());
            try {
                Thread.sleep(1000 / 10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void updateGame() {
        for (Bullet bullet : bullets) {
            bullet.moveVertically();
        }
        for (Enemy enemy : enemies) {
            enemy.moveVertically();
        }
        if (inputHandler.isLeftPressed()) {
            player.moveLeft();
        }
        if (inputHandler.isRightPressed()) {
            player.moveRight();
        }
        if (inputHandler.isSpacePressed()) {
            player.shoot();
        }
        gamePanel.setGameObjects(enemies, bullets, player);
        //TODO
    }

    private void setupEnemies() {
        int numberOfEnemies = settings.getEnemyCount();
        int rows = settings.getEnemyRows();
        // Przykładowe rozstawienie wrogów w siatce
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < numberOfEnemies / rows; col++) {
                Enemy enemy = new Enemy(col * 50, row * 50, 1); // Zakładając, że konstruktor Enemy przyjmuje pozycje x, y
                enemies.add(enemy);
            }
        }
    }
    public boolean isGameRunning() {
        return isRunning;
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public void removeEnemy(Enemy enemy) {
        enemies.remove(enemy);
    }

    public void removeBullet(Bullet bullet) {
        bullets.remove(bullet);
    }


    // Getters and Setters
    public List<Enemy> getEnemies() {
        return enemies;
    }

    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }

    public List<Bullet> getBullets() {
        return bullets;
    }

    public void setBullets(List<Bullet> bullets) {
        this.bullets = bullets;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void setGamePanel(GamePanel panel) {
        this.gamePanel = panel;
    }
    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }
}
