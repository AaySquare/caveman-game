package Entities.Creatures;

import Entities.Entity;
import Entities.Weapons.ArrowProjectile;
import Entities.Weapons.Projectile;
import Entities.Weapons.SpearProjectile;
import TileGame.Handler;

public abstract class Creature extends Entity {

    public static final float DEFAULT_SPEED = 2.5f;
    public static final int DEFAULT_CREATURE_WIDTH = 50, DEFAULT_CREATURE_HEIGHT = 45;

    private float speed;
    protected float xMove, yMove;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
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

    protected void shootSpear(int x, int y, double dir){
        Projectile spear = new SpearProjectile(handler, x, y, height, width);
        handler.getGameWorld().addProjectiles(spear);
    }

   protected void shootArrow(int x, int y, double dir){
        Projectile arrow = new ArrowProjectile(handler, x, y, height, width);
        handler.getGameWorld().addProjectiles(arrow);
    }

    protected boolean collision(int xMove, int yMove){
        boolean solid = false;
        for (int c = 0; c < 4; c++){
            int xtile = (int) (((x+xMove) + c % 2 * 20 + 15) / 64);
            int yTile = (int) (((y+yMove) + c / 2 * 20 + 31) / 64);
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
