package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeOut;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class SplashScreen implements Screen {
    final MyGame game;
    Image logo;
    Stage stage;

    public SplashScreen(final MyGame game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());

        logo = new Image(new TextureRegion(new Texture(Gdx.files.internal("logo.png"))));
        logo.setPosition(Gdx.graphics.getWidth()/2 - logo.getWidth()/2,
                Gdx.graphics.getHeight() / 2);


        stage.addActor(logo);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

//        Gdx.app.setLogLevel(Application.LOG_DEBUG);
//        Gdx.app.debug("KHAC TRIEU", "RENDER : zo rui nek");

        stage.act();
        stage.draw();

        stage.addAction(sequence(
                delay(2f),       // wait 2 seconds
                fadeOut(1.75f), // fade out the stage, taking 1.75 seconds
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        // the last action will move to the next screen
                        game.setScreen(new MenuScreen(game));
                        return true;
                    }}));

    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }
}
