package Entities.Creatures;

import Audio.AudioPlayer;
import Entities.Entity;
import Entities.Static.Tree;
import Entities.Weapons.ArrowProjectile;
import Entities.Weapons.Projectile;
import Entities.Weapons.SpearProjectile;
import Inputs.Mouse;
import Inventory.Inventory;
import TileGame.Game;
import TileGame.Handler;
import UI.UIManager;
import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

import static Entities.Creatures.Tiger.tigerDead;
import static Entities.Weapons.SpearProjectile.numberOfSpears;
import static Entities.Weapons.ArrowProjectile.numberOfArrows;

public class Player extends Creature {

    public static final int DEFAULT_PLAYER_WIDTH = 50, DEFAULT_PLAYER_HEIGHT = 50;
    private Animation downAnim, upAnim, leftAnim, rightAnim, leftAttackAnim, rightAttackAnim, leftBowAnim, rightBowAnim, rightThrow, leftThrow;
    private int speedAnim = 170;
    private BufferedImage curPosition = Assets.player_down[0];
    public static Boolean playerDead = false;

    private long lastAttackTimer, attackCooldown = 750, attackTimer = attackCooldown;
    private int     meleeDamage = 5;
    private int fireRate_spear;
    private int fireRate_arrow;

    public final int MAX_PLAYER_HEALTH = 100;
    public int playerHealth = MAX_PLAYER_HEALTH;

    private Inventory inventory;
    private AudioPlayer meleeSound;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, DEFAULT_PLAYER_WIDTH, DEFAULT_PLAYER_HEIGHT);

        downAnim = new Animation(speedAnim, Assets.player_down);
        upAnim = new Animation(speedAnim, Assets.player_up);
        leftAnim = new Animation(speedAnim, Assets.player_left);
        rightAnim = new Animation(speedAnim, Assets.player_right);
        leftAttackAnim = new Animation(speedAnim, Assets.player_attack_left);
        rightAttackAnim = new Animation(speedAnim, Assets.player_attack_right);
        leftBowAnim = new Animation(speedAnim, Assets.bow_arrow_left);
        rightBowAnim = new Animation(speedAnim, Assets.bow_arrow_right);
        leftThrow = new Animation(speedAnim, Assets.player_throw_left);
        rightThrow = new Animation(speedAnim, Assets.player_throw_right);

        collider.x = 12;
        collider.y = 33;
        collider.width = 16;
        collider.height = 19;

        fireRate_spear = SpearProjectile.SPEAR_FIRE_RATE;
        fireRate_arrow = ArrowProjectile.ARROW_FIRE_RATE;

        inventory = new Inventory(handler, 120, 120);
        meleeSound = new AudioPlayer("/SFX/melee_attack.mp3");
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

        meleeAttack();

        updateShoot();
        inventory.update();
    }

    private void clear(){
        for (int i = 0; i < handler.getGameWorld().getEntityManager().getProjectiles().size(); i++){
            Projectile p = handler.getGameWorld().getEntityManager().getProjectiles().get(i);
            if (p.isRemoved()){
                handler.getGameWorld().getEntityManager().getProjectiles().remove(i);
            }
        }
    }

    private void meleeAttack(){
        if (Inventory.isOpen || tigerDead || playerDead) {
            return;
        }

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
            meleeSound.play();
            mRect.x = mCollider.x + mCollider.width / 2 - mRectSize / 2;
            mRect.y = mCollider.y - mRectSize;
        }
        else return;

        attackTimer = 0;

        for(Entity e : handler.getGameWorld().getEntityManager().getEntities()){
            if(e.equals(this))
                continue;
            if(e.getCollisionBounds(0f, 0f).intersects(mRect) && e instanceof Tree) {
                e.damageTree(meleeDamage);
                return;
            }
            if(e.getCollisionBounds(0f, 0f).intersects(mRect) && e instanceof Fox) {
                e.damageFox(meleeDamage);
                return;
            }
            if(e.getCollisionBounds(0f, 0f).intersects(mRect) && e instanceof Tiger) {
                e.damageTiger(meleeDamage);
                return;
            }
        }
    }

    private void updateShoot(){
        if (Inventory.isOpen || tigerDead || playerDead) {
            return;
        }

        if (Mouse.getMouseB() == 1 && fireRate_spear <= 0 && numberOfSpears > 0){
            shootSpear((int)x, (int)y, direction);
            numberOfSpears--;
            fireRate_spear = SpearProjectile.SPEAR_FIRE_RATE;
        }
        else if (Mouse.getMouseB() == 3 && fireRate_arrow <= 0 && numberOfArrows > 0 && xMove == 0 && yMove == 0){
            shootArrow((int)x, (int)y, direction);
            numberOfArrows--;
            fireRate_arrow = ArrowProjectile.ARROW_FIRE_RATE;
        }
    }

    private void getInput(){
        xMove = 0;
        yMove = 0;

       if (Inventory.isOpen || tigerDead || playerDead) {
           return;
       }

        if (handler.getKeyController().up){
            yMove = -getPlayerSpeed();
        }
        if (handler.getKeyController().down){
            yMove = getPlayerSpeed();
        }
        if (handler.getKeyController().left){
            xMove = -getPlayerSpeed();
        }
        if (handler.getKeyController().right){
            xMove = getPlayerSpeed();
        }

        if (handler.getKeyController().right && handler.getKeyController().up){
            xMove = (getPlayerSpeed()/2);
        }

        if (handler.getKeyController().left && handler.getKeyController().down){
            yMove = (getPlayerSpeed()/2);
        }

        if (handler.getKeyController().right && handler.getKeyController().down){
            yMove = (getPlayerSpeed()/2);
        }

        if (handler.getKeyController().left && handler.getKeyController().up){
            xMove = (-getPlayerSpeed()/2);
        }

    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(Color.black);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset())-6, width, 5);
        g.setColor(Color.green);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset())-6,
                (width * playerHealth) / MAX_PLAYER_HEALTH, 5);

        g.drawImage(getAnimationFrame(),(int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);

        /*g.setColor(Color.red);
        g.fillRect((int)(x + collider.x - handler.getGameCamera().getxOffset()), (int)(y + collider.y - handler.getGameCamera().getyOffset()),
                collider.width, collider.height);*/
    }

    public void postRender(Graphics2D g){
        inventory.render(g);
    }

    @Override
    public void die() {
        playerDead = true;
    }

    public Inventory getInventory() {
        return inventory;
    }

    private BufferedImage getAnimationFrame(){

        if (xMove < 0){
            curPosition = Assets.player_idle_left[0];
            if ((handler.getKeyController().meleeButton || Mouse.getMouseB() == 2)){
                return leftAttackAnim.getFrames();
            }
            return leftAnim.getFrames();
        }
        else if (xMove > 0){
            curPosition = Assets.player_idle_right[0];
            if ((handler.getKeyController().meleeButton || Mouse.getMouseB() == 2)){
                return rightAttackAnim.getFrames();
            }
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
        else if ((Mouse.getMouseB() == 3) && (curPosition == Assets.player_idle_right[0] || curPosition == Assets.player_up[0])) {
            curPosition = Assets.player_idle_right[0];
            return rightBowAnim.getFrames();
        }
        else if ((Mouse.getMouseB() == 3) && (curPosition == Assets.player_idle_left[0] || curPosition == Assets.player_down[0])) {
            curPosition = Assets.player_idle_left[0];
            return leftBowAnim.getFrames();
        }

        if (numberOfSpears > 0){
            if ((Mouse.getMouseB() == 1) && (curPosition == Assets.player_idle_right[0] ||
                    curPosition == Assets.player_up[0])) {
                return rightThrow.getFrames();
            }
            if ((Mouse.getMouseB() == 1) && (curPosition == Assets.player_idle_left[0] ||
                    curPosition == Assets.player_down[0])) {
                return leftThrow.getFrames();
            }
        }

        return curPosition;
    }
}
