package org.game.services;

import javax.swing.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

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
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            scoreList.addAll(lines);
        } catch (IOException e) {
            System.err.println("Error reading scores from file.");
        }
    }

    public boolean addScore(String player, int score) {
        String newEntry = player + " - " + score;
        scoreList.add(newEntry);
        Collections.sort(scoreList, (a, b) -> Integer.compare(Integer.parseInt(b.split(" - ")[1]), Integer.parseInt(a.split(" - ")[1])));
        if (scoreList.size() > 10) {
            scoreList = scoreList.subList(0, 10);
        }
        saveScores();
        return scoreList.contains(newEntry);
    }


    private void saveScores() {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(filePath))) {
            for (String score : scoreList) {
                writer.write(score);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing scores to file.");
        }
    }

    public String getTopScores() {
        return String.join("\n", scoreList);
    }
}
