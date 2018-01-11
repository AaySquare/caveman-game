package Entities;

import TileGame.Handler;
import gfx.Assets;

import java.awt.*;

public class SpearProjectile extends Projectile {


    public SpearProjectile(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        range = 200;
        speed = 5;
        damage = 20;
        rateOfFire = 15;

        nx = speed*Math.cos(angle);
        ny = speed*Math.sin(angle);
    }

    @Override
    public void update() {
        move();
    }

    protected void move(){
        x += nx;
        y += ny;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.spear, (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }
}
