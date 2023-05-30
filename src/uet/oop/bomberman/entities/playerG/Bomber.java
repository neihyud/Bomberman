package uet.oop.bomberman.entities.playerG;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.Bomb;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.stillObj.Brick;
import uet.oop.bomberman.media.MediaG;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame._muted;
import static uet.oop.bomberman.BombermanGame.stillObjects;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class Bomber extends AnimatedEntity {
    protected final List<Bomb> bombs = new ArrayList<>();
    protected KeyCode direction = null;
    protected boolean isPutBomb = false;
    protected int timeBetweenBombs = 0;
    private int speed = 2;
    private int numBombs = 1;
    private int radius = 1;
    private boolean flamePass = false;
    private boolean bombPass = false;
    private boolean wallPass = false;
    private boolean detonatorBomb = false;


    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    public abstract void handleKeyPressed(KeyCode keyCode);

    public abstract void handleKeyReleased(KeyCode keyCode);

    public void placeBomb() {
        if (numBombs > 0) {
            if (timeBetweenBombs > 0) return;
            int xBomb = getTile(x);
            int yBomb = getTile(y);
            Bomb bomb = new Bomb(xBomb, yBomb, Sprite.bomb.getFxImage());
            for (Bomb b : bombs) {
                if (b.getX() == bomb.getX() && b.getY() == bomb.getY()) {
                    return;
                }
            }

            if (wallPass) {
                for (Entity stillObject : stillObjects) {
                    if (stillObject instanceof Brick) {
                        int xBrick = getTile(stillObject.getX());
                        int yBrick = getTile(stillObject.getY());
                        if (xBomb == xBrick && yBomb == yBrick) {
                            return;
                        }
                    }
                }
            }

            bomb.setOverBomb(false);
            bomb.setRadius(radius);
            bomb.setDetonatorB(detonatorBomb);
            bombs.add(bomb);
            numBombs--;
            timeBetweenBombs++;
        }
    }

    public void handleNumBombs() {
        for (int i = 0; i < bombs.size(); i++) {
            if (!bombs.get(i).isAlive()) {
                bombs.remove(bombs.get(i));
                numBombs++;
            }
        }
    }

    public void died() {
        if (timeExist < 10) {
            MediaG mediaG = new MediaG(_muted);
            mediaG.soundEffect(MediaG.SOUND_DEAD);
        } else if (timeExist >= 92) {
            setExist(false);
        }

        img = Sprite.movingSprite(Sprite.player_dead1, Sprite.player_dead2,
                Sprite.player_dead3, timeExist, 90).getFxImage();

        timeExist++;
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(posX + 1, posY + 4, SCALED_SIZE * 5 / 8, SCALED_SIZE * 3 / 4);
    }

    public void goLeft() {
        posX = x - speed;
        img = Sprite.movingSprite(Sprite.player_left, Sprite.player_left_1,
                Sprite.player_left_2, left++, 20).getFxImage();
    }

    public void goRight() {
        posX = x + speed;
        img = Sprite.movingSprite(Sprite.player_right, Sprite.player_right_1,
                Sprite.player_right_2, right++, 20).getFxImage();
    }

    public void goUp() {
        posY = y - speed;
        img = Sprite.movingSprite(Sprite.player_up, Sprite.player_up_1,
                Sprite.player_up_2, up++, 20).getFxImage();
    }

    public void goDown() {
        posY = y + speed;
        img = Sprite.movingSprite(Sprite.player_down, Sprite.player_down_1,
                Sprite.player_down_2, down++, 20).getFxImage();
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

    public int getNumBombs() {
        return numBombs;
    }

    public void setNumBombs(int numBombs) {
        this.numBombs = numBombs;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public boolean isFlamePass() {
        return flamePass;
    }

    public void setFlamePass(boolean flamePass) {
        this.flamePass = flamePass;
    }

    public boolean isBombPass() {
        return bombPass;
    }

    public void setBombPass(boolean bombPass) {
        this.bombPass = bombPass;
    }

    public boolean isWallPass() {
        return wallPass;
    }

    public void setWallPass(boolean wallPass) {
        this.wallPass = wallPass;
    }

    public boolean isDetonatorBomb() {
        return detonatorBomb;
    }

    public void setDetonatorBomb(boolean detonatoBomb) {
        this.detonatorBomb = detonatoBomb;
    }
}
