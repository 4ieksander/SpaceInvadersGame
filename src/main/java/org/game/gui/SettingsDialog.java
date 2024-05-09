package org.game.gui;

import javax.swing.*;
import java.awt.*;
import org.game.models.GameSettings;

public class SettingsDialog extends JDialog {
    private final JFrame parentFrame;
    private JSlider descentRateSlider;
    private JSpinner enemyCountSpinner;
    private JComboBox<GameSettings.DifficultyLevel> difficultyComboBox;
    private JCheckBox hardcoreModeCheckBox;
    private GameSettings settings;

    public SettingsDialog(JFrame parentFrame, GameSettings settings) {
        super(parentFrame, "Ustawienia gry", true);
        this.parentFrame = parentFrame;

        setSize(300, 400);
        setLocationRelativeTo(parentFrame);
        setLayout(new BorderLayout());
        this.settings = settings;
        setSize(400, 300);
        setLayout(new BorderLayout());
        add(createSettingsPanel(), BorderLayout.CENTER);
        add(createButtonsPanel(), BorderLayout.SOUTH);
    }


    private JPanel createSettingsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        panel.add(new JLabel("Prędkość zniżania się wrogów (szybko - wolno):"));
        descentRateSlider = new JSlider(3, 100, settings.getEnemyDescentRate());
        panel.add(descentRateSlider);

        panel.add(new JLabel("Liczba lini wrogów:"));
        enemyCountSpinner = new JSpinner(new SpinnerNumberModel(settings.getEnemyRows(), 1, 25, 1));
        panel.add(enemyCountSpinner);

        panel.add(new JLabel("Poziom trudności:"));
        difficultyComboBox = new JComboBox<>(GameSettings.DifficultyLevel.values());
        difficultyComboBox.setSelectedItem(settings.getDifficultyLevel());
        panel.add(difficultyComboBox);

        panel.add(new JLabel("Tryb hardcore:"));
        hardcoreModeCheckBox = new JCheckBox();
        hardcoreModeCheckBox.setSelected(settings.isHardcoreMode());
        panel.add(hardcoreModeCheckBox);

        return panel;
    }

    private JPanel createButtonsPanel() {
        JPanel buttonsPanel = new JPanel();
        JButton saveButton = new JButton("Zapisz");
        saveButton.addActionListener(e -> saveSettings());
        buttonsPanel.add(saveButton);

        JButton cancelButton = new JButton("Anuluj");
        cancelButton.addActionListener(e -> this.dispose());
        buttonsPanel.add(cancelButton);

        return buttonsPanel;
    }

    private void saveSettings() {
        settings.setEnemyDescentRate(descentRateSlider.getValue());
        settings.setEnemyRows((Integer) enemyCountSpinner.getValue());
        settings.setDifficultyLevel((GameSettings.DifficultyLevel) difficultyComboBox.getSelectedItem());
        settings.setHardcoreMode(hardcoreModeCheckBox.isSelected());
        dispose();
    }


}
