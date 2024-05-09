package org.game.gui;

import org.game.models.GameSettings;
import org.game.services.GameEngine;
import org.game.services.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;


public class GameWindow extends JFrame {
    private JFrame frame;
    private GameEngine gameEngine;
    private InputHandler inputHandler;
    private GameSettings gameSettings;


    public GameWindow() {
        frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());


        inputHandler = new InputHandler();
        gameSettings = new GameSettings();

        JButton startButton = new JButton("Start");
        JButton pauseButton = new JButton("Pauza");
        startButton.addActionListener(e -> startGame());
        pauseButton.addActionListener(e -> pauseGame());
        startButton.setFocusable(false);
        pauseButton.setFocusable(false);

        JPanel northPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        northPanel.add(startButton);
        northPanel.add(pauseButton);

        GamePanel gamePanel = new GamePanel(inputHandler);

        gameEngine = new GameEngine(gameSettings);
        gameEngine.setGamePanel(gamePanel);
        gameEngine.setInputHandler(inputHandler);
        gameEngine.initializeGame();

        initializeUI(gamePanel, northPanel);
        frame.setVisible(true);
    }


    private void openSettingsDialog() {
        SettingsDialog settingsDialog = new SettingsDialog(this, gameSettings);
        settingsDialog.setVisible(true);
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
        JMenuItem rulesItem = new JMenuItem("Zasady gry");
        JMenuItem settingsItem = new JMenuItem("Ustawienia");
        JMenuItem exitItem = new JMenuItem("Wyjście");

        startItem.addActionListener(e -> startGame());
        pauseItem.addActionListener(e -> pauseGame());
        restartItem.addActionListener(e -> restartGame());
        rulesItem.addActionListener(e -> showRules());
        settingsItem.addActionListener(e -> openSettingsDialog());
        exitItem.addActionListener(e -> System.exit(0));

        gameMenu.add(startItem);
        gameMenu.add(pauseItem);
        gameMenu.add(restartItem);
        gameMenu.addSeparator();
        gameMenu.add(rulesItem);
        gameMenu.add(settingsItem);
        gameMenu.addSeparator();
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);


        return menuBar;
    }


    private void showRules() {
        JOptionPane.showMessageDialog(frame, "Zasady gry.");
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

    private void startGame() {
        gameEngine.startGame();
    }

    private void pauseGame() {
        gameEngine.stopGame();
    }

    private void restartGame () {
        gameEngine.stopGame();
        gameEngine.initializeGame();
        gameEngine.startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
