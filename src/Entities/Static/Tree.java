package Entities.Static;

import Entities.StaticEntity;
import TileGame.Handler;
import Tiles.Tile;

import java.awt.*;

public class Tree extends StaticEntity {

    public Tree(Handler handler, float x, float y){
        super(handler, x, y, Tile.TILE_WIDTH, Tile.TILE_HEIGHT);
    }
    @Override
    public void update() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(gfx.Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
    }

}
