package Tiles;

import Audio.AudioPlayer;
import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WaterfallTile extends Tile {

    private Animation waterFallAnim;
    private int speedAnim = 200;

    public WaterfallTile(int id) {
        super(Assets.waterfall, id);
        waterFallAnim = new Animation(speedAnim, Assets.waterfall);
    }

    @Override
    public void update() {
        waterFallAnim.update();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getAnimationFrame(), x, y, TILE_WIDTH, TILE_HEIGHT, null);

    }

    private BufferedImage getAnimationFrame() {

        return waterFallAnim.getFrames();

    }

    @Override
    public Boolean isSolid(){
        return true;
    }
}
