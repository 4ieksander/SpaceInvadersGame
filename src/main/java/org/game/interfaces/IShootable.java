package org.game.interfaces;

import org.game.models.Bullet;

public interface IShootable {
    Bullet shoot(); // umożliwia obiektowi wystrzelenie pocisku, zwraca obiekt typu Bullet
}
