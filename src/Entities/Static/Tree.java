package Entities.Static;

import Inventory.Items.Item;
import TileGame.Handler;
import Tiles.Tile;
import gfx.Assets;

import java.awt.*;

public class Tree extends StaticEntity {

    public Tree(Handler handler, float x, float y){
        super(handler, x, y, Tile.TILE_WIDTH*2, Tile.TILE_HEIGHT*3);

        collider.x = 36;
        collider.y = 150;
        collider.width = 47;
        collider.height = 37;
    }
    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        /*g.setColor(Color.black);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()+5), width, 5);
        g.setColor(Color.green);
        g.fillRect((int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()+5),
                (width * treeHealth) / MAX_TREE_HEALTH, 5);*/
        g.drawImage(Assets.tree, (int) (x - handler.getGameCamera().getxOffset()), (int)(y - handler.getGameCamera().getyOffset()), width, height, null);
       /* g.setColor(Color.red);
        g.fillRect((int)(x + collider.x - handler.getGameCamera().getxOffset()), (int)(y + collider.y - handler.getGameCamera().getyOffset()),
                collider.width, collider.height);*/
    }

    @Override
    public void die() {
        handler.getGameWorld().getItemManager().addItem(Item.woodItem.createNew((int)x+50, (int)y+120));
    }


}
