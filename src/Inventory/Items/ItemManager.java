package Inventory.Items;

import TileGame.Handler;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

import static Entities.Weapons.SpearProjectile.numberOfSpears;
import static Entities.Weapons.ArrowProjectile.numberOfArrows;
import static Inventory.ItemStack.amount;

public class ItemManager {

    private Handler handler;
    private ArrayList<Item> items;
    public static Boolean canCraft = false;

    public ItemManager(Handler handler){
        this.handler = handler;
        items = new ArrayList<>();
    }

    public void update(){
        Iterator<Item> it = items.iterator();
        while (it.hasNext()){
            Item item = it.next();
            item.update();
            if (item.isPickedUp()){
                if (item.getId() == 1){
                    if (handler.getGameWorld().getPlayer().playerHealth == handler.getGameWorld().getPlayer().MAX_PLAYER_HEALTH){

                    }
                    else {
                        handler.getGameWorld().getPlayer().playerHealth = handler.getGameWorld().getPlayer().playerHealth + 10;
                        it.remove();
                    }
                }
                if (item.getId() == 0){
                    canCraft = true;
                    it.remove();
                }
            }
        }
    }

    public void render(Graphics2D g){
        for (Item item : items){
            item.render(g);
        }
    }

    public void addItem(Item item){
        item.setHandler(handler);
        items.add(item);
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
