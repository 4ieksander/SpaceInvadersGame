package org.game.models;

public class GameSettings {
    private int enemySpeed;
    private int enemyCount;
    private int enemyRows;
    private String difficultyLevel;

    // Constructor with default settings
    public GameSettings() {
        this.enemySpeed = 1;
        this.enemyCount = 10;
        this.enemyRows = 2;
        this.difficultyLevel = "Medium";
    }

    // Gettery i settery
    public int getEnemySpeed() {
        return enemySpeed;
    }

    public void setEnemySpeed(int enemySpeed) {
        this.enemySpeed = enemySpeed;
    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    public int getEnemyRows() {
        return enemyRows;
    }

    public void setEnemyRows(int enemyRows) {
        this.enemyRows = enemyRows;
    }


    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(String difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }
}
