package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.playerG.Bomber;
import uet.oop.bomberman.entities.stillObj.Brick;
import uet.oop.bomberman.entities.stillObj.Grass;
import uet.oop.bomberman.entities.stillObj.Portal;
import uet.oop.bomberman.entities.stillObj.Wall;
import uet.oop.bomberman.media.MediaG;
import uet.oop.bomberman.entities.enemiesG.Enemy;
import uet.oop.bomberman.graphics.Sprite;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Bomb extends AnimatedEntity {
    List<Flame> flames = new ArrayList<>();
    private int radius = 1;
    private int timeBombs = 0;
    private boolean collisionBomb = true; //check flame collision Bomb
    private boolean overBomb = true;    // check pass bomb
    private boolean detonatorB = false; // remove control bomb

    private int typeDict = 0; // remove direction flame


    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public List<Flame> getFlames() {
        return flames;
    }

    public void setTimeBombs(int timeBombs) {
        this.timeBombs = timeBombs;
    }


    @Override
    public void update() {
        if (!detonatorB) {
            if (timeBombs < 120) {
                img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeBombs, 60).getFxImage();
            } else if (timeBombs < 135) {
                if (timeBombs < 127) {
                    MediaG mediaG = new MediaG(_muted);
                    mediaG.soundEffect(MediaG.SOUND_EXPLOSION);
                }
                img = Sprite.movingSprite(Sprite.bomb_exploded, Sprite.bomb_exploded1,
                        Sprite.bomb_exploded2, timeBombs, 15).getFxImage();
                handleExplosiveBombs();
                for (int i = 0; i < flames.size(); i++) {
                    flames.get(i).update(timeBombs);
                }
            } else {
                isAlive = false;
            }
        } else {
            img = Sprite.movingSprite(Sprite.bomb, Sprite.bomb_1, Sprite.bomb_2, timeBombs, 60).getFxImage();
        }
        timeBombs++;
    }


    public void handleExplosiveBombs() {
        if (typeDict != 4) flameTop();
        if (typeDict != 3) flameRight();
        if (typeDict != 1) flameDown();
        if (typeDict != 2) flameLeft();
    }

    /**
     * from center bomb, calculate coordinate flame(use value radius), handle collision.
     */

    public void flameDown() {
        for (int i = 1; i <= radius; i++) {
            int xB = getTile(x);
            int yB = getTile(y);
            for (Entity stillObject : stillObjects) {
                if (stillObject instanceof Grass) continue;
                if (getTile(stillObject.getX()) == xB && getTile(stillObject.getY()) == (yB + i)) {
                    if (stillObject instanceof Brick) {
                        ((Brick) stillObject).setStartCountdown(true);
                        return;
                    }
                    if (stillObject instanceof Wall) {
                        return;
                    }
                    if (stillObject instanceof Portal && mapItem[yB + i][xB] == 0) {
                        return;
                    }
                }
            }

            Flame flame = new Flame(xB, yB + i, Sprite.explosion_vertical_down_last1.getFxImage());
            collisionFlame(xB, yB + i, 4);
            if (i == radius) flame.setTypeFlame(5);
            else flame.setTypeFlame(1);
            flames.add(flame);
        }
    }


    public void flameRight() {
        for (int i = 1; i <= radius; i++) {
            int xB = getTile(x);
            int yB = getTile(y);
            for (Entity stillObject : stillObjects) {
                if (stillObject instanceof Grass) continue;
                if (stillObject.getX() / SCALED_SIZE == (xB + i) && stillObject.getY() / SCALED_SIZE == yB) {
                    if (stillObject instanceof Brick) {
                        ((Brick) stillObject).setStartCountdown(true);
                        return;
                    }
                    if (stillObject instanceof Wall) {
                        return;
                    }
                    if (stillObject instanceof Portal && mapItem[yB][xB + i] == 0) {
                        return;
                    }
                }
            }

            Flame flame = new Flame(xB + i, yB, Sprite.explosion_horizontal_right_last1.getFxImage());
            collisionFlame(xB + i, yB, 2);
            if (i == radius) flame.setTypeFlame(4);
            else flame.setTypeFlame(2);
            flames.add(flame);
        }
    }


    public void flameLeft() {
        for (int i = 1; i <= radius; i++) {
            int xB = getTile(x);
            int yB = getTile(y);
            for (Entity stillObject : stillObjects) {
                if (stillObject instanceof Grass) continue;
                if (getTile(stillObject.getX()) == (xB - i) && getTile(stillObject.getY()) == yB) {
                    if (stillObject instanceof Brick) {
                        ((Brick) stillObject).setStartCountdown(true);
                        return;
                    }
                    if (stillObject instanceof Wall) {
                        return;
                    }
                    if (stillObject instanceof Portal && mapItem[yB][xB - i] == 0) {
                        return;
                    }
                }
            }

            Flame flame = new Flame(xB - i, yB, Sprite.explosion_horizontal_left_last1.getFxImage());
            collisionFlame(xB - i, yB, 3);
            if (i == radius) flame.setTypeFlame(6);
            else flame.setTypeFlame(2);
            flames.add(flame);
        }
    }

    public void flameTop() {
        for (int i = 1; i <= radius; i++) {
            int xB = getTile(x);
            int yB = getTile(y);
            for (Entity stillObject : stillObjects) {
                if (stillObject instanceof Grass) continue;
                if (getTile(stillObject.getX()) == xB && getTile(stillObject.getY()) == (yB - i)) {
                    if (stillObject instanceof Brick) {
                        ((Brick) stillObject).setStartCountdown(true);
                        return;
                    }
                    if (stillObject instanceof Wall) {
                        return;
                    }
                    if (stillObject instanceof Portal && mapItem[yB - i][xB] == 0) {
                        return;
                    }
                }
            }

            Flame flame = new Flame(xB, yB - i, Sprite.explosion_vertical_top_last1.getFxImage());
            collisionFlame(xB, yB - i, 1);
            if (i == radius) flame.setTypeFlame(3);
            else flame.setTypeFlame(1);
            flames.add(flame);
        }
    }


    public void collisionFlame(int xF, int yF, int type) {
        // collision Bomb with Flame
        Rectangle r2 = new Rectangle(xF * SCALED_SIZE + 4, yF * SCALED_SIZE + 4,
                SCALED_SIZE - 8, SCALED_SIZE - 8); // flame
        Rectangle r3 = getRectangle(); // center bomb
        this.setCollisionBomb(false);
        for (Bomber bomber : bombers) {
            List<Bomb> bombs = bomber.getBombs();
            for (Bomb bomb : bombs) {
                if (bomb.collision(r2) && bomb.isCollisionBomb()) {
                    bomb.setTimeBombs(120);
                    bomb.setCollisionBomb(false);
                    bomb.typeDict = type;
                    break;
                }
            }


            /* collision Enemy with Flame */
            for (Enemy enemy : enemies) {
                if (enemy.collision(r2) || enemy.collision(r3)) {
                    enemy.setStartCountdown(true);
                }
            }

            /* collision Bomber with Flame */
            if (bomber.collision(r2) || bomber.collision(r3)) {
                if (!bomber.isFlamePass()) {
                    bomber.isAlive = false;
                }
            }
        }
    }

    @Override
    public Rectangle getRectangle() {
        Rectangle rect = new Rectangle(x + 8, y + 4 ,
                SCALED_SIZE * 2 / 3 + 4, SCALED_SIZE * 2 / 3 + 4);
        return rect;
    }

    public boolean isCollisionBomb() {
        return collisionBomb;
    }

    public void setCollisionBomb(boolean collisionBomb) {
        this.collisionBomb = collisionBomb;
    }

    public boolean isOverBomb() {
        return overBomb;
    }

    public void setOverBomb(boolean overBomb) {
        this.overBomb = overBomb;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void setDetonatorB(boolean detonatorB) {
        this.detonatorB = detonatorB;
    }
}


