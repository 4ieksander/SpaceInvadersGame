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
import org.game.gui.GameWindow;

public class StartScreen extends JFrame {
    private JTextField playerNickField;
    private JButton startButton;
    private JButton scoresButton;
    private JComboBox<Icon> shipSelector;

    public StartScreen() {
        setTitle("Ekran startowy");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        String currentDirectory = System.getProperty("user.dir");
        String imagePath = currentDirectory + "\\src\\main\\resources\\BackgroundSpaceInvaders.png";
        JLabel background = new JLabel(new ImageIcon(imagePath));
        background.setLayout(new BorderLayout());
        add(background);

        JPanel lowerPanel = new JPanel();
        lowerPanel.setOpaque(false);
        lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        playerNickField = new JTextField(20);
        lowerPanel.add(playerNickField);

        Icon shipIcon1 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\SpaceInvader1.png");
        Icon shipIcon2 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\SpaceInvader2.png");
        Icon shipIcon3 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\SpaceInvader3.png");
        Icon shipIcon4 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\spaceIcon1.png");
        Icon shipIcon5 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\spaceIcon2.png");
        Icon shipIcon6 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\spaceIcon3.png");
        Icon shipIcon7 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\spaceIcon4.png");

        shipSelector = new JComboBox<>(new Icon[]{shipIcon1, shipIcon2, shipIcon3, shipIcon4, shipIcon5, shipIcon6, shipIcon7});
        shipSelector.setRenderer(new IconListRenderer());
        lowerPanel.add(shipSelector);

        startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGame());
        lowerPanel.add(startButton);

        scoresButton = new JButton("Top 10 Scores");
        scoresButton.addActionListener(e -> showScores());
        lowerPanel.add(scoresButton);

        background.add(lowerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void startGame() {
        Icon selectedIcon = (Icon) shipSelector.getSelectedItem();
        String playerName = playerNickField.getText();
        GameWindow.startGame(playerName, selectedIcon);  // Założenie, że GameWindow obsługuje teraz ikonę statku
        this.dispose();
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

    class IconListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setIcon((Icon) value);
            label.setText("");
            return label;
        }
    }

    public static void main(String[] args) {
        new StartScreen();
    }
}