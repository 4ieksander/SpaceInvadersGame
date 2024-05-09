package org.game.gui;

import org.game.services.GameEngine;
import org.game.services.RenderService;
import org.game.models.Player;
import org.game.models.Enemy;
import org.game.models.Bullet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class GameWindow {
    private JFrame frame;
    private GameEngine gameEngine;
    private RenderService renderService;

    public GameWindow() {
        frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 700);
        frame.setResizable(false); // Uniemożliwia zmianę rozmiaru okna-
        frame.setLayout(new BorderLayout());


        Player player = new Player(400, 500, 3);

        ArrayList<Enemy> enemies = new ArrayList<>();
        ArrayList<Bullet> bullets = new ArrayList<>();

        Enemy enemy = new Enemy(50,50,100); //test
        enemies.add(enemy);

        gameEngine = new GameEngine(player, enemies, bullets);
        JPanel gamePanel = createGamePanel();
        gameEngine.setGamePanel(gamePanel);
        renderService = new RenderService(gamePanel);

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
