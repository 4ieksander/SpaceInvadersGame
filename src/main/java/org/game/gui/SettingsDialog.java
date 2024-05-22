package org.game.gui;

import javax.swing.*;
import java.awt.*;
import org.game.models.GameSettings;

public class SettingsDialog extends JDialog {
    private JSlider descentRateSlider; // Suwak do regulacji prędkości zniżania się wrogów
    private JSpinner enemyCountSpinner; // Licznik do ustawienia liczby linii wrogów
    private JComboBox<GameSettings.DifficultyLevel> difficultyComboBox; // Lista rozwijana do wyboru poziomu trudności
    private JCheckBox hardcoreModeCheckBox; // Pole wyboru dla trybu hardcore
    private GameSettings settings; // Ustawienia gry, które będą modyfikowane

    public SettingsDialog(JFrame parentFrame, GameSettings settings) {
        super(parentFrame, "Ustawienia gry", true); // Ustawienie modalności dialogu
        setLocationRelativeTo(parentFrame); // Ustawienie lokalizacji względem okna nadrzędnego
        this.settings = settings; // Inicjalizacja ustawień gry

        setSize(400, 300); // Ustawienie rozmiarów okna dialogowego
        setLayout(new BorderLayout()); // Ustawienie menedżera układu
        add(createSettingsPanel(), BorderLayout.CENTER); // Dodanie panelu ustawień do centrum
        add(createButtonsPanel(), BorderLayout.SOUTH); // Dodanie panelu przycisków na dole
    }

    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel(); // Tworzenie panelu dla ustawień
        panel.setLayout(new GridLayout(5, 2, 10, 10)); // Ustawienie układu siatki dla elementów

        // Dodanie etykiety i suwaka dla prędkości zniżania się wrogów
        panel.add(new JLabel("Prędkość zniżania się wrogów (szybko - wolno):"));
        descentRateSlider = new JSlider(3, 100, settings.getEnemyDescentRate());
        panel.add(descentRateSlider);

        // Dodanie etykiety i licznika dla liczby linii wrogów
        panel.add(new JLabel("Liczba lini wrogów:"));
        enemyCountSpinner = new JSpinner(new SpinnerNumberModel(settings.getEnemyRows(), 1, 15, 1));
        panel.add(enemyCountSpinner);

        // Dodanie etykiety i listy rozwijanej dla poziomu trudności
        panel.add(new JLabel("Poziom trudności:"));
        difficultyComboBox = new JComboBox<>(GameSettings.DifficultyLevel.values()); // Tworzy nowy ComboBox z wartościami poziomów trudności gry
        // JComboBox wybór jednej z wielu dostępnych opcji z listy rozwijanej.
        difficultyComboBox.setSelectedItem(settings.getDifficultyLevel());
        panel.add(difficultyComboBox);

        // Dodanie etykiety i pola wyboru dla trybu hardcore
        panel.add(new JLabel("Tryb hardcore:"));
        hardcoreModeCheckBox = new JCheckBox();
        hardcoreModeCheckBox.setSelected(settings.isHardcoreMode());
        panel.add(hardcoreModeCheckBox);

        return panel; // Zwrócenie skonfigurowanego panelu ustawień
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel(); // Tworzenie panelu dla przycisków
        JButton saveButton = new JButton("Zapisz"); // Przycisk zapisu ustawień
        saveButton.addActionListener(e -> saveSettings()); // Dodanie akcji zapisu
        buttonsPanel.add(saveButton);

        JButton cancelButton = new JButton("Anuluj"); // Przycisk anulowania zmian
        cancelButton.addActionListener(e -> this.dispose()); // Dodanie akcji zamykania okna
        buttonsPanel.add(cancelButton);

        return buttonsPanel; // Zwrócenie skonfigurowanego panelu przycisków
    }

    private void saveSettings() {
        settings.setEnemyDescentRate(descentRateSlider.getValue()); // Zapisanie ustawienia prędkości zniżania się wrogów
        settings.setEnemyRows((Integer) enemyCountSpinner.getValue()); // Zapisanie ustawienia liczby linii wrogów
        settings.setDifficultyLevel((GameSettings.DifficultyLevel) difficultyComboBox.getSelectedItem()); // Zapisanie wybranego poziomu trudności
        settings.setHardcoreMode(hardcoreModeCheckBox.isSelected()); // Zapisanie stanu trybu hardcore
        dispose(); // Zamknięcie okna dialogowego
    }
}
