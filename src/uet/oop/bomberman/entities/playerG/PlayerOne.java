package uet.oop.bomberman.entities.playerG;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.media.MediaG;

import static uet.oop.bomberman.BombermanGame._muted;

public class PlayerOne extends Bomber {
    public PlayerOne(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (isAlive) {
            if (direction == KeyCode.LEFT) {
                goLeft();
            }
            if (direction == KeyCode.RIGHT) {
                goRight();
            }
            if (direction == KeyCode.UP) {
                goUp();
            }
            if (direction == KeyCode.DOWN) {
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
        if (keyCode == KeyCode.LEFT || keyCode == KeyCode.RIGHT
                || keyCode == KeyCode.UP || keyCode == KeyCode.DOWN) {
            this.direction = keyCode;
        }

        if (keyCode == KeyCode.SPACE) {
            MediaG mediaG = new MediaG(_muted);
            mediaG.soundEffect(MediaG.SOUND_PLACE_BOMB);
            if (isAlive) isPutBomb = true;
        }
    }

    @Override
    public void handleKeyReleased(KeyCode keyCode) {
        if (direction == keyCode) {
            if (direction == KeyCode.LEFT) {
                img = Sprite.player_left.getFxImage();
            }
            if (direction == KeyCode.RIGHT) {
                img = Sprite.player_right.getFxImage();
            }
            if (direction == KeyCode.UP) {
                img = Sprite.player_up.getFxImage();
            }
            if (direction == KeyCode.DOWN) {
                img = Sprite.player_down.getFxImage();
            }
            direction = null;
        }
        if (keyCode == KeyCode.SPACE) {
            isPutBomb = false;
        }
    }
}
