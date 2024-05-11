package org.game.services;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class ScoreManager {
    private static String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\scores.txt";
    private List<String> scoreList;

    public ScoreManager() {
        this.scoreList = new ArrayList<>();
        loadScores();
    }


    public static void showTopScores() {
        ScoreManager scoreManager = new ScoreManager();
        String topScores = scoreManager.getTopScores();
        JOptionPane.showMessageDialog(null, "Top 10 Scores:\n" + topScores, "Top Scores", JOptionPane.INFORMATION_MESSAGE);
    }

    private void loadScores() {
        Path path = Paths.get(filePath);
        try {
            scoreList = Files.lines(path).collect(Collectors.toList());
        } catch (IOException e) {
            System.err.println("Error reading scores from file." + e.getMessage());
        }
    }

    public boolean addScore(String player, int score) {
        String newEntry = player + " - " + score;
        scoreList.add(newEntry);
        scoreList = scoreList.stream()
                        .sorted((a,b)-> Integer.compare(Integer.parseInt(b.split(" - ")[1]), Integer.parseInt(a.split(" - ")[1])))
                                .limit(10)
                                        .collect(Collectors.toList());
        saveScores();
        return scoreList.contains(newEntry);
    }


    private void saveScores() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            scoreList.forEach(score -> {
                try {
                    writer.write(score);
                    writer.newLine();
                } catch (IOException e) {
                    System.err.println("Error writing scores to file." + e.getMessage());
                }
            });
        }catch (IOException e) {
            System.err.println("Error writing scores to file." + e.getMessage());
        }
    }

    public String getTopScores() {
        return String.join("\n", scoreList);
    }
}
