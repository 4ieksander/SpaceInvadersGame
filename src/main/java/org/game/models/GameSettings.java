package org.game.models;

public class GameSettings {
    private final String playerName; // nazwa gracza
    private int enemyDescentRate; // szybkość zniżania się wrogów
    private int enemyRows; // liczba rzędów wrogów
    private boolean hardcoreMode; // czy gra jest w trybie hardcore
    private DifficultyLevel difficultyLevel; // poziom trudności gry
    public enum DifficultyLevel {EASY, MEDIUM, HARD} // enum z poziomami trudności


    public GameSettings(String playerName) {
        this.playerName = playerName; // ustawienie nazwy gracza
        this.enemyDescentRate = 15; // domyślna szybkość zniżania wrogów
        this.enemyRows = 4; // domyślna liczba rzędów wrogów
        this.difficultyLevel = DifficultyLevel.MEDIUM; // domyślny poziom trudności
        this.hardcoreMode = false; // domyślnie tryb hardcore nieaktywny
    }


    public int getEnemyCountPerLine(){
        if (hardcoreMode){
            if (difficultyLevel == DifficultyLevel.EASY){
                return 12; // większa liczba wrogów na linię w trybie hardcore i poziomie łatwym
            }
            else if (difficultyLevel == DifficultyLevel.MEDIUM){
                return 24;
            }
            else {
                return 32;
            }
        }
        if (difficultyLevel == DifficultyLevel.EASY){
            return 8; // liczba wrogów na linię na poziomie łatwym w trybie zwykłym
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
                return 40; // mniejszy odstęp między wrogami w trybie hardcore
            }
            else if (difficultyLevel == DifficultyLevel.MEDIUM){
                return 30;
            }
            else {
                return 20;
            }
        }

        if (difficultyLevel == DifficultyLevel.EASY){
            return 50; // większy odstęp między wrogami na poziomie łatwym
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
        return playerName; // zwraca nazwę gracza
    }

    public int getEnemyDescentRate() {
        return enemyDescentRate; // zwraca szybkość zniżania się wrogów
    }

    public void setEnemyDescentRate(int enemySpeed) {
        this.enemyDescentRate = enemySpeed; // ustawia szybkość zniżania się wrogów
    }

    public int getEnemyRows() {
        return enemyRows; // zwraca liczbę rzędów wrogów
    }

    public void setEnemyRows(int enemyRows) {
        this.enemyRows = enemyRows; // ustawia liczbę rzędów wrogów
    }

    public DifficultyLevel getDifficultyLevel() {
        return difficultyLevel; // zwraca poziom trudności gry
    }

    public void  setDifficultyLevel(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel; // ustawia poziom trudności gry
    }

    public boolean isHardcoreMode() {
        return hardcoreMode; // zwraca czy gra jest w trybie hardcore
    }

    public void setHardcoreMode(boolean hardcoreMode) {
        this.hardcoreMode = hardcoreMode; // ustawia tryb hardcore
    }

}
