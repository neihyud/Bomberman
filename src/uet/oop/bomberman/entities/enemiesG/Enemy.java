package uet.oop.bomberman.entities.enemiesG;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.enemiesG.AI.AI;
import uet.oop.bomberman.media.MediaG;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame._muted;
import static uet.oop.bomberman.BombermanGame.bombers;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class Enemy extends AnimatedEntity {
    public Enemy(int x, int y, Image img) {
        super(x, y, img);
    }

    protected int direction = 1;
    protected int speed = 1;
    protected boolean moveEnemy = true;
    protected AI ai;


    public abstract void generateDirection();

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(posX + 1, posY + 1, SCALED_SIZE - 2, SCALED_SIZE - 2);
    }

    public void countDown(Image image) {
        if (timeExist < 58) {
            if (timeExist < 10) {
                MediaG mediaG = new MediaG(_muted);
                mediaG.soundEffect(MediaG.SOUND_DEAD2);
            }
            img = image;
        } else {
            isExist = false;
            return;
        }
        timeExist++;
    }

    public int randomBomber() {
        Random randomb = new Random();
        if (bombers.size() == 1) return 0;
        return randomb.nextInt(2);
    }

    public boolean isMoveEnemy() {
        return moveEnemy;
    }

    public void setMoveEnemy(boolean moveEnemy) {
        this.moveEnemy = moveEnemy;
    }
}
