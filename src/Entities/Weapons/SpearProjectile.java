package Entities.Weapons;

import Entities.Creatures.AnimalTest;
import Entities.Entity;
import Entities.Static.Tree;
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

        xMove = speed*Math.cos(angle);
        yMove = speed*Math.sin(angle);
    }

    @Override
    public void attack() {
        Rectangle collisionBounds = getCollisionBounds(0, 0);
        Rectangle mRect = new Rectangle();
        int pRectSize = 10;
        mRect.width = pRectSize;
        mRect.height = pRectSize;
        mRect.x = collisionBounds.x;
        mRect.y = collisionBounds.y;

        for(Entity e : handler.getGameWorld().getEntityManager().getEntities()){
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(0f, 0f).intersects(mRect) && e instanceof Tree) {
                die();
                return;
            }
            if(e.getCollisionBounds(0f, 0f).intersects(mRect) && e instanceof AnimalTest) {
                e.damageFox(damage);
                die();
                return;
            }
        }
    }

    protected void move(){
        x += xMove;
        y += yMove;

        if (distance() > range || handler.getGameWorld().tileCollision(x, y, xMove, yMove, 32)){
            die();
        }
    }

    private double distance(){
        double dist = Math.sqrt(Math.abs((xOrigin-x)*(xOrigin-x)+(yOrigin-y)*(yOrigin-y)));
        return dist;
    }

    @Override
    public void update() {
        move();
        attack();
    }

    @Override
    public void render(Graphics2D g) {
        g.rotate(angle, x-handler.getGameCamera().getxOffset(), y-handler.getGameCamera().getyOffset());
        g.drawImage(Assets.spear, (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset())-10, width, height/2, null);
        g.rotate(-angle, x-handler.getGameCamera().getxOffset(), y-handler.getGameCamera().getyOffset());
    }

    @Override
    public void die() {
        remove();
    }
}
