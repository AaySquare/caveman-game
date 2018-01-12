package Entities.Weapons;

import Entities.Entity;
import TileGame.Handler;

public abstract class Projectile extends Entity {

    protected final int xOrigin, yOrigin;
    protected double angle;
    protected double nx, ny;
    protected double distance;
    protected double speed, range, damage;

    public Projectile(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        xOrigin = (int) x;
        yOrigin = (int) y;
        this.x = x+20;
        this.y = y+20;
        angle = direction;
    }
}
