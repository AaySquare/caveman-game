package Entities.Creatures;

import Entities.Entity;
import Entities.Projectile;
import Entities.SpearProjectile;
import TileGame.Handler;

import java.util.ArrayList;
import java.util.List;

public abstract class Creature extends Entity {

    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 2.5f;
    public static final int DEFAULT_CREATURE_WIDTH = 50, DEFAULT_CREATURE_HEIGHT = 50;

    protected int health;
    private float speed;
    protected float xMove, yMove;
    protected int dir = -1;
    protected List<Projectile> projectiles = new ArrayList<Projectile>();

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
        xMove = 0;
        yMove = 0;
    }

    public void move(){
        if (!collision(0, (int) yMove) && !checkEntityCollisions(0, yMove)){
            y += yMove;
        }

        if (!collision((int) xMove, 0) && !checkEntityCollisions(xMove, 0)){
            x += xMove;
        }
    }

    protected void shoot(int x, int y, double dir){
        //dir *= 180 / Math.PI;
        //System.out.println("Angle: " + dir);
        Projectile p = new SpearProjectile(handler, x, y, height, width);
        projectiles.add(p);
        handler.getGameWorld().add(p);
    }

    protected boolean collision(int xMove, int yMove){
        boolean solid = false;
        for (int c = 0; c < 4; c++){
            int xtile = (int) (((x+xMove) + c % 2 * 40 + 9) / 64);
            int yTile = (int) (((y+yMove) + c / 2 * 25 + 30) / 64);
            if (handler.getGameWorld().getTile(xtile, yTile).isSolid()){
                solid = true;
            }
        }
        return solid;
    }

    //Getters and Setters

    public float getxMove() {
        return xMove;
    }

    public void setxMove(int xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(int yMove) {
        this.yMove = yMove;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
