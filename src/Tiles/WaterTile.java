package Tiles;

import TileGame.Handler;
import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WaterTile extends Tile {

    private Animation waterAnim;
    private int speedAnim = 1000;

    public WaterTile(int id) {
        super(Assets.water, id);
        waterAnim = new Animation(speedAnim, Assets.water);
    }

    public void update(){
        waterAnim.update();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getAnimationFrame(), x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT, null);
    }

    private BufferedImage getAnimationFrame() {

        return waterAnim.getFrames();

    }

    public Boolean isSolid(){
        return true;
    }
}
