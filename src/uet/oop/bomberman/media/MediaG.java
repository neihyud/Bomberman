package uet.oop.bomberman.media;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;
import java.nio.file.Paths;

public class MediaG {
    public static final String SOUND_BACKGROUND = "res/audio/backgroundG.mp3";
    public static final String SOUND_EXPLOSION = "res/audio/explosion.wav";
    public static final String SOUND_DEAD = "res/audio/dead.wav";
    public static final String SOUND_DEAD2 = "res/audio/dead2.wav";
    public static final String SOUND_PLACE_BOMB = "res/audio/place_bomb.wav";
    public static final String SOUND_POWER_UP = "res/audio/power_up.wav";

    private boolean _mute = false;

    public MediaG() {}

    public MediaG(boolean _mute) {
        this._mute = _mute;
    }

    Media media;
    MediaPlayer mediaPlayer;

    public void soundBackground() {
        media = new Media(new File(SOUND_BACKGROUND).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    public void soundBackground2() {
        media = new Media(Paths.get(SOUND_BACKGROUND).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
    }


    public void soundEffect(String path) {
        Media media = new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
       if (!_mute) mediaPlayer.play();
    }


    public void soundPause() {
        mediaPlayer.pause();
    }

    public void soundPlay() {
        mediaPlayer.play();
    }

}
