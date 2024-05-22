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
    private int score; // Punkty zdobyte przez gracza
    private List<Enemy> enemies; // Lista wrogów w grze
    private List<Bullet> bullets; // Lista pocisków w grze
    private Player player; // Obiekt gracza
    private Thread gameThread; // Wątek używany do głównej pętli gry
    private GamePanel gamePanel; // Panel GUI, na którym odbywa się gra
    private InputHandler inputHandler; // Zarządca obsługi wejścia z klawiatury
    private GameSettings settings; // Ustawienia gry
    private CollisionManager collisionManager; // Zarządca kolizji
    private Timer shootingTimer; // Timer do zarządzania strzelaniem
    private Icon shipIcon; // Ikona statku gracza
    private int shootingInterval = 2000; // Interwał strzałów początkowy w milisekundach
    private boolean gameOver = false; // Flag indicating whether the game is over
    private int playerLives; // Ilość żyć gracza
    private boolean isRunning; // Flaga określająca, czy gra jest w trakcie

    public GameEngine(GameSettings gameSettings, Icon shipIcon) {
        this.settings = gameSettings; // Przypisanie ustawień gry
        this.shipIcon = shipIcon; // Przypisanie ikony statku
        this.enemies = new ArrayList<>(); // Inicjalizacja listy wrogów
        this.bullets = new ArrayList<>(); // Inicjalizacja listy pocisków
        this.isRunning = false; // Ustawienie flagi działania gry na false
    }

    public void initializeGame() {
        score = 0; // Reset punktów
        enemies.clear(); // Wyczyszczenie listy wrogów
        bullets.clear(); // Wyczyszczenie listy pocisków
        setupEnemies(); // Inicjalizacja wrogów
        player = new Player(380, 510, 3); // Inicjalizacja gracza
        playerLives = 3; // Ustawienie ilości żyć gracza
        this.player.setShipIcon(shipIcon); // Ustawienie ikony statku gracza
        this.gamePanel.setGameObjects(enemies, bullets, player); // Przekazanie obiektów do panelu gry
        this.collisionManager = new CollisionManager(enemies, bullets, player); // Inicjalizacja zarządcy kolizji
        initializeShooting(); // Inicjalizacja strzelania
    }

    public void startGame() {
        System.out.println(enemies); // Wyświetlenie listy wrogów (debugging)
        gamePanel.hideEndGameLabel(); // Ukrycie etykiety końca gry
        if (gameThread == null || !gameThread.isAlive()) {
            isRunning = true; // Ustawienie flagi działania gry na true
            gameThread = new Thread(this::gameLoop); // Inicjalizacja wątku gry
            gameThread.start(); // Start wątku
        }
    }

    public void stopGame() {
        isRunning = false; // Ustawienie flagi działania gry na false
        if (gameThread != null) {
            gameThread.interrupt(); // Przerwanie wątku
        }
    }

    public void checkForHighScore() {
        int currentScore = this.score; // Aktualny wynik gracza
        String playerName = settings.getPlayerName(); // Pobranie nazwy gracza z ustawień
        ScoreManager scoreManager = new ScoreManager(); // Utworzenie managera wyników
        boolean isInTopTen = scoreManager.addScore(playerName, currentScore); // Dodanie wyniku do listy i sprawdzenie, czy jest wśród 10 najlepszych

        SwingUtilities.invokeLater(() -> {
            if (isInTopTen) {
                // Wyświetlenie komunikatu o wysokim wyniku
                JOptionPane.showMessageDialog(null, "Gratulacje, " + playerName + "! Twój wynik " + currentScore + " znajduje się w Top 10!");
            } else {
                // Komunikat, gdy wynik nie jest w top 10
                JOptionPane.showMessageDialog(null, playerName + ", twój wynik to " + currentScore + ". Niestety, nie udało się znaleźć miejsca w Top 10.");
            }
        });
    }

    private void gameLoop() {
        int fps = 60; // Ustawienie liczby klatek na sekundę
        int counterToDescentRate = settings.getEnemyDescentRate(); // Częstotliwość opadania wrogów
        int i = 0;
        while (isRunning) {
            updateGame(); // Aktualizacja stanu gry
            collisionManager.checkCollisions(); // Sprawdzenie kolizji
            SwingUtilities.invokeLater(() -> gamePanel.repaint()); // Odświeżenie panelu gry
            try {
                Thread.sleep(1000 / fps); // Kontrola szybkości gry
                i += 1;
                if (i == counterToDescentRate) {
                    i = 0;
                    for (Enemy enemy : enemies) {
                        enemy.moveVertically(); // Ruch wrogów w dół
                        // Sprawdzenie, czy wrogowie doszli do dolnej krawędzi ekranu
                        if (enemy.getYPosition() >= gamePanel.getGameHeight() - 90 && enemy.isAlive()) {
                            gameOver = true;
                            break;
                        }
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Obsługa przerwania wątku
            }
        }
    }

    private void updateGame() {
        if (inputHandler.isEnterPressed()) {
            if (!isRunning) {
                startGame(); // Rozpoczęcie gry, jeśli nie jest uruchomiona
            } else {
                stopGame(); // Zatrzymanie gry, jeśli jest uruchomiona
            }
        }
        updateBullets(); // Aktualizacja stanu pocisków
        moveEnemyAndAddScore(); // Aktualizacja ruchu wrogów i punktacji
        catchKeyboard(); // Obsługa klawiatury
        checkPlayerLives(); // Sprawdzenie ilości żyć gracza
        gamePanel.setGameObjects(enemies, bullets, player); // Aktualizacja obiektów na panelu gry
        checkGameOver(); // Sprawdzenie warunków zakończenia gry
    }

    private void catchKeyboard() {
        if (inputHandler.isLeftPressed()) {
            player.moveLeft(); // Ruch gracza w lewo
        }
        if (inputHandler.isRightPressed()) {
            player.moveRight(); // Ruch gracza w prawo
        }
        if (inputHandler.isSpacePressed()) {
            addBullet(player.shoot()); // Wystrzelenie pocisku przez gracza
        }
    }

    private void moveEnemyAndAddScore() {
        int enemiesNotAlive = 0;
        for (Enemy enemy : enemies) {
            if (!enemy.isAlive()) enemiesNotAlive += 1; // Liczenie martwych wrogów
            if (Enemy.isMovingRight()){
                enemy.moveRight();
            }
            else{
                enemy.moveLeft();
            } // Ruch wrogów w prawo lub w lewo
            // Sprawdzenie, czy wrogowie osiągnęli krawędzie ekranu
            if (enemy.getXPosition() > gamePanel.getGameWidth() - 2 * enemy.getWidth() || enemy.getXPosition() < 5) {
                Enemy.reverseDirection(); // Zmiana kierunku ruchu wrogów
            }
        }
        if (enemiesNotAlive != score) {
            score = enemiesNotAlive; // Aktualizacja wyniku
            gamePanel.updateScore(score); // Aktualizacja wyświetlanego wyniku
        }
    }

    private void updateBullets() {
        List<Bullet> bulletsToRemove = new ArrayList<>();
        for (Bullet bullet : getBullets()) {
            bullet.moveVertically(); // Ruch pocisków w pionie
            // Usuwanie pocisków, które wyszły poza ekran lub zostały zniszczone
            if (bullet.isOffScreen(gamePanel.getGameHeight()) || !bullet.isAlive()) {
                bulletsToRemove.add(bullet);
            }
        }
        getBullets().removeAll(bulletsToRemove); // Usuwanie nieaktywnych pocisków z listy
    }

    private void setupEnemies() {
        int numberOfEnemiesPerLine = settings.getEnemyCountPerLine(); // Liczba wrogów w linii
        int distanceBetweenEnemies = settings.getDistanceBetweenEnemies(); // Odległość między wrogami
        int rows = settings.getEnemyRows(); // Liczba rzędów wrogów
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < numberOfEnemiesPerLine; col++) {
                // Tworzenie wrogów z uwzględnieniem trybu trudnego
                Enemy enemy = new Enemy(col * distanceBetweenEnemies + 5, 40 + row * (settings.isHardcoreMode() ? distanceBetweenEnemies : 40), 1);
                enemies.add(enemy);
            }
        }
    }

    private void initializeShooting() {
        shootingTimer = new Timer(shootingInterval, (e) -> {
            enemyShoot(); // Wykonanie akcji strzelania wroga
        });
        shootingTimer.start(); // Start timera strzelania
    }

    private void enemyShoot() {
        if (enemies.isEmpty()) return; // Przerwanie jeśli lista wrogów jest pusta
        Random rand = new Random(); // Generator liczb losowych
        int shooterIndex = rand.nextInt(enemies.size()); // Losowanie indeksu strzelającego wroga
        Enemy shooter = enemies.get(shooterIndex); // Pobranie wroga

        if (shooter.isAlive()) {
            bullets.add(shooter.shoot()); // Dodanie pocisku wroga do listy
        }
        adjustShootingInterval(); // Dostosowanie interwału strzałów
    }

    private void adjustShootingInterval() {
        if (shootingInterval > 500) {
            shootingInterval -= 100; // Zmniejszenie interwału strzałów
            shootingTimer.setDelay(shootingInterval); // Ustawienie nowego interwału w timerze
        }
    }

    private void checkPlayerLives(){
        if (player.getHealth() < playerLives){
            playerLives -= 1; // Zmniejszenie liczby żyć gracza
            gamePanel.updateLives(playerLives); // Aktualizacja wyświetlanych żyć
        }
        if (!player.isAlive()){
            gameOver = true; // Ustawienie flagi końca gry
        }
    }

    private void checkGameOver() {
        if (areAllEnemiesDead()){
            gamePanel.displayEndGameLabel(); // Wyświetlenie etykiety końca gry
            checkForHighScore(); // Sprawdzenie nowego rekordu
        };
        if (gameOver) {
            checkForHighScore(); // Sprawdzenie nowego rekordu
            stopGame(); // Zatrzymanie gry
            System.out.println("Game Over"); // Komunikat końca gry
            SwingUtilities.invokeLater(() -> {
                gamePanel.displayGameOver(); // Wyświetlenie informacji o końcu gry
            });
            initializeGame(); // Ponowne zainicjowanie gry

            gameOver = false; // Reset flagi końca gry
        }
    }

    private boolean areAllEnemiesDead() {
        return enemies.stream().noneMatch(Enemy::isAlive); // Sprawdzenie, czy wszyscy wrogowie są martwi
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet); // Dodanie pocisku do listy
    }

    // Getters and Setters
    public List<Bullet> getBullets() {
        return bullets; // Zwrócenie listy pocisków
    }

    public Player getPlayer() {
        return player; // Zwrócenie gracza
    }

    public boolean isRunning() {
        return isRunning; // Zwrócenie stanu gry
    }

    public void setGamePanel(GamePanel panel) {
        this.gamePanel = panel; // Ustawienie panelu gry
    }

    public void setInputHandler(InputHandler inputHandler) {
        this.inputHandler = inputHandler; // Ustawienie handlera wejścia
    }
}
