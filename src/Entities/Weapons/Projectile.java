package Entities.Weapons;

import Entities.Entity;
import TileGame.Handler;

import java.awt.*;

public abstract class Projectile extends Entity {

    protected final int xOrigin, yOrigin;
    public static double angle;
    protected double xMove, yMove;
    protected double speed, range;
    protected int damage;

    public Projectile(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        xOrigin = (int) x;
        yOrigin = (int) y;
        this.x = x+20;
        this.y = y+20;
        angle = direction;
    }

    public abstract void attack();
}
