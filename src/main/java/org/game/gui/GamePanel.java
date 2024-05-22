package org.game.gui;

import org.game.models.Bullet;
import org.game.models.Enemy;
import org.game.models.Player;
import org.game.services.InputHandler;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    // Swing -> JPanel -  służący do grupowania różnych komponentów interfejsu użytkownika (UI).
    // Jest to lekki kontener, który może być używany do organizowania komponentów, takich jak przyciski, etykiety, pola tekstowe
    // i inne panele w jednym miejscu w ramach okna aplikacji lub innego kontenera.
    private final JLabel scoreLabel; // Etykieta wyświetlająca wynik
    private final JLabel livesLabel; // Etykieta wyświetlająca ilość żyć
    private final JLabel endGameLabel; // Etykieta wyświetlająca komunikat końcowy gry
    private final int gameWidth; // Szerokość panelu gry
    private final int gameHeight; // Wysokość panelu gry
    private List<Enemy> enemies; // Lista wrogów w grze
    private List<Bullet> bullets; // Lista pocisków w grze
    private Player player; // Gracz


    public GamePanel(InputHandler inputHandler, int gameWidth, int gameHeight){
        this.gameWidth = gameWidth; // Inicjalizacja szerokości gry
        this.gameHeight = gameHeight; // Inicjalizacja wysokości gry
        setPreferredSize(new Dimension(gameWidth, gameHeight)); // Ustawienie preferowanych wymiarów
        addKeyListener(inputHandler); // Dodanie obsługi klawiatury
        setFocusable(true); // Ustawienie panelu jako skupiającego uwagę
        requestFocusInWindow(); // Żądanie fokusu na oknie
        setBackground(Color.WHITE); // Ustawienie koloru tła
        setLayout(new BorderLayout()); // Ustawienie menedżera rozkładu

        scoreLabel = new JLabel("Score: 0", SwingConstants.LEFT); // Inicjalizacja etykiety wyniku
        livesLabel = new JLabel("Lives: 3", SwingConstants.RIGHT); // Inicjalizacja etykiety żyć

        scoreLabel.setOpaque(true); // Ustawienie tła etykiet jako nieprzeźroczyste
        scoreLabel.setBackground(Color.BLACK); // Kolor tła etykiety
        scoreLabel.setForeground(Color.WHITE); // Kolor tekstu etykiety
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Czcionka etykiety
        livesLabel.setOpaque(true);
        livesLabel.setBackground(Color.BLACK);
        livesLabel.setForeground(Color.WHITE);
        livesLabel.setFont(new Font("Arial", Font.BOLD, 20));

        add(scoreLabel, BorderLayout.SOUTH); // Dodanie etykiety wyniku na dole
        add(livesLabel, BorderLayout.NORTH); // Dodanie etykiety żyć na górze

        endGameLabel = new JLabel("Gratulacje! " +
                "Zwiększ poziom trudności i spróbuj jeszcze raz!"
                + SwingConstants.CENTER); // Inicjalizacja etykiety końcowej
        endGameLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Czcionka etykiety końcowej
        endGameLabel.setForeground(Color.RED); // Kolor tekstu etykiety końcowej
        endGameLabel.setVisible(false); // Ustawienie etykiety końcowej jako niewidocznej
        add(endGameLabel);
    }

    public int getGameWidth() {
        return gameWidth; // Getter szerokości gry
    }
    public int getGameHeight() {
        return gameHeight; // Getter wysokości gry
    }

    public void displayGameOver() {
        JOptionPane.showMessageDialog(this, "Game Over!", "Koniec Gry", JOptionPane.ERROR_MESSAGE); // Wyświetlenie okna dialogowego "Koniec Gry"
    }

    public void displayEndGameLabel(){
        endGameLabel.setVisible(true); // Wyświetlenie etykiety końcowej
    }

    public void hideEndGameLabel(){
        endGameLabel.setVisible(false); // Ukrycie etykiety końcowej
    }

    public void updateScore(int score) {
        scoreLabel.setText("Score: " + score); // Aktualizacja wyniku na etykiecie
    }

    public void updateLives(int lives) {
        livesLabel.setText("Lives: " + lives); // Aktualizacja liczby żyć na etykiecie
    }

    public void setGameObjects(List<Enemy> enemies, List<Bullet> bullets, Player player) {
        this.enemies = enemies; // Ustawienie listy wrogów
        this.bullets = bullets; // Ustawienie listy pocisków
        this.player = player; // Ustawienie gracza
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawPlayer(g, player); // Rysowanie gracza
        drawEnemies(g, enemies); // Rysowanie wrogów
        drawBullets(g, bullets); // Rysowanie pocisków
    }

    private void drawPlayer(Graphics g, Player player) {
        if (player != null && player.isAlive()) {
            if (player.getShipIcon() != null){
                player.getShipIcon().paintIcon(this, g, player.getXPosition(), player.getYPosition()); // Rysowanie ikony statku
            }
            else
            {
                g.setColor(Color.BLUE); // Kolor statku, gdy brak ikony
                g.fillRect(player.getXPosition(), player.getYPosition(), player.getWidth(), player.getHeight());
            // Metoda fillRect w klasie Graphics w Java Swing jest używana do rysowania wypełnionego prostokąta na komponentach GUI.
            }
        }
    }

    private void drawEnemies(Graphics g, List<Enemy> enemies) {
        g.setColor(Color.RED); // Kolor wrogów
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                g.fillRect(enemy.getXPosition(), enemy.getYPosition(), enemy.getWidth(), enemy.getHeight()); // Rysowanie żywych wrogów
            }
        }
    }

    private void drawBullets(Graphics g, List<Bullet> bullets) {
        g.setColor(Color.YELLOW); // Kolor pocisków
        for (Bullet bullet : bullets) {
            if (bullet.isAlive()) {
                g.fillRect(bullet.getXPosition(), bullet.getYPosition(), Bullet.getWidth(), Bullet.getHeight()); // Rysowanie aktywnych pocisków
            }
        }
    }
}
