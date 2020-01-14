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

public class MenuScreen implements Screen {

    Button playButton;
    Image shopButton;
    Image backgroundImage;
    Image logoImage;
    MyGame game;
    Stage stage;
    ScreenViewport viewPort;

    public MenuScreen(final MyGame game) {
        this.game = game;
        this.viewPort = new ScreenViewport();
        this.viewPort.apply();
        stage = new Stage(this.viewPort);
        backgroundImage = new Image(new TextureRegion(new Texture(Gdx.files.internal("Home/background.png"))));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        playButton = new Button(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Home/play.png")))));
        playButton.setSize(playButton.getWidth(), playButton.getHeight());
        playButton.setPosition(Gdx.graphics.getWidth()/2 - playButton.getWidth()/2, Gdx.graphics.getHeight() / 2 - 100);

        shopButton = new Image(new TextureRegion(new Texture(Gdx.files.internal("Home/shop.png"))));
        shopButton.setPosition(Gdx.graphics.getWidth()/2 - shopButton.getWidth()/2,
                Gdx.graphics.getHeight() / 2 - playButton.getHeight() - 140);

        logoImage = new Image(new TextureRegion(new Texture(Gdx.files.internal("logo_text.png"))));
        logoImage.setPosition(Gdx.graphics.getWidth()/2 - logoImage.getWidth()/2,
                Gdx.graphics.getHeight() / 2 + logoImage.getHeight()/3);

        playButton.setTouchable(Touchable.enabled);
        playButton.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                game.setScreen(new BrickBreaker(game, 1));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(backgroundImage);
        stage.addActor(playButton);
        stage.addActor(shopButton);
        stage.addActor(logoImage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        SoundPlay.playHomeSound();
    }

    @Override
    public void resize(int width, int height) {
        this.viewPort.update(width,height);
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
