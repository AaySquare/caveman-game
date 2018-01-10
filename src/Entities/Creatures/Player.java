package Entities.Creatures;

import Entities.Creatures.Creature;
import Inputs.Mouse;
import TileGame.Game;
import TileGame.Handler;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    private gfx.Animation downAnim, upAnim, leftAnim, rightAnim;
    private int speedAnim = 200;
    private BufferedImage curPosition = gfx.Assets.player_idle[0];

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        downAnim = new gfx.Animation(speedAnim, gfx.Assets.player_down);
        upAnim = new gfx.Animation(speedAnim, gfx.Assets.player_up);
        leftAnim = new gfx.Animation(speedAnim, gfx.Assets.player_left);
        rightAnim = new gfx.Animation(speedAnim, gfx.Assets.player_right);

        collider.x = 16;
        collider.y = 32;
        collider.width = 32;
        collider.height = 16;
    }

    @Override
    public void update() {
        downAnim.update();
        upAnim.update();
        leftAnim.update();
        rightAnim.update();
        getInput();
        move();
        handler.getGameCamera().focusOnEntity(this);
        updateShoot();
    }

    private void updateShoot(){
        if (Mouse.getMouseB() == 1){
            double dx = Mouse.getMouseX() - handler.getWidth()/2;
            double dy = Mouse.getMouseY() - handler.getHeight()/2;
            double dir = Math.atan2(dy, dx);
            shoot((int)x, (int)y, dir);
        }
    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

        if (handler.getKeyController().up){
            yMove = -getSpeed();
        }
        if (handler.getKeyController().down){
            yMove = getSpeed();
        }
        if (handler.getKeyController().left){
            xMove = -getSpeed();
        }
        if (handler.getKeyController().right){
            xMove = getSpeed();
        }

        if (handler.getKeyController().right && handler.getKeyController().up){
            xMove = (getSpeed()/2);
        }

        if (handler.getKeyController().left && handler.getKeyController().down){
            yMove = (getSpeed()/2);
        }

        if (handler.getKeyController().right && handler.getKeyController().down){
            yMove = (getSpeed()/2);
        }

        if (handler.getKeyController().left && handler.getKeyController().up){
            xMove = (-getSpeed()/2);
        }

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getAnimationFrame(),(int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
    }

    private BufferedImage getAnimationFrame(){
        if (xMove < 0){
            curPosition = Assets.player_left[0];
            return leftAnim.getFrames();
        }
        else if (xMove > 0){
            curPosition = Assets.player_right[0];
            return rightAnim.getFrames();
        }
        else if (yMove < 0){
            curPosition = gfx.Assets.player_right[0];
            return upAnim.getFrames();
        }
        else if (yMove > 0){
            curPosition = gfx.Assets.player_left[0];
            return downAnim.getFrames();
        }
        return curPosition;
    }
}
