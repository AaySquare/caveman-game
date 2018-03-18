package Entities.Weapons;

import Entities.Creatures.Fox;
import Entities.Creatures.Tiger;
import Entities.Entity;
import Entities.Static.Tree;
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

        xMove = speed*Math.cos(angle);
        yMove = speed*Math.sin(angle);

        collider.x = 5;
        collider.y = 0;
        collider.width = 32;
        collider.height = 2;
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

    public void attack(){
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
            if(e.getCollisionBounds(0f, 0f).intersects(mRect) && e instanceof Fox) {
                e.damageFox(damage);
                die();
                return;
            }
            if(e.getCollisionBounds(0f, 0f).intersects(mRect) && e instanceof Tiger) {
                e.damageTiger(damage);
                die();
                return;
            }
        }
    }

    @Override
    public void update() {
        move();
        attack();
    }

    @Override
    public void render(Graphics2D g) {
        g.rotate(angle, x-handler.getGameCamera().getxOffset(), y-handler.getGameCamera().getyOffset());
        g.drawImage(Assets.arrow, (int) (x - handler.getGameCamera().getxOffset()) + 5, (int) (y - handler.getGameCamera().getyOffset()) - 5, width/2, height/2, null);
        g.rotate(-angle, x-handler.getGameCamera().getxOffset(), y-handler.getGameCamera().getyOffset());
        /*g.setColor(Color.red);
        g.fillRect((int)(x + collider.x - handler.getGameCamera().getxOffset()), (int)(y + collider.y - handler.getGameCamera().getyOffset()),
                collider.width, collider.height);*/
    }

    @Override
    public void die() {
        remove();
    }
}
