package uet.oop.bomberman.entities;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import uet.oop.bomberman.graphics.Sprite;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public abstract class Entity {
    //Tọa độ X tính từ góc trái trên trong Canvas
    protected int x;

    //Tọa độ Y tính từ góc trái trên trong Canvas
    protected int y;

    protected Image img;

    protected boolean isExist = true;

    //Khởi tạo đối tượng, chuyển từ tọa độ đơn vị sang tọa độ trong canvas
    public Entity(int xUnit, int yUnit, Image img) {
        this.x = xUnit * Sprite.SCALED_SIZE;
        this.y = yUnit * Sprite.SCALED_SIZE;
        this.img = img;
    }


    public void render(GraphicsContext gc) {
        gc.drawImage(img, x, y + 40);
    }

    public abstract void update();

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public Rectangle getRectangle() {
        return new Rectangle(x, y, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    /**
     * chuyen sang toa do don vi.
     */
    public int getTile(int x) {
        if (x % SCALED_SIZE > SCALED_SIZE / 2) {
            x = x / SCALED_SIZE + 1;
        } else {
            x = x / SCALED_SIZE;
        }
        return (int) x;
    }

    public int getMapX() {
        return x / SCALED_SIZE;
    }

    public int getMapY() {
        return y / SCALED_SIZE;
    }

    public boolean collision(Rectangle rect) {
        return getRectangle().getBoundsInParent().intersects(rect.getBoundsInParent());
    }
}
