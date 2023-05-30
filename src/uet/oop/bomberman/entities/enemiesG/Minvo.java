package uet.oop.bomberman.entities.enemiesG;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.enemiesG.AI.AIHigh;
import uet.oop.bomberman.entities.playerG.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.bombers;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Minvo extends Enemy {
    int timeDirection = 0;

    public Minvo(int x, int y, Image img) {
        super(x, y, img);
        ai = new AIHigh(bombers.get(randomBomber()), this);
        generateDirection();
    }


    @Override
    public void update() {
        if (startCountdown) {
            img = Sprite.movingSprite(Sprite.minvo_dead, Sprite.mob_dead1,
                    Sprite.mob_dead2, timeExist, 60).getFxImage();
            countDown(img);
        } else if (moveEnemy) {
            if (direction == 1) {
                goLeft();
            }
            if (direction == 2) {
                goRight();
            }
            if (direction == 3) {
                goUp();
            }
            if (direction == 4) {
                goDown();
            }
        }
        timeDirection++;
    }


    @Override
    public void noMove() {
        super.noMove();
        generateDirection();
    }

    @Override
    public void generateDirection() {
        direction = ai.calculateDirection();
    }


    @Override
    public Rectangle getRectangle() {
        Rectangle rect = new Rectangle(posX + 1, posY + 1, SCALED_SIZE - 2, SCALED_SIZE - 2);
        return rect;
    }

    public void goLeft() {
        posX = x - speed;
        img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2,
                Sprite.minvo_left3, left++, 20).getFxImage();
    }

    public void goRight() {
        posX = x + speed;
        img = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2,
                Sprite.minvo_right3, right++, 20).getFxImage();
    }

    public void goUp() {
        posY = y - speed;
        img = Sprite.movingSprite(Sprite.minvo_left1, Sprite.minvo_left2,
                Sprite.minvo_left3, up++, 20).getFxImage();
    }

    public void goDown() {
        posY = y + speed;
        img = Sprite.movingSprite(Sprite.minvo_right1, Sprite.minvo_right2,
                Sprite.minvo_right3, down++, 20).getFxImage();
    }


}
