package Entities;

import Inputs.Mouse;
import TileGame.Handler;

import java.awt.*;

public abstract class Entity {

    public static final int DEFAULT_HEALTH = 10;
    protected int health;
    protected Handler handler;
    protected float x, y;
    protected double direction;
    protected int width, height;
    protected Rectangle collider;
    private boolean removed = false;

    public Entity(Handler handler, float x, float y, int width, int height){
        this.handler = handler;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        health = DEFAULT_HEALTH;
        double dx = Mouse.getMouseX() - handler.getWidth()/2;
        double dy = Mouse.getMouseY() - handler.getHeight()/2;
        direction = Math.atan2(dy, dx);
        collider = new Rectangle(0, 0, width, height);
    }

    public abstract void update();
    public abstract void render(Graphics g);


    public void damage(int amount){
        health -= amount;
        System.out.println(health);
        if (health <= 0){
            remove();
        }
    }

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

    public void remove(){
        removed = true;
    }

    public boolean isRemoved(){
        return removed;
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
