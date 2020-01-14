package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.MyGame;
import com.mygdx.game.SoundPlay.SoundPlay;

public class LoseScreen implements Screen{
    Image loseLogo, backgroundImage;
    Button playAgainButton, exitButton;

    Stage stage;
    int currentLevel;
    MyGame game;

    public LoseScreen(MyGame game, int currrentLevel) {
        this.game = game;
        this.currentLevel = currrentLevel;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        backgroundImage = new Image(new TextureRegion(new Texture(Gdx.files.internal("background.png"))));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());


        playAgainButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game/play.png")))));
        playAgainButton.setSize(playAgainButton.getWidth(), playAgainButton.getHeight());
        playAgainButton.setPosition(Gdx.graphics.getWidth()/2 - playAgainButton.getWidth()/2,
                Gdx.graphics.getHeight() / 3);
        playAgainButton.setTouchable(Touchable.enabled);


        exitButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game/exit.png")))));
        exitButton.setSize(exitButton.getWidth(), exitButton.getHeight());
        exitButton.setPosition(Gdx.graphics.getWidth()/2 - exitButton.getWidth()/2,
                Gdx.graphics.getHeight() / 3 - exitButton.getHeight() - exitButton.getHeight() / 3);
        exitButton.setTouchable(Touchable.enabled);


        loseLogo = new Image(new TextureRegion(new Texture(Gdx.files.internal("Game/gameover.png"))));
        loseLogo.setPosition(Gdx.graphics.getWidth()/2 - loseLogo.getWidth()/2, Gdx.graphics.getHeight() / 2);

        playAgainButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                SoundPlay.stopLoseSound();
                game.setScreen(new BrickBreaker(game, currentLevel));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        exitButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        //play sound
        SoundPlay.playLoseSound();

        stage.addActor(backgroundImage);
        stage.addActor(playAgainButton);
        stage.addActor(exitButton);
        stage.addActor(loseLogo);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
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
