package uet.oop.bomberman.entities.enemiesG;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.playerG.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import java.util.Random;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Oneal extends Enemy {

    public Oneal(int x, int y, Image img) {
        super(x, y, img);
        generateDirection();
    }

    int timeDirection = 0;
    int xOld = 0;
    int yOld = 0;
    int newDirection = 0;
    Bomber br = bombers.get(0);

    @Override
    public void update() {
        if (startCountdown) {
            img = Sprite.movingSprite(Sprite.oneal_dead, Sprite.mob_dead1,
                    Sprite.mob_dead2, timeExist, 60).getFxImage();
            countDown(img);
        } else if (moveEnemy) {
            if (timeDirection % 300 == 0) generateDirection();
            int bX = br.getTile(br.getX()) * SCALED_SIZE;
            int bY = br.getTile(br.getY()) * SCALED_SIZE;

            if (Math.abs(bX - x) == 0 && (bY - y) < 0) {
                direction = 3;
            } else if (Math.abs(bX - x) == 0 && (bY - y) > 0) {
                direction = 4;
            } else if (Math.abs(bY - y) == 0 && (bX - x) < 0) {
                direction = 1;
            } else if (Math.abs(bY - y) == 0 && (bX - x) > 0) {
                direction = 2;
            }


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
        Random randoms = new Random();
        speed = randoms.nextInt(2) + 1;

        if (timeDirection % 31 == 0) {
            xOld = getX();
            yOld = getY();
            if (timeDirection % 155 == 0) newDirection = 0;
        }
        if (timeDirection % 35 == 0) {
            if (xOld == getX() && yOld == getY()) {
                newDirection = 1;
            }
        }

        int bX = br.getTile(br.getX()) * SCALED_SIZE;
        int bY = br.getTile(br.getY()) * SCALED_SIZE;
        if (Math.abs(bX - x) > Math.abs(bY - y)) {
            if (newDirection == 1) {
                if (bY - y > 0) direction = 4;
                else direction = 3;
                return;
            }
            if (bX < x) direction = 1; //left
            else if (bX > x) direction = 2; //right
        } else {
            if (newDirection == 1) {
                if (bX - x > 0) direction = 2;
                else direction = 1;
                return;
            }
            if (bY > y) direction = 4; //down
            else if (bY < y) direction = 3; //top
        }


        // if (Math.abs(bX - x) > Math.abs(bY - y)) {
        //     if (bY > y) direction = 4; //down
        //     else if (bY < y) direction = 3; //top
        // } else {
        //     if (bX < x) direction = 1; //left
        //     else if (bX > x) direction = 2; //right
        // }

    }

    @Override
    public Rectangle getRectangle() {
        Rectangle rect = new Rectangle(posX + 1, posY + 1, SCALED_SIZE - 2, SCALED_SIZE - 2);
        rect.setArcHeight(SCALED_SIZE);
        rect.setArcWidth(SCALED_SIZE);
        return rect;
    }

    public void goLeft() {
        posX = x - speed;
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2,
                Sprite.oneal_left3, left++, 20).getFxImage();
    }

    public void goRight() {
        posX = x + speed;
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2,
                Sprite.oneal_right3, right++, 20).getFxImage();
    }

    public void goUp() {
        posY = y - speed;
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2,
                Sprite.oneal_left3, up++, 20).getFxImage();
    }

    public void goDown() {
        posY = y + speed;
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2,
                Sprite.oneal_right3, down++, 20).getFxImage();
    }

}

