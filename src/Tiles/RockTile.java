package Tiles;

import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class RockTile extends Tile {

    private Animation rockAnim;
    private int speedAnim = 150;

    public RockTile(int id) {
        super(Assets.rock, id);
        rockAnim = new Animation(speedAnim, Assets.rock);
    }

    @Override
    public void update() {
        rockAnim.update();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getAnimationFrame(), x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    private BufferedImage getAnimationFrame() {

        return rockAnim.getFrames();

    }

    @Override
    public Boolean isSolid(){
        return true;
    }
}
