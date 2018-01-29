package Entities.Weapons;

import TileGame.Handler;
import gfx.Assets;

import java.awt.*;

public class ArrowProjectile extends Projectile {

    public static final int ARROW_FIRE_RATE = 50;
    public static int numberOfArrows = 10;

    public ArrowProjectile(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        range = 450;
        speed = 10;
        damage = 20;

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
        g.drawImage(Assets.arrow, (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), (width/2)+12, (height/2)+12, null);
    }
}
