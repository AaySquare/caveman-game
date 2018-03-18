package Tiles;

import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ShadowOnWaterTile extends Tile {

    private Animation shadowAnim;
    private int speedAnim = 1000;

    public ShadowOnWaterTile(int id) {
        super(Assets.waterShadow, id);
        shadowAnim = new Animation(speedAnim, Assets.waterShadow);
    }

    @Override
    public void update() {
        shadowAnim.update();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getAnimationFrame(), x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    private BufferedImage getAnimationFrame() {

        return shadowAnim.getFrames();

    }

    @Override
    public Boolean isSolid(){
        return true;
    }
}
