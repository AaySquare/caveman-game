package Entities;

import TileGame.Handler;
import gfx.Assets;

public abstract class Projectile extends Entity {

    protected double angle;
    protected double nx, ny;
    protected double speed, rateOfFire, range, damage;

    public Projectile(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        this.x = x+20;
        this.y = y+20;
        angle = dir;
    }

    protected void move(){

    }
}
