package org.game.services;

import org.game.gui.GamePanel;
import org.game.models.GameSettings;
import org.game.models.Player;
import org.game.models.Enemy;
import org.game.models.Bullet;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.*;
import java.util.Random;

public class GameEngine {
    private int score;
    private List<Enemy> enemies;
    private List<Bullet> bullets;
    private Player player;
    private Thread gameThread;
    private GamePanel gamePanel;
    private InputHandler inputHandler;
    private GameSettings settings;
    private CollisionManager collisionManager;
    private Timer shootingTimer;
    private Icon shipIcon;
    private int shootingInterval = 2000;
    private boolean gameOver = false;
    private int playerLives;
    private boolean isRunning;


    public GameEngine(GameSettings gameSettings, Icon shipIcon) {
        this.settings = gameSettings;
        this.shipIcon = shipIcon;
        this.enemies = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.isRunning = false;
    }

    public void initializeGame() {
        score = 0;
        enemies.clear();
        bullets.clear();
        setupEnemies();
        player = new Player(380, 510, 3);
        playerLives = 3;
        this.player.setShipIcon(shipIcon);
        this.gamePanel.setGameObjects(enemies, bullets, player);
        this.collisionManager = new CollisionManager(enemies, bullets, player);
        initializeShooting();
    }


    public void startGame() {
        System.out.println(enemies);
        gamePanel.hideEndGameLabel();
        if (gameThread == null || !gameThread.isAlive()) {
            isRunning = true;
            gameThread = new Thread(this::gameLoop);
            gameThread.start();
        }
    }

    public void stopGame() {
        isRunning = false;
        if (gameThread != null) {
            gameThread.interrupt();
        }
    }

    public void checkForHighScore() {
        int currentScore = this.score;
        String playerName = settings.getPlayerName();
        ScoreManager scoreManager = new ScoreManager();
        boolean isInTopTen = scoreManager.addScore(playerName, currentScore);

        SwingUtilities.invokeLater(() -> {
            if (isInTopTen) {
                JOptionPane.showMessageDialog(null, "Gratulacje, " + playerName + "! Twój wynik " + currentScore + " znajduje się w Top 10!");
            } else {
                JOptionPane.showMessageDialog(null, playerName + ", twój wynik to " + currentScore + ". Niestety, nie udało się znaleźć miejsca w Top 10.");
            }
        });
    }

    private void gameLoop() {
        int fps = 60;
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
                        if (enemy.getYPosition() >= gamePanel.getGameHeight() - 90 && enemy.isAlive()) {
                            gameOver = true;
                            break;
                        }
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void updateGame() {
        if (inputHandler.isEnterPressed()) {
            if (!isRunning) {
                startGame();
            } else {
                stopGame();
            }
        }
        updateBullets();
        moveEnemyAndAddScore();
        catchKeyboard();
        checkPlayerLives();
        gamePanel.setGameObjects(enemies, bullets, player);
        checkGameOver();
    }

    private void catchKeyboard(){
        if (inputHandler.isLeftPressed()) {
            player.moveLeft();
        }
        if (inputHandler.isRightPressed()) {
            player.moveRight();
        }
        if (inputHandler.isSpacePressed()) {
            addBullet(player.shoot());
        }
    }

    private void moveEnemyAndAddScore(){
        int enemiesNotAlive = 0;
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) enemiesNotAlive += 1;
            if (Enemy.isMovingRight()){
                enemy.moveRight();
            }
            else{
                enemy.moveLeft();
            }
            if (enemy.getXPosition() > gamePanel.getGameWidth() - 2*enemy.getWidth() || enemy.getXPosition() < 5) {
                Enemy.reverseDirection();
            }
        }
        if (enemiesNotAlive != score){
            score = enemiesNotAlive;
            gamePanel.updateScore(score);
        }
    }

    private void updateBullets() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : getBullets()) {
            bullet.moveVertically();
            if (bullet.isOffScreen(gamePanel.getGameHeight())) {
                bulletsToRemove.add(bullet);
            }
            else if (!bullet.isAlive())
                bulletsToRemove.add(bullet);
        }
        getBullets().removeAll(bulletsToRemove);
    }


    private void setupEnemies() {
        int numberOfEnemiesPerLine = settings.getEnemyCountPerLine();
        int distanceBetweenEnemies = settings.getDistanceBetweenEnemies();
        int rows = settings.getEnemyRows();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < numberOfEnemiesPerLine; col++) {
                if (settings.isHardcoreMode()){
                    Enemy enemy = new Enemy(col * distanceBetweenEnemies + 5, 40 + row * distanceBetweenEnemies, 1, 10, 10);
                    enemies.add(enemy);
                }
                else{
                    Enemy enemy = new Enemy(col * distanceBetweenEnemies + 5, 40+ row * 40, 1);
                    enemies.add(enemy);

                }
            }
        }
    }

    private void initializeShooting() {
        shootingTimer = new Timer(shootingInterval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enemyShoot();
            }
        });
        shootingTimer.start();
    }

    private void enemyShoot() {
        if (enemies.isEmpty()) return;
        Random rand = new Random();
        int shooterIndex = rand.nextInt(enemies.size());
        Enemy shooter = enemies.get(shooterIndex);

        if (shooter.isAlive()) {
            bullets.add(shooter.shoot());
        }
        adjustShootingInterval();
    }
    private void adjustShootingInterval() {
        if (shootingInterval > 500) {
            shootingInterval -= 100;
            shootingTimer.setDelay(shootingInterval);
        }
    }

    private void checkPlayerLives(){
        if (player.getHealth() < playerLives){
            playerLives -= 1;
            gamePanel.updateLives(playerLives);
        }
        if (!player.isAlive()){
            gameOver = true;
        }
    }

    private void checkGameOver() {
        if (areAllEnemiesDead()){
            gamePanel.displayEndGameLabel();
            checkForHighScore();
        };
        if (gameOver) {
            checkForHighScore();
            stopGame();
            System.out.println("Game Over");
            SwingUtilities.invokeLater(() -> {
                gamePanel.displayGameOver();
            });
            initializeGame();

            gameOver = false;
        }
    }

    private boolean areAllEnemiesDead() {
        return enemies.stream().noneMatch(Enemy::isAlive);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    // Getters and Setters
    public List<Bullet> getBullets() {
        return bullets;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setGamePanel(GamePanel panel) {
        this.gamePanel = panel;
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler;
    }

}
