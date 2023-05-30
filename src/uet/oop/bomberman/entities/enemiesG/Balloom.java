package uet.oop.bomberman.entities.enemiesG;

import javafx.scene.image.Image;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Balloom extends Enemy {
    public Balloom(int x, int y, Image img) {
        super(x, y, img);
        generateDirection();
    }

    @Override
    public void update() {
        if (startCountdown) {
            img = Sprite.movingSprite(Sprite.balloom_dead, Sprite.mob_dead2,
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
    }


    @Override
    public void noMove() {
        super.noMove();
        generateDirection();
    }

    public void generateDirection() {
        Random random = new Random();
        direction = random.nextInt(4) + 1;
    }


    public void goLeft() {
        posX = x - speed;
        img = Sprite.movingSprite(Sprite.balloom_left1,
                Sprite.balloom_left2, Sprite.balloom_left3, left++, 30).getFxImage();
    }

    public void goRight() {
        posX = x + speed;
        img = Sprite.movingSprite(Sprite.balloom_right1,
                Sprite.balloom_right2, Sprite.balloom_right3, right++, 30).getFxImage();
    }

    public void goUp() {
        posY = y - speed;
        img = Sprite.movingSprite(Sprite.balloom_left1,
                Sprite.balloom_left2, Sprite.balloom_left3, up++, 30).getFxImage();
    }

    public void goDown() {
        posY = y + speed;
        img = Sprite.movingSprite(Sprite.balloom_right1,
                Sprite.balloom_right2, Sprite.balloom_right3, right++, 30).getFxImage();
    }

}
