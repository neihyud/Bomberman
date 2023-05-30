package uet.oop.bomberman.entities;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Flame extends AnimatedEntity {
    private int typeFlame = 0;
    public Flame(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
    }

    public void update(int timeFlame) {
        switch(typeFlame) {
            case 1:
                img = Sprite.movingSprite(Sprite.explosion_vertical, Sprite.explosion_vertical1,
                        Sprite.explosion_vertical2, timeFlame, 15).getFxImage();
                break;
            case 2:
                img = Sprite.movingSprite(Sprite.explosion_horizontal, Sprite.explosion_horizontal1,
                        Sprite.explosion_horizontal2, timeFlame, 15).getFxImage();
                break;
            case 3:
                img = Sprite.movingSprite(Sprite.explosion_vertical_top_last,
                        Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2,
                        timeFlame, 15).getFxImage();
                break;
            case 4:
                img = Sprite.movingSprite(Sprite.explosion_horizontal_right_last,
                        Sprite.explosion_horizontal_right_last1,Sprite.explosion_horizontal_right_last2,
                        timeFlame, 15).getFxImage();
                break;
            case 5:
                img = Sprite.movingSprite(Sprite.explosion_vertical_down_last,
                        Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2,
                        timeFlame, 15).getFxImage();
                break;
            case 6:
                img = Sprite.movingSprite(Sprite.explosion_horizontal_left_last,
                        Sprite.explosion_horizontal_left_last1,Sprite.explosion_horizontal_left_last2,
                        timeFlame, 15).getFxImage();
                break;
        }
    }


    public void setTypeFlame(int typeFlame) {
        this.typeFlame = typeFlame;
    }
}
