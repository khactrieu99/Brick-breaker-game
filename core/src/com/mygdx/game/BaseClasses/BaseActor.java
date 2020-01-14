package com.mygdx.game.BaseClasses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;


public class BaseActor extends Actor {
    Texture texture;
    protected Vector2 velocity;
    Polygon boundaryPolygon;

    public BaseActor(float x, float y, Stage s) {
        setPosition(x, y);
        s.addActor(this);

        velocity = new Vector2(0,0);
        boundaryPolygon = null;
    }

    public void loadTexture(String fileName) {
        texture = new Texture(Gdx.files.internal(fileName));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        setWidth(texture.getWidth());
        setHeight(texture.getHeight());

        if(boundaryPolygon==null) setBoundaryRectangle();
    }

    public void setSpeed(float speed) {
        if(velocity.len() == 0) {
            velocity.set(speed, 0);
        }
        else {
            velocity.setLength(speed);
        }
    }

    public float getMotionAngel() {
        return velocity.angle();
    }

    public void setMotionAngel(float angel) {
        velocity.setAngle(angel);
    }

    public void setBoundaryRectangle() {
        float w = getWidth();
        float h = getHeight();

        float[] vertices = {0,0, w,0, w,h, 0,h};
        boundaryPolygon = new Polygon(vertices);
    }

    public void setBoundaryPolygon(int numSides) {
        float w = getWidth();
        float h = getHeight();

        float[] vertices = new float[2*numSides];
        for (int i = 0; i < numSides; i++) {
            float angle = i * 6.28f / numSides;
            // x-coordinate
            vertices[2*i] = w/2 * MathUtils.cos(angle) + w/2;
            // y-coordinate
            vertices[2*i+1] = h/2 * MathUtils.sin(angle) + h/2;
        }
        boundaryPolygon = new Polygon(vertices);
    }

    public Polygon getBoundaryPolygon() {
        boundaryPolygon.setPosition(getX(), getY());
        boundaryPolygon.setOrigin(getOriginX(), getOriginY());
        boundaryPolygon.setRotation(getRotation());
        boundaryPolygon.setScale(getScaleX(), getScaleY());
        return boundaryPolygon;
    }

    public boolean overlaps(BaseActor other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        // initial test to improve performance
        if ( !poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()) )
            return false;

        return Intersector.overlapConvexPolygons( poly1, poly2 );
    }

    public Vector2 preventOverlap(BaseActor other) {
        Polygon poly1 = this.getBoundaryPolygon();
        Polygon poly2 = other.getBoundaryPolygon();

        // initial test to improve performance
        if ( !poly1.getBoundingRectangle().overlaps(poly2.getBoundingRectangle()) )
            return null;

        Intersector.MinimumTranslationVector mtv = new Intersector.MinimumTranslationVector();
        boolean polygonOverlap = Intersector.overlapConvexPolygons(poly1, poly2, mtv);

        if ( !polygonOverlap )
            return null;

        this.moveBy( mtv.normal.x * mtv.depth, mtv.normal.y * mtv.depth );
        return mtv.normal;
    }

    public void draw(Batch batch, float parentAlpha) {
        // apply color tint effect
        Color c = getColor();
        batch.setColor(c.r, c.g, c.b, c.a);

        batch.draw(texture, getX(), getY());

        super.draw( batch, parentAlpha );
    }
}
