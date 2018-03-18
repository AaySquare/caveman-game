package Entities.Creatures;

import Inventory.Items.Item;
import TileGame.Handler;
import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Fox extends Creature {

    private Animation downAnim, upAnim, leftAnim, rightAnim;
    private BufferedImage curPosition = Assets.fox_left[1];
    private int speedAnim = 150;
    private int time;
    private Random random = new Random();

    public Fox(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        this.x = x;
        this.y = y;
        downAnim = new Animation(speedAnim, Assets.fox_down);
        upAnim = new Animation(speedAnim, Assets.fox_up);
        leftAnim = new Animation(speedAnim, Assets.fox_left);
        rightAnim = new Animation(speedAnim, Assets.fox_right);

        collider.x = 6;
        collider.y = 25;
        collider.width = 32;
        collider.height = 16;
    }

    @Override
    public void update() {
        downAnim.update();
        upAnim.update();
        leftAnim.update();
        rightAnim.update();

        if (collision((int) xMove, 0) || EntityCollision(xMove, 0)){
            xMove = -xMove;
        }

        if (collision(0, (int) yMove) || EntityCollision(0, yMove)){
            yMove = -yMove;
        }

        time++;
        if (time % (random.nextInt(120) + 90) == 0){

            xMove = random.nextInt(3) - getFoxSpeed();
            yMove = random.nextInt(3) - getFoxSpeed();

            if (random.nextInt(3) == 0){
                xMove = 0;
                yMove = 0;
            }
        }
        move();
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, 5);
        g.setColor(Color.red);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()),
                (width * foxHealth) / MAX_FOX_HEALTH, 5);
        g.drawImage(getAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
        /*g.setColor(Color.red);
        g.fillRect((int)(x + collider.x - handler.getGameCamera().getxOffset()), (int)(y + collider.y - handler.getGameCamera().getyOffset()),
                collider.width, collider.height);*/

    }

    private BufferedImage getAnimationFrame() {
        if (xMove < 0) {
            curPosition = Assets.fox_left[1];
            return leftAnim.getFrames();
        } else if (xMove > 0) {
            curPosition = Assets.fox_right[1];
            return rightAnim.getFrames();
        } else if (yMove < 0) {
            curPosition = Assets.fox_up[1];
            return upAnim.getFrames();
        } else if (yMove > 0) {
            curPosition = Assets.fox_down[1];
            return downAnim.getFrames();
        }
        return curPosition;
    }

    @Override
    public void die() {
        handler.getGameWorld().getItemManager().addItem(Item.meatItem.createNew((int)x, (int)y));
    }
}
