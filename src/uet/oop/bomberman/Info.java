package uet.oop.bomberman;

import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;

import static uet.oop.bomberman.BombermanGame.HEIGHT;
import static uet.oop.bomberman.BombermanGame.WIDTH;
import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class Info {

    public void drawEndGame(GraphicsContext gc, int score) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH * SCALED_SIZE, HEIGHT * SCALED_SIZE + 40);

        Font font = Font.font("Comic Sans MS", FontWeight.BLACK, 40);
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        gc.fillText("GAME OVER!", SCALED_SIZE * 13, SCALED_SIZE * 6);

        Font font2 = Font.font("Comic Sans MS", FontWeight.BLACK, FontPosture.ITALIC, 20);
        gc.setFont(font2);
        gc.fillText("Press L to back to Menu", SCALED_SIZE * 13, SCALED_SIZE * 7);

        Font font3 = Font.font("Comic Sans MS", FontWeight.BLACK, FontPosture.ITALIC, 60);
        gc.setFont(font3);
        gc.fillText("SCORE: " + score, SCALED_SIZE * 12 + 16, SCALED_SIZE * 3);
    }

    public void drawStage(GraphicsContext gc, int level) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 40, WIDTH * SCALED_SIZE, HEIGHT * SCALED_SIZE);

        Font font = Font.font("Comic Sans MS", FontWeight.BLACK, 40);
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        gc.fillText("STAGE " + level, SCALED_SIZE * 13, 270);
    }

    public void drawPause(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH * SCALED_SIZE, HEIGHT * SCALED_SIZE + 40);

        Font font = Font.font("Comic Sans MS", FontWeight.BLACK, 40);
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        gc.fillText("GAME PAUSE", SCALED_SIZE * 13, 270);

        Font font2 = Font.font("Comic Sans MS", FontWeight.BLACK, FontPosture.ITALIC, 20);
        gc.setFont(font2);
        gc.fillText("Press P to continue", SCALED_SIZE * 14, 310);
    }

    public void drawGround(GraphicsContext gc, int score, int time, int level) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH * SCALED_SIZE, 40);

        Font font = Font.font("Comic Sans MS", FontWeight.BLACK, 20);
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        gc.fillText("Score: " + score, SCALED_SIZE * 26, 28);

        gc.fillText("Time: " + time, SCALED_SIZE * 5, 28);

        gc.fillText("Level: " + level, SCALED_SIZE * 15, 28);
    }


    public void drawMenu(GraphicsContext gc) {
        gc.setFill(Color.BLACK);
        gc.fillRect(0, 0, WIDTH * SCALED_SIZE, HEIGHT * SCALED_SIZE + 40);

        Font font = Font.font("Comic Sans MS", FontWeight.BLACK, 30);
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        gc.fillText("1. ONE PLAYER", SCALED_SIZE * 12 + 32, 320);
        gc.fillText("2. TWO PLAYER", SCALED_SIZE * 12 + 32, 380);

        Font font2 = Font.font("Comic Sans MS", FontWeight.BLACK, 60);
        gc.setFont(font2);
        gc.fillText("MAIN MENU", SCALED_SIZE * 11, 200);

        Font font3 = Font.font("Comic Sans MS", FontWeight.BLACK, 20);
        gc.setFont(font3);
        gc.fillText("Press Number to Play", SCALED_SIZE * 12 + 32, SCALED_SIZE*5);
    }

}
