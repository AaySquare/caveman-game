package Tiles;

import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DirtTile extends Tile {
    private Animation dirtAnim;
    private int speedAnim = 150;

    public DirtTile(int id) {
        super(Assets.dirt, id);
        dirtAnim = new Animation(speedAnim, Assets.dirt);
    }

    @Override
    public void update() {
        dirtAnim.update();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getAnimationFrame(), x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }


    private BufferedImage getAnimationFrame() {

        return dirtAnim.getFrames();

    }

}
