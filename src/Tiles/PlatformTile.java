package Tiles;

import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class PlatformTile extends Tile {

    private Animation platformAnim;
    private int speedAnim = 150;

    public PlatformTile(int id) {
        super(Assets.platform, id);
        platformAnim = new Animation(speedAnim, Assets.platform);
    }

    @Override
    public void update() {
        platformAnim.update();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getAnimationFrame(), x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    private BufferedImage getAnimationFrame() {

        return platformAnim.getFrames();

    }
}
