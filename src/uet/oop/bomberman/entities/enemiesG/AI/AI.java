package uet.oop.bomberman.entities.enemiesG.AI;

import java.util.Random;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class AI {
    protected Random random = new Random();
    public abstract int calculateDirection();

    public int getTile(int x) {
        if (x % SCALED_SIZE > SCALED_SIZE / 2) {
            x = x / SCALED_SIZE + 1;
        } else {
            x = x / SCALED_SIZE;
        }
        return x;
    }

}
