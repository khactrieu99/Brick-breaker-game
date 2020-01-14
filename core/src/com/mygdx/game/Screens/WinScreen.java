package com.mygdx.game.Screens;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.SoundPlay.SoundPlay;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class WinScreen implements Screen {
    Stage stage;
    Image backgroundImage, winLogo;

    public WinScreen() { stage = new Stage(new ScreenViewport()); }

    @Override
    public void show() {
        backgroundImage = new Image(new TextureRegion(new Texture(Gdx.files.internal("background.png"))));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        winLogo = new Image(new TextureRegion(new Texture(Gdx.files.internal("Game/win.png"))));
        winLogo.setPosition(Gdx.graphics.getWidth()/2 - winLogo.getWidth()/2, Gdx.graphics.getHeight() / 2);

        // play sound
        SoundPlay.playWinSound();

        stage.addActor(backgroundImage);
        stage.addActor(winLogo);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.addAction(sequence(
                delay( 4f),       // wait 2 seconds
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        // the last action will move to the next screen
                        Gdx.app.exit();
                        return true;
                    }}));

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
