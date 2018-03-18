package Tiles;

import Audio.AudioPlayer;
import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WaterSplashTile extends Tile {

    private Animation waterSplashAnim;
    private int speedAnim = 200;

    private AudioPlayer waterSplashSFX;

    public WaterSplashTile(int id) {
        super(Assets.waterSplash, id);
        waterSplashSFX = new AudioPlayer("/SFX/waterfall.mp3");
        waterSplashAnim = new Animation(speedAnim, Assets.waterSplash);
    }

    @Override
    public void update() {

        waterSplashAnim.update();
    }

    @Override
    public void render(Graphics g, int x, int y) {
        g.drawImage(getAnimationFrame(), x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    private BufferedImage getAnimationFrame() {
        return waterSplashAnim.getFrames();
    }

    @Override
    public Boolean isSolid(){
        return true;
    }
}
