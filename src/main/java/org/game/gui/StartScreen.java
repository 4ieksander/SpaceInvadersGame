package org.game.gui;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class StartScreen extends JFrame {
    private JTextField playerNickField;
    private JButton startButton;
    private JButton scoresButton;

    public StartScreen() {
        setTitle("Ekran startowy");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        String currentDirectory = System.getProperty("user.dir");
        System.out.println("Aktualna ścieżka: " + currentDirectory);
        String imagePath = currentDirectory + "\\src\\main\\resources\\BackgroundSpaceInvaders.png";
        System.out.println(imagePath);
        JLabel background = new JLabel(new ImageIcon(imagePath));
        background.setLayout(new BorderLayout());
        add(background);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        playerNickField = new JTextField(20);
        lowerPanel.add(playerNickField);

        startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        lowerPanel.add(startButton);

        scoresButton = new JButton("Top 10 Scores");
        scoresButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showScores();
            }
        });
        lowerPanel.add(scoresButton);

        background.add(lowerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void startGame() {
        System.out.println("Starting game for player: " + playerNickField.getText());

    }

    private void showScores() {
        try {
            List<String> scores = Files.readAllLines(Paths.get("scores.txt"));
            JOptionPane.showMessageDialog(this, "Top Scores:\n" + scores.stream()
                    .limit(10)
                    .collect(Collectors.joining("\n")));
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Failed to load scores.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new StartScreen();
    }
}
