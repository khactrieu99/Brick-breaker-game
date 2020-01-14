package com.mygdx.game.Actors;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.BaseClasses.BaseActor;

public class Brick extends BaseActor {
    int level;

    public Brick(int level, float x, float y, Stage s) {
        super(x, y, s);
        this.level = level;
        loadTexture("Game/brick_level_" + this.level + ".png");
    }

    public int getLevel() {
        return level;
    }
}