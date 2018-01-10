package Entities;

import gfx.Assets;

import java.awt.*;

public class SpearProjectile extends Projectile {

    public SpearProjectile(int x, int y, int dir){
        super(x, y, dir);
        range = 200;
        speed = 4;
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
        y += nx;
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(gfx.Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }
}
