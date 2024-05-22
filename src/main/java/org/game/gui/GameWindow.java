package org.game.gui;

import org.game.models.GameSettings;
import org.game.services.GameEngine;
import org.game.services.InputHandler;
import org.game.services.ScoreManager;

import javax.swing.*;
import java.awt.*;

public class GameWindow extends JFrame {
    private final JFrame frame;
    private final GameEngine gameEngine;
    private final GameSettings gameSettings;
    private final int windowWidth = 850;
    private final int windowHeight = 700;
    private InputHandler inputHandler;


    public GameWindow(String playerName, Icon shipIcon) {
        frame = new JFrame("Space Invaders - " + playerName);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        inputHandler = new InputHandler(this);
        gameSettings = new GameSettings(playerName);
        GamePanel gamePanel = new GamePanel(inputHandler, windowWidth, windowHeight);

        gameEngine =  new GameEngine(gameSettings, shipIcon);
        gameEngine.setGamePanel(gamePanel);
        gameEngine.setInputHandler(inputHandler);
        gameEngine.initializeGame();

        JPanel northPanel = getjPanel();

        initializeUI(gamePanel, northPanel);
        frame.setVisible(true);
    }


    public GameWindow() {
        this("Unknown", new ImageIcon("src\\main\\resources\\SpaceInvader1.png"));
    }


    public static void startGame(String playerName, Icon shipIcon) {
        SwingUtilities.invokeLater(() -> new GameWindow(playerName, shipIcon));
    }

    public boolean isGameRunning(){
        return this.gameEngine.isRunning();
    }

    public void startGame() {
        gameEngine.startGame();
    }

    private void pauseGame() {
        gameEngine.stopGame();
    }

    private void restartGame () {
        gameEngine.stopGame();
        gameEngine.initializeGame();
    }




    private void initializeUI(JPanel gamePanel, JPanel northPanel) {
        frame.setJMenuBar(createMenuBar());
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(createControlPanel(), BorderLayout.SOUTH);
        frame.add(northPanel, BorderLayout.NORTH);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Gra");
        JMenuItem startItem = new JMenuItem("Start");
        JMenuItem pauseItem = new JMenuItem("Pauza");
        JMenuItem restartItem = new JMenuItem("Restart");
        JMenuItem showTopScores = new JMenuItem("Zobacz najlepsze wyniki");
        JMenuItem rulesItem = new JMenuItem("Zasady gry");
        JMenuItem settingsItem = new JMenuItem("Ustawienia");
        JMenuItem exitItem = new JMenuItem("Wyjście");

        startItem.addActionListener(e -> startGame());
        pauseItem.addActionListener(e -> pauseGame());
        restartItem.addActionListener(e -> restartGame());
        showTopScores.addActionListener(e -> ScoreManager.showTopScores());
        rulesItem.addActionListener(e -> showRules());
        settingsItem.addActionListener(e -> openSettingsDialog());
        exitItem.addActionListener(e -> System.exit(0));

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

        return menuBar;
    }

    private void openSettingsDialog() {
        SettingsDialog settingsDialog = new SettingsDialog(this, gameSettings);
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
                "- Gdy stracisz wszystkie życia, również przegrywasz\n");
    }

    private JPanel getjPanel() {
        JButton restartButton = new JButton("Restart");
        JButton startButton = new JButton("               Start               ");
        JButton pauseButton = new JButton("Pauza");
        restartButton.addActionListener(e -> restartGame());
        startButton.addActionListener(e -> startGame());
        pauseButton.addActionListener(e -> pauseGame());
        startButton.setFocusable(false);
        restartButton.setFocusable(false);
        pauseButton.setFocusable(false);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(restartButton);
        northPanel.add(startButton);
        northPanel.add(pauseButton);
        return northPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(1, 3));
        JButton leftButton = new JButton("<");
        JButton shootButton = new JButton("Shoot");
        JButton rightButton = new JButton(">");

        leftButton.setFocusable(false);
        shootButton.setFocusable(false);
        rightButton.setFocusable(false);

        leftButton.addActionListener(e -> gameEngine.getPlayer().moveLeft());
        shootButton.addActionListener(e -> gameEngine.addBullet(gameEngine.getPlayer().shoot()));
        rightButton.addActionListener(e -> gameEngine.getPlayer().moveRight());

        controlPanel.add(leftButton);
        controlPanel.add(shootButton);
        controlPanel.add(rightButton);

        return controlPanel;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
