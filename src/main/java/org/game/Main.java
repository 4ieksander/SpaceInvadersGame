package org.game;
import javax.swing.*;
import java.awt.*;

import org.game.gui.GameWindow;
import org.game.services.ScoreManager;


public class Main extends JFrame {
    private JTextField playerNickField;
    private JButton startButton;
    private JButton scoresButton;
    private JComboBox<Icon> shipSelector;

    public Main() {
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
        scoresButton.addActionListener(e -> ScoreManager.showTopScores());
        lowerPanel.add(scoresButton);

        background.add(lowerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void startGame() {
        Icon selectedIcon = (Icon) shipSelector.getSelectedItem();
        String playerName = playerNickField.getText();
        GameWindow.startGame(playerName, selectedIcon);
        this.dispose();
    }

    static class IconListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setIcon((Icon) value);
            label.setText("");
            return label;
        }
    }


    public static void main(String[] args) {
        new Main();
    }
}