package uet.oop.bomberman;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.ItemG.*;
import uet.oop.bomberman.entities.enemiesG.*;
import uet.oop.bomberman.entities.playerG.Bomber;
import uet.oop.bomberman.entities.playerG.PlayerOne;
import uet.oop.bomberman.entities.playerG.PlayerTwo;
import uet.oop.bomberman.entities.stillObj.Brick;
import uet.oop.bomberman.entities.stillObj.Grass;
import uet.oop.bomberman.entities.stillObj.Portal;
import uet.oop.bomberman.entities.stillObj.Wall;
import uet.oop.bomberman.graphics.Sprite;
import uet.oop.bomberman.media.MediaG;

import java.util.*;

import static uet.oop.bomberman.graphics.Sprite.SCALED_SIZE;

public class BombermanGame extends Application {
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int COUNT_TIME = 120;
    public static final int TIME_STAGE = 180;
    public static final int START_LEVEL = 1;
    private GraphicsContext gc;
    private Canvas canvas;

    public static int[][] mapItem = new int[HEIGHT][WIDTH];
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Bomber> bombers = new ArrayList<>();
    public static boolean _muted = false;

    Info info = new Info();
    MediaG mediaG = new MediaG();
    LevelLoader levelMap = new LevelLoader();

    private int counter = COUNT_TIME; // count time display between Stages
    private int countDown = COUNT_TIME / 2; // countDown time Game
    private int timeBetweenControl = 0;     // time remote control bomb
    private int _timeStage = TIME_STAGE;
    private int _score = 0;
    private boolean _menu = true;
    private boolean _nextLevel = false;
    private boolean _endGame = false;
    private boolean _pause = false;
    private int _level = START_LEVEL;
    private int _numPlayer = 1;


    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {

        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT + 40);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        mediaG.soundBackground2();
        //mediaG.soundBackground();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (_menu) {
                    info.drawMenu(gc);
                } else if (_pause) {
                    info.drawPause(gc);
                } else if (_nextLevel) {
                    nextLevel();
                } else if (_endGame) {
                    info.drawEndGame(gc, _score);
                } else {
                    render();
                    update();
                }

                if (_muted) {
                    mediaG.soundPause();
                } else {
                    mediaG.soundPlay();
                }
            }
        };
        timer.start();

        scene.setOnKeyPressed(e -> {
            for (int i = 0; i < bombers.size(); i++) {
                bombers.get(i).handleKeyPressed(e.getCode());
            }

            if (_menu) {
                if (e.getCode() == KeyCode.NUMPAD1) {
                    _menu = false;
                    _numPlayer = 1;
                    createMap();
                } else if (e.getCode() == KeyCode.NUMPAD2) {
                    _menu = false;
                    _numPlayer = 2;
                    createMap();
                }
            }

            if (e.getCode() == KeyCode.M) {
                _menu = true;
                resetLevel();
            } else if (e.getCode() == KeyCode.P) {
                _pause = !_pause;
            } else if (e.getCode() == KeyCode.K) {
                _muted = !_muted;
            } else if (e.getCode() == KeyCode.B) {          // remote control bomb
                if (timeBetweenControl == 0) {
                    for (Bomber bomber : bombers) {
                        if (!bomber.isDetonatorBomb()) continue;
                        List<Bomb> bombs = bomber.getBombs();
                        for (Bomb b : bombs) {
                            b.setDetonatorB(false);
                            b.setTimeBombs(120);
                        }
                    }
                    timeBetweenControl++;
                }
            }

            for (Bomber bomber : bombers)
                if (e.getCode() == KeyCode.L && !bomber.isExist()) {
                    resetLevel();
                }

        });

        scene.setOnKeyReleased(e -> {
            for (int i = 0; i < bombers.size(); i++) {
                bombers.get(i).handleKeyReleased(e.getCode());
            }
        });
    }

    public void createMap() {
        levelMap.levelLoader(_level);
        char[][] tempMap = levelMap.getMap();
        int H = tempMap.length;
        int W = tempMap[0].length;

        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                Entity object;
                stillObjects.add(new Grass(j, i, Sprite.grass.getFxImage()));
                if (tempMap[i][j] == 'p') {
                    PlayerOne playerOne = new PlayerOne(j, i, Sprite.player_right.getFxImage());
                    bombers.add(playerOne);
                }
                if (_numPlayer == 2 && tempMap[i][j] == 'q') {
                    PlayerTwo playerTwo = new PlayerTwo(j, i, Sprite.player_left.getFxImage());
                    bombers.add(playerTwo);
                }

                if (tempMap[i][j] == '#') {
                    stillObjects.add(new Wall(j, i, Sprite.wall.getFxImage()));
                } else if (tempMap[i][j] == '*') {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                    stillObjects.add(object);
                } else if (tempMap[i][j] == '1') {
                    enemies.add(new Balloom(j, i, Sprite.balloom_left3.getFxImage()));
                } else if (tempMap[i][j] == '2') {
                    enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                } else if (tempMap[i][j] == '3') {
                    enemies.add(new Doll(j, i, Sprite.doll_left1.getFxImage()));
                } else if (tempMap[i][j] == '4') {
                    enemies.add(new Kondoria(j, i, Sprite.kondoria_left1.getFxImage()));
                } else if (tempMap[i][j] == '5') {
                    enemies.add(new Minvo(j, i, Sprite.minvo_left1.getFxImage()));
                } else if (tempMap[i][j] == 'b') {
                    stillObjects.add(new ItemBomb(j, i, Sprite.powerup_bombs.getFxImage()));
                    stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    mapItem[i][j] = 1;
                } else if (tempMap[i][j] == 'B') {
                    stillObjects.add(new ItemBombPass(j, i, Sprite.powerup_bombpass.getFxImage()));
                    stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    mapItem[i][j] = 1;
                } else if (tempMap[i][j] == 'f') {
                    stillObjects.add(new ItemFlame(j, i, Sprite.powerup_flames.getFxImage()));
                    stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    mapItem[i][j] = 1;
                } else if (tempMap[i][j] == 'F') {
                    stillObjects.add(new ItemFlamePass(j, i, Sprite.powerup_flamepass.getFxImage()));
                    stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    mapItem[i][j] = 1;
                } else if (tempMap[i][j] == 'W') {
                    stillObjects.add(new ItemWallPass(j, i, Sprite.powerup_wallpass.getFxImage()));
                    stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    mapItem[i][j] = 1;
                } else if (tempMap[i][j] == 's') {
                    stillObjects.add(new ItemSpeed(j, i, Sprite.powerup_speed.getFxImage()));
                    stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    mapItem[i][j] = 1;
                } else if (tempMap[i][j] == 'x') {
                    stillObjects.add(new Portal(j, i, Sprite.portal.getFxImage()));
                    stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    mapItem[i][j] = 1;
                } else if (tempMap[i][j] == 'D') {
                    stillObjects.add(new ItemDetonator(j, i, Sprite.powerup_detonator.getFxImage()));
                    stillObjects.add(new Brick(j, i, Sprite.brick.getFxImage()));
                    mapItem[i][j] = 1;
                }
            }
        }
    }

    public void update() {
        timeStage(); // time in one stage

        for (int i = 0; i < enemies.size(); i++) {
            if (!enemies.get(i).isExist()) {
                scores(enemies.get(i));
                enemies.remove(enemies.get(i));
                i--;
                continue;
            }
            enemies.get(i).update();
        }

        for (Bomber bomber : bombers) {
            if (bomber.isExist())
                bomber.update();
        }

        for (Bomber bomber : bombers) {
            List<Bomb> bombs = bomber.getBombs();
            for (Bomb bomb : bombs) {
                bomb.update();
            }
        }

        for (int i = 0; i < stillObjects.size(); i++) {
            if (!stillObjects.get(i).isExist()) {
                stillObjects.remove(stillObjects.get(i));
                i--;
                continue;
            }
            stillObjects.get(i).update();
        }
        handleCollision();
        if (timeBetweenControl > 0) timeBetweenControl++;
        if (timeBetweenControl > 30) timeBetweenControl = 0;
    }


    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight() + 50);
        info.drawGround(gc, _score, _timeStage, _level);

        for (int i = 0; i < bombers.size(); i++) {
            if (!bombers.get(i).isExist() && bombers.size() == 2) {
                bombers.remove(bombers.get(i));
            } else if (!bombers.get(i).isExist() && bombers.size() == 1) {
                _endGame = true;
                return;
            }
        }

        for (Entity stillObject : stillObjects) {
            stillObject.render(gc);
        }

        for (Enemy enemy : enemies) {
            enemy.render(gc);
        }

        for (Bomber bomber : bombers) {
            List<Bomb> bombs = bomber.getBombs();
            for (Bomb bomb : bombs) {
                bomb.render(gc);
                List<Flame> flames = bomb.getFlames();
                for (Flame flame : flames) {
                    flame.render(gc);
                }
            }
        }

        for (Bomber bomber : bombers) {
            if (bomber.isExist())
                bomber.render(gc);
        }
    }

    public void handleCollision() {
        List<Bomb> sumBombs = new ArrayList<>(); // Sum bombs of all bomber
        if (bombers.size() == 2 && bombers.get(0).collision(bombers.get(1).getRectangle())) {
            if (bombers.get(0).isExist() && bombers.get(1).isExist()) {
                bombers.get(0).noMove();
                bombers.get(1).noMove();
            }
        }
        for (Bomber bomber : bombers) {
            List<Bomb> bombs = bomber.getBombs();
            sumBombs.addAll(bombs);
            boolean collisionDetected = false;

            // check bomber can move over bomb
            if (!bomber.isBombPass()) {
                for (Bomb bomb : bombs) {
                    if (bomber.collision(bomb.getRectangle()) && !bomb.isOverBomb()) {
                        break;
                    }
                    if (bomber.collision(bomb.getRectangle()) && bomb.isOverBomb()) {
                        collisionDetected = true;
                        break;
                    }
                    bomb.setOverBomb(true);
                }
            }


            // collision Bomber with StillObjects
            for (int i = 0; i < stillObjects.size(); i++) {
                if (stillObjects.get(i) instanceof Grass) continue;
                Rectangle bBer = new Rectangle(bomber.getPosX() + 6, bomber.getPosY() + 20,
                        SCALED_SIZE * 5 / 8 - 6, SCALED_SIZE - 21); // di chuyen de hon
                if (stillObjects.get(i).collision(bBer)) {
                    // if (bomber.collision(stillObjects.get(i).getRectangle())) {
                    if (stillObjects.get(i) instanceof Item) {
                        if (checkMapItem(stillObjects.get(i))) {
                            if (!_muted) mediaG.soundEffect(MediaG.SOUND_POWER_UP);
                            if (stillObjects.get(i) instanceof ItemBomb) {
                                bomber.setNumBombs(bomber.getNumBombs() + 1);
                            } else if (stillObjects.get(i) instanceof ItemFlame) {
                                bomber.setRadius(bomber.getRadius() + 1);
                            } else if (stillObjects.get(i) instanceof ItemSpeed) {
                                bomber.setSpeed(bomber.getSpeed() + 1);
                            } else if (stillObjects.get(i) instanceof ItemBombPass) {
                                bomber.setBombPass(true);
                            } else if (stillObjects.get(i) instanceof ItemFlamePass) {
                                bomber.setFlamePass(true);
                            } else if (stillObjects.get(i) instanceof ItemWallPass) {
                                bomber.setWallPass(true);
                            } else if (stillObjects.get(i) instanceof ItemDetonator) {
                                bomber.setDetonatorBomb(true);
                            }
                            stillObjects.remove(stillObjects.get(i));
                        }
                    } else if (checkMapItem(stillObjects.get(i)) && stillObjects.get(i) instanceof Portal) {
                        if (enemies.size() == 0) {
                            _nextLevel = true;
                        } else {
                            collisionDetected = true; // portal don't go
                            break;
                        }
                    } else if ((stillObjects.get(i) instanceof Brick
                            || stillObjects.get(i) instanceof Portal) && bomber.isWallPass()) {
                        collisionDetected = false;
                        break;
                    } else {
                        collisionDetected = true;
                        break;
                    }
                }
            }

            if (collisionDetected) {
                bomber.noMove();
            } else {
                bomber.move();
            }


            // collision Enemy with Bomber
            for (Enemy enemy : enemies) {
                if (bomber.collision(enemy.getRectangle()) && !enemy.getStartCountdown()) {
                    bomber.setAlive(false);
                    break;
                }
            }
        }

        // collision Enemy with StillObject
        for (Enemy enemy : enemies) {
            for (Entity stillObject : stillObjects) {
                if (stillObject instanceof Grass) continue;
                if (enemy.collision(stillObject.getRectangle())) {
//                    int xObj = stillObject.getMapX();
//                    int yObj = stillObject.getMapY();
//                    if ((stillObject instanceof Item) && mapItem[yObj][xObj] == 1)
//                        continue;
//                    if (enemy instanceof Kondoria && stillObject instanceof Brick) break;
                    enemy.setMoveEnemy(false);
                    break;
                }
            }

            // collision Enemy with Bomb
            for (Bomb bomb : sumBombs) {
                if (enemy.collision(bomb.getRectangle())) {
                    enemy.setMoveEnemy(false);
                    break;
                }
            }

            if (enemy.isMoveEnemy()) {
                enemy.move();
            } else {
                enemy.noMove();
                enemy.setMoveEnemy(true);
            }
        }

    }


    public boolean checkMapItem(Entity entity) {
        int xMap = entity.getX() / SCALED_SIZE;
        int yMap = entity.getY() / SCALED_SIZE;
        return mapItem[yMap][xMap] == 0;
    }


    public void scores(Enemy enemy) {
        if (enemy instanceof Balloom) {
            _score += 100;
        } else if (enemy instanceof Doll) {
            _score += 50;
        } else if (enemy instanceof Oneal) {
            _score += 300;
        } else if (enemy instanceof Minvo) {
            _score += 200;
        } else if (enemy instanceof Kondoria) {
            _score += 200;
        }

    }

    public void timeStage() {
        countDown--;
        if (countDown == 0) {
            if (_timeStage != 0) {
                _timeStage--;
            } else {
                for (Bomber bomber : bombers) {
                    bomber.setAlive(false);
                }
                return;
            }
            countDown = COUNT_TIME / 2;
        }
    }


    public void resetLevel() {
        _menu = true;
        _endGame = false;
        _numPlayer = 1;
        _score = 0;
        _level = START_LEVEL;
        resetStage();
    }

    public void nextLevel() {
        _level = levelMap.get_level() + 1;
        info.drawStage(gc, _level);
        counter--;
        if (counter < 0) {
            _nextLevel = false;
            resetStage();
            createMap();

        }
    }

    public void resetStage() {
        _timeStage = TIME_STAGE;
        _pause = false;
        countDown = COUNT_TIME / 2;
        counter = COUNT_TIME;
        bombers = new ArrayList<>();
        stillObjects = new ArrayList<>();
        enemies = new ArrayList<>();
        mapItem = new int[HEIGHT][WIDTH];
    }

}
