package org.game.models;

public class GameSettings {
    private int enemyDescentRate;
    private int enemyRows;
    public enum DifficultyLevel {EASY, MEDIUM, HARD}
    private DifficultyLevel difficultyLevel;
    private boolean hardcoreMode;
    private String playerName;

    // Constructor with default settings
    public GameSettings(String playerName) {
        this.playerName = playerName;
        this.enemyDescentRate = 5;
        this.enemyRows = 10;
        this.difficultyLevel = DifficultyLevel.MEDIUM;
        this.hardcoreMode = false;
    }

    public int getEnemyCountPerLine(){
        if (hardcoreMode){
            if (difficultyLevel == DifficultyLevel.EASY){
                return 12;
            }
            else if (difficultyLevel == DifficultyLevel.MEDIUM){
                return 24;
            }
            else {
                return 32;
            }
        }
        if (difficultyLevel == DifficultyLevel.EASY){
            return 8;
        }
        else if (difficultyLevel == DifficultyLevel.MEDIUM){
            return 16;
        }
        else{
            return 24;
        }
    }

    public int getDistanceBetweenEnemies(){
        if (hardcoreMode){
            if (difficultyLevel == DifficultyLevel.EASY){
                return 40;
            }
            else if (difficultyLevel == DifficultyLevel.MEDIUM){
                return 30;
            }
            else {
                return 20;
            }
        }

        if (difficultyLevel == DifficultyLevel.EASY){
            return 50;
        }
        else if (difficultyLevel == DifficultyLevel.MEDIUM){
            return 40;
        }
        else {
            return 30;
        }
    }

    // Gettery i settery
    public String getPlayerName() {
        return playerName;
    }

    public int getEnemyDescentRate() {
        return enemyDescentRate;
    }

    public void setEnemyDescentRate(int enemySpeed) {
        this.enemyDescentRate = enemySpeed;
    }

    public int getEnemyRows() {
        return enemyRows;
    }

    public void setEnemyRows(int enemyRows) {
        this.enemyRows = enemyRows;
    }


    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel;
    }

    public void  setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
    }

    public boolean isHardcoreMode() {
        return hardcoreMode;
    }

    public void setHardcoreMode(boolean hardcoreMode) {
        this.hardcoreMode = hardcoreMode;
    }

}
