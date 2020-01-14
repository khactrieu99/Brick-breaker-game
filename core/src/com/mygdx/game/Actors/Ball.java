package com.mygdx.game.Actors;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.mygdx.game.BaseClasses.BaseActor;

public class Ball extends BaseActor {
    public boolean isPaused;

    public Ball(float x, float y, Stage s) {
        super(x, y, s);
        loadTexture("Game/ball.png");

        setSpeed(10);
        setMotionAngel(startAngle(45, 135));
        setBoundaryPolygon(12);
        setPaused(true);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean b) {
        isPaused = b;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public void bounceOff(BaseActor other)
    {
        Vector2 v = this.preventOverlap(other);
        if ( Math.abs(v.x) >= Math.abs(v.y) ) // horizontal bounce
            this.velocity.x *= -1;
        else // vertical bounce
            this.velocity.y *= -1;
    }

    public void move() {
        setX(getX() + velocity.x);
        setY(getY() + velocity.y);
    }

    public int startAngle(int min, int max) {
       return (int)(Math.random()*((max-min)+1))+min;
    }

    public void updateSpeed() {
        this.velocity.x += (this.velocity.x > 0 ? 0.0001 : -0.0001) ;
        this.velocity.y += (this.velocity.y > 0 ? 0.0001 : -0.0001);
    }
}