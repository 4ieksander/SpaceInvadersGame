package org.game.services;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreManager {
    private static String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\scores.txt"; // Ścieżka do pliku z wynikami
    private List<String> scoreList; // Lista przechowująca wyniki

    public ScoreManager() {
        this.scoreList = new ArrayList<>(); // Inicjalizacja listy wyników
        loadScores(); // Wczytanie wyników z pliku przy tworzeniu obiektu
    }

    public static void showTopScores() {
        ScoreManager scoreManager = new ScoreManager(); // Tworzenie nowego managera wyników
        String topScores = scoreManager.getTopScores(); // Pobranie top 10 wyników
        JOptionPane.showMessageDialog(null, "Top 10 Scores:\n" + topScores, "Top Scores", JOptionPane.INFORMATION_MESSAGE);
        // Wyświetlenie wyników w oknie dialogowym
    }

    public boolean addScore(String player, int score) {
        String newEntry = player + " - " + score; // Formatowanie nowego wpisu
        scoreList.add(newEntry); // Dodanie wpisu do listy
        scoreList = scoreList.stream() // Sortowanie i ograniczenie listy do top 10
                .sorted((a, b) -> Integer.compare(Integer.parseInt(b.split(" - ")[1]), Integer.parseInt(a.split(" - ")[1])))
                .limit(10)
                .collect(Collectors.toList());
        saveScores(); // Zapisanie zmian w pliku
        return scoreList.contains(newEntry); // Zwraca true, jeśli nowy wpis jest na liście
    }

    public String getTopScores() {
        return String.join("\n", scoreList); // Zwraca wyniki w formacie tekstowym oddzielonym znakami nowej linii
    }

    private void loadScores() {
        Path path = Paths.get(filePath); // Ścieżka do pliku z wynikami
        try {
            scoreList = Files.lines(path).collect(Collectors.toList()); // Wczytanie wyników z pliku do listy
        } catch (IOException e) {
            System.err.println("Error reading scores from file." + e.getMessage()); // Obsługa błędu odczytu pliku
        }
    }

    private void saveScores() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) { // Otwarcie pliku do zapisu
            scoreList.forEach(score -> {
                try {
                    writer.write(score); // Zapisanie każdego wyniku w nowej linii
                    writer.newLine();
                } catch (IOException e) {
                    System.err.println("Error writing scores to file." + e.getMessage()); // Obsługa błędu zapisu do pliku
                }
            });
        } catch (IOException e) {
            System.err.println("Error writing scores to file." + e.getMessage()); // Obsługa błędu zapisu do pliku
        }
    }
}
