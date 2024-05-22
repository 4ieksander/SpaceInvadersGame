package org.game.interfaces;

public interface IHittable {
    boolean isAlive(); // sprawdza, czy obiekt jest "żywy", czyli czy jest barany pod uwage na planszy.
    // w przypadku gracza konczy gre
    void hit(); // wykonuje akcję uderzenia, np. zmniejsza punkty życia obiektu
}
