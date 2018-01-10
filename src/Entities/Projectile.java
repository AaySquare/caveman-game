package Entities;

import TileGame.Handler;
import gfx.Assets;

public abstract class Projectile extends Entity {

    protected final int xOrigin, yOrigin;
    protected double angle;
    protected double nx, ny;
    protected double speed, rateOfFire, range, damage;

    public Projectile(int x, int y, int dir) {
        super(x, y, dir);
        xOrigin = x;
        yOrigin = y;
        angle = dir;
        this.x = x;
        this.y = y;
    }

    protected void move(){

    }
}
