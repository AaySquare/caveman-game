package Entities.Weapons;

import TileGame.Handler;
import gfx.Assets;

import java.awt.*;

public class SpearProjectile extends Projectile {

    public static final int SPEAR_FIRE_RATE = 100;
    public static int numberOfSpears = 3;

    public SpearProjectile(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        range = 300;
        speed = 5;
        damage = 50;

        nx = speed*Math.cos(angle);
        ny = speed*Math.sin(angle);
    }

    protected void move(){
        x += nx;
        y += ny;

        if (distance() > range){
            remove();
        }
    }

    private double distance(){
        double dist = Math.sqrt(Math.abs((xOrigin-x)*(xOrigin-x)+(yOrigin-y)*(yOrigin-y)));
        return dist;
    }

    @Override
    public void update() {
        if (handler.getGameWorld().tileCollision(x, y, nx, ny, 32)){
            remove();
        }
        move();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.spear, (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }
}
