package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.mygdx.game.Screens.MenuScreen;
import com.mygdx.game.Screens.SplashScreen;
import com.mygdx.game.Screens.WinScreen;

public class MyGame extends Game {

    @Override
    public void create() {
        setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public Screen getScreen() {
        return super.getScreen();
    }
}
