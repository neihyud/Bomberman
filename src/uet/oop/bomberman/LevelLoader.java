package uet.oop.bomberman;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LevelLoader {
    private int _level = 0;
    private char[][] _map;

    public void levelLoader(int level) {
        String path = "res/levels/Level" + Integer.toString(level) + ".txt";
        try {
            FileReader file = new FileReader(path);
            Scanner sc = new Scanner(file);
            _level = sc.nextInt();
            int height = sc.nextInt();
            int weight = sc.nextInt();
            sc.nextLine();
            _map = new char[height][weight];
            for (int i = 0; i < height; i++) {
                String line = sc.nextLine();
                for (int j = 0; j < weight; j++) {
                    _map[i][j] = line.charAt(j);
                }
            }
            sc.close();
            file.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public char[][] getMap() {
        return _map;
    }

    public int get_level() {
        return _level;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public LevelLoader() {
    }

}
