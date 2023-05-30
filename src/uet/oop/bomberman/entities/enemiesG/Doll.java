package uet.oop.bomberman.entities.enemiesG;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.playerG.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

public class Doll extends Enemy {
    public Doll(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
        generateDirection();
    }


    @Override
    public void update() {
        if (startCountdown) {
            img = Sprite.movingSprite(Sprite.doll_dead, Sprite.mob_dead2,
                    Sprite.mob_dead3, timeExist, 60).getFxImage();
            countDown(img);
        } else if (moveEnemy) {
            if (direction == 1) {
                goLeft();
            }
            if (direction == 2) {
                goRight();
            }
        }
    }


    @Override
    public void noMove() {
        super.noMove();
        generateDirection();
    }

    @Override
    public void generateDirection() {
        Random random = new Random();
        direction = random.nextInt(2) + 1;
        speed = random.nextInt(3) + 1;
    }


    public void goLeft() {
        posX = x - speed;
        img = Sprite.movingSprite(Sprite.doll_left1, Sprite.doll_left2,
                Sprite.doll_left3, left++, 20).getFxImage();
    }

    public void goRight() {
        posX = x + speed;
        img = Sprite.movingSprite(Sprite.doll_right1, Sprite.doll_right2,
                Sprite.doll_right3, right++, 20).getFxImage();
    }


}

