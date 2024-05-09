package org.game.services;

import org.game.gui.GamePanel;
import org.game.models.GameSettings;
import org.game.models.Player;
import org.game.models.Enemy;
import org.game.models.Bullet;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {
    private int score;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private boolean isRunning;
    private Thread gameThread;
    private GamePanel gamePanel;
    private InputHandler inputHandler;
    private GameSettings settings;
    private CollisionManager collisionManager;


    public GameEngine(GameSettings gameSettings) {
        this.settings = gameSettings;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.isRunning = false;
    }

    public void initializeGame() {
        score = 0;
        enemies.clear();
        bullets.clear();
        setupEnemies();
        player = new Player(380, 550, 3);
        this.gamePanel.setGameObjects(enemies, bullets, player);

        this.collisionManager = new CollisionManager(enemies, bullets, player);

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
        int fps = 30;
        int counterToDescentRate = settings.getEnemyDescentRate();
        int i = 0;
        while (isRunning) {
            updateGame();
            collisionManager.checkCollisions();
            SwingUtilities.invokeLater(() -> gamePanel.repaint());
            try {
                Thread.sleep(1000 / fps); // 30FPS
                i += 1;
                if (i == counterToDescentRate){
                    i = 0;
                    for (Enemy enemy : enemies){
                        enemy.moveVertically();
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void updateGame() {
        for (Bullet bullet : bullets) {
            bullet.moveVertically();
        }
        int enemiesNotAlive = 0;
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) enemiesNotAlive += 1;
            if (Enemy.isMovingRight()){
                enemy.moveRight();
            }
            else{
                enemy.moveLeft();
            }
            if (enemy.getXPosition() > GamePanel.getGameWidth() - enemy.getWidth() || enemy.getXPosition() < 0) {
                Enemy.reverseDirection();
            }
        }
        if (enemiesNotAlive != score){
            score = enemiesNotAlive;
            gamePanel.updateScore(score);
        }
        if (inputHandler.isLeftPressed()) {
            player.moveLeft();
        }
        if (inputHandler.isRightPressed()) {
            player.moveRight();
        }
        if (inputHandler.isSpacePressed()) {
            addBullet(player.shoot());
        }
        gamePanel.setGameObjects(enemies, bullets, player);
        //TODO
    }

    private void setupEnemies() {
        int numberOfEnemiesPerLine = settings.getEnemyCountPerLine();
        int distanceBetweenEnemies = settings.getDistanceBetweenEnemies();
        int rows = settings.getEnemyRows();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < numberOfEnemiesPerLine; col++) {
                if (settings.isHardcoreMode()){
                    Enemy enemy = new Enemy(col * distanceBetweenEnemies, 40 + row * distanceBetweenEnemies, 1, 10, 10);
                    enemies.add(enemy);
                }
                else{
                    Enemy enemy = new Enemy(col * distanceBetweenEnemies, 40+ row * 40, 1);
                    enemies.add(enemy);

                }
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
