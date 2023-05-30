package uet.oop.bomberman.entities.playerG;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.media.MediaG;

import static uet.oop.bomberman.BombermanGame._muted;

public class PlayerTwo extends Bomber {
    public PlayerTwo(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (isAlive) {
            if (direction == KeyCode.A) {
                goLeft();
            }
            if (direction == KeyCode.D) {
                goRight();
            }
            if (direction == KeyCode.W) {
                goUp();
            }
            if (direction == KeyCode.S) {
                goDown();
            }

        } else {
            died();
        }

        handleNumBombs();
        if (isPutBomb) {
            placeBomb();
        }
        resetAnimation();
        if (timeBetweenBombs > 0) timeBetweenBombs++;
        if (timeBetweenBombs > 30) timeBetweenBombs = 0;
    }

    @Override
    public void handleKeyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.A || keyCode == KeyCode.D
                || keyCode == KeyCode.W || keyCode == KeyCode.S) {
            this.direction = keyCode;
        }

        if (keyCode == KeyCode.F) {
            MediaG mediaG = new MediaG(_muted);
            mediaG.soundEffect(MediaG.SOUND_PLACE_BOMB);
            if (isAlive) isPutBomb = true;
        }
    }

    @Override
    public void handleKeyReleased(KeyCode keyCode) {
        if (direction == keyCode) {
            if (direction == KeyCode.A) {
                img = Sprite.player_left.getFxImage();
            }
            if (direction == KeyCode.D) {
                img = Sprite.player_right.getFxImage();
            }
            if (direction == KeyCode.W) {
                img = Sprite.player_up.getFxImage();
            }
            if (direction == KeyCode.S) {
                img = Sprite.player_down.getFxImage();
            }
            direction = null;
        }
        if (keyCode == KeyCode.F)
            isPutBomb = false;
    }
}

