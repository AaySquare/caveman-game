package Tiles;

import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GrassTile extends Tile {
    private Animation grassAnim;
    private int speedAnim = 150;

     public GrassTile(int id) {
        super(Assets.grass, id);
         grassAnim = new Animation(speedAnim, Assets.grass);
    }

    @Override
    public void update() {
        grassAnim.update();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getAnimationFrame(), x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }


    private BufferedImage getAnimationFrame() {

        return grassAnim.getFrames();

    }
}
