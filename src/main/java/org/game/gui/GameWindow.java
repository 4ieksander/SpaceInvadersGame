package org.game.gui;

import org.game.models.GameSettings;
import org.game.services.GameEngine;
import org.game.services.InputHandler;
import org.game.services.ScoreManager;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private final JFrame frame; // Główne okno gry
    private final GameEngine gameEngine; // Silnik gry
    private final GameSettings gameSettings; // Ustawienia gry
    private final int windowWidth = 850; // Szerokość okna
    private final int windowHeight = 700; // Wysokość okna
    private InputHandler inputHandler; // Obsługa wejścia

    public GameWindow(String playerName, Icon shipIcon) {
        frame = new JFrame("Space Invaders - " + playerName); // Ustawienie tytułu okna z nazwą gracza
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Operacja zamknięcia okna
        frame.setSize(windowWidth, windowHeight); // Ustawienie rozmiaru okna
        frame.setResizable(false); // Okno nie będzie można zmieniać rozmiaru
        frame.setLayout(new BorderLayout()); // Ustawienie menedżera rozkładu
        frame.setLocationRelativeTo(null); // Wyśrodkowanie okna

        inputHandler = new InputHandler(this); // Inicjalizacja obsługi wejścia
        gameSettings = new GameSettings(playerName); // Inicjalizacja ustawień gry
        GamePanel gamePanel = new GamePanel(inputHandler, windowWidth, windowHeight); // Stworzenie panelu gry

        gameEngine = new GameEngine(gameSettings, shipIcon); // Inicjalizacja silnika gry
        gameEngine.setGamePanel(gamePanel); // Ustawienie panelu gry
        gameEngine.setInputHandler(inputHandler); // Ustawienie obsługi wejścia
        gameEngine.initializeGame(); // Inicjalizacja gry

        JPanel northPanel = getjPanel(); // Utworzenie górnego panelu z przyciskami

        initializeUI(gamePanel, northPanel); // Inicjalizacja interfejsu użytkownika
        frame.setVisible(true); // Ustawienie okna jako widoczne
    }

    public GameWindow() {
        this("Unknown", new ImageIcon("src\\main\\resources\\SpaceInvader1.png")); // Konstruktor domyślny z domyślną ikoną i nazwą gracza
    }

    public static void startGame(String playerName, Icon shipIcon) {
        SwingUtilities.invokeLater(() -> new GameWindow(playerName, shipIcon)); // Metoda uruchamiająca grę z określonym graczem i ikoną statku
    }

    public boolean isGameRunning() {
        return this.gameEngine.isRunning(); // Sprawdzenie, czy gra jest w trakcie
    }

    public void startGame() {
        gameEngine.startGame(); // Uruchomienie gry przez silnik
    }

    private void pauseGame() {
        gameEngine.stopGame(); // Zatrzymanie gry
    }

    private void restartGame() {
        gameEngine.stopGame(); // Zatrzymanie gry
        gameEngine.initializeGame(); // Ponowna inicjalizacja gry
    }

    private void initializeUI(JPanel gamePanel, JPanel northPanel) {
        frame.setJMenuBar(createMenuBar()); // Ustawienie paska menu
        frame.add(gamePanel, BorderLayout.CENTER); // Dodanie panelu gry do centrum
        frame.add(createControlPanel(), BorderLayout.SOUTH); // Dodanie dolnego panelu sterowania
        frame.add(northPanel, BorderLayout.NORTH); // Dodanie górnego panelu
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar(); // Stworzenie paska menu
        JMenu gameMenu = new JMenu("Gra"); // Dodanie menu "Gra"
        JMenuItem startItem = new JMenuItem("Start");
        JMenuItem pauseItem = new JMenuItem("Pauza");
        JMenuItem restartItem = new JMenuItem("Restart");
        JMenuItem showTopScores = new JMenuItem("Zobacz najlepsze wyniki");
        JMenuItem rulesItem = new JMenuItem("Zasady gry");
        JMenuItem settingsItem = new JMenuItem("Ustawienia");
        JMenuItem exitItem = new JMenuItem("Wyjście");

        // Dodanie akcji do elementów menu
        startItem.addActionListener(e -> startGame());
        pauseItem.addActionListener(e -> pauseGame());
        restartItem.addActionListener(e -> restartGame());
        showTopScores.addActionListener(e -> ScoreManager.showTopScores());
        rulesItem.addActionListener(e -> showRules());
        settingsItem.addActionListener(e -> openSettingsDialog());
        exitItem.addActionListener(e -> System.exit(0));

        // Dodanie elementów do menu
        gameMenu.add(startItem);
        gameMenu.add(pauseItem);
        gameMenu.add(restartItem);
        gameMenu.addSeparator();
        gameMenu.add(showTopScores);
        gameMenu.add(rulesItem);
        gameMenu.add(settingsItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);

        return menuBar; // Zwrócenie skonfigurowanego paska menu
    }

    private void openSettingsDialog() {
        SettingsDialog settingsDialog = new SettingsDialog(this, gameSettings); // Otwarcie okna dialogowego ustawień
        settingsDialog.setVisible(true);
    }

    private void showRules() {
        JOptionPane.showMessageDialog(frame, "Zasady gry:\n" +
                "- Zabij jak najwięcej przeciwników\n" +
                "- Jeśli chcesz mieć możliwość zdobycia większej ilości punktów to zwiększ:\n" +
                "       - poziom trudności,\n" +
                "       - liczbę linii\n" +
                "       - włącz tryb hardcore\n" +
                "- Gdy przeciwnicy dojdą do ciebie, przegrywasz\n" +
                "- Gdy stracisz wszystkie życia, również przegrywasz\n"); // Wyświetlenie zasad gry
    }

    private JPanel getjPanel() {
        JButton restartButton = new JButton("Restart"); // Przycisk do restartowania gry
        JButton startButton = new JButton("               Start               "); // Przycisk do rozpoczęcia gry
        JButton pauseButton = new JButton("Pauza"); // Przycisk do pauzowania gry
        restartButton.addActionListener(e -> restartGame()); // Dodanie akcji restartowania gry
        startButton.addActionListener(e -> startGame()); // Dodanie akcji startowania gry
        pauseButton.addActionListener(e -> pauseGame()); // Dodanie akcji pauzowania gry
        startButton.setFocusable(false); // Wyłączenie możliwości skupienia na przycisku
        restartButton.setFocusable(false);
        pauseButton.setFocusable(false);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Panel z przyciskami w układzie środkowym
        northPanel.add(restartButton); // Dodanie przycisku restartowania do panelu
        northPanel.add(startButton); // Dodanie przycisku startowania do panelu
        northPanel.add(pauseButton); // Dodanie przycisku pauzy do panelu
        return northPanel; // Zwrócenie panelu z przyciskami
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(1, 3)); // Panel sterowania w układzie siatki 1x3
        JButton leftButton = new JButton("<"); // Przycisk do poruszania się w lewo
        JButton shootButton = new JButton("Shoot"); // Przycisk do strzelania
        JButton rightButton = new JButton(">"); // Przycisk do poruszania się w prawo

        leftButton.setFocusable(false); // Wyłączenie możliwości skupienia na przyciskach
        shootButton.setFocusable(false);
        rightButton.setFocusable(false);

        leftButton.addActionListener(e -> gameEngine.getPlayer().moveLeft()); // Dodanie akcji poruszania się w lewo
        shootButton.addActionListener(e -> gameEngine.addBullet(gameEngine.getPlayer().shoot())); // Dodanie akcji strzelania
        rightButton.addActionListener(e -> gameEngine.getPlayer().moveRight()); // Dodanie akcji poruszania się w prawo

        controlPanel.add(leftButton); // Dodanie przycisku do lewej
        controlPanel.add(shootButton); // Dodanie przycisku strzału
        controlPanel.add(rightButton); // Dodanie przycisku do prawej

        return controlPanel; // Zwrócenie panelu sterowania
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new); // Uruchomienie aplikacji w wątku dystrybucji zdarzeń
        // rozwiniecie co to i po co: https://4programmers.net/Forum/Java/140542-EventQueue.invokeLater_

    }
}