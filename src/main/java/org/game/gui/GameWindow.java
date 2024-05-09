package org.game.gui;

import org.game.models.GameSettings;
import org.game.services.GameEngine;
import org.game.services.RenderService;
import org.game.models.Player;
import org.game.models.Enemy;
import org.game.models.Bullet;
import org.game.services.InputHandler;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Set;


public class GameWindow {
    private JFrame frame;
    private GameEngine gameEngine;
    private RenderService renderService;
    private InputHandler inputHandler;

    public GameWindow() {
        frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        inputHandler = new InputHandler();

        JPanel gamePanel = createGamePanel();


        gameEngine = new GameEngine(new GameSettings());
        gameEngine.setGamePanel(gamePanel);
        gameEngine.setInputHandler(inputHandler);
        gameEngine.initializeGame();
        Enemy enemy = new Enemy(50,50,100); //test
//        gameEngine.addEnemy(enemy);

        renderService = new RenderService();

        initializeUI(gamePanel);
        frame.setVisible(true);
    }

    private void initializeUI(JPanel gamePanel) {
        frame.setJMenuBar(createMenuBar());
        frame.add(gamePanel, BorderLayout.CENTER);
        frame.add(createControlPanel(), BorderLayout.SOUTH);
        frame.add(createStatusPanel(), BorderLayout.NORTH);
    }

    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                renderService.drawPlayer(g, gameEngine.getPlayer());
                renderService.drawEnemies(g, gameEngine.getEnemies());
                renderService.drawBullets(g, gameEngine.getBullets());
            }
        };
        gamePanel.addKeyListener(inputHandler);
        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();
        gamePanel.setBackground(Color.BLACK);
        return gamePanel;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Gra");
        JMenuItem startItem = new JMenuItem("Start");
        JMenuItem pauseItem = new JMenuItem("Pauza");
        JMenuItem restartItem = new JMenuItem("Restart");
        JMenuItem rulesItem = new JMenuItem("Zasady gry");

        startItem.addActionListener(e -> startGame());
        pauseItem.addActionListener(e -> pauseGame());
        restartItem.addActionListener(e -> restartGame());
        rulesItem.addActionListener(e -> showRules());

        gameMenu.add(startItem);
        gameMenu.add(pauseItem);
        gameMenu.add(restartItem);
        gameMenu.addSeparator();
        gameMenu.add(rulesItem);

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

        leftButton.addActionListener(e -> gameEngine.getPlayer().moveLeft());
        shootButton.addActionListener(e -> gameEngine.getPlayer().shoot());
        rightButton.addActionListener(e -> gameEngine.getPlayer().moveRight());

        controlPanel.add(leftButton);
        controlPanel.add(shootButton);
        controlPanel.add(rightButton);

        return controlPanel;
    }

    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Score: 0");
        statusPanel.add(statusLabel);

        return statusPanel;
    }

    private void startGame() {
        gameEngine.startGame();
    }

    private void pauseGame() {
        gameEngine.stopGame();
    }

    private void restartGame() {
        gameEngine.stopGame();
        gameEngine.startGame();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
