package Inventory.Items;

import TileGame.Handler;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class ItemManager {

    private Handler handler;
    private ArrayList<Item> items;

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
                it.remove();
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
