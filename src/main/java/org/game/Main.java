package org.game;
import javax.swing.*;
import java.awt.*;

import org.game.gui.GameWindow;
import org.game.services.ScoreManager;

public class Main extends JFrame {
    private JTextField playerNickField; // Pole tekstowe dla nicku gracza
    private JButton startButton; // Przycisk rozpoczynający grę
    private JButton scoresButton; // Przycisk wyświetlający top 10 wyników
    private JComboBox<Icon> shipSelector; // Lista rozwijana do wyboru ikony statku

    public Main() {
        setTitle("Ekran startowy"); // Tytuł okna
        setSize(800, 600); // Rozmiary okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Operacja zamknięcia okna
        setLayout(new BorderLayout()); // Ustawienie menedżera rozkładu
        setLocationRelativeTo(null); // Wyśrodkowanie okna

        String currentDirectory = System.getProperty("user.dir");   // zwraca katalog roboczy
        String imagePath = currentDirectory + "\\src\\main\\resources\\BackgroundSpaceInvaders.png";
        JLabel background = new JLabel(new ImageIcon(imagePath)); // Tło okna
        background.setLayout(new BorderLayout());
        add(background);

        JPanel lowerPanel = new JPanel(); // Dolny panel
        lowerPanel.setOpaque(false); // Ustawienie przezroczystości panelu
        lowerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        playerNickField = new JTextField(20); // Inicjalizacja pola tekstowego dla nicku
        lowerPanel.add(playerNickField); // Dodanie pola do panelu

        // Inicjalizacja ikon statków
        Icon shipIcon1 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\SpaceInvader1.png");
        Icon shipIcon2 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\SpaceInvader2.png");
        Icon shipIcon3 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\SpaceInvader3.png");
        Icon shipIcon4 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\spaceIcon1.png");
        Icon shipIcon5 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\spaceIcon2.png");
        Icon shipIcon6 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\spaceIcon3.png");
        Icon shipIcon7 = new ImageIcon(currentDirectory + "\\src\\main\\resources\\spaceIcon4.png");

        shipSelector = new JComboBox<>(new Icon[]{shipIcon1, shipIcon2, shipIcon3, shipIcon4, shipIcon5, shipIcon6, shipIcon7});
        shipSelector.setRenderer(new IconListRenderer()); // Ustawienie renderera dla wyboru ikon
        lowerPanel.add(shipSelector); // Dodanie listy rozwijanej do panelu

        startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGame()); // Dodanie akcji do przycisku start
        lowerPanel.add(startButton);

        scoresButton = new JButton("Top 10 Scores");
        scoresButton.addActionListener(e -> ScoreManager.showTopScores()); // Dodanie akcji do przycisku wyników
        lowerPanel.add(scoresButton);

        background.add(lowerPanel, BorderLayout.SOUTH); // Dodanie dolnego panelu do tła

        setVisible(true); // Ustawienie okna jako widocznego
    }

    private void startGame() {
        Icon selectedIcon = (Icon) shipSelector.getSelectedItem(); // Pobranie wybranej ikony statku
        String playerName = playerNickField.getText(); // Pobranie nicku gracza
        GameWindow.startGame(playerName, selectedIcon); // Uruchomienie gry
        this.dispose(); // Zamknięcie okna startowego
    }

    static class IconListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setIcon((Icon) value); // Ustawienie ikony na etykiecie
            label.setText(""); // Usunięcie tekstu z etykiety
            return label;
        }
    }

    public static void main(String[] args) {
        new Main(); // Uruchomienie aplikacji
    }
}
