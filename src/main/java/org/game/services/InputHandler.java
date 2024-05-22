package org.game.services;

import org.game.gui.GameWindow;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class InputHandler extends KeyAdapter {
    private GameWindow gameWindow; // Referencja do głównego okna gry
    private boolean leftPressed = false; // Czy klawisz lewo jest wciśnięty
    private boolean rightPressed = false; // Czy klawisz prawo jest wciśnięty
    private boolean spacePressed = false; // Czy klawisz spacji jest wciśnięty
    private boolean enterPressed = false; // Czy klawisz enter jest wciśnięty

    public InputHandler(GameWindow gameWindow) {
        this.gameWindow = gameWindow; // Inicjalizacja okna gry
    }

    public boolean isLeftPressed() {
        return leftPressed; // Zwraca stan klawisza lewo
    }

    public boolean isRightPressed() {
        return rightPressed; // Zwraca stan klawisza prawo
    }

    public boolean isSpacePressed() {
        return spacePressed; // Zwraca stan klawisza spacji
    }

    public boolean isEnterPressed() {
        return enterPressed; // Zwraca stan klawisza enter
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!gameWindow.isGameRunning()) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER){
                gameWindow.startGame(); // Rozpoczyna grę jeśli klawisz enter jest naciśnięty i gra nie jest aktywna
                return;
            } else{
                return;
            }
        };

        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                leftPressed = true; // Ustawienie stanu lewo na true
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = true; // Ustawienie stanu prawo na true
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = true; // Ustawienie stanu spacji na true
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = true; // Ustawienie stanu enter na true
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                leftPressed = false; // Ustawienie stanu lewo na false
                break;
            case KeyEvent.VK_RIGHT:
                rightPressed = false; // Ustawienie stanu prawo na false
                break;
            case KeyEvent.VK_SPACE:
                spacePressed = false; // Ustawienie stanu spacji na false
                break;
            case KeyEvent.VK_ENTER:
                enterPressed = false; // Ustawienie stanu enter na false
                break;
        }
    }
}
