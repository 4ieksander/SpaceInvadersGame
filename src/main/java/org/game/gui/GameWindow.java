package org.game.gui;
import javax.swing.*;
import java.awt.*;

public class GameWindow {
    private JFrame frame;

    public GameWindow() {
        frame = new JFrame("Space Invaders");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());
        initializeUI();
        frame.setVisible(true);
    }

    private void initializeUI() {
        frame.add(createGamePanel(), BorderLayout.CENTER);
        frame.add(createControlPanel(), BorderLayout.SOUTH);
        frame.add(createStatusPanel(), BorderLayout.NORTH);
    }

    private JPanel createGamePanel() {
        JPanel gamePanel = new JPanel();
        gamePanel.setBackground(Color.BLACK);
        return gamePanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(1, 3));
        JButton leftButton = new JButton("<");
        JButton shootButton = new JButton("Shoot");
        JButton rightButton = new JButton(">");

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameWindow::new);
    }
}
