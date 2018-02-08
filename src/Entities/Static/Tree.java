package Entities.Static;

import Inventory.Items.Item;
import TileGame.Handler;
import Tiles.Tile;
import gfx.Assets;

import java.awt.*;

public class Tree extends StaticEntity {

    public Tree(Handler handler, float x, float y){
        super(handler, x, y, Tile.TILE_WIDTH*2, Tile.TILE_HEIGHT*3);

        collider.x = 37;
        collider.y = (int)(height/1.25f);
        collider.width = 47;
        collider.height = (int)(height - height /1.2f);
    }
    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
        /*g.setColor(Color.red);
        g.fillRect((int)(x + collider.x - handler.getGameCamera().getxOffset()), (int)(y + collider.y - handler.getGameCamera().getyOffset()),
                collider.width, collider.height);*/
    }

    @Override
    public void die() {
        handler.getGameWorld().getItemManager().addItem(Item.woodItem.createNew((int)x+50, (int)y+120));
    }


}
