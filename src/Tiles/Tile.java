package Tiles;

import gfx.Animation;
import gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;

public abstract class Tile {

    public static Tile[] tiles = new Tile[256];
    public static Tile grassTile = new GrassTile(0);
    public static Tile dirtTile = new DirtTile(1);
    public static Tile rockTile = new RockTile(2);
    public static Tile waterTile = new WaterTile(3);
    public static Tile waterfallTile = new WaterfallTile(4);
    public static Tile wateSplashTile = new WaterSplashTile(5);
    public static Tile platformTile = new PlatformTile(6);
    public static Tile shadowTile = new ShadowOnWaterTile(7);
    public static Tile caveTile = new CaveTile(8);
    public static final int TILE_WIDTH = 64, TILE_HEIGHT = 64;

    protected BufferedImage[] texture;
    protected final int id;

    public Tile(BufferedImage[] texture, int id){
        this.texture = texture;
        this.id = id;
        tiles[id] = this;
    }

    public abstract void update();
    public abstract void render(Graphics g, int x, int y);

    public Boolean isSolid(){
        return false;
    }

    public int getId() {
        return id;
    }
}
