package Entities.Creatures;

import Entities.Entity;
import Entities.Weapons.ArrowProjectile;
import Entities.Weapons.Projectile;
import Entities.Weapons.SpearProjectile;
import Inputs.Mouse;
import TileGame.Handler;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Entities.Weapons.SpearProjectile.numberOfSpears;
import static Entities.Weapons.ArrowProjectile.numberOfArrows;

public class Player extends Creature {

    private gfx.Animation downAnim, upAnim, leftAnim, rightAnim, leftAttackAnim, rightAttackAnim, leftBowAnim, rightBowAnim;
    private int speedAnim = 200;
    private BufferedImage curPosition = Assets.player_down[0];

    private long lastAttackTimer, attackCooldown = 300, attackTimer = attackCooldown;
    private int fireRate_spear = 0;
    private int fireRate_arrow = 0;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);
        downAnim = new gfx.Animation(speedAnim, gfx.Assets.player_down);
        upAnim = new gfx.Animation(speedAnim, gfx.Assets.player_up);
        leftAnim = new gfx.Animation(speedAnim, gfx.Assets.player_left);
        rightAnim = new gfx.Animation(speedAnim, gfx.Assets.player_right);
        leftAttackAnim = new gfx.Animation(speedAnim, Assets.player_attack_left);
        rightAttackAnim = new gfx.Animation(speedAnim, Assets.player_attack_right);
        leftBowAnim = new gfx.Animation(speedAnim, Assets.bow_arrow_left);
        rightBowAnim = new gfx.Animation(speedAnim, Assets.bow_arrow_right);

        collider.x = 16;
        collider.y = 32;
        collider.width = 16;
        collider.height = 16;

        fireRate_spear = SpearProjectile.SPEAR_FIRE_RATE;
        fireRate_arrow = ArrowProjectile.ARROW_FIRE_RATE;
    }

    @Override
    public void update() {
        if (fireRate_spear > 0){
            fireRate_spear--;
        }

        if (fireRate_arrow > 0){
            fireRate_arrow--;
        }

        downAnim.update();
        upAnim.update();
        leftAnim.update();
        rightAnim.update();
        leftAttackAnim.update();
        rightAttackAnim.update();
        leftBowAnim.update();
        rightBowAnim.update();
        getInput();
        move();
        handler.getGameCamera().focusOnEntity(this);
        clear();
        //Attack
        updateMelee();
        updateShoot();

    }

    private void clear(){
        for (int i = 0; i < handler.getGameWorld().getEntityManager().getProjectiles().size(); i++){
            Projectile p = handler.getGameWorld().getEntityManager().getProjectiles().get(i);
            if (p.isRemoved()){
                handler.getGameWorld().getEntityManager().getProjectiles().remove(i);
            }
        }
    }

    private void updateMelee(){
        attackTimer += System.currentTimeMillis()  - lastAttackTimer;
        lastAttackTimer = System.currentTimeMillis();
        if (attackTimer < attackCooldown){
            return;
        }
        Rectangle mCollider = getCollisionBounds(0, 0);
        Rectangle mRect = new Rectangle();
        int mRectSize = 40;
        mRect.width = mRectSize;
        mRect.height = mRectSize;

        if (handler.getKeyController().meleeButton || Mouse.getMouseB() == 2){
            mRect.x = mCollider.x + mCollider.width / 2 - mRectSize / 2;
            mRect.y = mCollider.y - mRectSize;
        }
        else return;

        attackTimer = 0;

        for(Entity e : handler.getGameWorld().getEntityManager().getEntities()){
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(0f, 0f).intersects(mRect)) {
                e.damage(1);
                return;
            }
        }
    }

    private void updateShoot(){
        if (Mouse.getMouseB() == 1 && fireRate_spear <= 0 && numberOfSpears > 0){
            shootSpear((int)x, (int)y, direction);
            numberOfSpears--;
            fireRate_spear = SpearProjectile.SPEAR_FIRE_RATE;
        }
        else if (Mouse.getMouseB() == 3 && fireRate_arrow <= 0 && numberOfArrows > 0){
            shootArrow((int)x, (int)y, direction);
            numberOfArrows--;
            fireRate_arrow = ArrowProjectile.ARROW_FIRE_RATE;
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
            curPosition = Assets.player_idle_left[0];
            return leftAnim.getFrames();
        }
        else if (xMove > 0){
            curPosition = Assets.player_idle_right[0];
            return rightAnim.getFrames();
        }
        else if (yMove < 0){
            curPosition = Assets.player_up[0];
            return upAnim.getFrames();
        }
        else if (yMove > 0){
            curPosition = Assets.player_down[0];
            return downAnim.getFrames();
        }
        else if ((handler.getKeyController().meleeButton || Mouse.getMouseB() == 2) && (curPosition == Assets.player_idle_left[0] ||
                curPosition == Assets.player_down[0])){
            return leftAttackAnim.getFrames();

        }
        else if ((handler.getKeyController().meleeButton || Mouse.getMouseB() == 2) && (curPosition == Assets.player_idle_right[0] ||
                curPosition == Assets.player_up[0])){
            return rightAttackAnim.getFrames();
        }
        else if ((Mouse.getMouseB() == 3) && (curPosition == Assets.player_idle_right[0] ||
                curPosition == Assets.player_up[0])) {
            return rightBowAnim.getFrames();
        }
        else if ((Mouse.getMouseB() == 3) && (curPosition == Assets.player_idle_left[0] ||
                curPosition == Assets.player_down[0])) {
            return leftBowAnim.getFrames();
        }

        return curPosition;
    }
}
