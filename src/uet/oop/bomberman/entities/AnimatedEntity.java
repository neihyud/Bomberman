package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.media.MediaG;

public abstract class AnimatedEntity extends Entity {
    protected int posX = x;
    protected int posY = y;
    protected int up = 0;
    protected int right = 0;
    protected int down = 0;
    protected int left = 0;
    protected int timeExist = 0;
    protected boolean isAlive = true;
    protected boolean startCountdown = false;


    public AnimatedEntity(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public void move() {
        x = posX;
        y = posY;
    }

    public void noMove() {
        posX = x;
        posY = y;
    }


    public void setStartCountdown(boolean startCountdown) {
        this.startCountdown = startCountdown;
    }
    public boolean getStartCountdown() {
        return startCountdown;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    public void resetAnimation() {
        if (right > 6000) right = 0;
        if (left > 6000) left = 0;
        if (up > 6000) up = 0;
        if (down > 6000) down = 0;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }
}
