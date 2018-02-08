package Entities.Creatures;

import Entities.Entity;
import Entities.Static.Tree;
import Entities.Weapons.ArrowProjectile;
import Entities.Weapons.Projectile;
import Entities.Weapons.SpearProjectile;
import TileGame.Handler;

public abstract class Creature extends Entity {

    public static final float DEFAULT_SPEED_PLAYER = 2.0f;
    public static final float DEFAULT_SPEED_ANIMAL = 1.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 45, DEFAULT_CREATURE_HEIGHT = 45;

    private float playerSpeed;
    private float animalSpeed;
    protected float xMove, yMove;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        playerSpeed = DEFAULT_SPEED_PLAYER;
        animalSpeed = DEFAULT_SPEED_ANIMAL;
        xMove = 1;
        yMove = 1;
    }

    public void move(){
        if (!collision(0, (int) yMove) && !EntityCollision(0, yMove)){
            y += yMove;
        }

        if (!collision((int) xMove, 0) && !EntityCollision(xMove, 0)){
            x += xMove;
        }
    }

    protected void shootSpear(int x, int y, double dir){

        Projectile spear = new SpearProjectile(handler, x, y, height, width);
        handler.getGameWorld().addProjectiles(spear);
    }

   protected void shootArrow(int x, int y, double dir){
        Projectile arrow = new ArrowProjectile(handler, x, y, height, width);
        handler.getGameWorld().addProjectiles(arrow);
    }

    protected boolean collision(int xMove, int yMove){
        boolean solid = false;
        for (int c = 0; c < 4; c++){
            int xtile = (int) (((x+xMove) + c % 2 * 20 + 15) / 64);
            int yTile = (int) (((y+yMove) + c / 2 * 20 + 31) / 64);
            if (handler.getGameWorld().getTile(xtile, yTile).isSolid()){
                solid = true;
            }
        }
        return solid;
    }

    //Getters and Setters

    public float getxMove() {
        return xMove;
    }

    public void setxMove(int xMove) {
        this.xMove = xMove;
    }

    public float getyMove() {
        return yMove;
    }

    public void setyMove(int yMove) {
        this.yMove = yMove;
    }

    public float getPlayerSpeed() {
        return playerSpeed;
    }

    public void setPlayerSpeed(float playerSpeed) {
        this.playerSpeed = playerSpeed;
    }

    public float getAnimalSpeed() {
        return animalSpeed;
    }

    public void setAnimalSpeed(float animalSpeed) {
        this.animalSpeed = animalSpeed;
    }
}
