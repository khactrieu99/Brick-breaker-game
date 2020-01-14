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
import com.mygdx.game.SoundPlay.SoundPlay;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.delay;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

public class LevelUpScreen implements Screen {
    final MyGame game;
    int nextLevel;

    Image imageLevelUp;
    Stage stage;

    public LevelUpScreen(MyGame game, int nextLevel) {
        this.game = game;
        this.nextLevel = nextLevel;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        imageLevelUp = new Image(new TextureRegion(new Texture(Gdx.files.internal("Game/uplevel.png"))));
        imageLevelUp.setPosition(Gdx.graphics.getWidth()/2 - imageLevelUp.getWidth()/2, Gdx.graphics.getHeight() / 2);

        // play sound
        SoundPlay.playWinSound();

        stage.addActor(imageLevelUp);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.addAction(sequence(
                delay(2f),       // wait 2 seconds
                fadeIn(1.75f), // fade out the stage, taking 1.75 seconds
                new Action() {
                    @Override
                    public boolean act(float delta) {
                        // the last action will move to the next screen
                        SoundPlay.stopWinSound();
                        game.setScreen(new BrickBreaker(game, nextLevel));
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
