package com.mygdx.game.SoundPlay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class SoundPlay {
    static Sound Knock = Gdx.audio.newSound(Gdx.files.internal("Sound/knock.wav"));
    static Music Lose = Gdx.audio.newMusic(Gdx.files.internal("Sound/lose.mp3"));
    static Music Win = Gdx.audio.newMusic(Gdx.files.internal("Sound/win.mp3"));
    static Music Home = Gdx.audio.newMusic(Gdx.files.internal("Sound/home.mp3"));
    static Music Game = Gdx.audio.newMusic(Gdx.files.internal("Sound/game.mp3"));
    
    public static void playKnockSound() {
        Knock.play();
    }
    public static void stopKnockSound() { Knock.stop(); }

    public static void playLoseSound() {
        Lose.play();
    }
    public static void stopLoseSound() { Lose.stop(); }

    public static void playWinSound() {
        Win.play();
    }
    public static void stopWinSound() { Win.stop(); }

    public static void playHomeSound() {
        Home.play();
        Home.setLooping(true);
    }

    public static void playGameSound() {
        Game.play();
        Game.setLooping(true);
    }

    public static void stopHomeSound() { Home.stop(); }

    public static void stopGameSound() { Game.stop(); }
}
