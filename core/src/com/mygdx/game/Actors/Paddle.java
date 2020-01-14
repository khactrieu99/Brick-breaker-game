package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.BaseClasses.BaseActor;

public class Paddle extends BaseActor {
    public Paddle(int level, float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("Game/paddle_level_" + level + ".png");
    }
}