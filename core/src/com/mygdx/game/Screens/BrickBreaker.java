package com.mygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.Actors.Ball;
import com.mygdx.game.Actors.Brick;
import com.mygdx.game.Actors.Paddle;
import com.mygdx.game.MyGame;
import com.mygdx.game.SoundPlay.SoundPlay;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class BrickBreaker implements Screen {
    Paddle paddle;
    Ball ball;

    public static Stage mainStage;

    int screenWidth, screenHeight;
    boolean isFailed, isComplete;
    int currentLevel, maxLevel;

    Image backgroundImage, levelImage;
    Image leftWall, rightWall, topWall;

    MyGame game;

    public BrickBreaker(MyGame game, int levelStart) {
        this.game = game;
        screenHeight = Gdx.graphics.getHeight();
        screenWidth = Gdx.graphics.getWidth();

        mainStage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(mainStage);

        maxLevel = 4;
        currentLevel = levelStart;

        initialize();
    }

    public void initialize() {
        isFailed = false;
        isComplete = false;
        SoundPlay.stopHomeSound();
        SoundPlay.playGameSound();

        backgroundImage = new Image(new TextureRegion(new Texture(Gdx.files.internal("background.png"))));
        backgroundImage.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mainStage.addActor(backgroundImage);

        leftWall = new Image(new TextureRegion(new Texture(Gdx.files.internal("wall.png"))));
        leftWall.setSize(Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight());
        mainStage.addActor(leftWall);

        rightWall = new Image(new TextureRegion(new Texture(Gdx.files.internal("wall.png"))));
        rightWall.setSize(Gdx.graphics.getWidth() / 30, Gdx.graphics.getHeight());
        rightWall.setPosition(screenWidth - screenWidth/30, 0);
        mainStage.addActor(rightWall);

        topWall = new Image(new TextureRegion(new Texture(Gdx.files.internal("wall.png"))));
        topWall.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 10);
        topWall.setPosition(0, screenHeight - screenHeight / 10);
        mainStage.addActor(topWall);

        levelImage = new Image(new TextureRegion(new Texture(Gdx.files.internal("Game/level_"+ currentLevel +".png"))));
        levelImage.setPosition(topWall.getWidth()/2 - levelImage.getWidth() / 2,
                                  screenHeight - topWall.getHeight() / 2 - levelImage.getHeight() / 2);
        mainStage.addActor(levelImage);

        // use temp paddle to get width and height off paddle
        Paddle tempPaddle = new Paddle(1,0,0, mainStage);
        float paddleWidth = tempPaddle.getWidth();
        tempPaddle.remove();

        // use temp paddle to get width and height off paddle
        Ball tempBall = new Ball(0,0, mainStage);
        float ballWidth = tempBall.getWidth();
        tempBall.remove();

        paddle = new Paddle(currentLevel, screenWidth/2 - paddleWidth/2, 40 , mainStage);
        ball = new Ball(screenWidth/2 - ballWidth / 2 , paddle.getHeight() + 5, mainStage);

        // init bricks
        // get brick's width and height
        Brick tempBrick = new Brick(1, 0, 0, mainStage);
        float brickWidth = tempBrick.getWidth();
        float brickHeight = tempBrick.getHeight();
        tempBrick.remove();

        drawMap(brickWidth, brickHeight);
    }

    @Override
    public void render(float delta) {
        mainStage.act(Gdx.graphics.getDeltaTime());

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(Gdx.graphics.getDeltaTime());

        if (!isFailed)
            // if not lose yet
            mainStage.draw();
        else {
            // TODO: lose screen goes here
            mainStage.clear();
            SoundPlay.stopGameSound();
            game.setScreen(new LoseScreen(game, currentLevel));
            dispose();
        }

        if (isComplete) {
            mainStage.clear();
            SoundPlay.stopGameSound();
            if (currentLevel < maxLevel) {
                mainStage.clear();
                game.setScreen(new LevelUpScreen(game, currentLevel + 1));
                dispose();
            }
            else {
                mainStage.clear();
                game.setScreen(new WinScreen());
            }
        }
    }

    public void boundToScreen() {
        if(paddle.getX() < leftWall.getWidth()) paddle.setX(leftWall.getWidth());
        if(paddle.getX() + paddle.getWidth() > screenWidth - rightWall.getWidth())
            paddle.setX(screenWidth - rightWall.getWidth() - paddle.getWidth());
    }

    public void update(float dt) {

        if(Gdx.input.isTouched()) {
            if(ball.isPaused()) {
                ball.setPaused(false);
            }
        }

        int lastTouched = 0;
        for(int i=0; i<20; i++) if(Gdx.input.isTouched(i)) lastTouched = i;

        if(Gdx.input.isTouched(lastTouched)) {
            float touchedX = Gdx.input.getX(lastTouched);
            paddle.setX(touchedX - paddle.getWidth()/2);
            boundToScreen();
        }

        if(ball.isPaused()) {
            ball.setX( paddle.getX() + paddle.getWidth()/2 - ball.getWidth()/2 );
            ball.setY( paddle.getY() + paddle.getHeight()/2 + ball.getHeight()/2 );
        } else{
            ball.updateSpeed();
        }

        if (ball.overlaps(paddle)) {
            SoundPlay.playKnockSound();
            float ballCenterX = ball.getX() + ball.getWidth()/2;
            float paddlePercentHit = (ballCenterX - paddle.getX()) / paddle.getWidth();
            float bounceAngle = MathUtils.lerp( 150, 30, paddlePercentHit );
            ball.setMotionAngel(bounceAngle);
        }

        for(Brick brick: getListBrick()) {
            if ( ball.overlaps(brick) ) {
                //play sound
                SoundPlay.playKnockSound();

                ball.bounceOff(brick);

                if(brick.getLevel()==2) {
                    float brickX = brick.getX();
                    float brickY = brick.getY();
                    new Brick(1, brickX, brickY, mainStage);
                }
                else if(brick.getLevel()==3) {
                    float brickX = brick.getX();
                    float brickY = brick.getY();
                    new Brick(2, brickX, brickY, mainStage);
                }
                brick.remove();
            }
        }

        if(countBrick() == 0) isComplete = true;

        ball.move();

        // prevent ball is going out of screen
        if(ball.getX() >= screenWidth - rightWall.getWidth() - ball.getWidth() || ball.getX() < leftWall.getWidth()) {
            ball.setMotionAngel(180 - ball.getMotionAngel());
        }
        // prevent ball is going out of screen
        if(ball.getY() >= screenHeight - topWall.getHeight() - ball.getHeight()) {
            ball.setMotionAngel(ball.getMotionAngel() + (180 - 2 * (90 - (180 - ball.getMotionAngel()))));
        }
        //if ball is under the paddle, then player failed
        if(ball.getY() < 0) isFailed = true;
    }

    public ArrayList<Brick> getListBrick() {
        ArrayList<Brick> list = new ArrayList<>();

        for(Actor actor: mainStage.getActors()) {
            if(actor instanceof Brick)
                list.add(((Brick) actor));
        }

        return list;
    }

    public void drawMap(float brickWidth, float brickHeight) {
        String fileLevel = "Level/level" + currentLevel + ".txt";
        int totalRows = 10;
        int totalCols = 10;

        float marginX = (screenWidth - totalCols*brickWidth) / 2;
        float marginY = (screenHeight - totalRows*brickHeight) - 300;

        try {
            FileHandle fileHandle = Gdx.files.internal(fileLevel);
            InputStream inputStream = fileHandle.read();
            int i, j = 0;
            while((i = inputStream.read())!=-1) {
                int colNum = (totalCols - j % totalCols);
                int rowNum = (totalRows - j / totalRows);

                float brickX = marginX + colNum * brickWidth;
                float brickY = marginY + rowNum * brickHeight;

                switch ((char)i) {
                    case '_':
                        j++;
                        break;
                    case '1':
                        j++;
                        new Brick(1, brickX, brickY, mainStage);
                        break;
                    case '2':
                        j++;
                        new Brick(2, brickX, brickY, mainStage);
                        break;
                    case '3':
                        j++;
                        new Brick(3, brickX, brickY, mainStage);
                        break;
                    default:
                        break;
                }
            }
        }
        catch (FileNotFoundException ex) {
            System.out.println(ex);
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public int countBrick() {
        return getListBrick().size();
    }

    @Override
    public void dispose() {
        mainStage.dispose();
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
    }
}
