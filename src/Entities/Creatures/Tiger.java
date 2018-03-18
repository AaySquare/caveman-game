package Entities.Creatures;

import Entities.Entity;
import Entities.Static.Tree;
import Inventory.Items.Item;
import TileGame.Handler;
import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Random;

public class Tiger extends Creature {

    private Animation downAnim, upAnim, leftAnim, rightAnim;
    private BufferedImage curPosition = Assets.tiger_left[1];
    private int speedAnim = 150;
    private int time = 0;
    private Random random = new Random();

    public static Boolean tigerDead = false;

    private long lastAttackTimer, attackCooldown = 850, attackTimer = attackCooldown;

    public Tiger(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.x = x;
        this.y = y;
        downAnim = new Animation(speedAnim, Assets.tiger_down);
        upAnim = new Animation(speedAnim, Assets.tiger_up);
        leftAnim = new Animation(speedAnim, Assets.tiger_left);
        rightAnim = new Animation(speedAnim, Assets.tiger_right);

        collider.x = 6;
        collider.y = 25;
        collider.width = 32;
        collider.height = 16;
    }

    private void chase(){

        List<Player> players = handler.getGameWorld().getEntityManager().getPlayers(this, 250);

        if (players.size() > 0){
            xMove = 0;
            yMove = 0;

            Player player = players.get(0);
            if (x < player.getX()){
                xMove = getTigerSpeed();
            }
            if (x > player.getX()){
                xMove = -getTigerSpeed();
            }
            if (y < player.getY()){
                yMove = getTigerSpeed();
            }
            if (y > player.getY()){
                yMove = -getTigerSpeed();
            }

        }
        else if (players.size() == 0){
            if (collision((int) xMove, 0) || EntityCollision(xMove, 0)){
                xMove = -xMove;
            }

            if (collision(0, (int) yMove) || EntityCollision(0, yMove)){
                yMove = -yMove;
            }

            time++;
            if (time % (random.nextInt(120) + 90) == 0){

                xMove = random.nextInt(3) - getTigerSpeed();
                yMove = random.nextInt(3) - getTigerSpeed();
                if (random.nextInt(3) == 0){
                    xMove = 0;
                    yMove = 0;
                }
            }
        }
        move();
    }

    private void attackPlayer(){
        attackTimer += System.currentTimeMillis()  - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown){
            return;
        }

        Rectangle collisionBounds = getCollisionBounds(0, 0);
        Rectangle mRect = new Rectangle();
        int pRectSize = 70;
        mRect.width = pRectSize;
        mRect.height = pRectSize;
        mRect.x = collisionBounds.x-10;
        mRect.y = collisionBounds.y-10;

        attackTimer = 0;

        for(Entity e : handler.getGameWorld().getEntityManager().getEntities()){
            if(e.equals(this))
                continue;

            if(e.getCollisionBounds(0f, 0f).intersects(mRect) && e instanceof Player) {
                e.damagePlayer(20);
                return;
            }


        }
    }

    @Override
    public void update() {
        downAnim.update();
        upAnim.update();
        leftAnim.update();
        rightAnim.update();

        chase();
        attackPlayer();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, 5);
        g.setColor(Color.red);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()),
                (width * tigerHealth) / MAX_TIGER_HEALTH, 5);

        g.drawImage(getAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);

        /*g.setColor(Color.red);
        g.fillRect((int)(x + collider.x - handler.getGameCamera().getxOffset()), (int)(y + collider.y - handler.getGameCamera().getyOffset()),
                collider.width, collider.height);*/

    }

    private BufferedImage getAnimationFrame() {
        if (xMove < 0) {
            curPosition = Assets.tiger_left[1];
            return leftAnim.getFrames();
        } else if (xMove > 0) {
            curPosition = Assets.tiger_right[1];
            return rightAnim.getFrames();
        } else if (yMove < 0) {
            curPosition = Assets.tiger_up[1];
            return upAnim.getFrames();
        } else if (yMove > 0) {
            curPosition = Assets.tiger_down[1];
            return downAnim.getFrames();
        }
        return curPosition;
    }

    @Override
    public void die() {
        tigerDead = true;
    }
}
