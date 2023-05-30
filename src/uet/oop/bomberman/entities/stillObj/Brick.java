package uet.oop.bomberman.entities.stillObj;

import javafx.scene.image.Image;
import uet.oop.bomberman.entities.AnimatedEntity;
import uet.oop.bomberman.entities.playerG.Bomber;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.BombermanGame.*;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Brick extends AnimatedEntity {
    public Brick(int x, int y, Image img) {
        super(x, y, img);
    }

    @Override
    public void update() {
        if (startCountdown) {
            if (timeExist < 30) {
                img = Sprite.movingSprite(Sprite.brick_exploded, Sprite.brick_exploded1,
                        Sprite.brick_exploded2, timeExist, 30).getFxImage();
            } else {
                isExist = false;
                mapItem[y / SCALED_SIZE][x / SCALED_SIZE] = 0;
            }
            timeExist++;

            // check collision when have Item wallPass
            for (Bomber bomber : bombers) {
                if(bomber.isWallPass() && !bomber.isFlamePass())
                    if(bomber.collision(getRectangle())) {
                        bomber.setAlive(false);
                    }
            }
        }
    }
}
