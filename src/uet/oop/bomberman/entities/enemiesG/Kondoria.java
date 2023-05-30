package uet.oop.bomberman.entities.enemiesG;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.enemiesG.AI.AIHigh;
import uet.oop.bomberman.graphics.Sprite;


import static uet.oop.bomberman.BombermanGame.bombers;


public class Kondoria extends Enemy {
    public Kondoria(int x, int y, Image img) {
        super(x, y, img);
        speed = 1;
        ai = new AIHigh(bombers.get(randomBomber()), this);
        generateDirection();
    }


    @Override
    public void update() {
        if (startCountdown) {
            img = Sprite.movingSprite(Sprite.kondoria_dead, Sprite.mob_dead2,
                    Sprite.mob_dead3, timeExist, 60).getFxImage();
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

    @Override
    public void generateDirection() {
        direction = ai.calculateDirection();
    }



    public void goLeft() {
        posX = x - speed;
        img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2,
                Sprite.kondoria_left3, left++, 30).getFxImage();
    }

    public void goRight() {
        posX = x + speed;
        img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2,
                Sprite.kondoria_right3, right++, 30).getFxImage();
    }

    public void goUp() {
        posY = y - speed;
        img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2,
                Sprite.kondoria_left3, up++, 30).getFxImage();
    }

    public void goDown() {
        posY = y + speed;
        img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2,
                Sprite.kondoria_right3, down++, 30).getFxImage();
    }


}

