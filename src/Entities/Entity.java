package Entities;

import TileGame.Handler;

import java.awt.*;

public abstract class Entity {

    protected Handler handler;
    protected float x, y;
    protected int dir;
    protected int width, height;
    protected Rectangle collider;

    public Entity(Handler handler, float x, float y, int width, int height){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        collider = new Rectangle(0, 0, width, height);
    }

    public abstract void update();
    public abstract void render(Graphics g);

    public boolean checkEntityCollisions(float xOffset, float yOffset){
        for(Entity e : handler.getGameWorld().getEntityManager().getEntities()){
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
                return true;
        }
        return false;
    }

    public Rectangle getCollisionBounds(float xOffset, float yOffset){
        return new Rectangle((int) (x + collider.x + xOffset), (int) (y + collider.y + yOffset), collider.width, collider.height);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
